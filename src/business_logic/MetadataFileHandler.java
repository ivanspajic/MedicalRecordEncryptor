package business_logic;

import models.FormData;

import java.util.Base64;
import java.util.regex.Pattern;

public class MetadataFileHandler {
    public static final String FILE_EXTENSION = ".mremeta";

    private static final String LABEL_VALUE_SEPARATOR = ":";
    private static final String DATA_PAIR_SEPARATOR = "|";

    private static final String FILENAME_LABEL = "filename";
    private static final String FILE_EXTENSION_LABEL = "extension";
    private static final String SALT_LABEL = "salt";
    private static final String KEY_LABEL = "key";
    private static final String ITERATION_COUNT_LABEL = "iterations";
    private static final String SIGNATURE_LABEL = "signature";

    public static byte[] generateFileContents(FormData formData) {
        String hashedSaltedPassword = Base64.getEncoder().encodeToString(formData.getHashedSaltedPassword());
        String salt = Base64.getEncoder().encodeToString(formData.getSalt());
        String fileSignature = Base64.getEncoder().encodeToString(formData.getFileSignature());

        String file = FILENAME_LABEL + LABEL_VALUE_SEPARATOR + formData.getSourceFilename() + DATA_PAIR_SEPARATOR +
                      FILE_EXTENSION_LABEL + LABEL_VALUE_SEPARATOR + formData.getSourceFileExtension() + DATA_PAIR_SEPARATOR +
                      KEY_LABEL + LABEL_VALUE_SEPARATOR + hashedSaltedPassword + DATA_PAIR_SEPARATOR +
                      SALT_LABEL + LABEL_VALUE_SEPARATOR + salt + DATA_PAIR_SEPARATOR +
                      ITERATION_COUNT_LABEL + LABEL_VALUE_SEPARATOR + formData.getIterationCount() + DATA_PAIR_SEPARATOR +
                      SIGNATURE_LABEL + LABEL_VALUE_SEPARATOR + fileSignature;

        return file.getBytes();
    }

    public static FormData getFormDataFromMetadata(byte[] metadata) {
        FormData formData = new FormData();

        String file = new String(metadata);

        String[] dataPairs = file.split(Pattern.quote(DATA_PAIR_SEPARATOR));

        for (int i = 0; i < dataPairs.length; i++) {
            String[] labelValuePair = dataPairs[i].split(Pattern.quote(LABEL_VALUE_SEPARATOR));

            switch (labelValuePair[0]) {
                case FILENAME_LABEL:
                    formData.setSourceFilename(labelValuePair[1]);
                    break;

                case FILE_EXTENSION_LABEL:
                    formData.setSourceFileExtension(labelValuePair[1]);
                    break;

                case SALT_LABEL:
                    byte[] decodedSalt = Base64.getDecoder().decode(labelValuePair[1]);
                    formData.setSalt(decodedSalt);
                    break;

                case KEY_LABEL:
                    byte[] decodedKey = Base64.getDecoder().decode(labelValuePair[1]);
                    formData.setHashedSaltedPassword(decodedKey);
                    break;

                case ITERATION_COUNT_LABEL:
                    formData.setIterationCount(labelValuePair[1]);
                    break;

                case SIGNATURE_LABEL:
                    byte[] decodedSignature = Base64.getDecoder().decode(labelValuePair[1]);
                    formData.setFileSignature(decodedSignature);
                    break;
            }
        }

        return formData;
    }
}
