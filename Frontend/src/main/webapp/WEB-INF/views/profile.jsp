<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>My Profile | FNB Fresh Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <div class="page-title">My Profile</div>
    </div>

    <div class="profile-layout">

        <!-- Sidebar -->
        <div class="profile-sidebar">
            <div class="card" style="text-align:center; padding:30px 20px;">
                <div class="profile-avatar">${profile.username.substring(0,1).toUpperCase()}</div>
                <div class="profile-name">${profile.username}</div>
                <div class="profile-email">${profile.email}</div>
                <div style="margin-top:10px;">
                    <span class="badge ${profile.role == 'ADMIN' ? 'badge-warning' : 'badge-info'}">
                        ${profile.role}
                    </span>
                </div>

                <div class="divider"></div>

                <div class="profile-links">
                    <a href="/profile" class="profile-link-item active">
                        &#128100; My Profile
                    </a>
                    <a href="/orders/history" class="profile-link-item">
                        &#128230; My Orders
                    </a>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <a href="/admin" class="profile-link-item">
                            &#9881; Dashboard
                        </a>
                        <a href="/inventory" class="profile-link-item">
                            &#128218; Inventory
                        </a>
                    </c:if>
                    <a href="/products" class="profile-link-item">
                        &#128722; Shop
                    </a>
                    <a href="/logout" class="profile-link-item"
                       style="color:#e53e3e; margin-top:10px;">
                        &#128274; Logout
                    </a>
                </div>
            </div>
        </div>

        <!-- Main Content -->
        <div class="profile-content">

            <!-- Alerts -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <!-- Account Info -->
            <div class="card" style="margin-bottom:24px;">
                <div class="card-title">Account Information</div>
                <div class="profile-info-grid">
                    <div class="profile-info-item">
                        <div class="profile-info-label">Username</div>
                        <div class="profile-info-value">${profile.username}</div>
                    </div>
                    <div class="profile-info-item">
                        <div class="profile-info-label">Email</div>
                        <div class="profile-info-value">${profile.email}</div>
                    </div>
                    <div class="profile-info-item">
                        <div class="profile-info-label">Role</div>
                        <div class="profile-info-value">
                            <span class="badge ${profile.role == 'ADMIN' ?
                                  'badge-warning' : 'badge-info'}">
                                ${profile.role}
                            </span>
                        </div>
                    </div>
                    <div class="profile-info-item">
                        <div class="profile-info-label">Member Since</div>
                        <div class="profile-info-value">2026</div>
                    </div>
                </div>
            </div>

            <!-- Edit Profile -->
            <div class="card" style="margin-bottom:24px;">
                <div class="card-title">Edit Profile</div>
                <form action="/profile/update" method="post">
                    <div style="display:grid; grid-template-columns:1fr 1fr; gap:16px;">
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" name="username" class="form-control"
                                   value="${profile.username}"/>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control"
                                   value="${profile.email}"/>
                        </div>
                    </div>
                    <input type="hidden" name="password" value=""/>
                    <button type="submit" class="btn btn-success"
                            style="width:auto; padding:10px 30px;">
                        Save Changes
                    </button>
                </form>
            </div>

            <!-- Change Password -->
            <div class="card">
                <div class="card-title">Change Password</div>
                <form action="/profile/update" method="post">
                    <input type="hidden" name="username" value=""/>
                    <input type="hidden" name="email" value=""/>
                    <div style="display:grid; grid-template-columns:1fr 1fr; gap:16px;">
                        <div class="form-group">
                            <label>New Password</label>
                            <input type="password" name="password" class="form-control"
                                   placeholder="Enter new password" required/>
                        </div>
                        <div class="form-group">
                            <label>Confirm Password</label>
                            <input type="password" id="confirmPassword"
                                   class="form-control"
                                   placeholder="Confirm new password"
                                   required/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary"
                            style="width:auto; padding:10px 30px;"
                            onclick="return validatePassword()">
                        Update Password
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    function validatePassword() {
        const pwd = document.querySelector('input[name="password"]').value;
        const confirm = document.getElementById('confirmPassword').value;
        if (pwd !== confirm) {
            alert('Passwords do not match!');
            return false;
        }
        if (pwd.length < 6) {
            alert('Password must be at least 6 characters!');
            return false;
        }
        return true;
    }
</script>
</body>
</html>