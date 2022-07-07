package ru.hip_spb.controller;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.hip_spb.dao.DAOException;
import ru.hip_spb.dao.PerformerDAO;
import ru.hip_spb.model.Performer;

public class PerformerController {
    
    private static final Logger logger = Logger.getLogger("ConcertController.class.getName()");
    private ArrayList<Performer> performers;
    private PerformerDAO performerDAO;
    
    public PerformerController() {

        performers = new ArrayList<>();
    
        try {
            performerDAO = new PerformerDAO();
            performers = performerDAO.getAll();
        } catch (DAOException exception) {
            logger.log(Level.SEVERE, null, exception);
        }
    }

    /**
     * @return Returns a string containing datalist <option> tags with all the performers' names
     */
    public String printDatalist() {

        StringBuilder result = new StringBuilder();
        
        for(Performer performer : performers) {
            result.append("<option value='" + performer.getName() + "'>");
        }

        return result.toString();
    }

}