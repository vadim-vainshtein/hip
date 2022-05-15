package ru.hip_spb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.management.Query;
import static ru.hip_spb.dao.DAO.logger;
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

    @Override
    public int insert(Performer data) throws DAOException {

        final String INSERT_PERFORMER_QUERY
                = "INSERT INTO " + TABLE + " (" + PERFORMER_NAME + ") VALUES( ? )";
        
        int generatedID;

        try {
            PreparedStatement statement
                    = connectionFactory.getConnection().prepareStatement(INSERT_PERFORMER_QUERY);
            int rowsAffected = preparedStatement.executeUpdate();
            
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Performer getById(int id) throws DAOException {

        final String QUERY = "SELECT * FROM " + TABLE + "WHERE " + ID + "=" + String.valueOf(id);
        Performer performer;

        try {

            Statement statement = connectionFactory.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);
            String performerName = resultSet.getString("performer_name");

            performer = new Performer(id, performerName);

        } catch (SQLException exception) {
            logger.log(Level.SEVERE, null, exception);
            throw new DAOException("PerformerDAO.getById(): error reading DB");
        }

        return performer;
    }
}
