<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <div class="row g-4">
        <!-- Sidebar Filter -->
        <div class="col-md-3">
            <div class="card glass-card p-4">
                <h5 class="fw-bold mb-3"><i class="bi bi-funnel text-primary me-2"></i> Categories</h5>
                <div class="d-flex flex-column gap-2">
                    <a href="${pageContext.request.contextPath}/products" class="category-pill ${empty selectedCategoryId ? 'active' : ''}">
                        All Categories
                    </a>
                    <c:forEach var="cat" items="${categories}">
                        <a href="${pageContext.request.contextPath}/products?categoryId=${cat.categoryId}" 
                           class="category-pill ${selectedCategoryId == cat.categoryId ? 'active' : ''}">
                            ${cat.categoryName}
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>

        <!-- Product Grid -->
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h4 class="fw-bold m-0">Products Collection (${products.size()} Items)</h4>
            </div>

            <c:choose>
                <c:when test="${empty products}">
                    <div class="card glass-card text-center p-5">
                        <i class="bi bi-search display-4 text-muted mb-3"></i>
                        <h5>No products found in this category</h5>
                        <p class="text-muted">Explore other categories or check back soon.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
                        <c:forEach var="product" items="${products}">
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
                                                <i class="bi bi-eye-fill fs-6"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
