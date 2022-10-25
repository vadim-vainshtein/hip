package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import ru.hip_spb.model.Ensemble;

public class EnsembleDAO extends DAO<Ensemble> {

    protected EnsembleDAO() throws DAOException {
        super();

    }

    @Override
    public Ensemble getById(int id) throws DAOException {

        final String QUERY = "SELECT * FROM ensembles WHERE ensemble_id = " + id;
        
        Ensemble ensemble = null;
        try (
            Connection connection = connectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(QUERY);) {

            logger.log(Level.INFO, "EnsembleDAO.getById() executing query: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                String name = resultSet.getString("ensemble_name");
                ensemble = new Ensemble(id, name);
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, null, exception);
            throw new DAOException("EnsembleDAO.getById(): error reading DB");
        }
        return ensemble;
    }

    /*
     * This method doesn't insert Ensemble.performers array
     */
    @Override
    public int insert(Ensemble data) throws DAOException {

        final String INSERT_ENSEMBLE_QUERY = "INSERT INTO ensembles (ensemble_name) VALUES( ? )";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_ENSEMBLE_QUERY,
                        Statement.RETURN_GENERATED_KEYS);) {
            statement.setNString(1, data.getName());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                logger.log(Level.SEVERE, "EnsembleDAO.insert() error: no ensemble inserted");
                throw new DAOException("No ensemble inserted");
            }

            ResultSet generategKeys = statement.getGeneratedKeys();

            if (generategKeys.next()) {
                generatedID = generategKeys.getInt(1);
            } else {
                logger.log(Level.SEVERE, "EnsembleDAO.insert() error: no ID obtaned");
                throw new DAOException("No ID obtained");
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "ensembleDAO.insert(): error writing DB {0}", exception.getMessage());
            throw new DAOException("error writing DB: " + exception.getMessage());
        }

        return generatedID;
    }

    /**
     * @param name
     * @return Returns a Ensemble object. If there is no match in DB, id=0
     * @throws DAOException
     */
    public Ensemble getByName(String name) throws DAOException {

        final String QUERY = "SELECT * FROM ensembles WHERE ensemble_name = '" + name + "'";

        int id = 0;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(QUERY);) {
            logger.log(Level.INFO, "EnsembleDAO.getByName(): executing statement: {0}", QUERY);
            ResultSet resultSet = statement.executeQuery();

            // Try to find Ensemble in DB
            if (resultSet.next()) {
                id = resultSet.getInt("ensemble_id");
            }

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, "EnsembleDAO.getByNameOrCreate(): error writing DB", exception);
            throw new DAOException("EnsembleDAO.getByNameOrCreate(): error writing DB");
        }

        // TODO: is id=0 the best way to show that object was not found?
        return new Ensemble(id, name);
    }

    /**
     * Searchs database for a Ensemble called <code>name</code> Creates one if
     * not found.
     *
     * @param name - name of a Ensemble to be created
     * @return Returns Ensemble object with obtained ID
     * @throws ru.hip_spb.dao.DAOException
     */
    public Ensemble getByNameOrCreate(String name) throws DAOException {

        // Try to find Ensemble in DB
        Ensemble ensemble = getByName(name);

        // Create one if not found
        if (ensemble.getId() == 0) {
            int id = insert(ensemble);
            ensemble.setId(id);
        }

        logger.log(Level.INFO, "EnsembleDAO.getByNameOrCreate(): OK; id = {0}", ensemble.getId());

        return ensemble;
    }

    @Override
    public ArrayList<Ensemble> getAll() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void addToConcert(Ensemble ensemble, int generatedID) throws DAOException {

        // obtain id for ensemble
        ensemble.setId(getByNameOrCreate(ensemble.getName()).getId());

        // insert the performers with their instruments
        PerformerDAO performerDAO = new PerformerDAO();
        performerDAO.addToConcert(ensemble.getPerformers(), ensemble.getId(), generatedID);
    }

}
