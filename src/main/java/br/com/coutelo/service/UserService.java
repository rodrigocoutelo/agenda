package br.com.coutelo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.coutelo.dao.UserDao;
import br.com.coutelo.dao.UserDaoImpl;
import br.com.coutelo.model.Phone;
import br.com.coutelo.model.Phone.PhoneType;
import br.com.coutelo.model.User;

public class UserService {
	
	private static UserService INSTANCE;
	private static UserDao userDao = null;
	 
	
	public static UserService getInstance() {
		
		if (INSTANCE == null) {
			try {
				INSTANCE = new UserService();
				new UserDaoImpl();
				userDao = UserDaoImpl.getInstance();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} 
			
		}
		return INSTANCE;
	}
	
	public void createUser(String name, String email, String password, String password_check, String role) throws Exception {
	
		User user = null;
		
		this.validateCreate(name, email, password, password_check, role);
		
		user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		
		if (role.equals("") || role.equalsIgnoreCase(null)) {
			user.setRole(User.Role.USER);	
		} else {
			user.setRole(User.Role.valueOf(role));
		}
		
		userDao.saveUser(user);
	}
	
	
	public void updateUser(String name, String email, String password, String password_check, String role) throws Exception {
		
		User user = null;
		
		this.validateUpdate (name, email, password, password_check, role);
		
		user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		if (role.equals("") || role.equals(null)) {
			user.setRole(User.Role.USER);	
		} else {
			user.setRole(User.Role.valueOf(role));
		}
		userDao.updateUser(user);
	}
	
	
	public void delete(Long userId) throws Exception{
		
		User user = userDao.findUserById(userId).get();
		
		if (user != null) {
			userDao.deleteUser(user);
		} else {
			throw new Exception("Usuário não cadastrado!");
		}
	}
	
	
	public Long login(String email, String password) throws Exception{
		
		try {
			User user = userDao.findUserByCredentials(email, password).get();
			return user.getId();
		} catch (Exception e) {
			throw new Exception("Usuário não cadastrado!");
		}
		
	}	
	 
	public List<User> findAll(){
		
		return userDao.findAll();
	}
	
	public User findById(Long userId) throws Exception{
		
		try {
			User user = userDao.findUserById(userId).get();
			return user;
		} catch (Exception e) {
			throw new Exception("Usuário não cadastrado!");
		}
	}
	
public List<User> findByInicials(Character initial) throws Exception{
		
		try {
			List<User> users = userDao.findUserByInitials(initial);
			if (users.size()==0) {
				throw new Exception("Nenhum foi encontrato nenhum contato com a letra "+ Character.toUpperCase(initial));	
			}
			return users;
		} catch (Exception e) {
			throw new Exception("Nenhum foi encontrato nenhum contato com a letra "+ Character.toUpperCase(initial));
		}
	}
	
	
	public boolean existsUser(User user){
		return false;
	}
	
	public List<Phone> findPhonesFromUser(Long userId) throws Exception{
	
		try {
			User user = userDao.findUserById(userId).get();
			return user.getPhones();
		} catch (Exception e) {
			throw new Exception("Usuário não cadastrado!");
		}
	}
	
	public void addUserPhone(Long userId, int ddd, int number, String phoneType) throws Exception{
		
		this.validatePhone(ddd,number,phoneType);
		try {
			User user = userDao.findUserById(userId).get();
			Phone phone = new Phone(ddd,number,Phone.PhoneType.valueOf(phoneType));
			user.addPhone(phone);
			userDao.updateUser(user);
		
		} catch (Exception e) {
			throw new Exception("Falha ao adicionar telefone");
		}
		
	}
	
	
	public void removeUserPhone(Long userId, int ddd, int number, String phoneType) throws Exception{
		
		this.validatePhone(ddd,number,phoneType);
		
		try {
			User user = userDao.findUserById(userId).get();
			boolean findPhone = false;
			Phone deletePhone = null;
			
			Phone phone = new Phone(ddd,number,Phone.PhoneType.valueOf(phoneType));
			
			for (Phone savedPhone: user.getPhones()) {
				if (savedPhone.equals(phone)) {
					deletePhone = savedPhone;
					break;
				}
			}
			user.removePhone(deletePhone);
			userDao.updateUser(user);
		
		} catch (Exception e) {
			throw new Exception("Falha ao remover telefone");
		}
		
		
	}

	
	private void validateUpdate(String name, String email, String password, String password_check, String role) throws Exception {
	
		List<String> failFields = new ArrayList<String>();
		if (name.equals("") || name.isEmpty() || name == null) {
			failFields.add("Nome");
		}
		
		if (email.equals("") || email.isEmpty() || email == null) {
			failFields.add("E-mail");
		}
				
		String msgErro = "O(s) campo(s): ";
		for (String fieldName: failFields) {
			msgErro = msgErro + fieldName + " ";
		}
		
		if (failFields.size()>0) {
			throw new Exception(msgErro + "são obrigatórios");
		}
		
		if (!password.equals("") || !password.isEmpty() || password != null || !password_check.equals("") || !password_check.isEmpty() || password_check != null) {
			this.validatePassword(password, password_check);
		}
		
		if (!role.equals("")) {
			try {
				User.Role.valueOf(role);
			} catch (Exception e) {
				throw new Exception("Tipo de regra inválids");
			}
		}
		
	}
	

	private void validateCreate(String name, String email, String password, String password_check, String role) throws Exception {
	
		List<String> failFields = new ArrayList<String>();
		if (name.equals("") || name.isEmpty() || name == null) {
			failFields.add("Nome");
		}
		
		if (email.equals("") || email.isEmpty() || email == null) {
			failFields.add("E-mail");
		}
		
		if (password.equals("") || password.isEmpty() || password == null) {
			failFields.add("Password");
		}

		if (password_check.equals("") || password_check.isEmpty() || password_check == null) {
			failFields.add("Confirme Password");
		}

				
		String msgErro = "O(s) campo(s): ";
		for (String fieldName: failFields) {
			msgErro = msgErro + fieldName + " ";
		}
		
		if (failFields.size()>0) {
			throw new Exception("Campos obrigatórios não preenchidos");
		}
		
		if (!role.equals("")) {
			try {
				User.Role.valueOf(role);
			} catch (Exception e) {
				throw new Exception("Tipo de regra inválida");
			}
		}
		
		if(!this.validatePassword(password, password_check)) {
			throw new Exception("Senhas não conferem");
		};

	}

	public boolean validatePassword(String password, String password_check) {
		if (password.equals(password_check)) {
			return true;
		}
		return false;
	}
	

	private void validatePhone(int ddd, int number, String phoneType) throws Exception {
		
		final String DDD_BRASIL = "11,12,13,14,15,16,17,18,19,21,22,24,27,28,31,32,33,34,35,37,38,41,42,43,44,45,46,47,48,49,51,53,54,"
				+ "55,61,62,63,64,65,66,67,68,69,71,73,74,75,77,79,81,82,83,84,85,86,87,88,89,91,92,93,94,95,96,97,98,99";
		
		if (String.valueOf(ddd).length()!=2 || !DDD_BRASIL.contains(String.valueOf(ddd)) || ddd == 0) {
			throw new Exception("DDD inválido!");
		}
		
		if (phoneType.equals("CELULAR") && number <= 99999999) {
			throw new Exception("Formato inválido para Celular");
		}
		
		if (number <= 9999999) {
			throw new Exception("Formato inválido para telefone");
		}
		
		if (phoneType.equals("") || phoneType.isEmpty() || phoneType == null) {
			throw new Exception("Tipo de telefone é um campo obrigatório");
		}
		
		try {
			Phone.PhoneType.valueOf(phoneType);
		} catch (Exception e) {
			throw new Exception("Tipo de telefone inválido");
		}
			
			
	}


}
