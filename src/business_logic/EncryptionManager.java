package business_logic;

import models.FormData;
import data_access.FileUtils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public class EncryptionManager {
    public void encrypt(FormData formData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String salt = PasswordHandler.generateSalt();
        String hashedSaltedPassword = PasswordHandler.generateHashedAndSaltedPassword(formData.getPassword(), salt, formData.getIterationCount());

        formData.setSalt(salt);
        formData.setPassword(hashedSaltedPassword);

        //handle file encryption and signing
        //store generated data into new metadata file
        //write the encrypted file on the disk location provided
    }
}