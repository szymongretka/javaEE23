package pl.polsl.szymon.gretka.servlet.carshowroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.szymon.gretka.beans.CarService;
import pl.polsl.szymon.gretka.beans.CarShowroomService;
import pl.polsl.szymon.gretka.entity.Car;
import pl.polsl.szymon.gretka.entity.CarShowroom;

/**
 *
 * @author Szymon Gretka
 */
@WebServlet("/editCarshowroom")
public class EditCarshowroomServlet extends HttpServlet {

    @EJB
    CarShowroomService carShowroomService;
    
    @EJB
    private CarService carService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
           throws ServletException, IOException {  
        
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
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
        out.print("<tr>Enter IDs of the cars which would you like to\"\n" +
"                    + \" add and split them with the commas\"\n" +
"                    + \" if you don't want to add any cars just leave it blank: "
                + "\" : <input type='text' name='listOfIds'></tr>");
        out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");  
        out.print("</table>");  
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
        String listOfIds = request.getParameter("listOfIds");


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

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(listOfIds);
        List<Car> cars = new ArrayList<>();

        if (!listOfIds.isEmpty()) {
            while (m.find()) {
                Long carId = Long.valueOf(m.group());
                Car car = carService.getCarById(carId);
                cars.add(car);
            }
        }
        
        editedCarShowroom.setCars(cars);
        carShowroomService.updateCarShowroom(id, editedCarShowroom);

        response.sendRedirect("CarshowroomServlet");  
          
        out.close();  
    }  
}
