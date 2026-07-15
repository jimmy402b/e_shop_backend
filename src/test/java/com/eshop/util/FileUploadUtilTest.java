package com.eshop.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class FileUploadUtilTest {

    /**
     * T5: readFile 的路径 "/tmp/nonexistent.txt" 不存在，抛出 FileNotFoundException。
     */
    @Test
    void testReadFile() throws IOException {
        // T5 BUG: 路径不存在
        byte[] content = FileUploadUtil.readFile("/tmp/nonexistent.txt");
        assertNotNull(content);
        assertTrue(content.length > 0);
    }

    @Test
    void testSaveFile() throws IOException {
        byte[] content = "hello world".getBytes();
        String path = FileUploadUtil.saveFile("test.txt", content);
        assertNotNull(path);
        assertTrue(path.endsWith("test.txt"));
    }
}
