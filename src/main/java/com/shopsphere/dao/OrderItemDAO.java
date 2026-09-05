package com.shopsphere.dao;

import com.shopsphere.entity.OrderItem;
import java.util.List;

public interface OrderItemDAO {
    OrderItem save(OrderItem orderItem);
    OrderItem findById(Integer orderItemId);
    List<OrderItem> findByOrderId(Integer orderId);
}
