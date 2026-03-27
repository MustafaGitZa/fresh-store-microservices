<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Checkout | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">Checkout</div>
    </div>

    <!-- Checkout Steps -->
    <div class="checkout-steps">
        <div class="step active">
            <div class="step-number">1</div>
            <div class="step-label">Delivery</div>
        </div>
        <div class="step-line"></div>
        <div class="step">
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
        <!-- Delivery Form -->
        <div class="checkout-form">
            <div class="card">
                <div class="card-title">Delivery Details</div>

                <form action="/checkout" method="post">
                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" name="fullName" class="form-control"
                               placeholder="John Doe" required/>
                    </div>
                    <div class="form-group">
                        <label>Phone Number</label>
                        <input type="text" name="phone" class="form-control"
                               placeholder="072 123 4567" required/>
                    </div>
                    <div class="form-group">
                        <label>Street Address</label>
                        <input type="text" name="address" class="form-control"
                               placeholder="123 Main Street" required/>
                    </div>
                    <div style="display:grid; grid-template-columns:1fr 1fr; gap:15px;">
                        <div class="form-group">
                            <label>City</label>
                            <input type="text" name="city" class="form-control"
                                   placeholder="Johannesburg" required/>
                        </div>
                        <div class="form-group">
                            <label>Province</label>
                            <select name="province" class="form-control" required>
                                <option value="">Select Province</option>
                                <option>Gauteng</option>
                                <option>Western Cape</option>
                                <option>KwaZulu-Natal</option>
                                <option>Eastern Cape</option>
                                <option>Limpopo</option>
                                <option>Mpumalanga</option>
                                <option>North West</option>
                                <option>Free State</option>
                                <option>Northern Cape</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Postal Code</label>
                        <input type="text" name="postalCode" class="form-control"
                               placeholder="2000" required/>
                    </div>

                    <div class="delivery-estimate">
                        <img src="https://cdn-icons-png.flaticon.com/512/2830/2830312.png" width="20"/>
                        Estimated delivery: <strong>2-3 business days</strong>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        Continue to Payment
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
</body>
</html>