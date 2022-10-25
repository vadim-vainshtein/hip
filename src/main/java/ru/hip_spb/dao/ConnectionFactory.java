package ru.hip_spb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * Provides a DB connection pool using Tomcat's Data Source
 * Data Source is described in server.xml Tomcat's config and connected via web.xml
 */
public class ConnectionFactory {

    private static final String DATASOURCE_NAME = "hipDB";
    private DataSource dataSource = null;
    private static ConnectionFactory instance = null;

    private ConnectionFactory() throws NamingException {
        Context initialContext = new InitialContext();
        Context envContext = (Context) initialContext.lookup("java:/comp/env");
        dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
    }

    public static ConnectionFactory getInstance() throws NamingException {
        
        if(instance == null) {
            instance = new ConnectionFactory();
        }
        
        return instance;
    }

    public Connection getConnection() throws SQLException {
        
        return dataSource.getConnection();
    }
}
