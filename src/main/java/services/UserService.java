package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.UserDAOImpl;
import models.Phone;
import models.User;

public class UserService {

	private static DAO dao = new UserDAOImpl();

	public User createUser(String firstname, String lastname, String email, String password, String password_check,
			String role) throws Exception {

		User user = null;

		this.validateCreate(firstname, lastname, email, password, password_check, role);

		user = new User();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(email);
		user.setPass(password);

		if (role == null || role.equals("")) {
			user.setRole(User.Role.USER);
		} else {
			user.setRole(User.Role.getRole(role));
		}

		return dao.save(user);
	}

	public User updateUser(Integer id, String firstname, String lastname, String email, String password,
			String password_check, String role) throws Exception {

		User user = null;
		this.validateUpdate(firstname, lastname, email, password, password_check, role);
		user = dao.find(id);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(email);
		if (password !=null && !password.equals("")) {
			user.setPass(password);
		} 
		if (role.equals("") || role.equals(null)) {
			user.setRole(User.Role.USER);
		} 
		return dao.update(user);
	}

	public void delete(Integer id) throws Exception {

		User user = dao.find(id);

		if (user != null) {
			dao.delete(user);
		} else {
			throw new Exception("Usuário não cadastrado!");
		}
	}

	public User login(String email, String password) throws Exception {

		try {
			User user = dao.login(email, password);
			return user;
		} catch (Exception e) {
			throw new Exception("Usuário não cadastrado!");
		}

	}

	public List<User> find() throws SQLException {
		return dao.find();
	}

	public User find(Integer userId) throws Exception {
		try {
			User user = dao.find(userId);
			return user;
		} catch (Exception e) {
			throw new Exception("Usuário não cadastrado!");
		}
	}

	public List<User> find(String initial) throws Exception {

		try {
			List<User> users = dao.find(initial);
			if (users.size() == 0) {
				throw new Exception(
						"Nenhum foi encontrato nenhum contato com a letra " + initial.toUpperCase());
			}
			return users;
		} catch (Exception e) {
			throw new Exception("Nenhum foi encontrato nenhum contato com a letra " + initial.toUpperCase());
		}
	}

	public List<Phone> findPhonesFromUser(Integer userId) throws Exception {

		try {
			User user = dao.find(userId);
			return user.getPhones();
		} catch (Exception e) {
			throw new Exception("Usuário não cadastrado!");
		}
	}

	public void addUserPhone(Integer userId, int ddd, int number, String phoneType) throws Exception {

		this.validatePhone(ddd, number, phoneType);
		try {
			User user = dao.find(userId);
			Phone phone = new Phone(ddd, number, Phone.PhoneType.getPhoneType(phoneType));
			user.addPhone(phone);
			dao.update(user);

		} catch (Exception e) {
			throw new Exception("Falha ao adicionar telefone");
		}

	}

	public void removeUserPhone(Integer phoneId) throws Exception {

		try {
			dao.deletPhone(phoneId);
		} catch (Exception e) {
			throw new Exception("Falha ao remover telefone");
		}

	}

	private void validateUpdate(String firstname, String lastname, String email, String password, String password_check,
			String role) throws Exception {

		List<String> failFields = new ArrayList<String>();
		if (firstname.equals("") || firstname.isEmpty() || firstname == null) {
			failFields.add("Nome");
		}

		if (lastname.equals("") || lastname.isEmpty() || lastname == null) {
			failFields.add("Sobrenome");
		}

		if (email.equals("") || email.isEmpty() || email == null) {
			failFields.add("E-mail");
		}

		String msgErro = "O(s) campo(s): ";
		for (String fieldName : failFields) {
			msgErro = msgErro + fieldName + " ";
		}

		if (failFields.size() > 0) {
			throw new Exception(msgErro + "são obrigatórios");
		}

		if (!password.isEmpty() || !password_check.isEmpty()) {
			if (!this.validatePassword(password, password_check)) {
				throw new Exception("Senhas não conferem");
			}
		}

		if (role.equals("") || User.Role.getRole(role) == null) {
			throw new Exception("Tipo de regra inválida");
		}

	}

	private void validateCreate(String firstname, String lastname, String email, String password, String password_check,
			String role) throws Exception {

		List<String> failFields = new ArrayList<String>();

		if (firstname.equals("") || firstname.isEmpty() || firstname == null) {
			failFields.add("Nome");
		}

		if (lastname.equals("") || lastname.isEmpty() || lastname == null) {
			failFields.add("Sobrenome");
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

		String msgErro = "<p>O(s) campo(s) são obrigatórios:<p/><ul>";
		for (String fieldName : failFields) {
			msgErro = msgErro + "<li>"+ fieldName + "</li>";
		}
		msgErro = msgErro + "</ul>";
		
		
		if (failFields.size() > 0) {
			throw new Exception(msgErro);
		}

		if (User.Role.getRole(role) == null) {
				throw new Exception("Tipo de regra inválida");
		}

		User user = dao.findByEmail(email);
	
		if (user != null) {
			throw new Exception("Email já cadastrado");
		}

		if (!this.validatePassword(password, password_check)) {
			throw new Exception("Senhas não conferem");
		}
		;

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

		if (String.valueOf(ddd).length() != 2 || !DDD_BRASIL.contains(String.valueOf(ddd)) || ddd == 0) {
			throw new Exception("DDD inválido!");
		}

		if (phoneType.equals(Phone.PhoneType.CELULAR.getValue()) && number <= 99999999) {
			throw new Exception("Formato inválido para Celular");
		}

		if (number <= 9999999) {
			throw new Exception("Formato inválido para telefone");
		}

		if (phoneType.equals("") || phoneType.isEmpty() || phoneType == null) {
			throw new Exception("Tipo de telefone é um campo obrigatório");
		}

		if (Phone.PhoneType.getPhoneType(phoneType) == null) {
			throw new Exception("Tipo de telefone inválido");
		}

	}

}
