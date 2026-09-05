package com.shopsphere.service.impl;

import com.shopsphere.dao.CartDAO;
import com.shopsphere.dao.CartItemDAO;
import com.shopsphere.dao.OrderDAO;
import com.shopsphere.dao.OrderItemDAO;
import com.shopsphere.dao.ProductSizeDAO;
import com.shopsphere.dao.UserDAO;
import com.shopsphere.dto.CheckoutRequest;
import com.shopsphere.dto.OrderResponse;
import com.shopsphere.entity.*;
import com.shopsphere.exception.InsufficientStockException;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final CartDAO cartDAO;
    private final CartItemDAO cartItemDAO;
    private final ProductSizeDAO productSizeDAO;
    private final UserDAO userDAO;

    public OrderServiceImpl(OrderDAO orderDAO, OrderItemDAO orderItemDAO, CartDAO cartDAO, CartItemDAO cartItemDAO, ProductSizeDAO productSizeDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.cartDAO = cartDAO;
        this.cartItemDAO = cartItemDAO;
        this.productSizeDAO = productSizeDAO;
        this.userDAO = userDAO;
    }

    /**
     * Executes placing an order as a single atomic transaction.
     * 1. Validate cart
     * 2. Validate stock
     * 3. Create order
     * 4. Create order items
     * 5. Reduce inventory
     * 6. Clear cart
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order placeOrder(Integer userId, CheckoutRequest checkoutRequest) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        // 1. Validate cart
        Cart cart = cartDAO.findByUserId(userId);
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with an empty shopping cart!");
        }

        List<CartItem> cartItems = cart.getCartItems();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 2. Validate stock for all items upfront
        for (CartItem item : cartItems) {
            ProductSize ps = item.getProductSize();
            if (ps == null || ps.getStockQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " 
                        + item.getProduct().getProductName() 
                        + " (Size: " + item.getSizeLabel() + "). Available: " 
                        + (ps != null ? ps.getStockQuantity() : 0));
            }
            totalAmount = totalAmount.add(item.getSubtotal());
        }

        // 3. Create order
        Order order = new Order(
                user,
                totalAmount,
                checkoutRequest.getPaymentMethod(),
                "PENDING",
                checkoutRequest.getDeliveryAddress()
        );
        Order savedOrder = orderDAO.save(order);

        // 4. Create order items & 5. Reduce inventory
        for (CartItem item : cartItems) {
            ProductSize ps = item.getProductSize();
            
            // Reduce stock
            int newStock = ps.getStockQuantity() - item.getQuantity();
            ps.setStockQuantity(newStock);
            if (newStock == 0) {
                ps.setIsAvailable(false);
            }
            productSizeDAO.update(ps);

            // Create OrderItem
            OrderItem orderItem = new OrderItem(
                    savedOrder,
                    item.getProduct(),
                    ps,
                    item.getProduct().getProductName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal(),
                    item.getSizeLabel()
            );
            orderItemDAO.save(orderItem);
        }

        // 6. Clear cart
        cartItemDAO.deleteByCartId(cart.getCartId());

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderDetails(Integer orderId) {
        Order order = orderDAO.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        return new OrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getUserOrders(Integer userId) {
        return orderDAO.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Integer orderId, String newStatus) {
        Order order = orderDAO.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        order.setOrderStatus(newStatus);
        return orderDAO.update(order);
    }
}
