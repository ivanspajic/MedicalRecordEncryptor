package business_logic;

import models.FormData;
import data_access.FileUtils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public class EncryptionManager {
    public void encrypt(FormData formData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String hashedSaltedPassword = PasswordHandler.getHashedAndSaltedPassword(formData.getPassword(), null, formData.getIterationCount());
        System.out.println(hashedSaltedPassword);
        //handle salt generation and hashing of password
        //store generated data into new metadata file
        //handle file encryption and signing
        //write the encrypted file on the disk location provided
    }
}