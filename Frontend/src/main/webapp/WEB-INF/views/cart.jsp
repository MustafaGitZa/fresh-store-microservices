<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Cart | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">Your Cart</div>
        <c:if test="${not empty cart}">
            <form action="/cart/clear" method="post">
                <button type="submit" class="btn btn-danger">Clear Cart</button>
            </form>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${empty cart}">
            <div class="card" style="text-align:center; padding:60px;">
                <img src="https://cdn-icons-png.flaticon.com/512/3144/3144456.png"
                     width="60" style="opacity:0.3; margin-bottom:20px;"/>
                <p style="color:#888; font-size:1.1rem;">Your cart is empty</p>
                <a href="/products" class="btn btn-primary"
                   style="margin-top:20px; display:inline-flex; width:auto;">
                    Browse Products
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart-layout">
                <!-- Cart Items -->
                <div class="cart-items">
                    <div class="card">
                        <c:forEach var="item" items="${cart}">
                            <div class="cart-item">
                                <div class="cart-item-info">
                                    <div class="cart-item-name">${item.productName}</div>
                                    <div class="cart-item-price">R${item.price} each</div>
                                </div>

                                <div class="cart-item-actions">
                                    <form action="/cart/update" method="post"
                                          style="display:flex; align-items:center; gap:8px;">
                                        <input type="hidden" name="productName"
                                               value="${item.productName}"/>
                                        <input type="number" name="quantity"
                                               value="${item.quantity}" min="0"
                                               class="quantity-input"
                                               onchange="this.form.submit()"/>
                                    </form>

                                    <div class="cart-item-subtotal">
                                        R${item.subtotal}
                                    </div>

                                    <form action="/cart/remove" method="post">
                                        <input type="hidden" name="productName"
                                               value="${item.productName}"/>
                                        <button type="submit" class="btn-remove">
                                            Remove
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- Order Summary -->
                <div class="cart-summary">
                    <div class="card">
                        <div class="card-title">Order Summary</div>
                        <c:forEach var="item" items="${cart}">
                            <div class="summary-line">
                                <span>${item.productName} x${item.quantity}</span>
                                <span>R${item.subtotal}</span>
                            </div>
                        </c:forEach>
                        <div class="divider"></div>
                        <div class="summary-total">
                            <span>Total</span>
                            <span>R${total}</span>
                        </div>
                        <a href="/checkout" class="btn btn-primary"
                           style="margin-top:20px;">
                            Proceed to Checkout
                        </a>
                        <a href="/products"
                           style="display:block; text-align:center;
                           margin-top:15px; color:#888; font-size:0.85rem;">
                            Continue Shopping
                        </a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>