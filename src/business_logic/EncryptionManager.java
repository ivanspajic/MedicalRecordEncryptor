package business_logic;

import models.FormData;
import data_access.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class EncryptionManager {
    private final String PATH_SEPARATOR = "\\";
    private final String EXTENSION_SEPARATOR = ".";

    public void encrypt(FormData formData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        byte[] salt = PasswordHandler.generateSalt();
        formData.setSalt(salt);

        byte[] hashedSaltedPassword = PasswordHandler.generateHashedAndSaltedPassword(formData.getClearTextPassword(), formData.getSalt(), formData.getIterationCount());
        formData.setHashedSaltedPassword(hashedSaltedPassword);

        byte[] fileContents = FileUtils.readAllBytes(formData.getSourceFileLocation());

        byte[] encryptedFile = FileEncryptionHandler.encryptFile(fileContents, formData.getHashedSaltedPassword(), formData.getIterationCount());
        byte[] hashedFile = FileEncryptionHandler.hashFile(fileContents);

        formData.setSignature(hashedFile);

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