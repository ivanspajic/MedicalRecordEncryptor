package tests;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 *Testing our password handling logic.
 */
class PasswordHandlerTest {

    /**
     * We test if the algorithm's consistency. It should generate the same hash value when provided the same input.
     * If this test fails, we will (most likely) not be able to decrypt the file with the password given at encryption, so this is crucial.
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    void TestgenerateHashedAndSaltedPassword1() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = business_logic.PasswordHandler.generateSalt();
        byte[] result1 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("test", salt, 100);
        byte[] result2 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("test", salt, 100);
        Assert.assertTrue(Arrays.equals(result1, result2));
    }

    /**
     * We test the algorithm's security. Provided the same salt and iterationCount, but different passwords, we should always get different hash values.
     * If the test fails, that means multiple passwords can be used for decryption, which can compromise security.
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    void TestgenerateHashedAndSaltedPassword2() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = business_logic.PasswordHandler.generateSalt();
        byte[] result1 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("test", salt, 100);
        byte[] result2 = business_logic.PasswordHandler.generateHashedAndSaltedPassword("badpassword", salt, 100);
        Assert.assertTrue(!Arrays.equals(result1, result2));
    }


}