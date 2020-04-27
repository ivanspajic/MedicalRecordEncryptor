package business_logic;

public class CustomDialogException extends Exception {
    public static final String FILE_CONTENTS_MISSING = "The file at the supplied source path could not be read. It might be missing.";
    public static final String PASSWORD_INCORRECT = "The provided password was incorrect.";
    public static final String SIGNATURE_MISMATCH = "The file's signatures do not match. This file may have been tampered with.";

    public CustomDialogException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
