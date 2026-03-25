<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Order Status | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="card status-card">
        <img class="status-icon"
             src="https://cdn-icons-png.flaticon.com/512/190/190411.png"
             alt="Order Placed"/>
        <div class="status-title">Order Placed!</div>
        <div class="status-subtitle">Your order has been received and is being processed.</div>

        <div class="status-details">
            <p>
                <span>Order ID</span>
                <strong>#${order.orderId}</strong>
            </p>
            <p>
                <span>Product</span>
                <strong>${order.productName}</strong>
            </p>
            <p>
                <span>Quantity</span>
                <strong>${order.quantity}</strong>
            </p>
            <p>
                <span>Total</span>
                <strong>R${order.totalPrice}</strong>
            </p>
            <p>
                <span>Status</span>
                <strong>
                    <span class="badge badge-warning">${order.status}</span>
                </strong>
            </p>
        </div>

        <div style="display:flex; gap:15px; justify-content:center;">
            <a href="/payment/${order.orderId}" class="btn btn-success">
                <img src="https://cdn-icons-png.flaticon.com/512/2489/2489756.png" width="18"/>
                View Payment
            </a>
            <a href="/products" class="btn btn-outline">
                <img src="https://cdn-icons-png.flaticon.com/512/3724/3724788.png" width="18"/>
                Back to Products
            </a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>