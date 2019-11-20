/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.szymon.gretka.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.szymon.gretka.beans.CarService;
import pl.polsl.szymon.gretka.entity.Car;

/**
 *
 * @author Szymek
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

        out.println("<html>");
        out.println("<head><title>Table Example</title></head>");
        out.println("<body>");
        out.println("<h3>CarServlet</h3>");
        out.println("<p>All cars: " + carService.getAllCars() + "</p>");
        out.println("<form method='post' action='car'>");
        out.println("<p>Enter Model: <input type='text' name='model'></p>");
        out.println("<p>Enter Colour: <input type='text' name='colour'></p>");
        out.println("<p>Enter Brand: <input type='text' name='brand'></p>");
        out.println("<p>Enter Year: <input type='text' name='year'></p>");
        out.println("<p><input type='submit' value='submit'></p>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        String model = request.getParameter("model");
        String colour = request.getParameter("colour");
        String brand = request.getParameter("brand");
        String yearStr = request.getParameter("year");
        
        
 //       Enumeration<String> names = request.getParameterNames();
//            
//            while(names.hasMoreElements()) {
//                String name = names.nextElement();
//                out.println(name + " " + request.getParameter(name) + "<br/>");
//            }
//            String name = request.getParameter("name");
 //           String age = request.getParameter("age");

 //           if("admin".equals(name)) {
 //               HttpSession session = request.getSession();
//                session.setAttribute("LOGGED_IN", true);
//            }
//            
//            if (name == null || name.isEmpty()) { // name.length() == 0 // name.equals("")
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//                        "Name should not be empty");
//                return;
//            }
//            if (age == null || !age.matches("[0-9]+")) {
////                response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
////                        "Age should be an integer");
//                response.sendRedirect("http://google.com");
//                return;
//            }

//            request.getRequestDispatcher("/SecondServlet").
//                    forward(request, response);
//            System.out.println("I'm here");
//            out.println(name + " " + age + "<br/>");
//            request.getRequestDispatcher("/SecondServlet").
//                    forward(request, response);
//        }

        
        
        
        if(model == null || colour ==  null || brand == null || yearStr == null) { //TODO check if year is integer
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Car</title></head><body>");
            out.println("<p>blad</p>");
            out.println("</body></html>");
            
        } else {
            
            Integer year = Integer.valueOf(yearStr);

            Car createdCar = new Car(brand, colour, model, year);
            carService.createCar(createdCar);
        
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Car</title></head><body>");
            out.println("<p>New car: " + createdCar.toString() + ".</p>");
            out.println("</body></html>");
        
        }

    }
    
    
    
    
    
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
