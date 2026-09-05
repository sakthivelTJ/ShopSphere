package com.shopsphere.service.impl;

import com.shopsphere.dao.CartItemDAO;
import com.shopsphere.entity.CartItem;
import com.shopsphere.service.CartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemDAO cartItemDAO;

    public CartItemServiceImpl(CartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getItemsByCartId(Integer cartId) {
        return cartItemDAO.findByCartId(cartId);
    }

    @Override
    @Transactional
    public void removeItem(Integer cartItemId) {
        cartItemDAO.delete(cartItemId);
    }
}
