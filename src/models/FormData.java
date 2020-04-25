package models;

public class FormData {
    private String sourceFileLocation = null;
    private String targetDirectoryLocation = null;

    private int iterationCount = 0;

    private boolean deleteSourceFile = false;

    private String password = null;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
