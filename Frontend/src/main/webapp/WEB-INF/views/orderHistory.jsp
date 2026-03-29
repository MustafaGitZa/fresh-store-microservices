<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Order History | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">My Orders</div>
        <a href="/products" class="btn btn-outline"
           style="width:auto; padding:10px 20px;">
            Continue Shopping
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="card" style="text-align:center; padding:60px;">
                <div style="font-size:3rem; margin-bottom:20px;">&#128230;</div>
                <p style="color:#888; font-size:1.1rem; margin-bottom:20px;">
                    You haven't placed any orders yet
                </p>
                <a href="/products" class="btn btn-primary"
                   style="width:auto; display:inline-flex;">
                    Start Shopping
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Amount</th>
                            <th>Status</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td><strong>#${order.orderId}</strong></td>
                                <td>${order.productName}</td>
                                <td>${order.quantity}</td>
                                <td>R${order.totalPrice}</td>
                                <td>
                                    <span class="badge
                                        <c:choose>
                                            <c:when test="${order.status == 'PENDING'}">badge-warning</c:when>
                                            <c:when test="${order.status == 'PAID'}">badge-success</c:when>
                                            <c:when test="${order.status == 'FAILED'}">badge-danger</c:when>
                                            <c:otherwise>badge-info</c:otherwise>
                                        </c:choose>">
                                        ${order.status}
                                    </span>
                                </td>
                                <td style="font-size:0.85rem; color:#888;">
                                    ${order.createdAt}
                                </td>
                                <td>
                                    <a href="/tracking/${order.orderId}"
                                       class="btn btn-outline"
                                       style="padding:6px 14px;
                                              font-size:0.82rem;
                                              width:auto;">
                                        Track
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
