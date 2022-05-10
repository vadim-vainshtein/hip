package ru.hip_spb.servlet;

import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.hip_spb.dao.ConcertDAO;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 10,      // 10 MB
	maxRequestSize = 1024 * 1024 * 100   // 100 MB
)


public class HipServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
            
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" /></head>");
            writer.println("<body>HipServlet<br>");
            
            String connInfo;
            
            try {
                writer.println("Trying to get connection info...<br>");
                connInfo = ConcertDAO.TestConnection();
                writer.println("Got it!<br>");
            } catch (SQLException ex) {
                writer.print("Fail!<br>");
                Logger.getLogger(HipServlet.class.getName()).log(Level.SEVERE, null, ex);
                connInfo = ex.toString();
            }
            
            writer.println(connInfo);
            
            writer.println("</body></html>");
	}
     
        @Override
        protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
            
            PrintWriter writer = response.getWriter();
                        
            String programName = request.getParameter("program");
            String concertDate = request.getParameter("date");
            String concertTime = request.getParameter("time");
                
            writer.println("Program name: "  + programName);
            writer.println("Concert date: " + concertDate);
            writer.println("Concert time: " + concertTime);
            
            String performer = request.getParameter("performer0");
            int i = 1;
            
            while(performer != null) {
                writer.println("performer " + i + ": " + performer);
                performer = request.getParameter("performer" + i);
                i++;
            }
        } 
}
