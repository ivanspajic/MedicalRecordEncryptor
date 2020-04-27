package business_logic;

import models.FormData;

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
        String file = FILENAME_LABEL + LABEL_VALUE_SEPARATOR + formData.getSourceFilename() + DATA_PAIR_SEPARATOR +
                      FILE_EXTENSION_LABEL + LABEL_VALUE_SEPARATOR + formData.getSourceFileExtension() + DATA_PAIR_SEPARATOR +
                      KEY_LABEL + LABEL_VALUE_SEPARATOR + new String(formData.getHashedSaltedPassword()) + DATA_PAIR_SEPARATOR +
                      SALT_LABEL + LABEL_VALUE_SEPARATOR + new String(formData.getSalt()) + DATA_PAIR_SEPARATOR +
                      ITERATION_COUNT_LABEL + LABEL_VALUE_SEPARATOR + formData.getIterationCount() + DATA_PAIR_SEPARATOR +
                      SIGNATURE_LABEL + LABEL_VALUE_SEPARATOR + new String(formData.getSignature());

        return file.getBytes();
    }

    public static FormData getFormDataFromMetadata(byte[] metadata) {
        FormData formData = new FormData();



        return formData;
    }
}
