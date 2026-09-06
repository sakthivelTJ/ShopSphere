<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold m-0">Order Details <span class="text-primary">#${order.orderId}</span></h3>
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-glass-secondary">
            <i class="bi bi-arrow-left me-1"></i> Back to Orders
        </a>
    </div>

    <div class="row g-4">
        <!-- Order Items List -->
        <div class="col-lg-8">
            <div class="card glass-card p-4 shadow-sm">
                <h5 class="fw-bold mb-3">Purchased Items</h5>
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Size</th>
                                <th>Unit Price</th>
                                <th>Qty</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${order.items}">
                                <tr>
                                    <td class="fw-bold">${item.productName}</td>
                                    <td><span class="badge bg-secondary-subtle text-secondary-subtle">${item.sizeLabel}</span></td>
                                    <td>₹${item.unitPrice}</td>
                                    <td>${item.quantity}</td>
                                    <td class="fw-bold text-primary">₹${item.subtotal}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Meta Info -->
        <div class="col-lg-4">
            <div class="card glass-card p-4 shadow-sm">
                <h5 class="fw-bold mb-3">Order Summary</h5>
                <p class="mb-2"><strong>Order Date:</strong> ${order.orderDate}</p>
                <p class="mb-2"><strong>Status:</strong> <span class="badge bg-warning text-dark">${order.orderStatus}</span></p>
                <p class="mb-2"><strong>Payment Method:</strong> ${order.paymentMethod}</p>
                <hr>
                <p class="mb-2"><strong>Delivery Address:</strong></p>
                <p class="text-muted small mb-3">${order.deliveryAddress}</p>
                <hr>
                <div class="d-flex justify-content-between fs-5 fw-bold">
                    <span>Total Amount:</span>
                    <span class="price-tag">₹${order.totalAmount}</span>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
