<%-- 
    Document   : create_event
    Created on : 12 May 2025, 14:30:38
    Author     : Ebrahim
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="za.ac.tut.model.entity.Event.Category" %>

<html>
    <head>
        <title>Create Event</title>
    </head>
    <body>
        <h2>Create New Event</h2>
        <form action="<%=request.getContextPath()%>/organizer/CreatEventServlet" method="post" enctype="multipart/form-data">
            Title: <input type="text" name="title" required><br><br>
            Description:<br>
            <textarea name="description" rows="4" cols="50"></textarea><br><br>
            Date & Time: <input type="datetime-local" name="dateTime" required><br><br>
            Location: <input type="text" name="location" required><br><br>
            Category:
            <select name="category" required>
                <option value="SPORTS">Sports</option>
                <option value="MUSIC">Music</option>
                <option value="ACADEMIC">Academic</option>
                <option value="SOCIAL">Social</option>
                <option value="OTHER">Other</option>
            </select><br><br>
            Image: <input type="file" name="image"><br><br>
            <input type="submit" value="Create Event">
        </form>
    </body>
</html>

