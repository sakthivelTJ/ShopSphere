<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container my-5">
    <jsp:include page="/WEB-INF/views/common/messages.jsp" />

    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card glass-card p-4 p-md-5 shadow-lg rounded-4 text-center">
                <i class="bi bi-person-circle display-1 text-primary mb-3"></i>
                <h3 class="fw-bold mb-1">${user.fullName}</h3>
                <span class="badge bg-primary-subtle text-primary rounded-pill px-3 py-1 mb-4">${user.role}</span>

                <div class="bg-light p-4 rounded-3 text-start mb-4">
                    <p class="mb-2"><strong>Email:</strong> ${user.email}</p>
                    <p class="mb-2"><strong>Phone:</strong> ${user.phone != null ? user.phone : 'Not provided'}</p>
                    <p class="mb-2"><strong>Gender:</strong> ${user.gender != null ? user.gender : 'Not specified'}</p>
                    <p class="mb-0"><strong>Address:</strong> ${user.address != null ? user.address : 'No default address saved'}</p>
                </div>

                <div>
                    <a href="${pageContext.request.contextPath}/user/profile/edit" class="btn btn-primary-custom rounded-pill px-4">Edit Profile</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
