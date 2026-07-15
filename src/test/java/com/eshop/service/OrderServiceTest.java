package com.eshop.service;

import com.eshop.model.Order;
import com.eshop.model.Order.OrderItem;
import com.eshop.model.User;
import com.eshop.dao.DatabaseHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class OrderServiceTest {
    private OrderService orderService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        DatabaseHelper.reset();
        userService = new UserService();
        orderService = new OrderService();
    }

    @Test
    void testCreateOrder() {
        userService.register(new User("alice", "alice@test.com", "pass"));
        OrderItem item = new OrderItem(1L, 1, 50.0);

        // T2 BUG: 局部变量覆盖实例变量 — this.orderService 被忽略
        OrderService orderService = null;  // ← 导致 NPE
        Order order = orderService.createOrder(1L, Arrays.asList(item));

        assertNotNull(order);
        assertEquals("CREATED", order.getStatus());
    }
}
