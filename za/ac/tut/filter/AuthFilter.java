package za.ac.tut.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.model.entity.User;

@WebFilter("/secured/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // Role-based access check
        String uri = req.getRequestURI();

        if (uri.contains("/secured/student/") && user.getRole() != User.Role.STUDENT) {
            res.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
            return;
        }

        if (uri.contains("/secured/admin/") && user.getRole() != User.Role.ADMIN) {
            res.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
            return;
        }
        
        if (uri.contains("/secured/organizer/") && user.getRole() != User.Role.ORGANIZER) {
            res.sendRedirect(req.getContextPath() + "/unauthorized.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {}
    public void destroy() {}
}
