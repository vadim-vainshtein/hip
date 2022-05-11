package ru.hip_spb.dao;

import java.sql.Connection;
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

    private ConnectionFactory connectionFactory;

    public ConcertDAO() throws NamingException {

        // TODO: own exceptions and logging
        connectionFactory = ConnectionFactory.getInstance();
    }

    public static String TestConnection() throws SQLException {
        
        return "";
    }

    @Override
    public ArrayList<Concert> getAll() {

        final String GET_CONCERTS_QUERY = "SELECT * FROM concerts";
        ArrayList<Concert> concerts = new ArrayList<>();

// TODO: own exceptions and logging
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
            Logger.getLogger(ConcertDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return concerts;
    }
}
