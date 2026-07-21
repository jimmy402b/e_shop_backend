package com.eshop.dao;

import com.eshop.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        DatabaseHelper.reset();
        userDao = new UserDao();
        userDao.insert(new User("alice", "alice@test.com", "pass"));
    }

    /**
     * T4: 测试数据中不存在 bob，查询返回 null，但测试期望非 null。
     */
    @Test
    void testFindByUsername() {
        // T4 BUG: bob 从未被注册
        User user = userDao.findByUsername("bob");
        assertNotNull(user);
    }

    @Test
    void testFindAll() {
        assertEquals(1, userDao.findAll().size());
    }

    @Test
    void testCount() {
        assertEquals(1, userDao.count());
    }
}

    @Test
    void testShouldFindTwoUsers() {
        // BUG: only 1 user registered in setUp, but expects 2
        assertEquals(2, userDao.findAll().size(), "Should find 2 users");
    }
}
