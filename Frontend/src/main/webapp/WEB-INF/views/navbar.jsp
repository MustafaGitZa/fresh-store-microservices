<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar">
    <div class="navbar-left">
        <div class="navbar-brand">
            <img src="https://cdn-icons-png.flaticon.com/512/2553/2553651.png" alt="FNB Store"/>
            FNB Fresh Store
        </div>
        <c:if test="${not empty sessionScope.token}">
            <ul class="navbar-nav">
                <li><a href="/products">
                    <img src="https://cdn-icons-png.flaticon.com/512/3724/3724788.png" width="16"/> Products
                </a></li>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <li><a href="/inventory">
                        <img src="https://cdn-icons-png.flaticon.com/512/2917/2917995.png" width="16"/> Inventory
                    </a></li>
                </c:if>
            </ul>
        </c:if>
    </div>

    <div class="navbar-right">
        <c:if test="${not empty sessionScope.username}">
            <div class="navbar-user">
                Welcome, <span>${sessionScope.username}</span>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    &nbsp;| <span style="color:#f6ad55;">ADMIN</span>
                </c:if>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.token}">
            <a href="/logout" class="btn-logout">
                <img src="https://cdn-icons-png.flaticon.com/512/1828/1828479.png" width="16"/> Logout
            </a>
        </c:if>
    </div>
</nav>