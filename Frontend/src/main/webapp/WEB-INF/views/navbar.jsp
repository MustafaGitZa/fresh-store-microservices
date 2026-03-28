<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar">
    <div class="navbar-left">
        <div class="navbar-brand">
            <img src="https://cdn-icons-png.flaticon.com/512/2553/2553651.png" alt="FNB Store"/>
            FNB Fresh Store
        </div>
        <c:if test="${not empty sessionScope.token}">
            <ul class="navbar-nav">
                <li><a href="/products">Products</a></li>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <li><a href="/admin">Dashboard</a></li>
                    <li><a href="/inventory">Inventory</a></li>
                </c:if>
            </ul>
        </c:if>
    </div>

    <div class="navbar-right">
        <c:if test="${not empty sessionScope.token}">

            <!-- Cart -->
            <a href="/cart" class="cart-btn">
                &#128722;
                <c:if test="${not empty sessionScope.cart and sessionScope.cart.size() > 0}">
                    <span class="cart-badge">${sessionScope.cart.size()}</span>
                </c:if>
            </a>

            <!-- Profile Dropdown -->
            <div class="profile-dropdown">
                <div class="profile-trigger">
                    <div class="profile-avatar-sm">
                        ${sessionScope.username.substring(0,1).toUpperCase()}
                    </div>
                </div>
                <div class="profile-menu">
                    <div class="profile-menu-header">
                        <div class="profile-menu-name">${sessionScope.username}</div>
                        <div class="profile-menu-role">${sessionScope.role}</div>
                    </div>
                    <div class="profile-menu-divider"></div>
                    <a href="/profile" class="profile-menu-item">
                        &#128100; My Profile
                    </a>
                    <a href="/orders/history" class="profile-menu-item">
                        &#128230; My Orders
                    </a>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <a href="/admin" class="profile-menu-item">
                            &#9881; Dashboard
                        </a>
                    </c:if>
                    <div class="profile-menu-divider"></div>
                    <a href="/logout" class="profile-menu-item logout">
                        &#128274; Logout
                    </a>
                </div>
            </div>
        </c:if>
    </div>
</nav>