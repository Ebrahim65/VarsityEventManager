<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="za.ac.tut.model.entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        switch (user.getRole()) {
            case STUDENT:
                response.sendRedirect("secured/student/dashboard.jsp");
                return;
            case ORGANIZER:
                response.sendRedirect("secured/organizer/dashboard.jsp");
                return;
            case ADMIN:
                response.sendRedirect("secured/admin/dashboard.jsp");
                return;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Varsity Event Manager</title>
</head>
<body>
    <h1>Welcome to Varsity Event Manager</h1>
    <p>Plan, register, and manage campus events with ease.</p>

    <a href="login.jsp">Login</a> | <a href="register.jsp">Register</a>
</body>
</html>

