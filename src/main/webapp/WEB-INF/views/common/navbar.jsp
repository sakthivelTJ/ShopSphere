<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-glass sticky-top py-3">
    <div class="container">
        <a class="navbar-brand navbar-brand-logo" href="${pageContext.request.contextPath}/home">
            <i class="bi bi-shop-window text-primary"></i> ShopSphere
        </a>
        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMain">
            <i class="bi bi-list fs-1 text-primary"></i>
        </button>

        <div class="collapse navbar-collapse" id="navbarMain">
            <!-- Search Bar -->
            <form class="d-flex mx-auto my-2 my-lg-0 w-50" action="${pageContext.request.contextPath}/products/search" method="GET">
                <div class="input-group">
                    <input class="form-control rounded-start-pill ps-4 border-end-0" type="search" name="query" placeholder="Search products in ₹ INR..." value="${searchQuery}">
                    <button class="btn btn-primary-gradient rounded-end-pill px-4" type="submit">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </form>

            <ul class="navbar-nav ms-auto align-items-center gap-3">
                <li class="nav-item">
                    <a class="nav-link fw-semibold" href="${pageContext.request.contextPath}/products"><i class="bi bi-grid-fill me-1"></i> Products</a>
                </li>

                <!-- Theme Toggle Button -->
                <li class="nav-item">
                    <button id="themeToggleBtn" class="theme-toggle-btn" title="Toggle Light/Dark Theme">
                        <i class="bi bi-sun-fill" id="themeIcon"></i>
                    </button>
                </li>

                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser}">
                        <li class="nav-item">
                            <a class="nav-link fw-semibold position-relative" href="${pageContext.request.contextPath}/cart">
                                <i class="bi bi-cart3 fs-5 me-1"></i> Cart
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link fw-semibold" href="${pageContext.request.contextPath}/orders"><i class="bi bi-box-seam-fill me-1"></i> My Orders</a>
                        </li>
                        <c:if test="${sessionScope.userRole == 'ADMIN'}">
                            <li class="nav-item">
                                <a class="btn btn-sm btn-glass-secondary rounded-pill px-3" href="${pageContext.request.contextPath}/admin/dashboard">
                                    <i class="bi bi-speedometer2 text-warning me-1"></i> Admin Panel
                                </a>
                            </li>
                        </c:if>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle d-flex align-items-center gap-1 fw-bold" href="#" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle fs-5 text-primary"></i> ${sessionScope.userName}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow-lg glass-card border-0">
                                <li><a class="dropdown-item py-2" href="${pageContext.request.contextPath}/user/profile"><i class="bi bi-person-badge me-2"></i> My Profile</a></li>
                                <li><hr class="dropdown-divider opacity-10"></li>
                                <li><a class="dropdown-item py-2 text-danger" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i> Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link fw-semibold" href="${pageContext.request.contextPath}/login"><i class="bi bi-box-arrow-in-right me-1"></i> Sign In</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-primary-gradient px-4 py-2" href="${pageContext.request.contextPath}/register">Sign Up</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const themeBtn = document.getElementById('themeToggleBtn');
        const themeIcon = document.getElementById('themeIcon');

        function updateIcon(theme) {
            if (theme === 'dark') {
                themeIcon.className = 'bi bi-sun-fill text-warning';
            } else {
                themeIcon.className = 'bi bi-moon-stars-fill text-primary';
            }
        }

        const currentTheme = document.documentElement.getAttribute('data-theme') || 'dark';
        updateIcon(currentTheme);

        themeBtn.addEventListener('click', () => {
            const activeTheme = document.documentElement.getAttribute('data-theme');
            const newTheme = activeTheme === 'dark' ? 'light' : 'dark';
            document.documentElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('shopsphere_theme', newTheme);
            updateIcon(newTheme);
        });
    });
</script>
