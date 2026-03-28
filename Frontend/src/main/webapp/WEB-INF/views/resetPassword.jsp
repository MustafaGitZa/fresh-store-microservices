<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Reset Password | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<div class="container-sm">
    <div class="card">
        <div class="auth-logo">
            <img src="https://cdn-icons-png.flaticon.com/512/2553/2553651.png" alt="Logo"/>
            <h1>Reset Password</h1>
            <p>Enter your new password below</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="/reset-password" method="post">
            <input type="hidden" name="token" value="${token}"/>

            <div class="form-group">
                <label>New Password</label>
                <input type="password" name="password" class="form-control"
                       placeholder="Min 6 characters" required/>
            </div>
            <div class="form-group">
                <label>Confirm Password</label>
                <input type="password" name="confirmPassword"
                       class="form-control"
                       placeholder="Confirm your new password" required/>
            </div>
            <button type="submit" class="btn btn-primary">
                Reset Password
            </button>
        </form>

        <div class="auth-footer" style="margin-top:20px;">
            <a href="/login">Back to Login</a>
        </div>
    </div>
</div>
</body>
</html>