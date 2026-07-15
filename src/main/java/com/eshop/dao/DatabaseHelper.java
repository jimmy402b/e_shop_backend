package com.eshop.dao;

import com.eshop.model.*;

import java.util.*;

/**
 * 模拟数据库的存储。
 * BUG: 声明为线程安全的 ConcurrentHashMap 但实际使用 HashMap — 多线程下可能死循环。
 */
public class DatabaseHelper {
    // BUG: 实际使用了非线程安全的 HashMap
    private static final Map<String, Object> store = new HashMap<>();

    // 模拟自增 ID
    private static long nextUserId = 1;
    private static long nextProductId = 1;
    private static long nextOrderId = 1;

    @SuppressWarnings("unchecked")
    public static List<User> getUsers() {
        store.putIfAbsent("users", new ArrayList<User>());
        return (List<User>) store.get("users");
    }

    public static long nextUserId() { return nextUserId++; }

    @SuppressWarnings("unchecked")
    public static List<Product> getProducts() {
        store.putIfAbsent("products", new ArrayList<Product>());
        return (List<Product>) store.get("products");
    }

    public static long nextProductId() { return nextProductId++; }

    @SuppressWarnings("unchecked")
    public static List<Order> getOrders() {
        store.putIfAbsent("orders", new ArrayList<Order>());
        return (List<Order>) store.get("orders");
    }

    public static long nextOrderId() { return nextOrderId++; }

    public static void reset() {
        store.clear();
        nextUserId = 1;
        nextProductId = 1;
        nextOrderId = 1;
    }
}
