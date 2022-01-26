package controller;

import java.util.List;

import models.Phone;
import models.User;

public interface UserController {

	public User create(String firstname, String lastname, String email, String password, String password_check, String role) throws Exception ;
	public User update(Integer id, String firstname, String lastname, String email, String password, String password_check,String role) throws Exception ;
	public void delete(Integer id) throws Exception ;
	public User login(String email, String password) throws Exception;
	public List<User> findAllUsers() throws Exception ;
	public List<User> findAllUsers(String initial) throws Exception ;
	public User findUserById(Integer userId) throws Exception ;
	public List<Phone> findPhonesFromUser(Integer userId) throws Exception ; 
	public void addUserPhone(Integer userId, int ddd, int number, String type) throws Exception ;
	public void removeUserPhone(Integer userId) throws Exception;
	
}
