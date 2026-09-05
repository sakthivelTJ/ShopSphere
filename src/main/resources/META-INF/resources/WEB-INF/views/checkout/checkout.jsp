<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <h3 class="fw-bold mb-4"><i class="bi bi-shield-check text-primary me-2"></i> Secure Checkout (India)</h3>

    <div class="row g-4">
        <!-- Delivery and Payment Form -->
        <div class="col-lg-7">
            <div class="card glass-card p-4 shadow-sm">
                <h5 class="fw-bold mb-4">Shipping & Payment Details</h5>

                <form action="${pageContext.request.contextPath}/checkout/place-order" method="POST">
                    <div class="mb-4">
                        <label class="form-label fw-semibold">Recipient Full Name</label>
                        <input type="text" class="form-control py-2" value="${user.fullName}" readonly>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Delivery Address in India *</label>
                        <textarea name="deliveryAddress" class="form-control" rows="3" placeholder="Flat / House No., Street, Landmark, City, State, Pincode" required>${checkoutRequest.deliveryAddress}</textarea>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Select Payment Method *</label>
                        <div class="d-flex flex-column gap-3">
                            <div class="form-check p-3 border rounded-3 glass-card">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="payCOD" value="COD" checked>
                                <label class="form-check-label fw-semibold ms-2" for="payCOD">
                                    <i class="bi bi-cash-coin text-success fs-5 me-1"></i> Cash on Delivery (COD)
                                </label>
                            </div>
                            <div class="form-check p-3 border rounded-3 glass-card">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="payUPI" value="UPI">
                                <label class="form-check-label fw-semibold ms-2" for="payUPI">
                                    <i class="bi bi-qr-code-scan text-warning fs-5 me-1"></i> UPI (Google Pay, PhonePe, Paytm)
                                </label>
                            </div>
                            <div class="form-check p-3 border rounded-3 glass-card">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="payCard" value="CREDIT_CARD">
                                <label class="form-check-label fw-semibold ms-2" for="payCard">
                                    <i class="bi bi-credit-card-2-front text-primary fs-5 me-1"></i> Credit / Debit Card (RuPay, Visa, Mastercard)
                                </label>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary-gradient w-100 py-3 font-weight-bold shadow">
                        Place Order <i class="bi bi-bag-check-fill ms-2"></i>
                    </button>
                </form>
            </div>
        </div>

        <!-- Order Summary Column -->
        <div class="col-lg-5">
            <div class="card glass-card p-4 shadow-sm">
                <h5 class="fw-bold mb-4">Items Summary (${cart.cartItems.size()})</h5>
                <c:set var="totalPrice" value="0" />
                <div class="list-group list-group-flush mb-4">
                    <c:forEach var="item" items="${cart.cartItems}">
                        <c:set var="totalPrice" value="${totalPrice + item.subtotal}" />
                        <div class="list-group-item bg-transparent d-flex justify-content-between align-items-center py-3">
                            <div>
                                <h6 class="mb-0 fw-bold">${item.product.productName}</h6>
                                <small class="text-muted">Size: ${item.sizeLabel} | Qty: ${item.quantity}</small>
                            </div>
                            <span class="fw-bold text-primary">₹${item.subtotal}</span>
                        </div>
                    </c:forEach>
                </div>
                <hr>
                <div class="d-flex justify-content-between fs-5 fw-bold mt-2">
                    <span>Total Amount Payable:</span>
                    <span class="price-tag">₹${totalPrice}</span>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
