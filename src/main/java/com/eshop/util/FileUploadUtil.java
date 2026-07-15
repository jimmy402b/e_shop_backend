package com.eshop.util;

import java.io.*;

/**
 * 文件上传/读取工具。
 * S2: 路径遍历 — saveFile 未对 filename 做校验，攻击者传入 "../../../etc/crond" 可覆盖系统文件。
 * 参考: CVE-2020-17523 (Apache Shiro 路径遍历)
 */
public class FileUploadUtil {

    private static final String UPLOAD_DIR = "/tmp/uploads";

    /**
     * S2 BUG: 路径未校验，直接拼接用户提供的文件名。
     * 攻击示例: saveFile("../../../etc/crontab", maliciousBytes)
     */
    public static String saveFile(String filename, byte[] content) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);  // ← S2: 未规范化路径
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }
        return file.getAbsolutePath();
    }

    /**
     * BUG: 未关闭 InputStream — 文件句柄泄漏
     */
    public static byte[] readFile(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int read;
        while ((read = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, read);
        }
        // BUG: fis 未关闭 — 资源泄漏
        return bos.toByteArray();
    }
}
