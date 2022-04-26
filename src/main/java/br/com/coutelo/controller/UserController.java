package br.com.coutelo.controller;

import java.util.List;

import br.com.coutelo.model.Phone;
import br.com.coutelo.model.User;

public interface UserController {

	public void create(String name, String email, String password, String password_check, String role) throws Exception ;
	public void update(Long id, String name, String email, String password, String password_check, String role) throws Exception ;
	public void delete(Long userId) throws Exception ;
	public Long login(String email, String password) throws Exception;
	public List<User> findAllUsers() throws Exception ;
	public List<User> findAllUsers(Character initial) throws Exception ;
	public User findUserById(Long userId) throws Exception ;
	public List<Phone> findPhonesFromUser(Long userId) throws Exception ; 
	public void addUserPhone(Long userId, int ddd, int number, String type) throws Exception ;
	public void removeUserPhone(Long userId, int ddd, int number, String type) throws Exception;
	
	
}
