package ru.hip_spb.dao;

import java.util.ArrayList;

public abstract class DAO<T> {
	
/*	
	public abstract T getById(int id);
	public abstract void update(T data);
	public abstract void delete(T data);*/
        public abstract void insert(T data) throws DAOException;
        public abstract ArrayList<T> getAll() throws DAOException;
}
