package com.shopsphere.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportDAO {
    long getTotalUsers();
    long getTotalProducts();
    long getTotalOrders();
    BigDecimal getTotalRevenue();
    List<Map<String, Object>> getTopSellingProducts(int limit);
    List<Map<String, Object>> getLowStockProducts(int threshold);
    List<Map<String, Object>> getMonthlySales();
}
