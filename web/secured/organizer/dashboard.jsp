<%-- 
    Document   : dashboard
    Created on : 12 May 2025, 00:54:22
    Author     : Ebrahim
--%>

<%@ page import="za.ac.tut.model.entity.Event, java.util.List" %>
<jsp:useBean id="eventList" class="java.util.ArrayList" scope="request" />
<%@ page session="true" %>
<%
    String msg = request.getParameter("msg");
    if ("EventCreated".equals(msg)) {
%>
<div style="background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 10px;">
    Event successfully created!
</div>
<%
    }
%>
<%
    za.ac.tut.model.entity.User organizer = (za.ac.tut.model.entity.User) session.getAttribute("user");
    if (organizer == null || organizer.getRole() != za.ac.tut.model.entity.User.Role.ORGANIZER) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>


<html>
    <head><title>Organizer Dashboard</title></head>
    <body>
        <h2>Welcome, <%= organizer.getUsername()%> (Organizer)</h2>
        <form action="<%= request.getContextPath()%>/organizer/OrganizerDashboardServlet" method="get">
            <button type="submit">Refresh My Events</button>
        </form>

        <a href="create_event.jsp">+ Create New Event</a><br><br>

        <h3>Your Events:</h3>
        <%
            List<Event> events = (List<Event>) request.getAttribute("eventList");
            if (events != null && !events.isEmpty()) {
                for (Event e : events) {
        %>
        <div style="border:1px solid gray; padding:10px; margin-bottom:10px;">
            <b><%= e.getTitle()%></b><br>
            <%= e.getDateTime()%> @ <%= e.getLocation()%><br>
            Category: <%= e.getCategory()%><br>
            <% if (e.getImagePath() != null) {%>
            <img src="<%= request.getContextPath() + "/" + e.getImagePath()%>" width="200"/><br>
            <% }%>
            <i><%= e.getDescription()%></i>
        </div>
        <%
            }
        } else {
        %>
        <p>No events created yet.</p>
        <%
            }
        %>
    </body>
</html>

