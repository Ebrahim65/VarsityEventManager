package za.ac.tut.web;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import za.ac.tut.model.bl.EventFacadeLocal;
import za.ac.tut.model.entity.Event;
import za.ac.tut.model.entity.User;

@WebServlet(name = "CreatEventServlet", urlPatterns = {"/organizer/CreatEventServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024)
public class CreateEventServlet extends HttpServlet {

    @EJB
    private EventFacadeLocal eventFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get form fields
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String dateTimeStr = request.getParameter("dateTime");
            String location = request.getParameter("location");
            String categoryStr = request.getParameter("category");

            // Parse date/time
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Handle file upload
            String imagePath = null;
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                // Get the context-relative path
                String relativeUploadPath = "/uploads";

                // Get the absolute path on server
                String absoluteUploadPath = getServletContext().getRealPath(relativeUploadPath);

                // Create directory if needed
                File uploadDir = new File(absoluteUploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Generate safe filename
                String fileName = System.currentTimeMillis() + "_"
                        + filePart.getSubmittedFileName().replaceAll("[^a-zA-Z0-9.-]", "_");

                // Save file
                filePart.write(absoluteUploadPath + File.separator + fileName);

                // Set the context-relative path for web access
                imagePath = relativeUploadPath + "/" + fileName;

                System.out.println("Real path: " + getServletContext().getRealPath("/uploads"));
                System.out.println("Attempting to write to: " + absoluteUploadPath + File.separator + fileName);
            }

            // Get organizer from session
            HttpSession session = request.getSession(false);
            User organizer = (User) session.getAttribute("user");

            // Create and save event
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setDateTime(dateTime);
            event.setLocation(location);
            event.setCategory(Event.Category.valueOf(categoryStr));
            event.setImagePath(imagePath);
            event.setOrganizer(organizer);

            eventFacade.create(event);

            request.setAttribute("msg", "EventCreated");
            request.getRequestDispatcher("/organizer/OrganizerDashboardServlet").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/secured/organizer/create_event.jsp?error=UploadFailed");
        }
    }
}
