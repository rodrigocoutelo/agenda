package br.com.coutelo.controller;

import java.util.List;


import br.com.coutelo.model.Phone;
import br.com.coutelo.model.User;
import br.com.coutelo.service.UserService;

public class UserControllerImpl implements UserController{
	

	@Override
	public void create (String name, String email, String password, String password_check, String role) throws Exception {
		UserService.getInstance().createUser(name, email, password, password_check, role) ;
	}

	@Override
	public void update(Long id, String name, String email, String password, String password_check, String role) throws Exception {
		UserService.getInstance().updateUser(name, email, password, password_check, role) ;
	}


	@Override
	public void delete(Long userId) throws Exception {
		UserService.getInstance().delete(userId);
	}

	@Override
	public Long login(String email, String password) throws Exception {
		Long userId = UserService.getInstance().login(email, password);
		return userId;
	}

	@Override
	public List<User> findAllUsers() {
		return UserService.getInstance().findAll();
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		return UserService.getInstance().findById(userId);
	}

	@Override
	public List<Phone> findPhonesFromUser(Long userId) throws Exception {
		return UserService.getInstance().findPhonesFromUser(userId);
	}

	
	@Override
	public void addUserPhone(Long userId, int ddd, int number, String type) throws Exception {
		UserService.getInstance().addUserPhone(userId, ddd, number, type);
	}

	@Override
	public void removeUserPhone(Long userId, int ddd, int number, String type) throws Exception {
		UserService.getInstance().removeUserPhone(userId, ddd, number, type);
		
	}

	@Override
	public List<User> findAllUsers(Character initial) throws Exception {
		return UserService.getInstance().findByInicials(initial);
	}

}
