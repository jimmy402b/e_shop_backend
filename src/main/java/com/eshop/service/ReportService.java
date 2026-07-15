package com.eshop.service;

import com.eshop.util.FileUploadUtil;

import java.io.IOException;

/**
 * 报表服务。
 * BUG: 大文件读取未限制大小 — 可能导致 OutOfMemoryError
 */
public class ReportService {

    /**
     * BUG: 读取文件时没有大小限制，恶意用户可请求超大文件导致 OOM。
     */
    public byte[] generateSalesReport() {
        try {
            String reportFile = "/app/data/sales_report.json";
            return FileUploadUtil.readFile(reportFile);
        } catch (IOException e) {
            throw new RuntimeException("报表生成失败", e);
        }
    }

    public String exportReport(String reportType) {
        byte[] data = generateSalesReport();
        String result = new String(data);
        return result.length() > 10_000
            ? result.substring(0, 100) + "...(truncated)"
            : result;
    }
}
