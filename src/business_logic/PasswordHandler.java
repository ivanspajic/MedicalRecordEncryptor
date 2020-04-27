package business_logic;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

public class PasswordHandler {
    private static final String PROVIDER = "BC";
    private static final String SECURE_RANDOM_ALGORITHM = "DEFAULT";
    private static final String KEY_FACTORY_ALGORITHM = "PBKDF2WITHHMACSHA256";

    private static final int SALT_SIZE = 16;
    private static final int KEY_BITS_OUTPUT = 256;

    /**
     * This method generates a hashed and salted password, based on the
     * provided cleartext password and the iteration count from the user.
     * @param password
     * @param salt
     * @param iterationCount
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static byte[] generateHashedAndSaltedPassword(String password, byte[] salt, int iterationCount) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, KEY_BITS_OUTPUT);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_ALGORITHM, PROVIDER);

        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

        return secretKey.getEncoded();
    }

    /**
     * This method generates a random salt string.
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generateSalt() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM, PROVIDER);

        byte[] salt = new byte[SALT_SIZE];
        secureRandom.nextBytes(salt);

        return salt;
    }
}
