package com.shopsphere.service;

import com.shopsphere.entity.OrderItem;
import java.util.List;

public interface OrderItemService {
    List<OrderItem> getItemsByOrderId(Integer orderId);
}
