<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Forgot Password | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<div class="container-sm">
    <div class="card">
        <div class="auth-logo">
            <img src="https://cdn-icons-png.flaticon.com/512/2553/2553651.png" alt="Logo"/>
            <h1>Forgot Password</h1>
            <p>Enter your email to reset your password</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>

            <c:if test="${not empty token}">
                <!-- Show token for dev/demo — remove in production -->
                <div class="token-box">
                    <p style="font-size:0.85rem; color:#555; margin-bottom:8px;">
                        Your reset token — click to copy:
                    </p>
                    <div class="token-value" onclick="copyToken(this)"
                         title="Click to copy">
                            ${token}
                    </div>
                    <p style="font-size:0.75rem; color:#aaa; margin-top:6px;">
                        Click token to copy
                    </p>
                    <a href="/reset-password?token=${token}"
                       class="btn btn-primary" style="margin-top:16px;">
                        Continue to Reset Password
                    </a>
                </div>
            </c:if>

            <c:if test="${empty token}">
                <!-- Email was sent -->
                <div class="token-box">
                    <p style="font-size:0.9rem; color:#555; line-height:1.6;">
                        Check your inbox for the reset link.<br/>
                        If you don't see it, check your spam folder.
                    </p>
                    <a href="/reset-password" class="btn btn-outline"
                       style="margin-top:16px; width:auto;">
                        I have my token
                    </a>
                </div>
            </c:if>
        </c:if>

        <c:if test="${empty success}">
            <form action="/forgot-password" method="post">
                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" name="email" class="form-control"
                           placeholder="Enter your registered email" required/>
                </div>
                <button type="submit" class="btn btn-primary">
                    Reset Password
                </button>
            </form>
        </c:if>

        <div class="auth-footer" style="margin-top:20px;">
            Remember your password? <a href="/login">Sign in here</a>
        </div>
    </div>
</div>

<script>
    function copyToken(el) {
        navigator.clipboard.writeText(el.innerText.trim());
        el.style.background = '#4ecca3';
        el.style.color = '#1a1a2e';
        setTimeout(() => {
            el.style.background = '#1a1a2e';
            el.style.color = '#4ecca3';
        }, 1000);
    }
</script>
</body>
</html>