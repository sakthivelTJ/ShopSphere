package com.shopsphere.dto;

import com.shopsphere.entity.Order;
import com.shopsphere.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Integer orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String orderStatus;
    private String deliveryAddress;
    private String userName;
    private String userEmail;
    private List<OrderItem> items;

    public OrderResponse() {}

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.paymentMethod = order.getPaymentMethod();
        this.orderStatus = order.getOrderStatus();
        this.deliveryAddress = order.getDeliveryAddress();
        if (order.getUser() != null) {
            this.userName = order.getUser().getFullName();
            this.userEmail = order.getUser().getEmail();
        }
        this.items = order.getOrderItems();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
