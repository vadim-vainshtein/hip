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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int insert(Instrument data) throws DAOException {
        
        final String INSERT_Instrument_QUERY
                = "INSERT INTO instruments (instrument_name) VALUES( ? )";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement
                    = connection.prepareStatement(INSERT_Instrument_QUERY, Statement.RETURN_GENERATED_KEYS);
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

    @Override
    public ArrayList getAll() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
    

    
}
