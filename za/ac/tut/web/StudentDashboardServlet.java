package za.ac.tut.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import za.ac.tut.model.bl.EventFacadeLocal;
import za.ac.tut.model.bl.RegistrationFacadeLocal;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.Registration;
import za.ac.tut.model.entity.User;

@WebServlet(name = "StudentDashboardServlet", urlPatterns = {"/student/StudentDashboardServlet"})
public class StudentDashboardServlet extends HttpServlet {

    @EJB
    private EventFacadeLocal eventFacade;

    @EJB
    private RegistrationFacadeLocal registrationFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User student = (User) session.getAttribute("user");

        if (student == null || student.getRole() != User.Role.STUDENT) {
            response.sendRedirect("../login.jsp");
            return;
        }

        // Get all upcoming events
        List<Event> events = eventFacade.findAll(); // Implement this if not already

        // Get registrations for the student
        List<Registration> registrations = registrationFacade.findByUser(student);

        // Extract registered event IDs
        List<Long> registeredEventIds = new ArrayList<>();
        for (Registration r : registrations) {
            registeredEventIds.add(r.getEvent().getId());
        }

        request.setAttribute("eventList", events);
        request.setAttribute("registeredEventIds", registeredEventIds);

        RequestDispatcher rd = request.getRequestDispatcher("secured/student/dashboard.jsp");
        rd.forward(request, response);
    }
}
