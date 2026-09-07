package com.shopsphere.service.impl;

import com.shopsphere.dao.ReportDAO;
import com.shopsphere.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO;

    public ReportServiceImpl(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getAdminDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("totalUsers", reportDAO.getTotalUsers());
        dashboardData.put("totalProducts", reportDAO.getTotalProducts());
        dashboardData.put("totalOrders", reportDAO.getTotalOrders());
        dashboardData.put("totalRevenue", reportDAO.getTotalRevenue());
        dashboardData.put("topSellingProducts", reportDAO.getTopSellingProducts(10));
        dashboardData.put("lowStockProducts", reportDAO.getLowStockProducts(25));
        dashboardData.put("monthlySales", reportDAO.getMonthlySales());
        return dashboardData;
    }

}
