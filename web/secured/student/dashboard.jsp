<%-- 
    Document   : dashboard
    Created on : 12 May 2025, 00:32:42
    Author     : Ebrahim
--%>

<%@ page import="za.ac.tut.model.entity.Event, java.util.List" %>
<%@ page session="true" %>
<%
    za.ac.tut.model.entity.User student = (za.ac.tut.model.entity.User) session.getAttribute("user");
    if (student == null || student.getRole() != za.ac.tut.model.entity.User.Role.STUDENT) {
        response.sendRedirect("../login.jsp");
        return;
    }

    List<Event> events = (List<Event>) request.getAttribute("events");
    List<Long> registeredEventIds = (List<Long>) request.getAttribute("registeredEventIds"); // Passed from servlet
%>

<html>
    <head><title>Student Dashboard</title></head>
    <body>
        <h2>Welcome, <%= student.getUsername()%> (Student)</h2>
        <form action="<%= request.getContextPath()%>/student/StudentDashboardServlet" method="get">
            <button type="submit">Refresh Events</button>
        </form>


        <h3>Upcoming Events:</h3>
        <%
            if (events != null && !events.isEmpty()) {
                for (Event e : events) {
                    boolean isRegistered = registeredEventIds != null && registeredEventIds.contains(e.getId());
        %>
        <div style="border:1px solid gray; padding:10px; margin-bottom:10px;">
            <b><%= e.getTitle()%></b><br>
            <%= e.getDateTime()%> @ <%= e.getLocation()%><br>
            Category: <%= e.getCategory()%><br>
            <% if (e.getImagePath() != null) {%>
            <img src="<%= request.getContextPath() + "/" + e.getImagePath()%>" width="200"/><br>
            <% }%>
            <i><%= e.getDescription()%></i><br><br>

            <% if (!isRegistered) {%>
            <form action="RegisterEventServlet" method="post">
                <input type="hidden" name="eventId" value="<%= e.getId()%>"/>
                <input type="submit" value="Register"/>
            </form>
            <% } else {%>
            <form action="CancelRegistrationServlet" method="post">
                <input type="hidden" name="eventId" value="<%= e.getId()%>"/>
                <input type="submit" value="Cancel Registration" style="background-color: red; color: white;"/>
            </form>
            <% } %>
        </div>
        <%
            }
        } else {
        %>
        <p>No upcoming events found.</p>
        <%
            }
        %>
    </body>
</html>



