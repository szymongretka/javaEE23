package pl.polsl.szymon.gretka.servlet.car;

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
import pl.polsl.szymon.gretka.beans.CarService;
import pl.polsl.szymon.gretka.entity.Car;

/**
 * Car servlet used for Update operation
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
@WebServlet("/editCar")
public class EditCarServlet extends HttpServlet {

    @EJB
    CarService carService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
           throws ServletException, IOException {  
        
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
        
        HttpSession session = request.getSession();
        Map<String, Integer> carCrudCounter = 
                (Map) session.getAttribute("carCrudCounter");
        
        if(carCrudCounter == null || carCrudCounter.isEmpty()) {
            carCrudCounter = new HashMap<>();
            carCrudCounter.put("edit", 1);
            session.setAttribute("carCrudCounter", carCrudCounter);
        } else {
            if(carCrudCounter.containsKey("edit")) {
                Integer value = carCrudCounter.get("edit");
                value++;
                carCrudCounter.put("edit", value);
            } else {
                carCrudCounter.put("edit", 1);
            }   
        }
        
        
        out.println("<h1>Update Car</h1>");  
        String carId = request.getParameter("id");  
        Long id = Long.parseLong(carId);
          
        Car car = carService.getCarById(id);
          
        out.print("<form action='editCar' method='post'>");  
        out.print("<table>");  
        out.print("<tr><td></td><td><input type='hidden' name='id' value='"+car.getId()+"'/></td></tr>");  
        out.print("<tr><td>Name:</td><td><input type='text' name='brand' value='"+car.getBrand()+"'/></td></tr>");  
        out.print("<tr><td>Password:</td><td><input type='text' name='model' value='"+car.getModel()+"'/></td></tr>");  
        out.print("<tr><td>Email:</td><td><input type='text' name='colour' value='"+car.getColour()+"'/></td></tr>");  
        out.print("<tr><td>Email:</td><td><input type='text' name='year' value='"+car.getYear()+"'/></td></tr>"); 
        out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");  
        out.print("</table>");
        out.println("<p>Edit operation counter: " + carCrudCounter.get("edit") + "</p>");
        out.print("</form>");  
          
        out.close();  
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
          throws ServletException, IOException {  
        
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        String carId = request.getParameter("id");  
        Long id = Long.parseLong(carId);
        
        String model = request.getParameter("model");
        String colour = request.getParameter("colour");
        String brand = request.getParameter("brand");
        String yearStr = request.getParameter("year");

        Map<String, String> errorMap = new HashMap<>();

        if (model == null || model.isEmpty()) {
            errorMap.put("model", "cannot be empty!");
        }
        if (yearStr == null || !yearStr.matches("[0-9]+")) {
            errorMap.put("year", "must be integer!");
        }
        if (colour == null || colour.isEmpty()) {
            errorMap.put("colour", "cannot be empty!");
        }
        if (brand == null || brand.isEmpty()) {
            errorMap.put("brand", "cannot be empty!");
        }

        if (!errorMap.isEmpty()) {
            request.setAttribute("errorMap", errorMap);
            request.getRequestDispatcher("/ErrorServlet").
                    forward(request, response);
        }

        Integer year = Integer.valueOf(yearStr);
        
        Car editedCar = new Car(brand, colour, model, year);
        carService.updateCar(id, editedCar);

         response.sendRedirect("CarServlet");  
          
        out.close();  
    }  
    

}
