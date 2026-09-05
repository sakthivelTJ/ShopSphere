package com.shopsphere.service;

import com.shopsphere.entity.CartItem;
import java.util.List;

public interface CartItemService {
    List<CartItem> getItemsByCartId(Integer cartId);
    void removeItem(Integer cartItemId);
}
