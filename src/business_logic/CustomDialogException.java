package business_logic;

public class CustomDialogException extends Exception {
    public static final String FILE_CONTENTS_MISSING = "The file at the supplied source path could not be read. It might be missing.";
    public static final String PASSWORD_INCORRECT = "The provided password was incorrect.";
    public static final String SIGNATURE_MISMATCH = "The file's signatures do not match. This file may have been tampered with.";

    /**
     * Handles custom exceptions by extending the Exception class. It also
     * contains all the previously thrown exceptions through the requirement
     * of a Throwable object.
     * @param errorMessage
     * @param error
     */
    public CustomDialogException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}