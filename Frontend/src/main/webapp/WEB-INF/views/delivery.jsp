<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Delivery | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="card status-card">

        <c:if test="${not empty deliveries}">
            <c:forEach var="delivery" items="${deliveries}">
                <c:choose>
                    <c:when test="${delivery.status == 'CONFIRMED'}">
                        <img class="status-icon"
                             src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png"
                             alt="Delivered"/>
                        <div class="status-title" style="color:#276749;">Delivery Confirmed!</div>
                        <div class="status-subtitle">Your order is on its way.</div>
                    </c:when>
                    <c:otherwise>
                        <img class="status-icon"
                             src="https://cdn-icons-png.flaticon.com/512/564/564619.png"
                             alt="Skipped"/>
                        <div class="status-title" style="color:#c53030;">Delivery Not Processed</div>
                        <div class="status-subtitle">Delivery was skipped due to payment or inventory issues.</div>
                    </c:otherwise>
                </c:choose>

                <div class="status-details">
                    <p>
                        <span>Delivery ID</span>
                        <strong>#${delivery.deliveryId}</strong>
                    </p>
                    <p>
                        <span>Order ID</span>
                        <strong>#${delivery.orderId}</strong>
                    </p>
                    <p>
                        <span>Product</span>
                        <strong>${delivery.productName}</strong>
                    </p>
                    <p>
                        <span>Quantity</span>
                        <strong>${delivery.quantity}</strong>
                    </p>
                    <p>
                        <span>Status</span>
                        <strong>
                            <c:choose>
                                <c:when test="${delivery.status == 'CONFIRMED'}">
                                    <span class="badge badge-success">${delivery.status}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger">${delivery.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </strong>
                    </p>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty deliveries}">
            <div class="alert alert-info">
                <img src="https://cdn-icons-png.flaticon.com/512/1198/1198227.png" width="18"/>
                Delivery is still being processed. Please check back shortly.
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