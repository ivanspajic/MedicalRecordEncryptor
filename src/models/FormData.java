package models;

public class FormData {
    private String sourceFileLocation = null;
    private String targetDirectoryLocation = null;

    private String sourceFilename = null;
    private String sourceFileExtension = null;

    private boolean deleteSourceFile = false;

    private String clearTextPassword = null;
    private byte[] hashedSaltedPassword = null;
    private byte[] salt = null;

    private byte[] fileSignature = null;

    private int iterationCount = 0;

    public String getSourceFileLocation() {
        return sourceFileLocation;
    }

    public void setSourceFileLocation(String sourceFileLocation) {
        this.sourceFileLocation = sourceFileLocation;
    }

    public String getTargetDirectoryLocation() {
        return targetDirectoryLocation;
    }

    public void setTargetDirectoryLocation(String targetDirectoryLocation) {
        this.targetDirectoryLocation = targetDirectoryLocation;
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    public void setSourceFilename(String sourceFilename) {
        this.sourceFilename = sourceFilename;
    }

    public String getSourceFileExtension() {
        return sourceFileExtension;
    }

    public void setSourceFileExtension(String sourceFileExtension) {
        this.sourceFileExtension = sourceFileExtension;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public void setIterationCount(String iterationCount){
        this.iterationCount = Integer.parseInt(iterationCount);
    }

    public boolean getDeleteSourceFile() {
        return deleteSourceFile;
    }

    public void setDeleteSourceFile(boolean deleteSourceFile) {
        this.deleteSourceFile = deleteSourceFile;
    }

    public String getClearTextPassword() {
        return clearTextPassword;
    }

    public void setClearTextPassword(String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
    }

    public byte[] getHashedSaltedPassword() {
        return hashedSaltedPassword;
    }

    public void setHashedSaltedPassword(byte[] hashedSaltedPassword) {
        this.hashedSaltedPassword = hashedSaltedPassword;
    }

    public byte[] getSalt() { return salt; }

    public void setSalt(byte[] salt) { this.salt = salt; }

    public byte[] getFileSignature() { return fileSignature; }

    public void setFileSignature(byte[] fileSignature) { this.fileSignature = fileSignature; }
}
