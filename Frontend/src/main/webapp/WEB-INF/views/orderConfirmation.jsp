<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Order Confirmed | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">

    <!-- Checkout Steps -->
    <div class="checkout-steps">
        <div class="step completed">
            <div class="step-number">1</div>
            <div class="step-label">Delivery</div>
        </div>
        <div class="step-line"></div>
        <div class="step completed">
            <div class="step-number">2</div>
            <div class="step-label">Payment</div>
        </div>
        <div class="step-line"></div>
        <div class="step active">
            <div class="step-number">3</div>
            <div class="step-label">Confirmation</div>
        </div>
    </div>

    <div class="card status-card">
        <img class="status-icon"
             src="https://cdn-icons-png.flaticon.com/512/190/190411.png"
             alt="Success"/>
        <div class="status-title" style="color:#276749;">Order Confirmed!</div>
        <div class="status-subtitle">
            Thank you for your order. Your fresh produce is being prepared!
        </div>

        <!-- Delivery Info -->
        <div class="status-details" style="max-width:500px; margin:0 auto 30px;">
            <p>
                <span>Delivering to</span>
                <strong>${delivery.fullName}</strong>
            </p>
            <p>
                <span>Address</span>
                <strong>${delivery.address}, ${delivery.city}</strong>
            </p>
            <p>
                <span>Province</span>
                <strong>${delivery.province}</strong>
            </p>
            <p>
                <span>Estimated Delivery</span>
                <strong style="color:#4ecca3;">2-3 Business Days</strong>
            </p>
            <p>
                <span>Total Paid</span>
                <strong style="color:#4ecca3;">R${total}</strong>
            </p>
        </div>

        <!-- Orders -->
        <div style="max-width:500px; margin:0 auto 30px; text-align:left;">
            <div class="card-title" style="font-size:1rem;">Your Orders</div>
            <c:forEach var="order" items="${orders}">
                <div class="summary-line">
                    <span>${order.productName} x${order.quantity}</span>
                    <a href="/tracking/${order.orderId}"
                       style="color:#4ecca3; font-size:0.85rem; font-weight:600;">
                        Track Order #${order.orderId}
                    </a>
                </div>
            </c:forEach>
        </div>

        <a href="/products" class="btn btn-outline">
            Continue Shopping
        </a>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>