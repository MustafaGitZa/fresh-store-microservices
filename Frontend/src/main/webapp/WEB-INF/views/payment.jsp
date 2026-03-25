<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Payment | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="card status-card">

        <c:if test="${not empty payments}">
            <c:forEach var="payment" items="${payments}">
                <c:choose>
                    <c:when test="${payment.status == 'APPROVED'}">
                        <img class="status-icon"
                             src="https://cdn-icons-png.flaticon.com/512/190/190411.png"
                             alt="Approved"/>
                        <div class="status-title" style="color:#276749;">Payment Approved!</div>
                        <div class="status-subtitle">Your payment was successful.</div>
                    </c:when>
                    <c:otherwise>
                        <img class="status-icon"
                             src="https://cdn-icons-png.flaticon.com/512/564/564619.png"
                             alt="Declined"/>
                        <div class="status-title" style="color:#c53030;">Payment Declined</div>
                        <div class="status-subtitle">Your payment could not be processed.</div>
                    </c:otherwise>
                </c:choose>

                <div class="status-details">
                    <p>
                        <span>Payment ID</span>
                        <strong>#${payment.paymentId}</strong>
                    </p>
                    <p>
                        <span>Order ID</span>
                        <strong>#${payment.orderId}</strong>
                    </p>
                    <p>
                        <span>Amount</span>
                        <strong>R${payment.amount}</strong>
                    </p>
                    <p>
                        <span>Status</span>
                        <strong>
                            <c:choose>
                                <c:when test="${payment.status == 'APPROVED'}">
                                    <span class="badge badge-success">${payment.status}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger">${payment.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </strong>
                    </p>
                </div>

                <c:if test="${payment.status == 'APPROVED'}">
                    <a href="/delivery/${payment.orderId}" class="btn btn-success">
                        <img src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png" width="18"/>
                        View Delivery
                    </a>
                </c:if>
            </c:forEach>
        </c:if>

        <c:if test="${empty payments}">
            <div class="alert alert-info">
                <img src="https://cdn-icons-png.flaticon.com/512/1198/1198227.png" width="18"/>
                Payment is still being processed. Please check back shortly.
            </div>
        </c:if>

        <div style="margin-top:20px;">
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