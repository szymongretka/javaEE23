package pl.polsl.szymon.gretka.servlet.car;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.szymon.gretka.beans.CarService;

/**
 *
 * @author Szymek
 */
public class DeleteCarServlet extends HttpServlet {

    @EJB
    CarService carService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
             throws ServletException, IOException {  
        String carId = request.getParameter("id");  
        Long id = Long.parseLong(carId);
        carService.deleteCar(id);
        response.sendRedirect("CarServlet");  
    }  

}
