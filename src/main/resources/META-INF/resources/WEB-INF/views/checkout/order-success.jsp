<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5 text-center">
    <div class="card glass-card max-w-600 mx-auto p-5 shadow-lg">
        <i class="bi bi-check-circle-fill display-1 text-success mb-3 animated-float"></i>
        <h2 class="fw-bold mb-2">Order Placed Successfully!</h2>
        <p class="text-muted mb-4">Thank you for shopping with ShopSphere. Your order ID is <span class="fw-bold text-primary">#${order.orderId}</span>.</p>

        <div class="glass-card p-4 text-start mb-4">
            <h6 class="fw-bold mb-3 border-bottom pb-2">Order Summary</h6>
            <p class="mb-1"><strong>Status:</strong> <span class="badge bg-warning text-dark">${order.orderStatus}</span></p>
            <p class="mb-1"><strong>Payment Method:</strong> ${order.paymentMethod}</p>
            <p class="mb-1"><strong>Total Paid:</strong> ₹${order.totalAmount}</p>
            <p class="mb-0"><strong>Delivery Address:</strong> ${order.deliveryAddress}</p>
        </div>

        <div class="d-flex justify-content-center gap-3">
            <a href="${pageContext.request.contextPath}/orders/${order.orderId}" class="btn btn-glass-secondary px-4">View Order Details</a>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary-gradient px-4">Continue Shopping</a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
