package com.shopsphere.dao;

import com.shopsphere.entity.Cart;

public interface CartDAO {
    Cart save(Cart cart);
    Cart update(Cart cart);
    Cart findById(Integer cartId);
    Cart findByUserId(Integer userId);
    void delete(Integer cartId);
}
