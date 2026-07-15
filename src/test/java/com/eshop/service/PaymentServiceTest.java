package com.eshop.service;

import com.eshop.model.PaymentResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    void testProcessPayment() {
        PaymentResult result = paymentService.processPayment(99.99);
        assertTrue(result.isSuccess());
        assertNotNull(result.getTransactionId());
    }

    /**
     * T3: 硬编码断言失败 — assertNotNull(null) 永远失败。
     */
    @Test
    void testRefundPayment() {
        PaymentResult result = paymentService.refundPayment("TXN-12345");
        assertTrue(result.isSuccess());
        // T3 BUG: 硬编码 null
        assertNotNull(null);
    }
}
