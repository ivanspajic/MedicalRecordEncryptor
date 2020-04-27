package Tests;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void TestreadAllBytes() {
        String output = System.getProperty("user.dir") + File.separator + "testfile.txt";
        data_access.FileUtils.write(output, "test".getBytes());
        String readtest = new String (data_access.FileUtils.readAllBytes(output));
        Assert.assertEquals("test", readtest);
    }
}