/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.szymon.gretka.servlet.carshowroom;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.szymon.gretka.beans.CarShowroomService;

/**
 *
 * @author Szymek
 */
public class DeleteCarshowroomServlet extends HttpServlet {

    @EJB
    CarShowroomService carShowroomService;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
             throws ServletException, IOException {  
        String carShowroomId = request.getParameter("id");  
        Long id = Long.parseLong(carShowroomId);
        carShowroomService.deleteCarshowroom(id);
        response.sendRedirect("CarshowroomServlet");  
    } 

}
