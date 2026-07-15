package com.eshop.controller;

import com.eshop.model.User;
import com.eshop.service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService = new UserService();

    public User register(String username, String email, String password) {
        User user = new User(username, email, password);
        return userService.register(user);
    }

    public String login(String username, String password) {
        return userService.login(username, password);
    }

    public User getUser(Long id) {
        return userService.findById(id);
    }

    public List<User> listUsers() {
        return userService.findAll();
    }
}
