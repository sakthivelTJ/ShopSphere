<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <div class="card glass-card p-4 p-md-5">
        <div class="row g-4 align-items-center">
            <div class="col-md-6 text-center">
                <div class="rounded-4 overflow-hidden shadow-lg border" style="max-height: 480px;">
                    <c:choose>
                        <c:when test="${not empty product.imageUrl}">
                            <img src="${product.imageUrl}" class="img-fluid w-100 object-fit-cover" alt="${product.productName}" onerror="this.onerror=null; this.src='https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop';">
                        </c:when>
                        <c:otherwise>
                            <img src="https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop" class="img-fluid w-100 object-fit-cover" alt="Placeholder">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="col-md-6">
                <span class="badge bg-primary-subtle text-primary rounded-pill px-3 py-2 fw-bold mb-3">${product.category.categoryName}</span>
                <h2 class="fw-bold mb-3">${product.productName}</h2>

                <div class="d-flex align-items-baseline gap-3 mb-4">
                    <span class="display-5 fw-extrabold price-tag">₹${product.effectivePrice}</span>
                    <c:if test="${product.discountPercent > 0}">
                        <span class="fs-4 text-muted text-decoration-line-through">₹${product.price}</span>
                        <span class="badge bg-danger rounded-pill px-3 py-1 fs-6">${product.discountPercent}% OFF</span>
                    </c:if>
                </div>

                <p class="text-secondary lead fs-6 mb-4">${product.description}</p>

                <form action="${pageContext.request.contextPath}/cart/add" method="POST">
                    <input type="hidden" name="productId" value="${product.productId}">

                    <!-- Size Selection -->
                    <div class="mb-4">
                        <label class="form-label fw-bold">Select Variant / Option:</label>
                        <select name="productSizeId" class="form-select border-0 py-2.5 fs-6" required>
                            <c:forEach var="size" items="${product.productSizes}">
                                <c:choose>
                                    <c:when test="${size.isAvailable && size.stockQuantity > 0}">
                                        <option value="${size.productSizeId}">${size.sizeLabel} (In Stock: ${size.stockQuantity})</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${size.productSizeId}" disabled>${size.sizeLabel} (Out of Stock)</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Quantity Control -->
                    <div class="mb-4">
                        <label class="form-label fw-bold">Quantity:</label>
                        <input type="number" name="quantity" class="form-control border-0 w-25" value="1" min="1" max="50" required>
                    </div>

                    <div class="d-grid gap-3 d-md-flex">
                        <button type="submit" class="btn btn-primary-gradient px-5 py-3 fs-6">
                            <i class="bi bi-cart-plus-fill me-2"></i> Add to Cart
                        </button>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-glass-secondary px-4 py-3">
                            Back to Products
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
