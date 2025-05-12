package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import za.ac.tut.model.bl.EventFacadeLocal;
import za.ac.tut.model.bl.RegistrationFacadeLocal;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.Registration;
import za.ac.tut.model.entity.User;

@WebServlet("/secured/student/RegisterEventServlet")
public class RegisterEventServlet extends HttpServlet {

    @EJB
    private EventFacadeLocal eventFacade;

    @EJB
    private RegistrationFacadeLocal registrationFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User student = (User) session.getAttribute("user");

        if (student == null || student.getRole() != User.Role.STUDENT) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Long eventId = Long.parseLong(request.getParameter("eventId"));
        Event event = eventFacade.find(eventId);

        if (!registrationFacade.isUserRegisteredForEvent(student, event)) {
            Registration registration = new Registration();
            registration.setUser(student);
            registration.setEvent(event);
            registrationFacade.create(registration);
        }

        response.sendRedirect("dashboard.jsp");
    }
}
