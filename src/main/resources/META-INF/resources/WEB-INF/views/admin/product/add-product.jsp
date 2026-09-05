<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-7">
            <div class="card glass-card p-4 p-md-5 shadow-lg">
                <h3 class="fw-bold mb-4">Add New Product</h3>

                <form action="${pageContext.request.contextPath}/admin/products/add" method="POST">
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Category *</label>
                        <select name="categoryId" class="form-select" required>
                            <option value="">Select Category</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryId}">${cat.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-semibold">Product Name *</label>
                        <input type="text" name="productName" class="form-control" placeholder="e.g. OnePlus 12 5G (16GB RAM)" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-semibold">Description</label>
                        <textarea name="description" class="form-control" rows="3" placeholder="Product specifications and features..."></textarea>
                    </div>

                    <div class="row g-3 mb-3">
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Price (₹ INR) *</label>
                            <input type="number" step="0.01" name="price" class="form-control" placeholder="64999.00" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Discount Percent (%)</label>
                            <input type="number" step="0.01" name="discountPercent" class="form-control" value="0.00">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-semibold">Image URL</label>
                        <input type="url" name="imageUrl" class="form-control" placeholder="https://images.unsplash.com/...">
                    </div>

                    <div class="row g-3 mb-4">
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Default Variant Label</label>
                            <input type="text" name="defaultSizeLabel" class="form-control" value="Standard / Free Size">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Initial Stock Quantity</label>
                            <input type="number" name="initialStock" class="form-control" value="25">
                        </div>
                    </div>

                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-glass-secondary px-4">Cancel</a>
                        <button type="submit" class="btn btn-primary-gradient px-4">Save Product</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
