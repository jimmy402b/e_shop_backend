package com.eshop.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheManagerTest {
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new CacheManager();
    }

    @Test
    void testPutAndGet() {
        cacheManager.put("key", "value");
        assertEquals("value", cacheManager.get("key"));
    }

    @Test
    void testSize() {
        cacheManager.put("a", 1);
        cacheManager.put("b", 2);
        assertEquals(2, cacheManager.size());
    }

    /**
     * T6: 实际 put 了 3 个元素，但测试期望 5。
     */
    @Test
    void testConcurrentAccess() throws InterruptedException {
        cacheManager.put("key1", 1);
        cacheManager.put("key2", 2);
        cacheManager.put("key3", 3);

        // T6 BUG: 期望值应为 3，实际写成了 5
        assertEquals(5, cacheManager.size());
    }
}
