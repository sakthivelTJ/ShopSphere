package com.shopsphere.dao.impl;

import com.shopsphere.dao.OrderDAO;
import com.shopsphere.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        return entityManager.merge(order);
    }

    @Override
    public Order findById(Integer orderId) {
        try {
            return entityManager.createQuery("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product LEFT JOIN FETCH o.user WHERE o.orderId = :orderId", Order.class)
                    .setParameter("orderId", orderId)
                    .getSingleResult();
        } catch (Exception e) {
            return entityManager.find(Order.class, orderId);
        }
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.user ORDER BY o.orderDate DESC", Order.class)
                .getResultList();
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return entityManager.createQuery("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.user.userId = :userId ORDER BY o.orderDate DESC", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void delete(Integer orderId) {
        Order order = findById(orderId);
        if (order != null) {
            entityManager.remove(order);
        }
    }
}
