package ru.hip_spb.servlet;

import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.hip_spb.dao.ConcertDAO;
import ru.hip_spb.dao.DAOException;
import ru.hip_spb.dao.PerformerDAO;
import ru.hip_spb.model.Concert;
import ru.hip_spb.model.Performer;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 10,      // 10 MB
	maxRequestSize = 1024 * 1024 * 100   // 100 MB
)


public class AddConcertServlet extends HttpServlet {
     
        private static final Logger logger = Logger.getLogger(AddConcertServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            
                final int ACT_GET_NAMES = 1;
                
                int action = Integer.parseInt(request.getParameter("act"));
                                
                switch(action) {
                    
                    // Get a list of performers' names matching "substr" and sends them back as a json
                    case ACT_GET_NAMES:
                        try {
                            String subString = request.getParameter("substr");
                            PerformerDAO performerDAO = new PerformerDAO();
                            ArrayList<String> namesList = performerDAO.getNamesList(subString);
                            PrintWriter writer = response.getWriter();
                            
                            //convert to JSON
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            
                            String jsonString = gson.toJson(namesList);
                            
                            writer.print(jsonString);
                            writer.close();
                            
                        } catch (DAOException ex) {
                            logger.log(Level.WARNING, "AddConcertServlet: getNames: error reading DB\n{0}", ex);
                        }
                        break;
                }
            }
        
        
        @Override
        protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
            
            //PrintWriter writer = response.getWriter();
            Concert concert = getConcertFormData(request);
            ConcertDAO concertDAO;
            
            try {
                 concertDAO= new ConcertDAO();
                 concertDAO.insert(concert);
            } catch (DAOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            
        } 
        
        private Concert getConcertFormData(HttpServletRequest request) {
            
            String programName = request.getParameter("program");
            String concertDate = request.getParameter("date");
            String concertTime = request.getParameter("time");
                       
            String performerNames[] = request.getParameterValues("performer");
            Performer performers[] = new Performer[performerNames.length];
            
            for(int i = 0; i < performers.length; i++) {
                performers[i] = new Performer(0, performerNames[i]);
            }
           
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(concertDate + " " + concertTime, formatter);
                          
            return new Concert(
                        0,
                        dateTime,
                        null,
                        programName,
                        null,
                        performers
            );
        }
}
