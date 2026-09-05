package com.shopsphere.service;

import com.shopsphere.entity.Cart;

public interface CartService {
    Cart getCartByUserId(Integer userId);
    Cart addToCart(Integer userId, Integer productId, Integer productSizeId, Integer quantity);
    Cart updateCartItemQuantity(Integer userId, Integer cartItemId, Integer quantity);
    Cart removeFromCart(Integer userId, Integer cartItemId);
    void clearCart(Integer userId);
}
