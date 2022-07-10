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

import ru.hip_spb.controller.PlaceController;
import ru.hip_spb.dao.ConcertDAO;
import ru.hip_spb.dao.DAOException;
import ru.hip_spb.dao.PlaceDAO;
import ru.hip_spb.model.Concert;
import ru.hip_spb.model.EnsembleName;
import ru.hip_spb.model.Instrument;
import ru.hip_spb.model.Performer;
import ru.hip_spb.model.Place;

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

                final int ACT_GET_ADDRESS = 1;
                
                int action = 0;
                
                try {
                    action = Integer.parseInt(request.getParameter("act"));
                } catch(NumberFormatException exception) { }

                //TODO: maybe to use JSON although I have one string only?
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                                
                switch(action) {
                    
                    // Get an address of a place
                    case ACT_GET_ADDRESS:
                        
                        String placeName = request.getParameter("name");
                        PlaceController placeController = new PlaceController();
                        String placeAddress = placeController.getAddressByPlaceName(placeName);
                        writer.print(placeAddress);

                        break;
                }

                writer.close();
            }
        
        @Override
        protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
            
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");

            PrintWriter writer = response.getWriter();

            try {
                saveConcertFormData(request);
            } catch (DAOException e) {
                logger.log(Level.INFO, e.getMessage());
                writer.print("<BR> Не удалось записать информацию");
            }
            
            writer.print("<BR> Информация успешно сохранена");
        } 
        
        /*
         * Gets info from a form and saves it to DB
         */
        private void saveConcertFormData(HttpServletRequest request) throws DAOException {
            
            String programName = request.getParameter("program_name");
            String concertDate = request.getParameter("date");
            String concertTime = request.getParameter("time");
            String placeName = request.getParameter("place");
            String placeAddress = request.getParameter("address");
            String link = request.getParameter("link");
            
            ArrayList<Performer> performers = new ArrayList<>();
                        
            String ensembleNamesStr[] = request.getParameterValues("ensemble");

            for(int i = 0; i < ensembleNamesStr.length; i++) {
                
                String performerNames[] = request.getParameterValues("performer" + i);
            
                // Generate Performer objects
                for(int j = 0; j < performerNames.length; j++) {
                    
                    String[] instrumentNames = request.getParameterValues("instrument" + i + "_" + "j");
                    Instrument[] instruments = new Instrument[instrumentNames.length];
                    
                    for(int k = 0; k < instrumentNames.length; k++) {
                        instruments[k] = new Instrument(0, instrumentNames[k]);
                    }

                    performers.add(
                        new Performer(0, performerNames[j], new EnsembleName(0, ensembleNamesStr[i]), instruments));
                }
            }
                       
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(concertDate + " " + concertTime, formatter);

            PlaceDAO placeDAO = new PlaceDAO();
            Place place = placeDAO.getByNameOrCreate(placeName, placeAddress);
            
            Concert concert = new Concert(
                        0,
                        dateTime,
                        place,
                        programName,
                        link,
                        performers.toArray(new Performer[0])
            );

            ConcertDAO concertDAO = new ConcertDAO();
            concertDAO.insert(concert);
        }
}
