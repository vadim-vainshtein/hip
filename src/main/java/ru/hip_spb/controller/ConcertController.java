package ru.hip_spb.controller;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @return Returns a String object containing html formatted information
     * from concerts array.
     */
    public String format() {
        
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < concerts.size(); i++) {

            Concert concert = concerts.get(i);

            stringBuilder.append(view.format(
                    concert.getProgramName(),
                    concert.getDateTime(),
                    concert.getEnsembles(),
                    concert.getPlace().getName(),
                    concert.getPlace().getAddress(),
                    concert.getProgramText(),
                    concert.getLink(),
                    i));
        }
        
        return stringBuilder.toString();
    }
}
