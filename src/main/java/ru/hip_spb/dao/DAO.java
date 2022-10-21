package ru.hip_spb.dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

public abstract class DAO<T> {
	    
protected ConnectionFactory connectionFactory;
protected static final Logger logger = Logger.getLogger(DAO.class.getName());

protected DAO() throws DAOException {
                
        try {
            connectionFactory = ConnectionFactory.getInstance();
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new DAOException("DAO: Couldn't get a connection factory instance.");
        }
        logger.info("Got a connection factory instance");
}


	public abstract T getById(int id) throws DAOException;
/*	public abstract void update(T data);
	public abstract void delete(T data);*/
        public abstract int insert(T data) throws DAOException;
        public abstract ArrayList<T> getAll() throws DAOException;
}
