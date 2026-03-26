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

<div class="container-sm">
    <div class="card">
        <div class="card-title">
            <img src="https://cdn-icons-png.flaticon.com/512/2489/2489756.png" width="28"/>
            Secure Payment
        </div>

        <!-- Order Summary -->
        <div class="order-summary">
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
                <strong style="color:#4ecca3; font-size:1.2rem;">R${order.totalPrice}</strong>
            </p>
        </div>

        <div class="divider"></div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="18"/>
                ${error}
            </div>
        </c:if>

        <form action="/payment/process" method="post">
            <input type="hidden" name="orderId" value="${order.orderId}"/>
            <input type="hidden" name="amount" value="${order.totalPrice}"/>
            <input type="hidden" name="orderId" value="${order.orderId}"/>
            <input type="hidden" name="customerId" value="${order.customerId}"/>
            <input type="hidden" name="amount" value="${order.totalPrice}"/>
            <input type="hidden" name="productName" value="${order.productName}"/>
            <input type="hidden" name="quantity" value="${order.quantity}"/>

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

            <div style="display:grid; grid-template-columns: 1fr 1fr; gap:15px;">
                <div class="form-group">
                    <label>Expiry Month</label>
                    <select name="expiryMonth" class="form-control" required>
                        <option value="">Month</option>
                        <c:forEach begin="1" end="12" var="m">
                            <option value="${m}">${m}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Expiry Year</label>
                    <select name="expiryYear" class="form-control" required>
                        <option value="">Year</option>
                        <c:forEach begin="2025" end="2035" var="y">
                            <option value="${y}">${y}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>CVV</label>
                <input type="password" name="cvv" class="form-control"
                       placeholder="123" maxlength="3" required/>
            </div>

            <button type="submit" class="btn btn-primary">
                <img src="https://cdn-icons-png.flaticon.com/512/2489/2489756.png" width="18"/>
                Pay R${order.totalPrice}
            </button>
        </form>

        <div style="text-align:center; margin-top:15px;">
            <a href="/products" style="color:#888; font-size:0.85rem;">
                ← Cancel and go back
            </a>
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