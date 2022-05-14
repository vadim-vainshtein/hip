package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import ru.hip_spb.model.Concert;

/**
 *
 *
 */
public class ConcertDAO extends DAO<Concert> {
    
    private static final String TABLE = "concerts";
            
    private ConnectionFactory connectionFactory;
    private static final Logger logger = Logger.getLogger(ConcertDAO.class.getName());

    public ConcertDAO() throws DAOException {

        try {
            connectionFactory = ConnectionFactory.getInstance();
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new DAOException("ConcertDAO: Couldn't get a connection factory instance.");
        }
        logger.info("Got a connection factory instance");
    }

    @Override
    public ArrayList<Concert> getAll() throws DAOException {

        final String GET_CONCERTS_QUERY = "SELECT * FROM " + TABLE;
        ArrayList<Concert> concerts = new ArrayList<>();

        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_CONCERTS_QUERY);

            while (resultSet.next()) {
                int concertId = resultSet.getInt("concert_id");
                String programName = resultSet.getString("program_name");

                Concert concert = new Concert(
                        concertId,
                        null,
                        null,
                        programName,
                        null,
                        null
                );
                
                concerts.add(concert);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new DAOException("ConcertDAO.getAll(): error reading DB");
        }
        
        return concerts;
    }

    @Override
    public void insert(Concert data) throws DAOException {
        
        final String QUERY = "INSERT INTO " + TABLE + "( program_name )" + "VALUES (?)";
        
        try {
            
            logger.info("ConcertDAO.insert(): get a connection..." );
            Connection connection = connectionFactory.getConnection();
            logger.info("ConcertDAO.insert(): prepare statement..." );
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.setString(1, data.getProgramName());
            logger.info("ConcertDAO.insert(): execute a query..." );
            int rowsAffected = statement.executeUpdate();
            
            logger.log(Level.INFO, "ConcertDAO.insert(): wrote {0} line(s)", rowsAffected);
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "ConcertDAO.insert(): error writing DB {0}", ex.getMessage());
            throw new DAOException("error writing DB: " + ex.getMessage());
        }
        
        
    }
}
