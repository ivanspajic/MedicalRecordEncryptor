package business_logic;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class FileEncryptionHandler {
    public static final String FILE_EXTENSION = ".aes";

    private static final String PROVIDER = "BC";
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String SECURE_RANDOM_ALGORITHM = "DEFAULT";
    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-256";
    private static final String CIPHER_ALGORITHM = ENCRYPTION_ALGORITHM + "/CBC/PKCS5Padding";

    private static final int IV_SIZE = 16;

    public static byte[] encryptDecryptFile(byte[] fileContents, byte[] hashedSaltedPassword, byte[] salt, int iterationCount, boolean encrypt) throws NoSuchProviderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
        SecretKeySpec keySpec = new SecretKeySpec(hashedSaltedPassword, ENCRYPTION_ALGORITHM);

        int cipherMode;
        if (encrypt) {
            cipherMode = 1;
        } else {
            cipherMode = 2;
        }

        cipher.init(cipherMode, keySpec, new IvParameterSpec(salt));

        byte[] newFileContents = cipher.doFinal(fileContents);
        for (int i = 0; i < iterationCount - 1; i++) {
            newFileContents = cipher.doFinal(newFileContents);
        }

        return newFileContents;
    }

    public static byte[] hashFile(byte[] fileContents) throws NoSuchProviderException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM, PROVIDER);

        messageDigest.update(fileContents);

        return messageDigest.digest(fileContents);
    }

    private static byte[] generateIv() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM, PROVIDER);

        byte[] hexIv = new byte[IV_SIZE];
        secureRandom.nextBytes(hexIv);

        return hexIv;
    }
}
