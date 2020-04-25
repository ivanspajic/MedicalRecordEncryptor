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

    private static final int SALT_SIZE = 32;
    private static final int KEY_BITS_OUTPUT = 256;

    public static String generateHashedAndSaltedPassword(String password, String salt, int iterationCount) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterationCount, KEY_BITS_OUTPUT);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_ALGORITHM, PROVIDER);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

        return new String(secretKey.getEncoded());
    }

    public static String generateSalt() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM, PROVIDER);

        byte[] hexSalt = new byte[SALT_SIZE];
        secureRandom.nextBytes(hexSalt);

        return new String(hexSalt);
    }
}
