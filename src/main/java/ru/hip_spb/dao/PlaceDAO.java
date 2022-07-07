package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import ru.hip_spb.model.Place;

public class PlaceDAO extends DAO<Place> {

    public PlaceDAO() throws DAOException {
        super();
    }

    @Override
    public ArrayList<Place> getAll() throws DAOException {
        
        final String QUERY = "SELECT * FROM places";

        ArrayList<Place> result = new ArrayList<>();
        
        try(
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);
            )
        {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                result.add(
                    new Place(
                        resultSet.getInt("place_id"),
                        resultSet.getString("place_name"),
                        resultSet.getString("place_address"))
                    );
            }
            
        } catch(SQLException exception) {
            logger.log(Level.SEVERE, null, exception);
            throw new DAOException("PlaceDAO.getAll(): error reading DB");
        }
        
        return result;
    }

    @Override
    public Place getById(int id) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int insert(Place data) throws DAOException {
        final String INSERT_PLACE_QUERY
                = "INSERT INTO places (place_name, place_address) VALUES( ?, ? )";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement
                    = connection.prepareStatement(INSERT_PLACE_QUERY, Statement.RETURN_GENERATED_KEYS); )
        {
            statement.setNString(1, data.getName());
            statement.setNString(2, data.getAddress());
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                logger.log(Level.SEVERE, "PlaceDAO.insert() error: no place inserted");
                throw new DAOException("No place inserted");
            }

            ResultSet generategKeys = statement.getGeneratedKeys();

            if (generategKeys.next()) {
                generatedID = generategKeys.getInt(1);
            } else {
                logger.log(Level.SEVERE, "PlaceDAO.insert() error: no ID obtaned");
                throw new DAOException("No ID obtained");
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "PlaceDAO.insert(): error writing DB {0}", exception.getMessage());
            throw new DAOException("error writing DB: " + exception.getMessage());
        }

        return generatedID;
    }

    /**
     * @param name
     * @return Returns a Place object. If there is no match in DB, id=0, and address=""
     * @throws DAOException
     */
    public Place getByName(String name) throws DAOException {

        final String QUERY = "SELECT * FROM places WHERE place_name = '" + name + "'";

        int id = 0;
        String address = "";
                
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);
            )
        {
            logger.log(Level.INFO, "PlaceDAO.getByName(): executing statement: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery();

            // Try to find Place in DB
            if (resultSet.next()) {
                id = resultSet.getInt("place_id");
                address = resultSet.getString("place_address");
            }
            
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "PlaceDAO.getByNameOrCreate(): error writing DB", exception);
            throw new DAOException("PlaceDAO.getByNameOrCreate(): error writing DB");
        }

        //TODO: is id=0 the best way to show, that object was not found?
        return new Place(id, name, address);
    }
    
    /**
     * Searchs database for a Place called <code>name</code> Creates one if
     * not found.
     *
     * @param name - name of a Place to be created
     * @return Returns Place object with obtained ID
     * @throws ru.hip_spb.dao.DAOException
     */
    public Place getByNameOrCreate(String name, String address) throws DAOException {

        
        // Try to find Place in DB
        Place place = getByName(name);

        // Create one if not found
        if(place.getId() == 0) {
            place.setAddress(address);
            int id = insert(place);
            place.setId(id);
        }

        logger.log(Level.INFO, "PlaceDAO.getByNameOrCreate(): OK; id = {0}", place.getId());
        
        return place;
    }
}
