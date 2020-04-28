package tests;

import business_logic.MetadataFileHandler;
import junit.framework.Assert;
import models.FormData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;

/**
 * Our metadata file is highly recommended (and required by the application) to decrypt the files, so we have to make
 * sure our logic works as intended.
 */
class MetadataFileHandlerTest {

    //Initializing test data
    FormData testform;
    {
        testform = new FormData();
        testform.setSourceFilename("test");
        testform.setSourceFileExtension("txt");
        testform.setIterationCount("100");
        testform.setHashedSaltedPassword(Base64.getDecoder().decode("hashedsaltedpassword"));
        testform.setSalt(Base64.getDecoder().decode("salt"));
        testform.setFileSignature(Base64.getDecoder().decode("setfilesignature"));
    }

    /**
     * We generate a metadata file based on our testform then extract the info again from the result to verify
     * that data is consistent. Corrupted data could mean loss of access to the application's decryption functionality.
     */
    @Test
    void getFormDataFromMetadata1() {
        byte[] metadatafile = MetadataFileHandler.generateFileContents(testform);
        FormData result = MetadataFileHandler.getFormDataFromMetadata(metadatafile);
        Assert.assertTrue(IsSame(testform, result));
    }

    /**
     *This is a simplified approach to test the data's integrity. If the file and/or the signature was tempered with,
     * we have to be able to detect it.
     */
    @Test
    void getFormDataFromMetadata2() {
        byte[] metadatafile = MetadataFileHandler.generateFileContents(testform);
        testform.setFileSignature(Base64.getDecoder().decode("wrongfilesignature"));
        FormData result = MetadataFileHandler.getFormDataFromMetadata(metadatafile);
        Assert.assertFalse(IsSame(testform, result));
    }

    /**
     * This method compares two FormData objects. It returns true if they are the same and false if they are not.
     * @param one
     * @param two
     * @return
     */
    private boolean IsSame(FormData one, FormData two) {
        if (!one.getSourceFilename().equals(two.getSourceFilename()))
            return false;
        else if (!one.getSourceFileExtension().equals(two.getSourceFileExtension()))
            return false;
        else if (one.getIterationCount() != two.getIterationCount())
            return false;
        else if (!Arrays.equals(one.getHashedSaltedPassword(), two.getHashedSaltedPassword()))
            return false;
        else if (!Arrays.equals(one.getSalt(), two.getSalt()))
            return false;
        else if (!Arrays.equals(one.getFileSignature(), two.getFileSignature()))
            return false;
        else
            return true;
    }
}