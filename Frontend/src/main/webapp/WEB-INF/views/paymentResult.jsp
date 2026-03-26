<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Payment Result | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="card status-card">

        <c:choose>
            <c:when test="${result.status == 'APPROVED'}">
                <img class="status-icon"
                     src="https://cdn-icons-png.flaticon.com/512/190/190411.png"
                     alt="Approved"/>
                <div class="status-title" style="color:#276749;">
                    Payment Approved!
                </div>
                <div class="status-subtitle">
                    Your order is being processed.
                </div>
            </c:when>
            <c:otherwise>
                <img class="status-icon"
                     src="https://cdn-icons-png.flaticon.com/512/564/564619.png"
                     alt="Declined"/>
                <div class="status-title" style="color:#c53030;">
                    Payment Declined
                </div>
                <div class="status-subtitle">
                    ${result.message}
                </div>
            </c:otherwise>
        </c:choose>

        <div class="status-details">
            <p>
                <span>Order ID</span>
                <strong>#${result.orderId}</strong>
            </p>
            <p>
                <span>Amount</span>
                <strong>R${result.amount}</strong>
            </p>
            <c:if test="${not empty result.cardHolder}">
                <p>
                    <span>Card Holder</span>
                    <strong>${result.cardHolder}</strong>
                </p>
                <p>
                    <span>Card</span>
                    <strong>**** **** **** ${result.last4Digits}</strong>
                </p>
            </c:if>
            <p>
                <span>Status</span>
                <strong>
                    <c:choose>
                        <c:when test="${result.status == 'APPROVED'}">
                            <span class="badge badge-success">${result.status}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">${result.status}</span>
                        </c:otherwise>
                    </c:choose>
                </strong>
            </p>
        </div>

        <div style="display:flex; gap:15px; justify-content:center;">
            <c:if test="${result.status == 'APPROVED'}">
                <a href="/tracking/${result.orderId}" class="btn btn-success">
                    <img src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png" width="18"/>
                    Track Order
<               /a>
            </c:if>
            <c:if test="${result.status == 'DECLINED'}">
                <a href="/payment/card/${result.orderId}" class="btn btn-primary">
                    <img src="https://cdn-icons-png.flaticon.com/512/2489/2489756.png" width="18"/>
                    Try Again
                </a>
            </c:if>
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