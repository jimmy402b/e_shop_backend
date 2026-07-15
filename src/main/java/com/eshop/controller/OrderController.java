package com.eshop.controller;

import com.eshop.model.Order;
import com.eshop.model.Order.OrderItem;
import com.eshop.service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService = new OrderService();

    public Order createOrder(Long userId, List<OrderItem> items) {
        return orderService.createOrder(userId, items);
    }

    public String payOrder(Long orderId) {
        return orderService.payOrder(orderId);
    }

    public String refundOrder(Long orderId, Long requestUserId) {
        return orderService.refundOrder(orderId, requestUserId);
    }

    public List<Order> findOrdersByUserId(Long userId) {
        return orderService.findOrdersByUserId(userId);
    }
}
