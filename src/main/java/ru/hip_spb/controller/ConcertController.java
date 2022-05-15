package ru.hip_spb.controller;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import ru.hip_spb.dao.ConcertDAO;
import ru.hip_spb.dao.DAOException;
import ru.hip_spb.model.Concert;
import ru.hip_spb.view.ConcertView;

/**
 *
 */
public class ConcertController {
    
    private static final Logger logger = Logger.getLogger("ConcertController.class.getName()");
    
    private ArrayList<Concert> concerts;
    private ConcertView view;
    private Writer writer;
    private ConcertDAO concertDAO;
    
    public ConcertController() {
        
        concerts = new ArrayList<>();
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        view = new ConcertView(writer);
        try {
            concertDAO = new ConcertDAO();
            concerts = concertDAO.getAll();
        } catch (DAOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
            
    /**
     * Uses ConcertView to format information from concerts array
     * @return Returns a String object containing formatted information
     * from concerts array.
     */
    public String format() {
        
        StringBuilder stringBuilder = new StringBuilder();
        
        for(Concert concert : concerts) {
            stringBuilder.append(view.format(
                    concert.getProgramName(),
                    concert.getDateTime()));
        }
        
        return stringBuilder.toString();
    }
}
