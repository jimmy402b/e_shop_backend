package com.eshop.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存管理器。
 * BUG: 使用非线程安全的 HashMap，多线程下可能死循环（T6）或数据丢失。
 * 参考: HashMap 在 JDK 7/8 中 resize() 的死循环问题。
 */
public class CacheManager {
    // BUG: 应为 ConcurrentHashMap
    private final Map<String, Object> cache = new HashMap<>();

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }
}
