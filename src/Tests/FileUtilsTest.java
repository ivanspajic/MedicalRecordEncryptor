package tests;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Integration tests for managing files on the disk.
 */

class FileUtilsTest {

    /**
     * Attempts to write a test file on the disk and then read it to verify that data is not changed in the process.
     */
    @Test
    void TestreadAllBytes() {
        String output = System.getProperty("user.dir") + File.separator + "testfile.txt";
        data_access.FileUtils.write(output, "test".getBytes());
        String readtest = new String (data_access.FileUtils.readAllBytes(output));
        Assert.assertEquals("test", readtest);
    }
}