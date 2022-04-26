package br.com.coutelo.dao;

import java.util.List;
import java.util.Optional;

import br.com.coutelo.model.User;


public interface UserDao {

	public Optional<User> saveUser(User user);
	public Optional<User> updateUser(User user);
	public void deleteUser(User user);
	public Optional<User>  findUserById(Long userId);
	public List<User> findAll();
	public Optional<User> findUserByCredentials(String email, String password);
	public List<User> findUserByInitials(Character initial);
	public Optional<User> findUserByEmail(String email);
	
}
