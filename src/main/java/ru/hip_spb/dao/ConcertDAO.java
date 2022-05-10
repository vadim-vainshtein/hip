package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * 
 */
public class ConcertDAO {
    
    public static String TestConnection() throws SQLException {
        
        final String query = "SELECT * FROM concerts";
        
        Connection connection;
        ConnectionFactory connectionFactory;
        
        try {
            connectionFactory = ConnectionFactory.getInstance();
        } catch (NamingException ex) {
            return "ConnectionFactory naming exception";
        }
        
        connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        StringBuilder result = new StringBuilder("");
        
        while(resultSet.next()) {
            result.append(resultSet.getString("program_name"));
        }
        
        return result.toString();
    }
}
