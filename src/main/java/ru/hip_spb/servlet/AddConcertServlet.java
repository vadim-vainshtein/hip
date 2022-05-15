package ru.hip_spb.servlet;

import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

import ru.hip_spb.dao.ConcertDAO;
import ru.hip_spb.dao.DAOException;
import ru.hip_spb.model.Concert;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 10,      // 10 MB
	maxRequestSize = 1024 * 1024 * 100   // 100 MB
)


public class AddConcertServlet extends HttpServlet {
     
        private static final Logger logger = Logger.getLogger(AddConcertServlet.class.getName());
        
        @Override
        protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
            
            PrintWriter writer = response.getWriter();
            Concert concert = getConcertFormData(request, writer);
            ConcertDAO concertDAO;
            
            try {
                 concertDAO= new ConcertDAO();
                 concertDAO.insert(concert);
            } catch (DAOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            
        } 
        
        private Concert getConcertFormData(HttpServletRequest request, PrintWriter writer) {
            
            String programName = request.getParameter("program");
            String concertDate = request.getParameter("date");
            String concertTime = request.getParameter("time");
            
            logger.log(Level.INFO, "getConcertFormData(): " + programName + "; " + concertDate + "; " + concertTime);
            
            writer.print(concertDate + "<br>" + concertTime);
            
            String performers[] = request.getParameterValues("performer0");
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(concertDate + " " + concertTime, formatter);
                          
            return new Concert(
                        0,
                        dateTime,
                        null,
                        programName,
                        null,
                        null
            );
        }
}
