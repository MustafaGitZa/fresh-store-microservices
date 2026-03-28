<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Admin Dashboard | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">Admin Dashboard</div>
        <div class="welcome-message">
            Logged in as <strong>${username}</strong>
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <!-- Stats Cards -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon orders">
                <img src="https://cdn-icons-png.flaticon.com/512/3144/3144456.png" width="28"/>
            </div>
            <div class="stat-info">
                <div class="stat-value">${totalOrders}</div>
                <div class="stat-label">Total Orders</div>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon revenue">
                <img src="https://cdn-icons-png.flaticon.com/512/2489/2489756.png" width="28"/>
            </div>
            <div class="stat-info">
                <div class="stat-value">R<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></div>
                <div class="stat-label">Total Revenue</div>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon stock">
                <img src="https://cdn-icons-png.flaticon.com/512/2917/2917995.png" width="28"/>
            </div>
            <div class="stat-info">
                <div class="stat-value">${lowStock.size()}</div>
                <div class="stat-label">Low Stock Alerts</div>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon inventory">
                <img src="https://cdn-icons-png.flaticon.com/512/1828/1828817.png" width="28"/>
            </div>
            <div class="stat-info">
                <div class="stat-value">
                    <a href="/inventory" style="color:#4ecca3;">Manage</a>
                </div>
                <div class="stat-label">Inventory</div>
            </div>
        </div>
    </div>

    <div class="dashboard-grid">
        <!-- Recent Orders -->
        <div class="card">
            <div class="card-title">Recent Orders</div>
            <c:choose>
                <c:when test="${empty recentOrders}">
                    <p style="color:#888; text-align:center; padding:20px;">
                        No orders yet
                    </p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Product</th>
                                <th>Qty</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${recentOrders}">
                                <tr>
                                    <td>#${order.orderId}</td>
                                    <td>${order.productName}</td>
                                    <td>${order.quantity}</td>
                                    <td>R${order.totalPrice}</td>
                                    <td>
                                        <span class="badge badge-warning">
                                            ${order.status}
                                        </span>
                                    </td>
                                    <td>${order.createdAt}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Low Stock -->
        <div class="card">
            <div class="card-title">Low Stock Alerts</div>
            <c:choose>
                <c:when test="${empty lowStock}">
                    <div class="alert alert-success">
                        All products are well stocked!
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="product" items="${lowStock}">
                        <div class="low-stock-item">
                            <div class="low-stock-info">
                                <div class="low-stock-name">${product.productName}</div>
                                <div class="low-stock-qty">
                                    Only ${product.quantity} left
                                </div>
                            </div>
                            <span class="badge badge-danger">Low Stock</span>
                        </div>
                    </c:forEach>
                    <a href="/inventory" class="btn btn-primary"
                       style="margin-top:20px;">
                        Manage Inventory
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>