package pl.polsl.szymon.gretka.servlet.carshowroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.szymon.gretka.beans.CarShowroomService;
import pl.polsl.szymon.gretka.entity.CarShowroom;

/**
 * Carshowroom servlet used for Edit operation
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
@WebServlet("/editCarshowroom")
public class EditCarshowroomServlet extends HttpServlet {

    @EJB
    CarShowroomService carShowroomService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
           throws ServletException, IOException {  
        
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter(); 
        
        HttpSession session = request.getSession();
        Map<String, Integer> carShowroomCrudCounter = 
                (Map) session.getAttribute("carShowroomCrudCounter");
        
        if(carShowroomCrudCounter == null || carShowroomCrudCounter.isEmpty()) {
            carShowroomCrudCounter = new HashMap<>();
            carShowroomCrudCounter.put("editCarshowroom", 1);
            session.setAttribute("carShowroomCrudCounter", carShowroomCrudCounter);
        } else {
            if(carShowroomCrudCounter.containsKey("editCarshowroom")) {
                Integer value = carShowroomCrudCounter.get("editCarshowroom");
                value++;
                carShowroomCrudCounter.put("editCarshowroom", value);
            } else {
                carShowroomCrudCounter.put("editCarshowroom", 1);
            }   
        }
        
        out.println("<h1>Update Carshowroom</h1>");
        
        String carShowroomId = request.getParameter("id");  
        Long id = Long.parseLong(carShowroomId);
          
        CarShowroom carShowroom = carShowroomService.getCarshowroomById(id);
          
        out.print("<form action='editCarshowroom' method='post'>");  
        out.print("<table>");  
        out.print("<tr><td></td><td><input type='hidden' name='id' value='"+carShowroom.getId()+"'/></td></tr>");  
        out.print("<tr><td>Name:</td><td><input type='text' name='name' value='"+carShowroom.getName()+"'/></td></tr>");  
        out.print("<tr><td>City:</td><td><input type='text' name='city' value='"+carShowroom.getCity()+"'/></td></tr>");  
        out.print("<tr><td>Street:</td><td><input type='text' name='street' value='"+carShowroom.getStreet()+"'/></td></tr>");
        out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");  
        out.print("</table>");
        out.println("<p>Edit operation counter: " + carShowroomCrudCounter.get("editCarshowroom") + "</p>");
        out.print("</form>");  
          
        out.close();  
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
          throws ServletException, IOException {  
        
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        String carShowroomId = request.getParameter("id");  
        Long id = Long.parseLong(carShowroomId);
        
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String street = request.getParameter("street");

        Map<String, String> errorMap = new HashMap<>();

        if (name == null || name.isEmpty()) {
            errorMap.put("name", "cannot be empty!");
        }
        if (city == null || city.isEmpty()) {
            errorMap.put("city", "cannot be empty!");
        }
        if (street == null || street.isEmpty()) {
            errorMap.put("street", "cannot be empty!");
        }

        if (!errorMap.isEmpty()) {
            request.setAttribute("errorMap", errorMap);
            request.getRequestDispatcher("/ErrorServlet").
                    forward(request, response);
        }
        
        CarShowroom editedCarShowroom = new CarShowroom(name, city, street);

        carShowroomService.updateCarShowroom(id, editedCarShowroom);

        response.sendRedirect("CarshowroomServlet");  
          
        out.close();  
    }  
}
