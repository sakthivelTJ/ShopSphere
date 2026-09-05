package com.shopsphere.service.impl;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.CartItemDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.ProductSizeDAO;
import com.shopsphere.dao.UserDAO;
import com.shopsphere.entity.*;
import com.shopsphere.exception.InsufficientStockException;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    private final CartDAO cartDAO;
    private final CartItemDAO cartItemDAO;
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final ProductSizeDAO productSizeDAO;

    public CartServiceImpl(CartDAO cartDAO, CartItemDAO cartItemDAO, UserDAO userDAO, ProductDAO productDAO, ProductSizeDAO productSizeDAO) {
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
        this.productSizeDAO = productSizeDAO;
    }

    @Override
    @Transactional
    public Cart getCartByUserId(Integer userId) {
        Cart cart = cartDAO.findByUserId(userId);
        if (cart == null) {
            User user = userDAO.findById(userId);
            if (user == null) {
                throw new ResourceNotFoundException("User not found with ID: " + userId);
            }
            cart = new Cart(user);
            cart = cartDAO.save(cart);
        }
        return cart;
    }

    @Override
    @Transactional
    public Cart addToCart(Integer userId, Integer productId, Integer productSizeId, Integer quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productDAO.findById(productId);
        if (product == null || !product.getIsActive()) {
            throw new ResourceNotFoundException("Product not available or active");
        }

        ProductSize productSize = productSizeDAO.findById(productSizeId);
        if (productSize == null || !productSize.getIsAvailable()) {
            throw new ResourceNotFoundException("Product size option not available");
        }

        if (productSize.getStockQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock! Available: " + productSize.getStockQuantity());
        }

        CartItem existingItem = cartItemDAO.findByCartAndProductAndSize(cart.getCartId(), productId, productSizeId);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (productSize.getStockQuantity() < newQuantity) {
                throw new InsufficientStockException("Cannot add more items. Available stock: " + productSize.getStockQuantity());
            }
            existingItem.setQuantity(newQuantity);
            cartItemDAO.update(existingItem);
        } else {
            BigDecimal effectivePrice = product.getEffectivePrice();
            CartItem newItem = new CartItem(cart, product, productSize, productSize.getSizeLabel(), quantity, effectivePrice);
            cartItemDAO.save(newItem);
        }

        return getCartByUserId(userId);
    }

    @Override
    @Transactional
    public Cart updateCartItemQuantity(Integer userId, Integer cartItemId, Integer quantity) {
        CartItem cartItem = cartItemDAO.findById(cartItemId);
        if (cartItem == null) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        if (quantity <= 0) {
            cartItemDAO.delete(cartItemId);
        } else {
            if (cartItem.getProductSize().getStockQuantity() < quantity) {
                throw new InsufficientStockException("Cannot exceed available stock of " + cartItem.getProductSize().getStockQuantity());
            }
            cartItem.setQuantity(quantity);
            cartItemDAO.update(cartItem);
        }

        return getCartByUserId(userId);
    }

    @Override
    @Transactional
    public Cart removeFromCart(Integer userId, Integer cartItemId) {
        cartItemDAO.delete(cartItemId);
        return getCartByUserId(userId);
    }

    @Override
    @Transactional
    public void clearCart(Integer userId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            cartItemDAO.deleteByCartId(cart.getCartId());
        }
    }
}
