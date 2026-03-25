<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Register | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<div class="container-sm">
    <div class="card">
        <div class="auth-logo">
            <img src="https://cdn-icons-png.flaticon.com/512/2553/2553651.png" alt="Logo"/>
            <h1>Create Account</h1>
            <p>Join FNB Fresh Store today</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <img src="https://cdn-icons-png.flaticon.com/512/564/564619.png" width="18"/>
                    ${error}
            </div>
        </c:if>

        <form action="/register" method="post">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" class="form-control"
                       placeholder="Choose a username" required/>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" class="form-control"
                       placeholder="Enter your email" required/>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" class="form-control"
                       placeholder="Min 6 characters" required/>
            </div>
            <button type="submit" class="btn btn-primary">
                <img src="https://cdn-icons-png.flaticon.com/512/1077/1077063.png" width="18"/>
                Create Account
            </button>
        </form>

        <div class="auth-footer">
            Already have an account? <a href="/login">Sign in here</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>