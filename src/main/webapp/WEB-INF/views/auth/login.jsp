<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card glass-card p-4 p-md-5 shadow-lg rounded-4">
                <div class="text-center mb-4">
                    <i class="bi bi-person-lock display-4 text-primary"></i>
                    <h3 class="fw-bold mt-2">Welcome Back</h3>
                    <p class="text-muted small">Sign in to your ShopSphere account</p>
                </div>

                <jsp:include page="/WEB-INF/views/common/messages.jsp" />

                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Email Address</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light border-0"><i class="bi bi-envelope"></i></span>
                            <input type="email" name="email" class="form-control bg-light border-0" placeholder="name@example.com" value="${loginRequest.email}" required>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label class="form-label fw-semibold">Password</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light border-0"><i class="bi bi-lock"></i></span>
                            <input type="password" name="password" class="form-control bg-light border-0" placeholder="••••••••" required>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary-custom w-100 py-2.5 rounded-pill font-weight-bold shadow-sm">
                        Sign In <i class="bi bi-arrow-right-short fs-5 align-middle"></i>
                    </button>
                </form>

                <div class="text-center mt-4">
                    <p class="text-muted small mb-0">Don't have an account? <a href="${pageContext.request.contextPath}/register" class="fw-bold text-primary text-decoration-none">Sign Up</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
