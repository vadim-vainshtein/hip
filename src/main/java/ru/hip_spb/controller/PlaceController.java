package ru.hip_spb.controller;

import ru.hip_spb.model.Place;
import ru.hip_spb.dao.DAOException;
import ru.hip_spb.dao.PlaceDAO;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaceController {
    
    private static final Logger logger = Logger.getLogger("ConcertController.class.getName()");
    private ArrayList<Place> Places;
    private PlaceDAO PlaceDAO;
    
    public PlaceController() {

        Places = new ArrayList<>();
    
        try {
            PlaceDAO = new PlaceDAO();
            Places = PlaceDAO.getAll();
        } catch (DAOException exception) {
            logger.log(Level.SEVERE, null, exception);
        }
    }

    /**
     * @return Returns a string containing datalist <option> tags with all the Places' names
     */
    public String printDatalist() {

        StringBuilder result = new StringBuilder();
        
        for(Place Place : Places) {
            result.append("<option value='" + Place.getName() + "'>");
        }

        return result.toString();
    }

    /**
     * @return Returns an address of a place by name, if one is found in DB. If not - returns an empty string
     */
    public String getAddressByPlaceName(String name) {

        Place place = new Place();
               
        try {
            PlaceDAO placeDAO = new PlaceDAO();
            place = placeDAO.getByName(name);
        } catch (DAOException e) {
            logger.log(Level.WARNING, "getAddressByPlaceName(): error executing PlaceDAO.getByName", e);
        }
        
        return place.getAddress();
    }
}
