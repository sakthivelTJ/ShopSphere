<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold m-0">Admin Order Fulfillment <span class="text-primary">#${order.orderId}</span></h3>
        <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-glass-secondary">
            <i class="bi bi-arrow-left me-1"></i> Back to Orders
        </a>
    </div>

    <div class="row g-4">
        <!-- Order Items -->
        <div class="col-lg-8">
            <div class="card glass-card p-4 shadow-sm rounded-4 mb-4">
                <h5 class="fw-bold mb-3">Line Items</h5>
                <div class="table-responsive">
                    <table class="table align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Item</th>
                                <th>Variant</th>
                                <th>Price</th>
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

        <!-- Status Update & Info -->
        <div class="col-lg-4">
            <div class="card glass-card p-4 shadow-sm rounded-4 mb-4">
                <h5 class="fw-bold mb-3">Order Status Control</h5>
                <form action="${pageContext.request.contextPath}/admin/orders/status" method="POST">
                    <input type="hidden" name="orderId" value="${order.orderId}">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Current Status:</label>
                        <select name="orderStatus" class="form-select bg-light border-0">
                            <option value="PENDING" ${order.orderStatus == 'PENDING' ? 'selected' : ''}>PENDING</option>
                            <option value="PROCESSING" ${order.orderStatus == 'PROCESSING' ? 'selected' : ''}>PROCESSING</option>
                            <option value="SHIPPED" ${order.orderStatus == 'SHIPPED' ? 'selected' : ''}>SHIPPED</option>
                            <option value="DELIVERED" ${order.orderStatus == 'DELIVERED' ? 'selected' : ''}>DELIVERED</option>
                            <option value="CANCELLED" ${order.orderStatus == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary-custom w-100 rounded-pill">Update Status</button>
                </form>
            </div>

            <div class="card glass-card p-4 shadow-sm rounded-4">
                <h5 class="fw-bold mb-3">Customer Information</h5>
                <p class="mb-1"><strong>Name:</strong> ${order.userName}</p>
                <p class="mb-1"><strong>Email:</strong> ${order.userEmail}</p>
                <p class="mb-1"><strong>Payment:</strong> ${order.paymentMethod}</p>
                <p class="mb-1"><strong>Address:</strong> ${order.deliveryAddress}</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
