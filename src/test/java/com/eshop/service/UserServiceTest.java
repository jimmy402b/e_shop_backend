package com.eshop.service;

import com.eshop.model.User;
import com.eshop.dao.DatabaseHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        DatabaseHelper.reset();
        userService = new UserService();
    }

    @Test
    void testRegister() {
        User user = userService.register(
            new User("alice", "alice@test.com", "123456"));
        assertNotNull(user.getId());
        assertEquals("alice", user.getUsername());
    }

    @Test
    void testLogin() {
        userService.register(new User("bob", "bob@test.com", "password"));
        String token = userService.login("bob", "password");
        assertNotNull(token);
        assertTrue(token.contains("bob"));
    }

    /**
     * T1: 期望值错误。
     * register 只注册了1个用户，但测试期望 count() 返回 2。
     */
    @Test
    void testCountUsers() {
        userService.register(new User("charlie", "charlie@test.com", "pass"));
        // T1 BUG: 期望值应为 1，实际写成了 2
        assertEquals(2, userService.count());
    }
}
