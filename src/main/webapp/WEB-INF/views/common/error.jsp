<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5 text-center">
    <div class="card glass-card max-w-500 mx-auto p-5 shadow-lg">
        <i class="bi bi-exclamation-triangle-fill display-1 text-warning mb-3"></i>
        <h2 class="fw-bold mb-3">Oops! Something went wrong</h2>
        <p class="text-muted mb-4">${errorMessage != null ? errorMessage : "An unexpected error occurred while processing your request."}</p>
        <div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary-custom rounded-pill px-4">Back to Home</a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
