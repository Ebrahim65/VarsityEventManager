package za.ac.tut.web;

import javax.ejb.EJB;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.model.bl.EventFacadeLocal;
import za.ac.tut.model.bl.UserFacadeLocal;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.User;
import za.ac.tut.util.PasswordUtil;

/**
 *
 * @author Ebrahim
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private EventFacadeLocal efv;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashedInputPassword = PasswordUtil.hashPassword(password);

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Both fields are required.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        User user = userFacade.findByUsername(username);
        if (user == null) {
            System.out.println("User not found: " + username);
        } else {
            System.out.println("User found. Stored password: " + user.getPassword());
            System.out.println("Entered (hashed) password: " + hashedInputPassword);
        }

        if (user != null && user.getPassword().equals(hashedInputPassword)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on role
            switch (user.getRole()) {
                case STUDENT:
                    List<Event> events = efv.findAll();
                    request.setAttribute("events", events);
                    response.sendRedirect("secured/student/dashboard.jsp");
                    break;
                case ORGANIZER:
                    response.sendRedirect("secured/organizer/dashboard.jsp");
                    break;
                case ADMIN:
                    response.sendRedirect("secured/admin/dashboard.jsp");
                    break;
            }

        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
