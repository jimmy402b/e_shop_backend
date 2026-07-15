package com.eshop;

import com.eshop.controller.*;
import com.eshop.model.*;
import com.eshop.model.Order.OrderItem;

import java.util.Arrays;

/**
 * E-Shop 后端入口。
 * 此文件无 bug。
 */
public class EShopApplication {
    public static void main(String[] args) {
        System.out.println("E-Shop Backend v1.0.0");
        System.out.println("================================");

        UserController userController = new UserController();
        ProductController productController = new ProductController();
        OrderController orderController = new OrderController();
        ReportController reportController = new ReportController();

        User user = userController.register("admin", "admin@eshop.com", "admin123");
        System.out.println("注册用户: " + user.getUsername());

        String token = userController.login("admin", "admin123");
        System.out.println("登录 Token: " + token);

        Product product = new Product("Java程序设计", 79.00, 100);
        productController.addProduct(product);
        System.out.println("添加商品: " + product.getName());

        OrderItem item = new OrderItem(1L, 2, 79.00);
        Order order = orderController.createOrder(1L, Arrays.asList(item));
        System.out.println("创建订单 #" + order.getId() + " 金额: " + order.getTotalAmount());

        String payResult = orderController.payOrder(order.getId());
        System.out.println("支付结果: " + payResult);

        System.out.println("销售报表: " + reportController.getSalesReport());

        System.out.println("================================");
        System.out.println("E-Shop Backend 运行完成");
    }
}
