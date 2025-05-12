package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import za.ac.tut.model.bl.RegistrationFacadeLocal;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.Registration;
import za.ac.tut.model.entity.User;

@WebServlet(name = "CancelRegistrationServlet", urlPatterns = {"/student/CancelRegistrationServlet"})
public class CancelRegistrationServlet extends HttpServlet {

    @EJB
    private RegistrationFacadeLocal registrationFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User student = (User) session.getAttribute("user");

        if (student == null || student.getRole() != User.Role.STUDENT) {
            response.sendRedirect("../login.jsp");
            return;
        }

        Long eventId = Long.parseLong(request.getParameter("eventId"));

        Event event = new Event();
        event.setId(eventId);

        // Find the registration entry
        Registration registrationToRemove = null;
        for (Registration r : registrationFacade.findByUser(student)) {
            if (r.getEvent().getId().equals(eventId)) {
                registrationToRemove = r;
                break;
            }
        }

        if (registrationToRemove != null) {
            registrationFacade.remove(registrationToRemove);
        }

        response.sendRedirect("StudentDashboardServlet");
    }
}
