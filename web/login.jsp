<%-- 
    Document   : login
    Created on : 12 May 2025, 00:29:21
    Author     : Ebrahim
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
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
        <title>Login - Varsity Event Manager</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f0f0f0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .login-container {
                background: white;
                padding: 30px 40px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h2 {
                margin-bottom: 20px;
            }

            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                background: #2e8b57;
                color: white;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
            }

            .error {
                color: red;
                margin-bottom: 10px;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Login</h2>

            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) {%>
            <div class="error"><%= error%></div>
            <% }%>

            <form action="login" method="post">
                Username: <input type="text" name="username" value="<%= request.getParameter("username") != null ? request.getParameter("username") : ""%>" required /><br/>
                Password: <input type="password" name="password" required /><br/>
                <input type="submit" value="Login" />
            </form>

            <a href="register.jsp">Don't have an account? Register here</a>
        </div>
    </body>
</html>

