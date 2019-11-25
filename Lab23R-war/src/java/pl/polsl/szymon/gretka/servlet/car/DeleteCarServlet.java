package pl.polsl.szymon.gretka.servlet.car;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.szymon.gretka.beans.CarService;

/**
 * Car servlet used for Delete operation
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
public class DeleteCarServlet extends HttpServlet {

    @EJB
    CarService carService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
             throws ServletException, IOException {  
        
        HttpSession session = request.getSession();
        Map<String, Integer> carCrudCounter = 
                (Map) session.getAttribute("carCrudCounter");
        
        if(carCrudCounter == null || carCrudCounter.isEmpty()) {
            carCrudCounter = new HashMap<>();
            carCrudCounter.put("delete", 1);
            session.setAttribute("carCrudCounter", carCrudCounter);
        } else {
            if(carCrudCounter.containsKey("delete")) {
                Integer value = carCrudCounter.get("delete");
                value++;
                carCrudCounter.put("delete", value);
            } else {
                carCrudCounter.put("delete", 1);
            }   
        }
        
        
        String carId = request.getParameter("id");  
        Long id = Long.parseLong(carId);
        carService.deleteCar(id);
        response.sendRedirect("CarServlet");  
    }  

}
