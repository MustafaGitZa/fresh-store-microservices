<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Inventory | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">
            <img src="https://cdn-icons-png.flaticon.com/512/2917/2917995.png" alt="Inventory"/>
            Inventory Management
        </div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="18"/>
            ${error}
        </div>
    </c:if>

    <!-- Add Product Form -->
    <div class="card" style="margin-bottom:30px;">
        <div class="card-title">
            <img src="https://cdn-icons-png.flaticon.com/512/1828/1828817.png" width="24"/>
            Add New Product
        </div>
        <form action="/inventory/add" method="post"
              style="display:grid; grid-template-columns: 1fr 1fr 1fr auto; gap:15px; align-items:end;">
            <div class="form-group" style="margin:0;">
                <label>Product Name</label>
                <input type="text" name="productName" class="form-control"
                       placeholder="e.g. Mangoes" required/>
            </div>
            <div class="form-group" style="margin:0;">
                <label>Quantity</label>
                <input type="number" name="quantity" class="form-control"
                       placeholder="e.g. 100" required/>
            </div>
            <div class="form-group" style="margin:0;">
                <label>Price (R)</label>
                <input type="number" name="price" class="form-control"
                       step="0.01" placeholder="e.g. 19.99" required/>
            </div>
            <button type="submit" class="btn btn-success">
                <img src="https://cdn-icons-png.flaticon.com/512/1828/1828817.png" width="16"/>
                Add
            </button>
        </form>
    </div>

    <!-- Inventory Table -->
    <div class="card">
        <div class="card-title">
            <img src="https://cdn-icons-png.flaticon.com/512/2917/2917995.png" width="24"/>
            Current Stock
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${inventory}">
                    <tr>
                        <td>#${item.productId}</td>
                        <td>${item.productName}</td>
                        <td>${item.quantity}</td>
                        <td>R${item.price}</td>
                        <td>
                            <c:choose>
                                <c:when test="${item.quantity > 10}">
                                    <span class="badge badge-success">In Stock</span>
                                </c:when>
                                <c:when test="${item.quantity > 0}">
                                    <span class="badge badge-warning">Low Stock</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger">Out of Stock</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>