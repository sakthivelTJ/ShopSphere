package com.shopsphere.dao.impl;

import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderItem save(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem findById(Integer orderItemId) {
        return entityManager.find(OrderItem.class, orderItemId);
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        return entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.order.orderId = :orderId", OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
