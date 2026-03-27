<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Products | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">
            <img src="https://cdn-icons-png.flaticon.com/512/3724/3724788.png" alt="Products"/>
            Fresh Products
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="18"/>
            ${error}
        </div>
    </c:if>

    <div class="products-grid">
        <c:forEach var="product" items="${products}">
            <div class="product-card">

              <c:choose>
                        <c:when test="${product.productName == 'Apples'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=400&h=200&fit=crop"
                                 alt="Apples"/>
                        </c:when>
                        <c:when test="${product.productName == 'Oranges'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1547514701-42782101795e?w=400&h=200&fit=crop"
                                 alt="Oranges"/>
                        </c:when>
                        <c:when test="${product.productName == 'Bananas'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=400&h=200&fit=crop"
                                 alt="Bananas"/>
                        </c:when>
                        <c:when test="${product.productName == 'Grapes'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1596363505729-4190a9506133?w=400&h=200&fit=crop"
                                 alt="Grapes"/>
                        </c:when>
                        <c:when test="${product.productName == 'Spinach'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=400&h=200&fit=crop"
                                 alt="Spinach"/>
                        </c:when>
                        <c:when test="${product.productName == 'Carrots'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=400&h=200&fit=crop"
                                 alt="Carrots"/>
                        </c:when>
                        <c:when test="${product.productName == 'Tomatoes'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1546094096-0df4bcaaa337?w=400&h=200&fit=crop"
                                 alt="Tomatoes"/>
                        </c:when>
                        <c:when test="${product.productName == 'Potatoes'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=400&h=200&fit=crop"
                                 alt="Potatoes"/>
                        </c:when>
                        <c:when test="${product.productName == 'Mangoes'}">
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1553279768-865429fa0078?w=400&h=200&fit=crop"
                                 alt="Mangoes"/>
                        </c:when>
                        <c:otherwise>
                            <img class="product-icon"
                                 src="https://images.unsplash.com/photo-1610832958506-aa56368176cf?w=400&h=200&fit=crop"
                                 alt="Product"/>
                        </c:otherwise>
                    </c:choose>

                <div class="product-info">
                    <div class="product-name">${product.productName}</div>
                    <div class="product-price">R${product.price}</div>
                    <div class="product-stock">
                        <img src="https://cdn-icons-png.flaticon.com/512/2917/2917995.png" width="14"/>
                        ${product.quantity} in stock
                    </div>
                </div>

                               <form action="/cart/add" method="post" class="product-actions">
                    <input type="hidden" name="productName" value="${product.productName}"/>
                    <input type="hidden" name="price" value="${product.price}"/>
                    <input type="hidden" name="productId" value="${product.productId}"/>
                    <input type="number" name="quantity" id="qty_${product.productId}"
                           class="quantity-input" value="1" min="1"
                           max="${product.quantity}"/>
                    <button type="submit" class="btn btn-success">
                        Add to Cart
                    </button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    function updateTotalPrice(price, qtyId, totalId) {
        const qty = parseInt(document.getElementById(qtyId).value) || 1;
        document.getElementById(totalId).value = (qty * price).toFixed(2);
    }
</script>
</body>
</html>