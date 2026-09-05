package com.shopsphere.dao;

import com.shopsphere.entity.CartItem;
import java.util.List;

public interface CartItemDAO {
    CartItem save(CartItem cartItem);
    CartItem update(CartItem cartItem);
    CartItem findById(Integer cartItemId);
    List<CartItem> findByCartId(Integer cartId);
    CartItem findByCartAndProductAndSize(Integer cartId, Integer productId, Integer productSizeId);
    void delete(Integer cartItemId);
    void deleteByCartId(Integer cartId);
}
