/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.ac.tut.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.model.bl.UserFacadeLocal;
import za.ac.tut.model.entity.User;
import za.ac.tut.util.PasswordUtil;

/**
 *
 * @author Ebrahim
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {

    @EJB
    private UserFacadeLocal userFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String roleStr = request.getParameter("role");

        // Validation
        if (email.isEmpty()) {
            request.setAttribute("error", "Email is required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || roleStr == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (username == null || username.trim().isEmpty() || password == null || password.length() < 4) {
            request.setAttribute("error", "Username is required and password must be at least 4 characters.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (userFacade.findByUsername(username) != null) {
            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        /*if (userFacade.findByEmail(email) != null) {
            request.setAttribute("error", "Email is already registered.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }*/

        // Create and persist user
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(PasswordUtil.hashPassword(password));
        newUser.setRole(User.Role.valueOf(roleStr));

        userFacade.create(newUser);

        response.sendRedirect("login.jsp");
    }
}
