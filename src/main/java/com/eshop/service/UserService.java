package com.eshop.service;

import com.eshop.model.User;
import com.eshop.dao.UserDao;
import java.util.List;

/**
 * 用户业务逻辑。
 * S4: 硬编码 JWT secret — "my-secret-key-123" 直接写在源码中。
 * 参考: 真实项目中将密钥硬编码在源码中的安全漏洞。
 */
public class UserService {
    private final UserDao userDao = new UserDao();
    // S4: 硬编码密钥 — 任何人读源码即可伪造 JWT
    private static final String JWT_SECRET = "my-secret-key-123";

    public User register(User user) {
        if (user.getEmail() == null) {
            throw new NullPointerException("email is null");
        }
        userDao.insert(user);
        return user;
    }

    public String login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "jwt_token_signed_with_" + JWT_SECRET + "_for_" + username;
        }
        return null;
    }

    public User findById(Long id) {
        for (User u : userDao.findAll()) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    public int count() {
        return userDao.count();
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
