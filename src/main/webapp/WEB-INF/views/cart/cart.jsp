<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <h3 class="fw-bold mb-4"><i class="bi bi-cart3 text-primary me-2"></i> Shopping Cart</h3>

    <c:choose>
        <c:when test="${empty cart || empty cart.cartItems}">
            <div class="card glass-card p-5 text-center shadow-sm">
                <i class="bi bi-cart-x display-1 text-muted mb-3"></i>
                <h4>Your cart is empty</h4>
                <p class="text-muted">Looks like you haven't added any products to your cart yet.</p>
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary-gradient px-4">Start Shopping</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <!-- Cart Items List -->
                <div class="col-lg-8">
                    <div class="card glass-card p-4 shadow-sm">
                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Variant</th>
                                        <th>Price</th>
                                        <th>Qty</th>
                                        <th>Subtotal</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="grandTotal" value="0" />
                                    <c:forEach var="item" items="${cart.cartItems}">
                                        <c:set var="grandTotal" value="${grandTotal + item.subtotal}" />
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center gap-3">
                                                    <c:choose>
                                                        <c:when test="${not empty item.product.imageUrl}">
                                                            <img src="${item.product.imageUrl}" class="rounded-3" style="width: 55px; height: 55px; object-fit: cover;" alt="">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="bg-light rounded-3 d-flex align-items-center justify-content-center" style="width: 55px; height: 55px;">
                                                                <i class="bi bi-image text-muted"></i>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div>
                                                        <a href="${pageContext.request.contextPath}/products/${item.product.productId}" class="fw-bold text-decoration-none">${item.product.productName}</a>
                                                    </div>
                                                </div>
                                            </td>
                                            <td><span class="badge bg-secondary-subtle text-secondary">${item.sizeLabel}</span></td>
                                            <td class="fw-semibold">₹${item.unitPrice}</td>
                                            <td style="width: 140px;">
                                                <form action="${pageContext.request.contextPath}/cart/update" method="POST" class="d-flex gap-1 align-items-center">
                                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                    <input type="number" name="quantity" value="${item.quantity}" min="1" max="50" class="form-control form-control-sm text-center">
                                                    <button type="submit" class="btn btn-sm btn-outline-primary" title="Update"><i class="bi bi-check-lg"></i></button>
                                                </form>
                                            </td>
                                            <td class="fw-bold text-primary">₹${item.subtotal}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/cart/remove/${item.cartItemId}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Remove item from cart?')">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Summary Card -->
                <div class="col-lg-4">
                    <div class="card glass-card p-4 shadow-sm">
                        <h5 class="fw-bold mb-4">Order Summary</h5>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Subtotal</span>
                            <span class="fw-semibold">₹${grandTotal}</span>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <span class="text-muted">Express Shipping</span>
                            <span class="text-success fw-semibold">FREE (India)</span>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between mb-4 fs-5 fw-bold">
                            <span>Total Payable</span>
                            <span class="text-primary">₹${grandTotal}</span>
                        </div>
                        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary-gradient w-100 py-3 fw-bold">
                            Proceed to Checkout <i class="bi bi-arrow-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
