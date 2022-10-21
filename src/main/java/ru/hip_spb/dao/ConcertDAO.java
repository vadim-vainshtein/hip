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
import ru.hip_spb.model.Ensemble;
import ru.hip_spb.model.Performer;
import ru.hip_spb.model.Place;
import ru.hip_spb.model.Instrument;

public class ConcertDAO extends DAO<Concert> {

    private static final String PERFORMERS_CONCERTS_TABLE = "perf_instr_ensembles_concerts";

    public ConcertDAO() throws DAOException {
        super();
    }

    @Override
    public ArrayList<Concert> getAll() throws DAOException {

        final String GET_CONCERTS_QUERY = "SELECT * FROM concerts LEFT JOIN places ON concerts.place_id = places.place_id";
        final String GET_ENSEMBLES_QUERY = "SELECT * FROM "
                + PERFORMERS_CONCERTS_TABLE + " WHERE concert_id = ? ";

        ArrayList<Concert> concerts = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(GET_CONCERTS_QUERY);

            EnsembleDAO ensembleDAO = new EnsembleDAO();
            PerformerDAO performerDAO = new PerformerDAO();
            InstrumentDAO instrumentDAO = new InstrumentDAO();

            while (resultSet.next()) {
                // get concert details 
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

                ArrayList<Ensemble> ensembles = new ArrayList<>();
                PreparedStatement ensemblesStatement = connection.prepareStatement(GET_ENSEMBLES_QUERY);
                ensemblesStatement.setInt(1, concertId);
                logger.log(Level.INFO, "ConcertDAO.getAll(): Executing query: {0}",
                        ensemblesStatement.toString());
                ResultSet ensemblesSet = ensemblesStatement.executeQuery();

                //get ensembles with performers and their instruments
                int currentEnsembleId = 0;
                int currentPerformerId = 0;
                Ensemble currentEnsemble = null;
                Performer currentPerformer = null;

                while (ensemblesSet.next()) {
                    
                    int ensembleId = ensemblesSet.getInt("ensemble_id");
                    int performerId = ensemblesSet.getInt("performer_id");
                    int instrumentId = ensemblesSet.getInt("instrument_id");

                    if(currentEnsembleId != ensembleId) {
                        currentEnsembleId = ensembleId;
                        currentEnsemble = ensembleDAO.getById(ensembleId);
                        ensembles.add(currentEnsemble);
                    }
                    
                    if(currentPerformerId != performerId) {
                        currentEnsembleId = performerId;
                        currentPerformer = performerDAO.getById(performerId);
                        currentEnsemble.getPerformers().add(currentPerformer);
                    }

                    currentPerformer.getInstruments().add(instrumentDAO.getById(instrumentId));
                }

                Concert concert = new Concert(
                        concertId,
                        dateTime,
                        place,
                        programName,
                        programText,
                        link,
                        ensembles);

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

        final String INSERT_CONCERT_QUERY = "INSERT INTO concerts " + 
                "( program_name, place_id, date_time, link, program_text )" +
                "VALUES (?, ?, ?, ?, ?)";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_CONCERT_QUERY,
                        Statement.RETURN_GENERATED_KEYS);) {

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

        EnsembleDAO ensembleDAO = new EnsembleDAO();

        // get IDs for every ensemble from db (or create them)
        for (Ensemble ensemble : data.getEnsembles()) {
            ensembleDAO.addToConcert(ensemble, generatedID);
        }

        return generatedID;
    }

    // TODO: implement :)
    @Override
    public Concert getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
