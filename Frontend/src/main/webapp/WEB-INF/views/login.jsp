<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Login | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<div class="container-sm">
    <div class="card">
        <div class="auth-logo">
            <img src="https://cdn-icons-png.flaticon.com/512/2553/2553651.png" alt="Logo"/>
            <h1>FNB Fresh Store</h1>
            <p>Sign in to your account</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                    ${error}
            </div>
        </c:if>

        <c:if test="${param.registered == 'true'}">
            <div class="alert alert-success">
                Registration successful! Please login.
            </div>
        </c:if>

        <c:if test="${param.reset == 'true'}">
            <div class="alert alert-success">
                Password reset successful! Please login with your new password.
            </div>
        </c:if>

        <form action="/login" method="post">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" class="form-control"
                       placeholder="Enter your username" required/>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" class="form-control"
                       placeholder="Enter your password" required/>
            </div>
            <button type="submit" class="btn btn-primary">
                Sign In
            </button>
        </form>

        <div style="text-align:center; margin-top:15px;">
            <a href="/forgot-password"
               style="font-size:0.85rem; color:#888;">
                Forgot your password?
            </a>
        </div>

        <div class="auth-footer">
            Don't have an account? <a href="/register">Register here</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>