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
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/categories"><i class="bi bi-tags me-2"></i> Categories</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/inventory"><i class="bi bi-boxes me-2"></i> Inventory</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders"><i class="bi bi-cart-check me-2"></i> Orders</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-people me-2"></i> Users</a>
            </nav>
        </div>

        <div class="col-md-9 col-lg-10">
            <jsp:include page="/WEB-INF/views/common/messages.jsp" />

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold m-0">Category Management</h3>
                <a href="${pageContext.request.contextPath}/admin/categories/add" class="btn btn-primary-custom rounded-pill">
                    <i class="bi bi-plus-lg"></i> Add New Category
                </a>
            </div>

            <div class="card glass-card shadow-sm rounded-4 overflow-hidden">
                <div class="table-responsive">
                    <table class="table table-hover align-middle m-0">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Category Name</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${categories}">
                                <tr>
                                    <td>#${c.categoryId}</td>
                                    <td class="fw-bold">${c.categoryName}</td>
                                    <td>${c.description}</td>
                                    <td>
                                        <span class="badge ${c.isActive ? 'bg-success' : 'bg-secondary'}">
                                            ${c.isActive ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/categories/edit/${c.categoryId}" class="btn btn-sm btn-outline-primary me-1">
                                            <i class="bi bi-pencil"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/categories/toggle/${c.categoryId}" class="btn btn-sm ${c.isActive ? 'btn-outline-warning' : 'btn-outline-success'}">
                                            ${c.isActive ? 'Deactivate' : 'Activate'}
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
