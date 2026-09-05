package com.shopsphere.dao.impl;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.entity.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOImpl implements CartDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cart save(Cart cart) {
        entityManager.persist(cart);
        return cart;
    }

    @Override
    public Cart update(Cart cart) {
        return entityManager.merge(cart);
    }

    @Override
    public Cart findById(Integer cartId) {
        return entityManager.find(Cart.class, cartId);
    }

    @Override
    public Cart findByUserId(Integer userId) {
        try {
            return entityManager.createQuery("SELECT DISTINCT c FROM Cart c LEFT JOIN FETCH c.cartItems ci LEFT JOIN FETCH ci.product LEFT JOIN FETCH ci.productSize WHERE c.user.userId = :userId", Cart.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(Integer cartId) {
        Cart cart = findById(cartId);
        if (cart != null) {
            entityManager.remove(cart);
        }
    }
}
