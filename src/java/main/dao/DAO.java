package ru.hip_spb.dao;

public abstract class DAO<T> {
	
	public abstract void insert(T data);
	public abstract T getById(int data);
	public abstract void update(T data);
	public abstract void delete(T data);
}
