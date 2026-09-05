package com.shopsphere.service.impl;

import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.entity.OrderItem;
import com.shopsphere.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemDAO orderItemDAO;

    public OrderItemServiceImpl(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getItemsByOrderId(Integer orderId) {
        return orderItemDAO.findByOrderId(orderId);
    }
}
