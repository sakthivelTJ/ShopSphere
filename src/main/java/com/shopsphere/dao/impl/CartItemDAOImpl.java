package com.shopsphere.dao.impl;

import com.shopsphere.dao.CartItemDAO;
import com.shopsphere.entity.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemDAOImpl implements CartItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CartItem save(CartItem cartItem) {
        entityManager.persist(cartItem);
        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        return entityManager.merge(cartItem);
    }

    @Override
    public CartItem findById(Integer cartItemId) {
        return entityManager.find(CartItem.class, cartItemId);
    }

    @Override
    public List<CartItem> findByCartId(Integer cartId) {
        return entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId", CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public CartItem findByCartAndProductAndSize(Integer cartId, Integer productId, Integer productSizeId) {
        try {
            return entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.product.productId = :productId AND ci.productSize.productSizeId = :sizeId", CartItem.class)
                    .setParameter("cartId", cartId)
                    .setParameter("productId", productId)
                    .setParameter("sizeId", productSizeId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(Integer cartItemId) {
        CartItem cartItem = findById(cartItemId);
        if (cartItem != null) {
            entityManager.remove(cartItem);
        }
    }

    @Override
    public void deleteByCartId(Integer cartId) {
        entityManager.createQuery("DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();
    }
}
