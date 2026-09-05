<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card glass-card p-4 p-md-5 shadow-lg rounded-4">
                <div class="text-center mb-4">
                    <i class="bi bi-person-plus display-4 text-primary"></i>
                    <h3 class="fw-bold mt-2">Create Account</h3>
                    <p class="text-muted small">Join ShopSphere for exclusive shopping deals</p>
                </div>

                <jsp:include page="/WEB-INF/views/common/messages.jsp" />

                <form action="${pageContext.request.contextPath}/register" method="POST">
                    <div class="row g-3 mb-3">
                        <div class="col-12">
                            <label class="form-label fw-semibold">Full Name</label>
                            <input type="text" name="fullName" class="form-control bg-light border-0" placeholder="John Doe" value="${registerRequest.fullName}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Email Address</label>
                            <input type="email" name="email" class="form-control bg-light border-0" placeholder="john@example.com" value="${registerRequest.email}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Phone Number</label>
                            <input type="text" name="phone" class="form-control bg-light border-0" placeholder="+1 234 567 890" value="${registerRequest.phone}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Password</label>
                            <input type="password" name="password" class="form-control bg-light border-0" placeholder="At least 6 characters" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Gender</label>
                            <select name="gender" class="form-select bg-light border-0">
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-semibold">Default Delivery Address</label>
                            <textarea name="address" class="form-control bg-light border-0" rows="2" placeholder="123 Main St, Apt 4B, New York, NY">${registerRequest.address}</textarea>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary-custom w-100 py-2.5 rounded-pill font-weight-bold shadow-sm mt-2">
                        Create Account <i class="bi bi-check-lg"></i>
                    </button>
                </form>

                <div class="text-center mt-4">
                    <p class="text-muted small mb-0">Already have an account? <a href="${pageContext.request.contextPath}/login" class="fw-bold text-primary text-decoration-none">Sign In</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
