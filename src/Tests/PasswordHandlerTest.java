package tests;

import junit.framework.Assert;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

class PasswordHandlerTest {

    @org.junit.jupiter.api.Test
    void TestgenerateHashedAndSaltedPassword1() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = business_logic.PasswordHandler.generateSalt();
        byte[] result1 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("test", salt, 100);
        byte[] result2 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("test", salt, 100);
        Assert.assertTrue(Arrays.equals(result1, result2));
    }

    @org.junit.jupiter.api.Test
    void TestgenerateHashedAndSaltedPassword2() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = business_logic.PasswordHandler.generateSalt();
        byte[] result1 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("test", salt, 100);
        byte[] result2 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("badpassword", salt, 100);
        Assert.assertTrue(!Arrays.equals(result1, result2));
    }


}