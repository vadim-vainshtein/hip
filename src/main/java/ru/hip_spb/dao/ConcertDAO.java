package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import ru.hip_spb.model.Concert;
import ru.hip_spb.model.Performer;

/**
 *
 *
 */
public class ConcertDAO extends DAO<Concert> {

    private static final String TABLE = "concerts";

    public ConcertDAO() throws DAOException {
        super();
    }

    @Override
    public ArrayList<Concert> getAll() throws DAOException {

        final String GET_CONCERTS_QUERY = "SELECT * FROM " + TABLE;
        ArrayList<Concert> concerts = new ArrayList<>();

        try (
                 Connection connection = connectionFactory.getConnection();  Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(GET_CONCERTS_QUERY);

            while (resultSet.next()) {
                int concertId = resultSet.getInt("concert_id");
                Timestamp timestamp = resultSet.getTimestamp("date_time");
                LocalDateTime dateTime = timestamp.toLocalDateTime();

                String programName = resultSet.getString("program_name");

                Concert concert = new Concert(
                        concertId,
                        dateTime,
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
    public int insert(Concert data) throws DAOException {

        final String INSERT_CONCERT_QUERY = "INSERT INTO " + TABLE
                + "( program_name, date_time )"
                + "VALUES (?, ?)";
        
        int generatedID;
        
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement
                = connection.prepareStatement(INSERT_CONCERT_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            
            statement.setString(1, data.getProgramName());
            statement.setString(2, data.getDateTime().toString());
            int rowsAffected = statement.executeUpdate();

            logger.log(Level.INFO, "ConcertDAO.insert(): wrote {0} line(s)", rowsAffected);
            if (rowsAffected == 0) {
                logger.log(Level.SEVERE, "ConcertDAO.insert() error: no data inserted");
                throw new DAOException("No data inserted");
            }

            ResultSet generategKeys = statement.getGeneratedKeys();
            if (generategKeys.next()) {
                generatedID = generategKeys.getInt(1);
            } else {
                logger.log(Level.SEVERE, "ConcertDAO.insert() error: no ID obtaned");
                throw new DAOException("No ID obtained");
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "ConcertDAO.insert(): error writing DB {0}", ex.getMessage());
            throw new DAOException("error writing DB: " + ex.getMessage());
        }
        
        PerformerDAO performerDAO = new PerformerDAO();
        
        // get IDs for every performer from db (or create them)
        for(Performer performer : data.getPerformers()) {
            performerDAO.addToConcert(performer, generatedID);
        }

        return generatedID;
    }

    // TODO: implement :)
    @Override
    public Concert getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
