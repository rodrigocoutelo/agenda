package controller;

import java.sql.SQLException;
import java.util.List;


import models.Phone;
import models.User;
import services.UserService;

public class UserControllerImpl implements UserController{
	
	UserService us = new UserService();

	@Override
	public User create (String firstname, String lastname, String email, String password, String password_check, String role) throws Exception {
		return us.createUser(firstname, lastname, email, password, password_check, role) ;
	}

	@Override
	public User update(Integer id, String firstname, String lastname, String email, String password, String password_check, String role) throws Exception {
		return us.updateUser(id, firstname, lastname, email, password, password_check, role) ;
	}


	@Override
	public void delete(Integer id) throws Exception {
		us.delete(id);
	}

	@Override
	public User login(String email, String password) throws Exception {
		User user = us.login(email, password);
		return user;
	}

	@Override
	public List<User> findAllUsers() throws SQLException {
		return us.find();
	}

	@Override
	public User findUserById(Integer id) throws Exception {
		return us.find(id);
	}

	@Override
	public void addUserPhone(Integer userId, int ddd, int number, String type) throws Exception {
		us.addUserPhone(userId, ddd, number, type);
	}

	@Override
	public void removeUserPhone(Integer phoneId) throws Exception {
		us.removeUserPhone(phoneId);
		
	}

	@Override
	public List<User> findAllUsers(String initial) throws Exception {
		return us.find(initial);
	}

	@Override
	public List<Phone> findPhonesFromUser(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
