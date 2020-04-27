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

    /**
     * Either encrypts or decrypts a file, based on the arguments provided.
     *
     * It begins by creating an instance of the cipher to be used, as well as the
     * corresponding SecretKeySpec that specifies the encryption algorithm. Next, it
     * checks the cipher mode it should use based on the provided arguments, and initializes
     * the cipher correspondingly. Afterward, the file is encrypted/decrypted by the number of
     * times equal to that of the iteration count provided.
     * @param fileContents
     * @param hashedSaltedPassword
     * @param salt
     * @param iterationCount
     * @param encrypt
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
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

    /**
     * This method hashes the file contents provided to create a signature.
     * @param fileContents
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] hashFile(byte[] fileContents) throws NoSuchProviderException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM, PROVIDER);

        messageDigest.update(fileContents);

        return messageDigest.digest(fileContents);
    }
}
