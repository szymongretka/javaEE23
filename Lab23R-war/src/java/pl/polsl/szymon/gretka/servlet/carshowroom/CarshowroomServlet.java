package pl.polsl.szymon.gretka.servlet.carshowroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
 * Carshowroom servlet used for Read and Create operation
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
@WebServlet("/carShowroom")
public class CarshowroomServlet extends HttpServlet {

    @EJB
    private CarShowroomService carShowroomService;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        Map<String, Integer> carShowroomCrudCounter = 
                (Map) session.getAttribute("carShowroomCrudCounter");
        
        if(carShowroomCrudCounter == null || carShowroomCrudCounter.isEmpty()) {
            carShowroomCrudCounter = new HashMap<>();
            carShowroomCrudCounter.put("readCarshowroom", 1);
            carShowroomCrudCounter.put("deleteCarshowroom", 0);
            session.setAttribute("carShowroomCrudCounter", carShowroomCrudCounter);
        } else {
            if(carShowroomCrudCounter.containsKey("readCarshowroom")) {
                Integer value = carShowroomCrudCounter.get("readCarshowroom");
                value++;
                carShowroomCrudCounter.put("readCarshowroom", value);
            } else {
                carShowroomCrudCounter.put("readCarshowroom", 1);
            }   
        }
        
        List<CarShowroom> carShowroomList = carShowroomService.getAllCarShowrooms();

        out.println("<html>");
        out.println("<head><title>Table</title></head>");
        out.println("<body>");
        out.println("<h2>CarShowroomServlet</h2>");
        out.println("<p>All Carshowrooms: </p>");
        out.print("<table border='1' width='100%'");  
        out.print("<tr><th>Id</th><th>Name</th><th>City</th><th>Street</th>"
                + "<th>Edit</th><th>Delete</th></tr>");  
        carShowroomList.forEach((carShowroom) -> {
            out.print("<tr><td>"+carShowroom.getId()+"</td><td>"+carShowroom.getName()+"</td><td>"
                    + carShowroom.getCity()+ "</td><td>" + carShowroom.getStreet()+"</td>"
                            + "<td><a href='EditCarshowroomServlet?id="+carShowroom.getId()+"'>edit</a></td>"
                                    + "<td><a href='DeleteCarshowroomServlet?id="+carShowroom.getId()+"'>delete</a></td></tr>");
        });  
        out.print("</table>");  
        out.println("<p>Read operation counter: " + carShowroomCrudCounter.get("readCarshowroom") + "</p>");
        out.println("<p>Delete operation counter: " + carShowroomCrudCounter.get("deleteCarshowroom") + "</p>");
        out.println("<h3>Enter new carshowroom here: </h3>");
        out.println("<form method='post' action='carShowroom'>");
        out.println("<p>Enter Name: <input type='text' name='name'></p>");
        out.println("<p>Enter City: <input type='text' name='city'></p>");
        out.println("<p>Enter Street: <input type='text' name='street'></p>");
        out.println("<p><input type='submit' value='submit'></p>");
        out.println("</form>");
        out.println("<a href='CarServlet'>Car servlet</a>");
        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

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
        
        CarShowroom createdCarshowroom = new CarShowroom(name, city, street);
        carShowroomService.createCarshowroom(createdCarshowroom);
        
        
        HttpSession session = request.getSession();
        Map<String, Integer> carShowroomCrudCounter = 
                (Map) session.getAttribute("carShowroomCrudCounter");
        
        if(carShowroomCrudCounter == null || carShowroomCrudCounter.isEmpty()) {
            carShowroomCrudCounter = new HashMap<>();
            carShowroomCrudCounter.put("createCarshowroom", 1);
            session.setAttribute("carShowroomCrudCounter", carShowroomCrudCounter);
        } else {
            if(carShowroomCrudCounter.containsKey("createCarshowroom")) {
                Integer value = carShowroomCrudCounter.get("createCarshowroom");
                value++;
                carShowroomCrudCounter.put("createCarshowroom", value);
            } else {
                carShowroomCrudCounter.put("createCarshowroom", 1);
            }   
        }

        out.println("<html><head><title>Carshowroom</title></head><body>");
        out.println("<p>New carshowroom: " + createdCarshowroom.toString() + ".</p>");
        out.println("<p>Create operation counter: " + carShowroomCrudCounter.get("createCarshowroom") + "</p>");
        out.println("<a href='CarshowroomServlet'>CarshowroomServlet</a>");
        out.println("</body></html>");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
