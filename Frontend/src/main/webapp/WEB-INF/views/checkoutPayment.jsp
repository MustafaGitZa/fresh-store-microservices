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
    <div class="page-header">
        <div class="page-title">Payment</div>
    </div>

    <!-- Checkout Steps -->
    <div class="checkout-steps">
        <div class="step completed">
            <div class="step-number">1</div>
            <div class="step-label">Delivery</div>
        </div>
        <div class="step-line"></div>
        <div class="step active">
            <div class="step-number">2</div>
            <div class="step-label">Payment</div>
        </div>
        <div class="step-line"></div>
        <div class="step">
            <div class="step-number">3</div>
            <div class="step-label">Confirmation</div>
        </div>
    </div>

    <div class="checkout-layout">
        <!-- Payment Form -->
        <div class="checkout-form">
            <div class="card">
                <div class="card-title">Card Details</div>

                <c:if test="${not empty error}">
                    <div class="alert alert-error">${error}</div>
                </c:if>

                <form action="/checkout/pay" method="post">
                    <div class="form-group">
                        <label>Card Holder Name</label>
                        <input type="text" name="cardHolder" class="form-control"
                               placeholder="John Doe" required/>
                    </div>
                    <div class="form-group">
                        <label>Card Number</label>
                        <input type="text" name="cardNumber" class="form-control"
                               placeholder="1234 5678 9012 3456"
                               maxlength="19" required
                               oninput="formatCardNumber(this)"/>
                    </div>
                    <div style="display:grid; grid-template-columns:1fr 1fr 1fr; gap:15px;">
                        <div class="form-group">
                            <label>Expiry Month</label>
                            <select name="expiryMonth" class="form-control" required>
                                <option value="">MM</option>
                                <c:forEach begin="1" end="12" var="m">
                                    <option value="${m}">${m}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Expiry Year</label>
                            <select name="expiryYear" class="form-control" required>
                                <option value="">YYYY</option>
                                <c:forEach begin="2025" end="2035" var="y">
                                    <option value="${y}">${y}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>CVV</label>
                            <input type="password" name="cvv" class="form-control"
                                   placeholder="123" maxlength="3" required/>
                        </div>
                    </div>

                    <div class="delivery-estimate">
                        <img src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png" width="20"/>
                        Delivering to: <strong>${delivery.address}, ${delivery.city}</strong>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        Pay R<c:out value="${total}"/>
                    </button>
                </form>
            </div>
        </div>

        <!-- Order Summary -->
        <div class="checkout-summary">
            <div class="card">
                <div class="card-title">Order Summary</div>
                <c:forEach var="item" items="${cart}">
                    <div class="summary-line">
                        <span>${item.productName} x${item.quantity}</span>
                        <span>R${item.subtotal}</span>
                    </div>
                </c:forEach>
                <div class="divider"></div>
                <div class="summary-line">
                    <span>Delivery</span>
                    <span style="color:#4ecca3;">Free</span>
                </div>
                <div class="summary-total">
                    <span>Total</span>
                    <span>R${total}</span>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    function formatCardNumber(input) {
        let value = input.value.replace(/\D/g, '');
        value = value.replace(/(.{4})/g, '$1 ').trim();
        input.value = value;
    }
</script>
</body>
</html>