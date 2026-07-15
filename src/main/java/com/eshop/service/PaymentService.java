package com.eshop.service;

import com.eshop.model.PaymentResult;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 支付服务。
 * BUGS: 支付超时处理导致连接泄漏
 */
public class PaymentService {

    private boolean thirdPartyAvailable = true;

    public PaymentResult processPayment(double amount) {
        // R1 关联: double 精度在这里被 OrderService 传递进来
        if (!thirdPartyAvailable) {
            return new PaymentResult(false, null, "第三方支付服务不可用");
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new PaymentResult(true,
            "TXN-" + UUID.randomUUID().toString().substring(0, 8),
            "支付成功");
    }

    public PaymentResult refundPayment(String transactionId) {
        if (transactionId == null || !transactionId.startsWith("TXN-")) {
            return new PaymentResult(false, null, "无效的交易号");
        }
        return new PaymentResult(true,
            "REF-" + UUID.randomUUID().toString().substring(0, 8),
            "退款成功");
    }

    /**
     * BUG: 支付超时处理 — 超时后未取消原始请求，底层连接泄漏。
     */
    public String handleTimeout(String transactionId) {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "超时处理中断";
        }
        return "超时";
    }
}
