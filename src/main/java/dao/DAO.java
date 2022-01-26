package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Phone;
import models.User;

public abstract class DAO {

	protected Connection myConn;
	

	public abstract User save(User user) throws SQLException;

	public abstract User update(User user) throws SQLException;

	public abstract User find(Integer userId) throws SQLException;
	
	public abstract User login(String email, String pass) throws SQLException;
	
	public abstract User findByEmail(String email) throws SQLException;

	public abstract ArrayList<User> find() throws SQLException;

	public abstract ArrayList<User> find(String initial) throws SQLException;

	public abstract void delete(User user) throws SQLException;

	public abstract void addPhones(User user) throws SQLException;

	public abstract void addPhone(Integer userId, Phone phone) throws SQLException;

	public abstract void deletPhone(Integer phoneId) throws SQLException;

}
