package pl.polsl.szymon.gretka.servlet.carshowroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.szymon.gretka.beans.CarShowroomService;

/**
 * Carshowroom servlet used for Delete operation
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
public class DeleteCarshowroomServlet extends HttpServlet {

    @EJB
    CarShowroomService carShowroomService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
             throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Map<String, Integer> carShowroomCrudCounter = 
                (Map) session.getAttribute("carShowroomCrudCounter");
        
        if(carShowroomCrudCounter == null || carShowroomCrudCounter.isEmpty()) {
            carShowroomCrudCounter = new HashMap<>();
            carShowroomCrudCounter.put("deleteCarshowroom", 1);
            session.setAttribute("carShowroomCrudCounter", carShowroomCrudCounter);
        } else {
            if(carShowroomCrudCounter.containsKey("deleteCarshowroom")) {
                Integer value = carShowroomCrudCounter.get("deleteCarshowroom");
                value++;
                carShowroomCrudCounter.put("deleteCarshowroom", value);
            } else {
                carShowroomCrudCounter.put("deleteCarshowroom", 1);
            }   
        }
        
        String carShowroomId = request.getParameter("id");  
        Long id = Long.parseLong(carShowroomId);
        carShowroomService.deleteCarshowroom(id);
        response.sendRedirect("CarshowroomServlet");  
    } 

}
