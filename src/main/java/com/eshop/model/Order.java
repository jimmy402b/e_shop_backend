package com.eshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Long userId;
    private List<OrderItem> items = new ArrayList<>();
    private double totalAmount;
    private String status;

    public Order() {}

    public Order(Long userId, List<OrderItem> items) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = calculateTotal(items);
        this.status = "CREATED";
    }

    private double calculateTotal(List<OrderItem> items) {
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        return total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static class OrderItem {
        private Long productId;
        private int quantity;
        private double unitPrice;

        public OrderItem() {}

        public OrderItem(Long productId, int quantity, double unitPrice) {
            this.productId = productId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    }
}
