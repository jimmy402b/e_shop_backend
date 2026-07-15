package com.eshop.dao;

import com.eshop.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public List<Order> findByUserId(Long userId) {
        List<Order> result = new ArrayList<>();
        for (Order o : DatabaseHelper.getOrders()) {
            if (o.getUserId().equals(userId)) {
                result.add(o);
            }
        }
        return result;
    }

    public Order findById(Long orderId) {
        for (Order o : DatabaseHelper.getOrders()) {
            if (o.getId().equals(orderId)) {
                return o;
            }
        }
        return null;
    }

    public void insert(Order order) {
        order.setId(DatabaseHelper.nextOrderId());
        DatabaseHelper.getOrders().add(order);
    }

    public void updateStatus(Long orderId, String status) {
        Order order = findById(orderId);
        if (order != null) {
            order.setStatus(status);
        }
    }
}
