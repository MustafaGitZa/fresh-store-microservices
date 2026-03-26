<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Order Tracking | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">
            <img src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png" alt="Tracking"/>
            Order Tracking
        </div>
        <div style="color:#888; font-size:0.9rem;">Order #${orderId}</div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="18"/>
            ${error}
        </div>
    </c:if>

    <div class="tracking-card">

        <!-- Order Summary -->
        <c:if test="${not empty order}">
            <div class="tracking-summary">
                <div class="tracking-summary-item">
                    <span>Product</span>
                    <strong>${order.productName}</strong>
                </div>
                <div class="tracking-summary-item">
                    <span>Quantity</span>
                    <strong>${order.quantity}</strong>
                </div>
                <div class="tracking-summary-item">
                    <span>Total</span>
                    <strong style="color:#4ecca3;">R${order.totalPrice}</strong>
                </div>
            </div>
        </c:if>

        <!-- Timeline -->
        <div class="timeline">
            <c:forEach var="step" items="${timeline}">
                <div class="timeline-item ${step.status}">
                    <div class="timeline-icon">
                        <c:choose>
                            <c:when test="${step.status == 'PLACED'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/3144/3144456.png" width="24"/>
                            </c:when>
                            <c:when test="${step.status == 'PAYMENT_APPROVED'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/190/190411.png" width="24"/>
                            </c:when>
                            <c:when test="${step.status == 'PAYMENT_DECLINED'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="24"/>
                            </c:when>
                            <c:when test="${step.status == 'INVENTORY_UPDATED'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/2917/2917995.png" width="24"/>
                            </c:when>
                            <c:when test="${step.status == 'INVENTORY_FAILED'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="24"/>
                            </c:when>
                            <c:when test="${step.status == 'OUT_FOR_DELIVERY'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png" width="24"/>
                            </c:when>
                            <c:when test="${step.status == 'DELIVERED'}">
                                <img src="https://cdn-icons-png.flaticon.com/512/190/190411.png" width="24"/>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="timeline-content">
                        <div class="timeline-title">
                            <c:choose>
                                <c:when test="${step.status == 'PLACED'}">Order Placed</c:when>
                                <c:when test="${step.status == 'PAYMENT_APPROVED'}">Payment Approved</c:when>
                                <c:when test="${step.status == 'PAYMENT_DECLINED'}">Payment Declined</c:when>
                                <c:when test="${step.status == 'INVENTORY_UPDATED'}">Stock Reserved</c:when>
                                <c:when test="${step.status == 'INVENTORY_FAILED'}">Stock Unavailable</c:when>
                                <c:when test="${step.status == 'OUT_FOR_DELIVERY'}">Out for Delivery</c:when>
                                <c:when test="${step.status == 'DELIVERED'}">Delivered</c:when>
                            </c:choose>
                        </div>
                        <div class="timeline-message">${step.message}</div>
                        <div class="timeline-time">${step.createdAt}</div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div style="text-align:center; margin-top:30px;">
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