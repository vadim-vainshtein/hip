package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import ru.hip_spb.model.EnsembleName;

public class EnsembleNameDAO extends DAO<EnsembleName> {

    protected EnsembleNameDAO() throws DAOException {
        super();

    }

    @Override
    public EnsembleName getById(int id) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int insert(EnsembleName data) throws DAOException {
        
        final String INSERT_ENSEMBLE_QUERY
                = "INSERT INTO ensembles (ensemble_name) VALUES( ? )";

        int generatedID;

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement
                    = connection.prepareStatement(INSERT_ENSEMBLE_QUERY, Statement.RETURN_GENERATED_KEYS);
            )
        {
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

    @Override
    public ArrayList<EnsembleName> getAll() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
