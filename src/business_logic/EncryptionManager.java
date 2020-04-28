package business_logic;

import models.CustomDialogException;
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

    /**
     * Handles the overseeing encryption process, step by step.
     *
     * It begins by generating a salt and hashing a password from the provided
     * user data. It then checks that the chosen file contents can be read from the
     * disk, and gets the encrypted file and a signature of the original. The information
     * about the file itself, including its signature as well as the user's hashed and
     * salted password are stored in a metadata file. Lastly, if the user chose to delete
     * the original, that file is promptly deleted.
     * @param formData
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws CustomDialogException
     */
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

    /**
     * Handles the overseeing decryption process, step by step.
     *
     * It begins by gathering all the information from the existing metadata file.
     * If no such file is found, it throws a respective exception. Otherwise, it
     * gains a generated form containing the encrypted file's information. It then
     * continues to hash and salt the user's password and compares it to the saved
     * one. If there is a mismatch, a respective exception is thrown. Otherwise, it
     * continues to read the encrypted file's contents and if it is not missing, it
     * decrypts it with the information gathered, and checks its signature. If there
     * is a mismatch, the user is given a warning about possible file tampering. Lastly,
     * The decrypted file is written to the chosen disk location, and if the user wished
     * to delete the encrypted files, they are deleted promptly.
     * @param formData
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws CustomDialogException
     */
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

    /**
     * Gets either the filename or the file extension from the provided combination.
     * @param path
     * @param extension
     * @return
     */
    private String getSplitNameExtensionFromPath(String path, boolean extension) {
        String[] split = path.split(Pattern.quote(EXTENSION_SEPARATOR));

        if (extension){
            return EXTENSION_SEPARATOR + split[split.length - 1];
        }

        return split[split.length - 2];
    }

    /**
     * Gets the filename and its extension from a provided full path.
     * @param path
     * @return
     */
    private String getFilenameFromPath(String path) {
        String[] split = path.split(Pattern.quote(PATH_SEPARATOR));

        return getSplitNameExtensionFromPath(split[split.length - 1], false);
    }
}