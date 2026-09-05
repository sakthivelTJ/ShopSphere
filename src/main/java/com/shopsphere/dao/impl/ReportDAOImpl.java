package com.shopsphere.dao.impl;

import com.shopsphere.dao.ReportDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class ReportDAOImpl implements ReportDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReportDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    @Override
    public long getTotalProducts() {
        String sql = "SELECT COUNT(*) FROM products";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    @Override
    public long getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    @Override
    public BigDecimal getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE order_status != 'CANCELLED'";
        BigDecimal total = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public List<Map<String, Object>> getTopSellingProducts(int limit) {
        String sql = "SELECT oi.product_id, oi.product_name, SUM(oi.quantity) as total_sold, SUM(oi.subtotal) as total_revenue " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.order_id " +
                     "WHERE o.order_status != 'CANCELLED' " +
                     "GROUP BY oi.product_id, oi.product_name " +
                     "ORDER BY total_sold DESC LIMIT ?";
        return jdbcTemplate.queryForList(sql, limit);
    }

    @Override
    public List<Map<String, Object>> getLowStockProducts(int threshold) {
        String sql = "SELECT p.product_id, p.product_name, ps.size_label, ps.stock_quantity " +
                     "FROM product_sizes ps " +
                     "JOIN products p ON ps.product_id = p.product_id " +
                     "WHERE ps.stock_quantity <= ? " +
                     "ORDER BY ps.stock_quantity ASC";
        return jdbcTemplate.queryForList(sql, threshold);
    }

    @Override
    public List<Map<String, Object>> getMonthlySales() {
        String sql = "SELECT DATE_FORMAT(order_date, '%Y-%m') as month, COUNT(order_id) as total_orders, SUM(total_amount) as total_revenue " +
                     "FROM orders " +
                     "WHERE order_status != 'CANCELLED' " +
                     "GROUP BY DATE_FORMAT(order_date, '%Y-%m') " +
                     "ORDER BY month DESC LIMIT 12";
        return jdbcTemplate.queryForList(sql);
    }
}
