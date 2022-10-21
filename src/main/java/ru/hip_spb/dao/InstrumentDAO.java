package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import ru.hip_spb.model.Instrument;

public class InstrumentDAO extends DAO<Instrument> {

    protected InstrumentDAO() throws DAOException {
        super();

    }

    @Override
    public Instrument getById(int id) throws DAOException {
        final String QUERY = "select * from instruments where instrument_id = " + id;

        Instrument instrument = null;

        try (Connection connection = connectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY); ) {
            
            logger.log(Level.INFO, "InstrumentDAO.getById() executing query: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()) {
                String name = resultSet.getString("instrument_name");
                instrument = new Instrument(id, name);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            throw new DAOException("InstrumentDAO.getById(): error reading DB");
        }
        return instrument;
    }

    @Override
    public int insert(Instrument data) throws DAOException {
        
        final String INSERT_INSTRUMENT_QUERY
                = "INSERT INTO instruments (instrument_name) VALUES( ? )";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement
                    = connection.prepareStatement(INSERT_INSTRUMENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            )
        {
            statement.setNString(1, data.getName());
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                logger.log(Level.SEVERE, "InstrumentDAO.insert() error: no instrument inserted");
                throw new DAOException("No instrument inserted");
            }

            ResultSet generategKeys = statement.getGeneratedKeys();

            if (generategKeys.next()) {
                generatedID = generategKeys.getInt(1);
            } else {
                logger.log(Level.SEVERE, "InstrumentDAO.insert() error: no ID obtaned");
                throw new DAOException("No ID obtained");
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "InstrumentDAO.insert(): error writing DB {0}", exception.getMessage());
            throw new DAOException("error writing DB: " + exception.getMessage());
        }

        return generatedID;
    }

        /**
     * @param name
     * @return Returns a Instrument object. If there is no match in DB, id=0
     * @throws DAOException
     */
    public Instrument getByName(String name) throws DAOException {

        final String QUERY = "SELECT * FROM instruments WHERE instrument_name = '" + name + "'";

        int id = 0;
                        
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);
            )
        {
            logger.log(Level.INFO, "InstrumentDAO.getByName(): executing statement: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery();

            // Try to find Instrument in DB
            if (resultSet.next()) {
                id = resultSet.getInt("instrument_id");
            }
            
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "InstrumentDAO.getByNameOrCreate(): error writing DB", exception);
            throw new DAOException("InstrumentDAO.getByNameOrCreate(): error writing DB");
        }

        //TODO: is id=0 the best way to show, that object was not found?
        return new Instrument(id, name);
    }

    /**
     * Searchs database for a Instrument called <code>name</code> Creates one if
     * not found.
     *
     * @param name - name of a Instrument to be created
     * @return Returns Instrument object with obtained ID
     * @throws ru.hip_spb.dao.DAOException
     */
    public Instrument getByNameOrCreate(String name) throws DAOException {

        
        // Try to find Instrument in DB
        Instrument instrument = getByName(name);

        // Create one if not found
        if(instrument.getId() == 0) {
            int id = insert(instrument);
            instrument.setId(id);
        }

        logger.log(Level.INFO, "InstrumentDAO.getByNameOrCreate(): OK; id = {0}", instrument.getId());
        
        return instrument;
    }

    @Override
    public ArrayList<Instrument> getAll() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
    

    
}
