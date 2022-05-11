package ru.hip_spb.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import ru.hip_spb.dao.ConcertDAO;
import ru.hip_spb.model.*;
import ru.hip_spb.view.ConcertView;

/**
 *
 */
public class ConcertController {
    
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
        } catch (NamingException ex) {
            Logger.getLogger(ConcertController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void print() throws IOException {
        
        System.out.print("print()");
        
        for(Concert concert : concerts) {
            view.print(concert.getProgramName());
        }
    }
    
    public String format() {
        
        StringBuilder stringBuilder = new StringBuilder();
        
        for(Concert concert : concerts) {
            stringBuilder.append(view.format(concert.getProgramName()));
        }
        
        return stringBuilder.toString();
    }
}
