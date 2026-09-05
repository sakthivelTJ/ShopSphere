<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <!-- Glassmorphic Hero Banner -->
    <div class="hero-glass mb-5 d-flex flex-column flex-md-row align-items-center justify-content-between">
        <div class="z-1 max-w-600">
            <span class="badge bg-white text-primary rounded-pill px-3 py-2 fw-bold mb-3 shadow-sm">
                <i class="bi bi-stars"></i> Exclusive Festival Offers Across India
            </span>
            <h1 class="display-4 fw-extrabold mb-3 text-white">Experience Next-Gen Online Shopping</h1>
            <p class="lead mb-4 opacity-90 text-white-50">Discover top brands in Electronics, Fashion, Home Decor & Lifestyle with free express delivery in ₹ INR.</p>
            <div class="d-flex flex-wrap gap-3">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-light btn-lg rounded-pill px-4 fw-bold text-primary shadow">
                    Explore Catalog <i class="bi bi-arrow-right ms-2"></i>
                </a>
            </div>
        </div>
        <div class="mt-4 mt-md-0 z-1 animated-float">
            <i class="bi bi-bag-heart display-1 text-white opacity-75"></i>
        </div>
    </div>

    <!-- Category Pills -->
    <div class="d-flex align-items-center justify-content-between mb-3">
        <h4 class="fw-bold m-0"><i class="bi bi-grid text-primary me-2"></i> Shop By Category</h4>
    </div>
    <div class="d-flex flex-wrap gap-2 mb-5">
        <a href="${pageContext.request.contextPath}/products" class="category-pill active">All Categories</a>
        <c:forEach var="cat" items="${categories}">
            <a href="${pageContext.request.contextPath}/products?categoryId=${cat.categoryId}" class="category-pill">
                ${cat.categoryName}
            </a>
        </c:forEach>
    </div>

    <!-- Featured Products Grid -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h4 class="fw-bold m-0"><i class="bi bi-fire text-danger me-2"></i> Trending Products in India</h4>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-sm btn-glass-secondary">View All <i class="bi bi-chevron-right ms-1"></i></a>
    </div>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 mb-5">
        <c:forEach var="product" items="${featuredProducts}">
            <div class="col">
                <div class="card product-card h-100">
                    <div class="product-img-box">
                        <c:if test="${product.discountPercent > 0}">
                            <span class="badge-inr-discount">-${product.discountPercent}%</span>
                        </c:if>
                        <c:choose>
                            <c:when test="${not empty product.imageUrl}">
                                <img src="${product.imageUrl}" alt="${product.productName}" onerror="this.onerror=null; this.src='https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop';">
                            </c:when>
                            <c:otherwise>
                                <img src="https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop" alt="Placeholder">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body d-flex flex-column p-4">
                        <span class="text-muted small mb-1 fw-semibold">${product.category.categoryName}</span>
                        <h6 class="card-title fw-bold mb-2 text-truncate">${product.productName}</h6>
                        <div class="mt-auto pt-3 d-flex align-items-center justify-content-between">
                            <div>
                                <span class="price-tag">₹${product.effectivePrice}</span>
                                <c:if test="${product.discountPercent > 0}">
                                    <span class="text-muted text-decoration-line-through small ms-1">₹${product.price}</span>
                                </c:if>
                            </div>
                            <a href="${pageContext.request.contextPath}/products/${product.productId}" class="btn btn-primary-gradient rounded-circle p-0 d-flex align-items-center justify-content-center" style="width: 42px; height: 42px;">
                                <i class="bi bi-arrow-right fs-5"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
