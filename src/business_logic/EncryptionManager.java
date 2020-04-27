package business_logic;

import models.FormData;
import data_access.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class EncryptionManager {
    private final String PATH_SEPARATOR = "\\";
    private final String EXTENSION_SEPARATOR = ".";

    public void encrypt(FormData formData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, CustomDialogException {
        byte[] salt = PasswordHandler.generateSalt();
        formData.setSalt(salt);

        byte[] hashedSaltedPassword = PasswordHandler.generateHashedAndSaltedPassword(formData.getClearTextPassword(), formData.getSalt(), formData.getIterationCount());
        formData.setHashedSaltedPassword(hashedSaltedPassword);

        byte[] fileContents = FileUtils.readAllBytes(formData.getSourceFileLocation());

        if (fileContents.length == 0){
            throw new CustomDialogException(CustomDialogException.FILE_CONTENTS_MISSING, null);
        }

        byte[] encryptedFile = FileEncryptionHandler.encryptDecryptFile(fileContents, formData.getHashedSaltedPassword(), formData.getSalt(), formData.getIterationCount(), true);
        byte[] fileSignature = FileEncryptionHandler.hashFile(fileContents);

        formData.setFileSignature(fileSignature);

        String fileExtension = getSplitNameExtensionFromPath(formData.getSourceFileLocation(), true);
        formData.setSourceFileExtension(fileExtension);

        String filename = getFilenameFromPath(formData.getSourceFileLocation());
        formData.setSourceFilename(filename);

        byte[] metadataContents = MetadataFileHandler.generateFileContents(formData);

        FileUtils.write(formData.getTargetDirectoryLocation() + PATH_SEPARATOR + formData.getSourceFilename() + FileEncryptionHandler.FILE_EXTENSION, encryptedFile);
        FileUtils.write(formData.getTargetDirectoryLocation() + PATH_SEPARATOR + formData.getSourceFilename() + MetadataFileHandler.FILE_EXTENSION, metadataContents);

        if (formData.getDeleteSourceFile()){
            FileUtils.deleteFile(formData.getSourceFileLocation());
        }
    }

    public void decrypt(FormData formData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, CustomDialogException {
        String metadataFilePath = getSplitNameExtensionFromPath(formData.getSourceFileLocation(), false);

        byte[] metadataContents = FileUtils.readAllBytes(metadataFilePath + MetadataFileHandler.FILE_EXTENSION);
        if (metadataContents.length == 0){
            throw new CustomDialogException(CustomDialogException.FILE_CONTENTS_MISSING, null);
        }

        FormData metaFormData = MetadataFileHandler.getFormDataFromMetadata(metadataContents);

        byte[] hashedSaltedPassword = PasswordHandler.generateHashedAndSaltedPassword(formData.getClearTextPassword(), metaFormData.getSalt(), metaFormData.getIterationCount());

        if (!MessageDigest.isEqual(metaFormData.getHashedSaltedPassword(), hashedSaltedPassword)) {
            throw new CustomDialogException(CustomDialogException.PASSWORD_INCORRECT, null);
        }

        byte[] fileContents = FileUtils.readAllBytes(formData.getSourceFileLocation());

        if (fileContents.length == 0){
            throw new CustomDialogException(CustomDialogException.FILE_CONTENTS_MISSING, null);
        }

        byte[] decryptedFile = FileEncryptionHandler.encryptDecryptFile(fileContents, metaFormData.getHashedSaltedPassword(), metaFormData.getSalt(), metaFormData.getIterationCount(), false);
        byte[] fileSignature = FileEncryptionHandler.hashFile(decryptedFile);

        if (!MessageDigest.isEqual(metaFormData.getFileSignature(), fileSignature)) {
            throw new CustomDialogException(CustomDialogException.SIGNATURE_MISMATCH, null);
        }

        FileUtils.write(formData.getTargetDirectoryLocation() + PATH_SEPARATOR + metaFormData.getSourceFilename() + metaFormData.getSourceFileExtension(), decryptedFile);

        if (formData.getDeleteSourceFile()){
            FileUtils.deleteFile(formData.getSourceFileLocation());
            FileUtils.deleteFile(metadataFilePath + MetadataFileHandler.FILE_EXTENSION);
        }
    }

    private String getSplitNameExtensionFromPath(String path, boolean extension) {
        String[] split = path.split(Pattern.quote(EXTENSION_SEPARATOR));

        if (extension){
            return EXTENSION_SEPARATOR + split[split.length - 1];
        }
        return split[split.length - 2];
    }

    private String getFilenameFromPath(String path) {
        String[] split = path.split(Pattern.quote(PATH_SEPARATOR));

        return getSplitNameExtensionFromPath(split[split.length - 1], false);
    }
}