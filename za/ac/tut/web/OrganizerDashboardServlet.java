/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.ac.tut.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.model.bl.EventFacadeLocal;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.User;

/**
 *
 * @author Ebrahim
 */
@WebServlet(name = "OrganizerDashboardServlet", urlPatterns = {"/organizer/OrganizerDashboardServlet"})
public class OrganizerDashboardServlet extends HttpServlet {

    @EJB
    private EventFacadeLocal eventFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User organizer = (User) session.getAttribute("user");

        if (organizer == null || organizer.getRole() != User.Role.ORGANIZER) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Event> events = eventFacade.findByOrganizer(organizer);
        request.setAttribute("eventList", events);
        request.getRequestDispatcher("secured/organizer/dashboard.jsp").forward(request, response);
    }
}

