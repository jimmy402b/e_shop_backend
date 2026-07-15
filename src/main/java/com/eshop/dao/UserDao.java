package com.eshop.dao;

import com.eshop.model.User;
import java.util.List;

/**
 * 用户数据访问对象。
 * BUGS:
 *   C1 - 缺少 import java.util.List (编译错误)
 *   S1 - findByUsername 存在 SQL 注入 (字符串拼接)
 */
public class UserDao {

    /**
     * 按用户名查询用户。
     * S1: SQL 注入 — 直接将 username 拼接到查询中，未使用参数化查询。
     * 攻击者输入 "' OR '1'='1" 可绕过认证。
     * 参考: CVE-2021-21345 (Hibernate/MyBatis SQL 注入)
     */
    public User findByUsername(String username) {
        // S1 BUG: SQL 拼接，攻击者可注入
        String fakeQuery = "SELECT * FROM users WHERE username = '" + username + "'";
        System.out.println("[SQL] " + fakeQuery);

        List<User> users = DatabaseHelper.getUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public List<User> findAll() {
        return new java.util.ArrayList<>(DatabaseHelper.getUsers());
    }

    public void insert(User user) {
        user.setId(DatabaseHelper.nextUserId());
        DatabaseHelper.getUsers().add(user);
    }

    public int count() {
        return DatabaseHelper.getUsers().size();
    }
}
