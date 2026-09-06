<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card glass-card p-4 p-md-5 shadow-lg rounded-4">
                <h3 class="fw-bold mb-4">Add New Category</h3>

                <form action="${pageContext.request.contextPath}/admin/categories/add" method="POST">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Category Name *</label>
                        <input type="text" name="categoryName" class="form-control bg-light border-0" placeholder="e.g. Electronics" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-semibold">Description</label>
                        <textarea name="description" class="form-control bg-light border-0" rows="3" placeholder="Category summary..."></textarea>
                    </div>

                    <div class="mb-4">
                        <div class="form-check">
                            <input type="checkbox" name="isActive" class="form-check-input" value="true" checked>
                            <label class="form-check-label fw-semibold">Category Active</label>
                        </div>
                    </div>

                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-glass-secondary px-4">Cancel</a>
                        <button type="submit" class="btn btn-primary-custom rounded-pill px-4">Save Category</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
