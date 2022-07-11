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
import ru.hip_spb.model.Place;


public class ConcertDAO extends DAO<Concert> {

    private static final String CONCERTS_TABLE = "concerts";
    private static final String CONCERT_ID = "concert_id";
    private static final String PERFORMERS_CONCERTS_TABLE = "perf_instr_ensembles_concerts";
    private static final String PERFORMER_ID = "performer_id";

    public ConcertDAO() throws DAOException {
        super();
    }

    @Override
    public ArrayList<Concert> getAll() throws DAOException {

        final String GET_CONCERTS_QUERY = "SELECT * FROM concerts LEFT JOIN places ON concerts.place_id = places.place_id";
        final String GET_PERFORMERS_ID_QUERY = "SELECT " + PERFORMER_ID +
                " FROM " + PERFORMERS_CONCERTS_TABLE + " WHERE " + 
                CONCERT_ID + " = ? ";
        
        ArrayList<Concert> concerts = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ) 
        {
            ResultSet resultSet = statement.executeQuery(GET_CONCERTS_QUERY);
            
            PerformerDAO performerDAO = new PerformerDAO();

            while (resultSet.next()) {
                int concertId = resultSet.getInt("concert_id");
                Timestamp timestamp = resultSet.getTimestamp("date_time");
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                String programName = resultSet.getString("program_name");
                String programText = resultSet.getString("program_text");
                String link = resultSet.getString("link");
                int placeId = resultSet.getInt("place_id");
                String placeName = resultSet.getString("place_name");
                String placeAddress = resultSet.getString("place_address");

                Place place = new Place(placeId, placeName, placeAddress);
                
                // Get all the performers (IDs) of the concert
                // TODO: just use a complex query?
                PreparedStatement performersStatement = connection.prepareStatement(GET_PERFORMERS_ID_QUERY);
                performersStatement.setInt(1, concertId);
                logger.log(Level.INFO, "ConcertDAO.getAll(): Executing query: {0}",
                        performersStatement.toString());
                ResultSet performersSet = performersStatement.executeQuery();
                
                // Get Performer objects by the obtained IDs, add them into a List
                ArrayList<Performer> performers = new ArrayList<>();
                                
                while(performersSet.next()) {
                    int performerId = performersSet.getInt(PERFORMER_ID);
                    performers.add(performerDAO.getById(performerId));
                }
              

                Concert concert = new Concert(
                        concertId,
                        dateTime,
                        place,
                        programName,
                        programText,
                        link,
                        performers.toArray(new Performer[0])
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

        final String INSERT_CONCERT_QUERY = "INSERT INTO " + CONCERTS_TABLE
                + "( program_name, place_id, date_time, link, program_text )"
                + "VALUES (?, ?, ?, ?, ?)";
        
        int generatedID;
        
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement
                = connection.prepareStatement(INSERT_CONCERT_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            
            statement.setString(1, data.getProgramName());
            statement.setInt(2, data.getPlace().getId());
            statement.setString(3, data.getDateTime().toString());
            statement.setString(4, data.getLink());
            statement.setString(5, data.getProgramText());
            
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
