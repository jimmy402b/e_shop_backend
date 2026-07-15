package com.eshop.controller;

import com.eshop.service.ReportService;

public class ReportController {
    private final ReportService reportService = new ReportService();

    public String getSalesReport() {
        return reportService.exportReport("sales");
    }

    public String getInventoryReport() {
        return reportService.exportReport("inventory");
    }

    public byte[] downloadReport() {
        return reportService.generateSalesReport();
    }
}
