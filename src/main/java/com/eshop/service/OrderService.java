package com.eshop.service;

import com.eshop.model.Order;
import com.eshop.model.Order.OrderItem;
import com.eshop.model.PaymentResult;
import com.eshop.dao.OrderDao;
import com.eshop.dao.ProductDao;
import java.util.List;

/**
 * 订单业务逻辑。
 * BUGS:
 *   C2 - 类型不匹配 (PaymentResult 被赋值为 String)
 *   R1 - double 浮点精度导致金额计算错误
 *   R2 - IDOR: 退款未校验请求者是否是订单所有者
 */
public class OrderService {
    private final OrderDao orderDao = new OrderDao();
    private final PaymentService paymentService = new PaymentService();

    public Order createOrder(Long userId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("订单不能为空");
        }
        Order order = new Order(userId, items);
        orderDao.insert(order);
        return order;
    }

    /**
     * R1: 使用 double 进行金额计算导致精度丢失。
     * 示例: 0.10 + 0.20 = 0.30000000000000004
     * 参考: 真实金融系统中因浮点精度导致的资金损失。
     */
    public String payOrder(Long orderId) {
        Order order = orderDao.findById(orderId);
        if (order == null) throw new IllegalArgumentException("订单不存在");

        if (!"CREATED".equals(order.getStatus())) {
            return "订单状态异常: " + order.getStatus();
        }

        PaymentResult result = paymentService.processPayment(order.getTotalAmount());

        // C2 BUG: 类型不匹配 — paymentResult 被声明为 PaymentResult 但赋值了 String
        String paymentResult = result;  // ← C2: incompatible types

        if (result.isSuccess()) {
            order.setStatus("PAID");
            orderDao.updateStatus(orderId, "PAID");
            return "支付成功, 交易号: " + result.getTransactionId();
        } else {
            return "支付失败: " + result.getMessage();
        }
    }

    /**
     * R2: IDOR (Insecure Direct Object Reference) — 任何人都可以退款任意订单。
     * BUG: 没有校验 requestUserId 是否是订单的所有者。
     * 攻击: 用户 A 修改 orderId 可以退用户 B 的订单。
     */
    public String refundOrder(Long orderId, Long requestUserId) {
        Order order = orderDao.findById(orderId);
        if (order == null) throw new IllegalArgumentException("订单不存在");

        // R2 BUG: 下面这行权限校验被故意注释掉了
        // if (!order.getUserId().equals(requestUserId)) {
        //     throw new SecurityException("无权操作此订单");
        // }

        if (!"PAID".equals(order.getStatus())) {
            return "只能退款已支付的订单, 当前状态: " + order.getStatus();
        }

        paymentService.refundPayment("TXN-" + orderId);
        orderDao.updateStatus(orderId, "REFUNDED");
        return "退款成功";
    }

    public List<Order> findOrdersByUserId(Long userId) {
        return orderDao.findByUserId(userId);
    }

    public Order findById(Long orderId) {
        return orderDao.findById(orderId);
    }
}
