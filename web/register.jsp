<%-- 
    Document   : register
    Created on : 12 May 2025, 01:51:43
    Author     : Ebrahim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Register</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 50px;
                background-color: #f4f4f4;
            }
            form {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                width: 300px;
                margin: auto;
                box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            }
            input, select {
                margin-bottom: 10px;
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
            }
            input[type="submit"] {
                background-color: #4CAF50;
                border: none;
                color: white;
                font-weight: bold;
                cursor: pointer;
            }
            h2 {
                text-align: center;
            }
            p {
                color: red;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h2>User Registration</h2>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <p style="color:red;"><%= error%></p>
        <%
            }
        %>
        <form action="register" method="post">
            Username: <input type="text" name="username" value="<%= request.getParameter("username") != null ? request.getParameter("username") : ""%>" required /><br/>
            Email: <input type="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : ""%>" required /><br/>
            Password: <input type="password" name="password" required /><br/>
            Confirm password: <input type="password" name="confirm_password" required /><br/>
            Role:
            <select name="role">
                <option value="STUDENT" <%= "STUDENT".equals(request.getParameter("role")) ? "selected" : ""%>>Student</option>
                <option value="ORGANIZER" <%= "ORGANIZER".equals(request.getParameter("role")) ? "selected" : ""%>>Organizer</option>
                <option value="ADMIN" <%= "ADMIN".equals(request.getParameter("role")) ? "selected" : ""%>>Admin</option>
            </select><br/>
            <input type="submit" value="Register" />
        </form>


        <p style="color:red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : ""%></p>
    </body>
</html>
