package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import ru.hip_spb.model.Instrument;
import ru.hip_spb.model.Performer;

/**
 *
 */
public class PerformerDAO extends DAO<Performer> {

    private static final String TABLE = "performers";
    private static final String ID = "performer_id";
    private static final String PERFORMER_NAME = "performer_name";

    public PerformerDAO() throws DAOException {
        super();
    }

    /**
     * Insert a Performer object. This method doesn't insert the Performer.instruments array.
     */
    @Override
    public int insert(Performer data) throws DAOException {

        final String INSERT_PERFORMER_QUERY = "INSERT INTO " + TABLE + " (" + PERFORMER_NAME + ") VALUES( ? )";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_PERFORMER_QUERY,
                        Statement.RETURN_GENERATED_KEYS);) {
            statement.setNString(1, data.getName());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                logger.log(Level.SEVERE, "PerformerDAO.insert() error: no performer inserted");
                throw new DAOException("No performer inserted");
            }

            ResultSet generategKeys = statement.getGeneratedKeys();

            if (generategKeys.next()) {
                generatedID = generategKeys.getInt(1);
            } else {
                logger.log(Level.SEVERE, "PerformerDAO.insert() error: no ID obtaned");
                throw new DAOException("No ID obtained");
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "PerformerDAO.insert(): error writing DB {0}", exception.getMessage());
            throw new DAOException("error writing DB: " + exception.getMessage());
        }

        return generatedID;
    }

    @Override
    public ArrayList<Performer> getAll() throws DAOException {

        final String QUERY = "SELECT * FROM " + TABLE;

        ArrayList<Performer> result = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new Performer(resultSet.getInt(ID), resultSet.getString(PERFORMER_NAME)));
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, null, exception);
            throw new DAOException("PerformerDAO.getAll(): error reading DB");
        }

        return result;
    }

    @Override
    public Performer getById(int id) throws DAOException {

        final String QUERY = "SELECT * FROM " + TABLE + " WHERE " + ID + "=" + String.valueOf(id);

        Performer performer = null;

        try (
                Connection connection = connectionFactory.getConnection();
                Statement statement = connection.createStatement();) {

            logger.log(Level.INFO, "PerformerDAO.getById() executing query: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery(QUERY);
            if (resultSet.next()) {
                String performerName = resultSet.getString(PERFORMER_NAME);
                performer = new Performer(id, performerName);
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, null, exception);
            throw new DAOException("PerformerDAO.getById(): error reading DB");
        }

        return performer;
    }

    /**
     * Search database of Performers for <code>subString</code> in performer's name
     * 
     * @param subString what to search in database
     * @return returns a list of performers' names, where subString is found
     */
    public ArrayList<String> getNamesList(String subString) throws DAOException {

        final String QUERY = "SELECT " + PERFORMER_NAME + " FROM " + TABLE +
                " WHERE LOCATE(" + subString + ", " + PERFORMER_NAME + ")>0";

        ArrayList<String> result = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(PERFORMER_NAME));
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, null, exception);
            throw new DAOException("PerformerDAO.getNamesList(): error reading DB");
        }

        return result;
    }

    /**
     * Searchs database for a performer called <code>name</code> Creates one if
     * not found.
     *
     * @param name - name of a performer to be created
     * @return Returns a Performer object
     * @throws ru.hip_spb.dao.DAOException
     */

    public Performer getByNameOrCreate(String name) throws DAOException {

        final String QUERY = "SELECT * FROM " + TABLE + " WHERE "
                + PERFORMER_NAME + "=" + "'" + name + "'";

        int id;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);) {

            logger.log(Level.INFO, "PerformerDAO.getByNameOrCreate(): executing statement: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery();

            // Try to find performer in DB
            if (resultSet.next()) {
                id = resultSet.getInt(ID);
            }
            // Create one if not found
            else {
                id = insert(new Performer(0, name));
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "PerformerDAO.getByNameOrCreate(): error writing DB", exception);
            throw new DAOException("PerformerDAO.getByNameOrCreate(): error writing DB");
        }

        logger.log(Level.INFO, "PerformerDAO.getByNameOrCreate(): OK; id = {0}", id);

        return new Performer(id, name);
    }

    /**
     * Adds a performer to a concert. The performer is identified by name,
     * performer's id is obtained while writing data to DB
     * 
     * @param performer  - a Performer to be added to a concert
     * @param concert_id - a concert to add the performer
     * @throws DAOException
     */
    void addToConcert(ArrayList<Performer> performers, int ensembleId, int concertId) throws DAOException {

        final String QUERY = "INSERT INTO perf_instr_ensembles_concerts"
                + "( performer_id, instrument_id, concert_id, ensemble_id ) VALUES(? , ?, ?, ?)";

        InstrumentDAO instrumentDAO = new InstrumentDAO();

        for (Performer performer : performers) {

            // obtain an id for the performer
            performer.setId(getByNameOrCreate(performer.getName()).getId());

            // for each instrument there will be it's own line in the table
            for (Instrument instrument : performer.getInstruments()) {

                try (
                        Connection connection = connectionFactory.getConnection();
                        PreparedStatement statement = connection.prepareStatement(QUERY);) {

                    // Set ID for instrument
                    instrument.setId(instrumentDAO.getByNameOrCreate(instrument.getName()).getId());

                    statement.setInt(1, performer.getId());
                    statement.setInt(2, instrument.getId());
                    statement.setInt(3, concertId);
                    statement.setInt(4, ensembleId);

                    logger.log(Level.FINE, "PerformerDAO.addToConcert(): execute query " +
                            QUERY + " performer_id = {0}instrument_id = {1}concert_id = {3}ensemble_id = {4}",
                            new Object[] { performer.getId(), instrument.getId(), concertId, ensembleId });

                    statement.executeUpdate();

                } catch (SQLException exception) {
                    logger.log(Level.SEVERE, null, exception);
                    throw new DAOException("PerformerDAO.addToConcert(): error writing DB");
                }
            }
        }
    }
}
