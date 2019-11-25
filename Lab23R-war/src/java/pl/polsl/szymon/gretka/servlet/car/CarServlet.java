package pl.polsl.szymon.gretka.servlet.car;

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
import pl.polsl.szymon.gretka.beans.CarService;
import pl.polsl.szymon.gretka.entity.Car;

/**
 * Car servlet used for Read and Create operation
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
@WebServlet("/car")
public class CarServlet extends HttpServlet {

    @EJB
    private CarService carService;
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        Map<String, Integer> carCrudCounter = (Map) session.getAttribute("carCrudCounter");
        
        if(carCrudCounter == null || carCrudCounter.isEmpty()) {
            carCrudCounter = new HashMap<>();
            carCrudCounter.put("read", 1);
            carCrudCounter.put("delete", 0);
            session.setAttribute("carCrudCounter", carCrudCounter);
        } else {
            if(carCrudCounter.containsKey("read")) {
                Integer value = carCrudCounter.get("read");
                value++;
                carCrudCounter.put("read", value);
            } else {
                carCrudCounter.put("read", 1);
            }   
        }
        
        List<Car> carList = carService.getAllCars();

        out.println("<html>");
        out.println("<head><title>Table</title></head>");
        out.println("<body>");
        out.println("<h2>CarServlet</h2>");
        out.println("<p>All cars: </p>");
        out.print("<table border='1' width='100%'");  
        out.print("<tr><th>Id</th><th>Brand</th><th>Model</th><th>Colour</th>"
                + "<th>Year</th><th>Edit</th><th>Delete</th></tr>");  
        for(Car car : carList){  
         out.print("<tr><td>"+car.getId()+"</td><td>"+car.getBrand()+"</td><td>"
                 + car.getModel() + "</td><td>" + car.getColour()+"</td>"
                 + "<td>" + car.getYear() + "</td>"
                 + "<td><a href='EditCarServlet?id="+car.getId()+"'>edit</a></td>"
                 + "<td><a href='DeleteCarServlet?id="+car.getId()+"'>delete</a></td></tr>");  
        }  
        out.print("</table>");
        out.println("<p>Read operation counter: " + carCrudCounter.get("read") + "</p>");
        out.println("<p>Delete operation counter: " + carCrudCounter.get("delete") + "</p>");
        out.println("<h3>Enter new car here: </h3>");
        out.println("<form method='post' action='car'>");
        out.println("<p>Enter Model: <input type='text' name='model'></p>");
        out.println("<p>Enter Colour: <input type='text' name='colour'></p>");
        out.println("<p>Enter Brand: <input type='text' name='brand'></p>");
        out.println("<p>Enter Year: <input type='text' name='year'></p>");
        out.println("<p><input type='submit' value='submit'></p>");
        out.println("<a href='CarshowroomServlet'>Carshowroom servlet</a>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

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

        Car createdCar = new Car(brand, colour, model, year);
        carService.createCar(createdCar);
        
        HttpSession session = request.getSession();
        Map<String, Integer> carCrudCounter = (Map) session.getAttribute("carCrudCounter");
        
        if(carCrudCounter == null || carCrudCounter.isEmpty()) {
            carCrudCounter = new HashMap<>();
            carCrudCounter.put("create", 1);
            session.setAttribute("carCrudCounter", carCrudCounter);
        } else {
            if(carCrudCounter.containsKey("create")) {
                Integer value = carCrudCounter.get("create");
                value++;
                carCrudCounter.put("create", value);
            } else {
                carCrudCounter.put("create", 1);
            }   
        }

        out.println("<html><head><title>Car</title></head><body>");
        out.println("<p>New car: " + createdCar.toString() + ".</p>");
        out.println("<p>Create operation counter: " + carCrudCounter.get("create") + "</p>");
        out.println("<a href='CarServlet'>Car servlet</a>");
        out.println("</body></html>");

    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
