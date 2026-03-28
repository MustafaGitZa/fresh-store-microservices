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
        <div class="page-title">Fresh Products</div>
        <div class="welcome-message">
            Welcome back, <strong>${username}</strong>!
        </div>
    </div>

    <!-- Search & Filter Bar -->
    <div class="filter-bar">
        <div class="search-box">
            <input type="text" id="searchInput" class="form-control"
                   placeholder="Search products..."
                   oninput="filterProducts()"/>
        </div>
        <div class="filter-group">
            <select id="sortSelect" class="form-control" onchange="filterProducts()">
                <option value="">Sort By</option>
                <option value="price-asc">Price: Low to High</option>
                <option value="price-desc">Price: High to Low</option>
                <option value="name-asc">Name: A to Z</option>
            </select>
        </div>
        <div class="filter-group">
            <input type="number" id="minPrice" class="form-control"
                   placeholder="Min R" oninput="filterProducts()"/>
        </div>
        <div class="filter-group">
            <input type="number" id="maxPrice" class="form-control"
                   placeholder="Max R" oninput="filterProducts()"/>
        </div>
    </div>

    <!-- Category Tabs -->
    <div class="category-tabs">
        <button class="category-tab active" onclick="filterByCategory('all', this)">
            All
        </button>
        <button class="category-tab" onclick="filterByCategory('Fruit', this)">
            Fruits
        </button>
        <button class="category-tab" onclick="filterByCategory('Vegetable', this)">
            Vegetables
        </button>
    </div>

    <!-- No Results -->
    <div id="noResults" class="no-results" style="display:none;">
        <p>No products found matching your search.</p>
    </div>

    <!-- Products Grid -->
    <div class="products-grid" id="productsGrid">
        <c:forEach var="product" items="${products}">
            <div class="product-card"
                 data-name="${product.productName}"
                 data-price="${product.price}"
                 data-category="${product.category}">

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

                <c:if test="${not empty product.category}">
                    <div class="product-category-badge ${product.category}">
                        ${product.category}
                    </div>
                </c:if>

                <div class="product-info">
                    <div class="product-name">${product.productName}</div>
                    <div class="product-price">R${product.price}</div>
                    <div class="product-stock">
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
    let activeCategory = 'all';

    function filterByCategory(category, btn) {
        activeCategory = category;
        document.querySelectorAll('.category-tab')
                .forEach(t => t.classList.remove('active'));
        btn.classList.add('active');
        filterProducts();
    }

    function filterProducts() {
        const search = document.getElementById('searchInput').value.toLowerCase();
        const sort = document.getElementById('sortSelect').value;
        const minPrice = parseFloat(document.getElementById('minPrice').value) || 0;
        const maxPrice = parseFloat(document.getElementById('maxPrice').value) || Infinity;

        const grid = document.getElementById('productsGrid');
        let cards = Array.from(grid.querySelectorAll('.product-card'));
        let visible = 0;

        cards.forEach(card => {
            const name = card.dataset.name.toLowerCase();
            const price = parseFloat(card.dataset.price);
            const category = card.dataset.category;

            const matchSearch = name.includes(search);
            const matchCategory = activeCategory === 'all' || category === activeCategory;
            const matchPrice = price >= minPrice && price <= maxPrice;

            if (matchSearch && matchCategory && matchPrice) {
                card.style.display = 'flex';
                visible++;
            } else {
                card.style.display = 'none';
            }
        });

        if (sort) {
            const visibleCards = cards.filter(c => c.style.display !== 'none');
            visibleCards.sort((a, b) => {
                if (sort === 'price-asc')
                    return parseFloat(a.dataset.price) - parseFloat(b.dataset.price);
                if (sort === 'price-desc')
                    return parseFloat(b.dataset.price) - parseFloat(a.dataset.price);
                if (sort === 'name-asc')
                    return a.dataset.name.localeCompare(b.dataset.name);
                return 0;
            });
            visibleCards.forEach(card => grid.appendChild(card));
        }

        document.getElementById('noResults').style.display =
                visible === 0 ? 'block' : 'none';
    }
</script>
</body>
</html>