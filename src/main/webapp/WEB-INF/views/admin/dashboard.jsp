<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container-fluid my-4">
    <div class="row">
        <!-- Admin Navigation Sidebar -->
        <div class="col-md-3 col-lg-2 admin-sidebar p-3 mb-4">
            <h6 class="text-uppercase text-muted small fw-bold px-3 mb-3">Admin Portal</h6>
            <nav class="nav flex-column">
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2 me-2"></i> Dashboard</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/products"><i class="bi bi-box-seam me-2"></i> Products</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories"><i class="bi bi-tags me-2"></i> Categories</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/inventory"><i class="bi bi-boxes me-2"></i> Inventory</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders"><i class="bi bi-cart-check me-2"></i> Orders</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people me-2"></i> Users</a>
            </nav>
        </div>

        <!-- Main Dashboard Body -->
        <div class="col-md-9 col-lg-10">
            <jsp:include page="/WEB-INF/views/common/messages.jsp" />

            <h3 class="fw-bold mb-4">Store Analytics & Management Dashboard</h3>

            <!-- 4 Stat Summary Cards -->
            <div class="row g-4 mb-5">
                <div class="col-sm-6 col-xl-3">
                    <div class="stat-card stat-users shadow-sm">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <small class="text-uppercase opacity-75 fw-bold">Total Customers</small>
                                <h2 class="fw-bold mb-0 mt-1">${dashboard.totalUsers}</h2>
                            </div>
                            <i class="bi bi-people fs-2 text-primary opacity-75"></i>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-xl-3">
                    <div class="stat-card stat-products shadow-sm">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <small class="text-uppercase opacity-75 fw-bold">Total Products</small>
                                <h2 class="fw-bold mb-0 mt-1">${dashboard.totalProducts}</h2>
                            </div>
                            <i class="bi bi-box-seam fs-2 text-info opacity-75"></i>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-xl-3">
                    <div class="stat-card stat-orders shadow-sm">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <small class="text-uppercase opacity-75 fw-bold">Total Orders</small>
                                <h2 class="fw-bold mb-0 mt-1">${dashboard.totalOrders}</h2>
                            </div>
                            <i class="bi bi-cart-check fs-2 text-warning opacity-75"></i>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-xl-3">
                    <div class="stat-card stat-revenue shadow-sm">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <small class="text-uppercase opacity-75 fw-bold">Gross Revenue</small>
                                <h2 class="fw-bold mb-0 mt-1">₹${dashboard.totalRevenue}</h2>
                            </div>
                            <i class="bi bi-currency-rupee fs-2 text-success opacity-75"></i>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Report Tables (JDBC Aggregation output) -->
            <div class="row g-4">
                <!-- Top Selling Products -->
                <div class="col-lg-6">
                    <div class="card glass-card p-4 shadow-sm h-100">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h5 class="fw-bold mb-0"><i class="bi bi-trophy-fill text-warning me-2"></i> Top Selling Products</h5>
                            <span class="badge bg-primary rounded-pill">Top Performers</span>
                        </div>
                        <div class="table-responsive">
                            <c:choose>
                                <c:when test="${not empty dashboard.topSellingProducts}">
                                    <table class="table table-hover align-middle">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th class="text-center">Units Sold</th>
                                                <th class="text-end">Sales Revenue</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${dashboard.topSellingProducts}">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex align-items-center gap-2">
                                                            <c:if test="${not empty item.image_url}">
                                                                <img src="${item.image_url}" alt="${item.product_name}" class="rounded border border-secondary" style="width: 40px; height: 40px; object-fit: cover;">
                                                            </c:if>
                                                            <span class="fw-bold text-truncate" style="max-width: 180px;" title="${item.product_name}">${item.product_name}</span>
                                                        </div>
                                                    </td>
                                                    <td class="text-center"><span class="badge bg-primary px-3 py-2 fs-7">${item.total_sold} units</span></td>
                                                    <td class="text-end fw-bold text-success">₹${item.total_revenue}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center py-4 text-muted">
                                        <i class="bi bi-cart-x fs-1 opacity-50 d-block mb-2"></i>
                                        <p class="mb-0">No sales recorded yet. Once orders are placed, top selling items will appear here.</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <!-- Low Stock Alert -->
                <div class="col-lg-6">
                    <div class="card glass-card p-4 shadow-sm h-100">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h5 class="fw-bold mb-0"><i class="bi bi-exclamation-triangle-fill text-danger me-2"></i> Low Stock Alert</h5>
                            <span class="badge bg-danger rounded-pill">Action Required</span>
                        </div>
                        <div class="table-responsive">
                            <c:choose>
                                <c:when test="${not empty dashboard.lowStockProducts}">
                                    <table class="table table-hover align-middle">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th class="text-center">Variant</th>
                                                <th class="text-end">Remaining Stock</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${dashboard.lowStockProducts}">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex align-items-center gap-2">
                                                            <c:if test="${not empty item.image_url}">
                                                                <img src="${item.image_url}" alt="${item.product_name}" class="rounded border border-secondary" style="width: 40px; height: 40px; object-fit: cover;">
                                                            </c:if>
                                                            <span class="fw-bold text-truncate" style="max-width: 180px;" title="${item.product_name}">${item.product_name}</span>
                                                        </div>
                                                    </td>
                                                    <td class="text-center"><span class="badge bg-secondary px-2 py-1">${item.size_label}</span></td>
                                                    <td class="text-end"><span class="badge bg-danger px-3 py-2 fs-7"><i class="bi bi-box-seam me-1"></i> ${item.stock_quantity} left</span></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="text-center py-4 text-success">
                                        <i class="bi bi-check-circle fs-1 opacity-75 d-block mb-2"></i>
                                        <p class="mb-0">All products have healthy inventory levels!</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
