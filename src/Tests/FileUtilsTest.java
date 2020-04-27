package tests;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;

class FileUtilsTest {

    @Test
    void TestreadAllBytes() {
        String output = System.getProperty("user.dir") + File.separator + "testfile.txt";
        data_access.FileUtils.write(output, "test".getBytes());
        String readtest = new String (data_access.FileUtils.readAllBytes(output));
        Assert.assertEquals("test", readtest);
    }
}