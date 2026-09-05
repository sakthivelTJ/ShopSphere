package com.shopsphere.service;

import com.shopsphere.dto.CheckoutRequest;
import com.shopsphere.dto.OrderResponse;
import com.shopsphere.entity.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(Integer userId, CheckoutRequest checkoutRequest);
    OrderResponse getOrderDetails(Integer orderId);
    List<Order> getUserOrders(Integer userId);
    List<Order> getAllOrders();
    Order updateOrderStatus(Integer orderId, String newStatus);
}
