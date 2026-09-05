package com.shopsphere.dao;

import com.shopsphere.entity.Order;
import java.util.List;

public interface OrderDAO {
    Order save(Order order);
    Order update(Order order);
    Order findById(Integer orderId);
    List<Order> findAll();
    List<Order> findByUserId(Integer userId);
    void delete(Integer orderId);
}
