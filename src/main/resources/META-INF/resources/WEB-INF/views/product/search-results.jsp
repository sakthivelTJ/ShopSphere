<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <div class="mb-4">
        <h3 class="fw-bold">Search Results for "<span class="text-primary">${searchQuery}</span>"</h3>
        <p class="text-muted">Found ${products.size()} matching item(s)</p>
    </div>

    <c:choose>
        <c:when test="${empty products}">
            <div class="card glass-card text-center p-5">
                <i class="bi bi-search display-4 text-muted mb-3"></i>
                <h4>No matching products found</h4>
                <p class="text-muted">Try searching with different keywords or explore our full product catalog.</p>
                <div>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary-gradient mt-2">View All Products</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 mb-5">
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

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
