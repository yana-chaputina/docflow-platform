package com.ipr.orderservice.service;

public interface OrdersExportFileGenerator {
    void generateOrdersCsv(Long taskId);
}
