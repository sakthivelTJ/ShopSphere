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
                        <h5 class="fw-bold mb-3"><i class="bi bi-trophy-fill text-warning me-2"></i> Top Selling Products</h5>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Units Sold</th>
                                        <th>Total Sales Revenue</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${dashboard.topSellingProducts}">
                                        <tr>
                                            <td class="fw-bold">${item.product_name}</td>
                                            <td><span class="badge bg-primary-subtle text-primary-subtle">${item.total_sold} units</span></td>
                                            <td class="fw-bold text-success">₹${item.total_revenue}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Low Stock Alert -->
                <div class="col-lg-6">
                    <div class="card glass-card p-4 shadow-sm h-100">
                        <h5 class="fw-bold mb-3"><i class="bi bi-exclamation-triangle-fill text-danger me-2"></i> Low Stock Alert</h5>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Variant</th>
                                        <th>Remaining Stock</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${dashboard.lowStockProducts}">
                                        <tr>
                                            <td class="fw-bold">${item.product_name}</td>
                                            <td><span class="badge bg-secondary-subtle text-secondary-subtle">${item.size_label}</span></td>
                                            <td><span class="badge bg-danger">${item.stock_quantity} left</span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
