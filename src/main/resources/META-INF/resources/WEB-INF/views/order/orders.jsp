<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <h3 class="fw-bold mb-4"><i class="bi bi-box-seam text-primary me-2"></i> My Order History</h3>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="card glass-card p-5 text-center shadow-sm">
                <i class="bi bi-inbox display-1 text-muted mb-3"></i>
                <h4>No orders found</h4>
                <p class="text-muted">You haven't placed any orders yet.</p>
                <div>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary-gradient px-4">Start Shopping</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card glass-card shadow-sm overflow-hidden">
                <div class="table-responsive">
                    <table class="table table-hover align-middle m-0">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Date</th>
                                <th>Items</th>
                                <th>Total Amount</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td class="fw-bold text-primary">#${order.orderId}</td>
                                    <td>${order.orderDate}</td>
                                    <td>${order.orderItems.size()} item(s)</td>
                                    <td class="fw-bold">₹${order.totalAmount}</td>
                                    <td>
                                        <span class="badge ${order.orderStatus == 'DELIVERED' ? 'bg-success' : 'bg-warning text-dark'}">
                                            ${order.orderStatus}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/orders/${order.orderId}" class="btn btn-sm btn-glass-secondary">
                                            Details <i class="bi bi-chevron-right ms-1"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
