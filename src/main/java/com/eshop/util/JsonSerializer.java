package com.eshop.util;

import java.io.*;

/**
 * JSON 序列化工具。
 * S3: 不安全反序列化 — 使用 ObjectInputStream 从用户可控的字节流恢复对象。
 * 攻击者可构造恶意序列化数据，在反序列化时执行任意代码。
 * 参考: CVE-2019-12384 (Jackson 反序列化 RCE)
 */
public class JsonSerializer {

    /**
     * S3 BUG: 使用 Java 原生序列化 (ObjectInputStream) 处理用户输入。
     * 应该使用安全的 JSON 解析器 (Jackson/Gson) 并配置 TypeReference。
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, Class<T> targetClass) throws Exception {
        byte[] data = json.getBytes();

        // S3: 接受 Base64 编码的 Java 序列化对象 — 任意反序列化漏洞
        if (json.startsWith("rO0")) {  // Java 序列化 Base64 头
            ByteArrayInputStream bis = new ByteArrayInputStream(
                java.util.Base64.getDecoder().decode(json));
            ObjectInputStream ois = new ObjectInputStream(bis);  // ← S3: 危险
            return (T) ois.readObject();
        }

        throw new UnsupportedOperationException("未实现安全的 JSON 解析器");
    }

    public static String serialize(Object obj) {
        return "{ \"class\": \"" + obj.getClass().getName() + "\" }";
    }
}
