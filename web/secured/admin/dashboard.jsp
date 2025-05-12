<%-- 
    Document   : dashboard
    Created on : 12 May 2025, 00:54:22
    Author     : Ebrahim
--%>

<%@page import="za.ac.tut.model.entity.User"%>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || user.getRole() != User.Role.ADMIN) {
        response.sendRedirect("../../login.jsp");
        return;
    }
%>
<html>
    <head>
        <title>Admin Dashboard</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 40px;
                background-color: #eef2f3;
            }

            h1 {
                color: #333;
            }

            .welcome {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="welcome">
            <h1>Welcome, <%= user.getUsername()%>!</h1>
            <p>This is your admin dashboard.</p>
        </div>
    </body>
</html>
