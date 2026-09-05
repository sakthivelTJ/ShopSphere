<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-4">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <h3 class="fw-bold mb-4">Explore Categories</h3>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="cat" items="${categories}">
            <div class="col">
                <div class="card glass-card h-100 p-4 shadow-sm text-center">
                    <i class="bi bi-tags display-3 text-primary mb-3"></i>
                    <h4 class="fw-bold mb-2">${cat.categoryName}</h4>
                    <p class="text-muted flex-grow-1">${cat.description}</p>
                    <a href="${pageContext.request.contextPath}/products?categoryId=${cat.categoryId}" class="btn btn-outline-primary rounded-pill mt-3">
                        View Products <i class="bi bi-arrow-right"></i>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
