<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container-fluid my-4">
    <div class="row">
        <!-- Admin Sidebar -->
        <div class="col-md-3 col-lg-2 admin-sidebar p-3 rounded-4 mb-4">
            <h6 class="text-uppercase text-white-50 small fw-bold px-3 mb-3">Admin Portal</h6>
            <nav class="nav flex-column">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2 me-2"></i> Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/products"><i class="bi bi-box-seam me-2"></i> Products</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories"><i class="bi bi-tags me-2"></i> Categories</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/inventory"><i class="bi bi-boxes me-2"></i> Inventory</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders"><i class="bi bi-cart-check me-2"></i> Orders</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people me-2"></i> Users</a>
            </nav>
        </div>

        <div class="col-md-9 col-lg-10">
            <jsp:include page="/WEB-INF/views/common/messages.jsp" />

            <h3 class="fw-bold mb-4">Inventory & Stock Controls</h3>

            <div class="card glass-card shadow-sm rounded-4 overflow-hidden">
                <div class="table-responsive">
                    <table class="table table-hover align-middle m-0">
                        <thead class="table-light">
                            <tr>
                                <th>Product</th>
                                <th>Size / Variant</th>
                                <th>SKU Code</th>
                                <th>Stock Level</th>
                                <th>Stock Status</th>
                                <th>Update Stock</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${products}">
                                <c:forEach var="size" items="${p.productSizes}">
                                    <tr>
                                        <td class="fw-bold">${p.productName}</td>
                                        <td><span class="badge bg-secondary-subtle text-secondary">${size.sizeLabel}</span></td>
                                        <td><code>${size.skuCode}</code></td>
                                        <td class="fw-bold">${size.stockQuantity}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${size.stockQuantity == 0}">
                                                    <span class="badge bg-danger">Out of Stock</span>
                                                </c:when>
                                                <c:when test="${size.stockQuantity <= 5}">
                                                    <span class="badge bg-warning text-dark">Low Stock (${size.stockQuantity})</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-success">In Stock</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="width: 200px;">
                                            <form action="${pageContext.request.contextPath}/admin/inventory/update-stock" method="POST" class="d-flex gap-2">
                                                <input type="hidden" name="productSizeId" value="${size.productSizeId}">
                                                <input type="number" name="stockQuantity" value="${size.stockQuantity}" min="0" class="form-control form-control-sm">
                                                <button type="submit" class="btn btn-sm btn-primary-custom">Update</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
