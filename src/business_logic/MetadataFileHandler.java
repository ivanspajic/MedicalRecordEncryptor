package business_logic;

import models.FormData;

public class MetadataFileHandler {
    private final String META_LABEL_VALUE_SEPARATOR = ":";
    private final String META_DATA_PAIR_SEPARATOR = "\n";
    private final String META_FILENAME_LABEL = "filename";
    private final String META_SALT_LABEL = "salt";
    private final String META_KEY_LABEL = "key";
    private final String META_ITERATION_COUNT_LABEL = "iterations";
    private final String META_SIGNATURE_LABEL = "signature";

    public static byte[] generateFileContents(FormData formData) {
        return new byte[0];
    }
}
