package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;

import models.Phone;
import models.User;
import utils.MyConnectionMysql;

public class UserDAOImpl extends DAO {

	
	final String SQL_INSERT_USER = "INSERT INTO Users (firstname, lastname, email, pass, role) VALUES (?, ?, ?, ?, ?)";
	final String SQL_UPDATE_USER = "UPDATE Users SET firstname = ?, lastname = ?, email = ?, pass = ?, role = ? WHERE id = ? ";
	//final String SQL_SELECT_USER_BY_ID = "SELECT Users.id, Users.firstname, Users.lastname, Users.email, Users.pass, Users.role, Phones.id, Phones.ddd, Phones.number, Phones.type FROM Users LEFT JOIN Phones ON Users.id = Phones.user WHERE Users.id = ?";
	final String SQL_SELECT_USER_BY_ID = "SELECT id, firstname, lastname, email,  pass, role FROM Users WHERE Users.id = ?";
	final String SQL_SELECT_USER_BY_EMAIL = "SELECT Users.id, Users.firstname, Users.lastname, Users.email,  Users.pass, Users.role, Phones.id, Phones.ddd, Phones.number, Phones.type FROM Users LEFT JOIN Phones ON Users.id = Phones.user WHERE Users.email = ?";
	final String SQL_SELECT_USER_BY_CREDENTIALS = "SELECT Users.id, Users.firstname, Users.lastname, Users.email,  Users.pass, Users.role, Phones.id, Phones.ddd, Phones.number, Phones.type FROM Users LEFT JOIN Phones ON Users.id = Phones.user WHERE Users.email = ? and Users.pass = ?";
	final String SQL_SELECT_USER_BY_INITIAL = "SELECT id, firstname, lastname, email,  pass, role FROM Users WHERE firstname LIKE ? ORDER BY firstname";
	final String SQL_SELECT_USERS = "SELECT id, firstname, lastname, email,  pass, role FROM Users ORDER BY firstname";
	final String SQL_DELETE_USER_BY_ID = "DELETE from Users WHERE id = ?";

	final String SQL_INSERT_PHONE = "INSERT INTO Phones (ddd, number, type, user) VALUES (?, ?, ?, ?)";
	final String SQL_SELECT_PHONES_BY_USER_ID = "SELECT id, ddd, number, type FROM Phones WHERE user = ?";
	final String SQL_DELETE_PHONES_BY_ID = "DELETE FROM Phones WHERE id = ?";
	final String SQL_DELETE_PHONES_BY_USER_ID = "DELETE FROM Phones WHERE user = ?";

	public UserDAOImpl() {
		this.myConn = MyConnectionMysql.getInstance().getConnection();
	}

	
	public User save(User user) throws SQLException {

		try {
			myConn.setAutoCommit(false);
			PreparedStatement statement = myConn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPass());
			statement.setString(5, user.getRole().getValue());

			int updatedRows = statement.executeUpdate();

			if (updatedRows == 0) {
				throw new SQLException("Nenhum registro criado");
			}

			try {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				} else {
					myConn.rollback();
					throw new SQLException("Não foi possível obter o ID");
				}
			} catch (Exception e) {
				throw new SQLException("Não foi possível obter o ID");
			}

			if (!user.getPhones().isEmpty()) {
				addPhones(user);
			}
			myConn.commit();

		} catch (SQLException e) {
			if (myConn != null) {
				try {
					myConn.rollback();
					throw new SQLException("Falha ao criar usuário . " + e.getMessage());
				} catch (SQLExceptionList sqle) {
					throw new SQLException("Falha ao criar usuário. " + sqle.getMessage());
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public User find(Integer id) throws SQLException {
		User user = null;
		Phone phone = null;

		if (id == null || id == 0) {
			throw new SQLException("Falho ao localizar usuário, Id nullo");
		}

		PreparedStatement statement = myConn.prepareStatement(SQL_SELECT_USER_BY_ID);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			user = new User();
			user.setFirstName(result.getString("firstname"));
			user.setLastName(result.getString("lastname"));
			user.setEmail(result.getString("email"));
			user.setPass(result.getString("pass"));
			user.setRole(User.Role.getRole(result.getString("role")));
			user.setId(result.getInt("id"));
			PreparedStatement statementPhones = myConn.prepareStatement(SQL_SELECT_PHONES_BY_USER_ID);
			statementPhones.setInt(1, user.getId());
			ResultSet resultPhones = statementPhones.executeQuery();
			while (resultPhones.next()) {
				phone = new Phone(resultPhones.getInt("id"), resultPhones.getInt("ddd"), resultPhones.getInt("number"),
						Phone.PhoneType.getPhoneType(resultPhones.getString("type")));
				user.addPhone(phone);
			}
			
		}
		return user;
	}
	
	public ArrayList<User> find(String initial) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		Phone phone = null;
		User user = null;
		if (initial == null || initial.equals("")) {
			throw new SQLException("Falha ao localizar usuário, inicial nula");
		}

		PreparedStatement statement = myConn.prepareStatement(SQL_SELECT_USER_BY_INITIAL);
		statement.setString(1, initial + "%");
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			user = new User();
			user.setFirstName(result.getString("firstname"));
			user.setLastName(result.getString("lastname"));
			user.setEmail(result.getString("email"));
			user.setPass(result.getString("pass"));
			user.setRole(User.Role.getRole(result.getString("role")));
			user.setId(result.getInt("id"));

			PreparedStatement statementPhones = myConn.prepareStatement(SQL_SELECT_PHONES_BY_USER_ID);
			statementPhones.setInt(1, user.getId());
			ResultSet resultPhones = statementPhones.executeQuery();
			while (resultPhones.next()) {
				phone = new Phone(resultPhones.getInt("id"), resultPhones.getInt("ddd"), resultPhones.getInt("number"),
						Phone.PhoneType.getPhoneType(resultPhones.getString("type")));
				user.addPhone(phone);
			}
			users.add(user);

		}

		return users;
	}

	public ArrayList<User> find() throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		Phone phone = null;
		User user = null;
		PreparedStatement statement = myConn.prepareStatement(SQL_SELECT_USERS);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			user = new User();
			user.setFirstName(result.getString("firstname"));
			user.setLastName(result.getString("lastname"));
			user.setEmail(result.getString("email"));
			user.setPass(result.getString("pass"));
			user.setRole(User.Role.getRole(result.getString("role")));
			user.setId(result.getInt("id"));

			PreparedStatement statementPhones = myConn.prepareStatement(SQL_SELECT_PHONES_BY_USER_ID);
			statementPhones.setInt(1, user.getId());
			ResultSet resultPhones = statementPhones.executeQuery();
			while (resultPhones.next()) {
				phone = new Phone(resultPhones.getInt("id"), resultPhones.getInt("ddd"), resultPhones.getInt("number"),
						Phone.PhoneType.getPhoneType(resultPhones.getString("type")));
				user.addPhone(phone);
			}
			users.add(user);
		}

		return users;
	}

	public User update(User user) throws SQLException {

		if (user.getId() == null) {
			throw new SQLException("Falha ao atualizar usuário. ID nulo");
		}

		try {
			myConn.setAutoCommit(false);
			PreparedStatement statement = myConn.prepareStatement(SQL_UPDATE_USER);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPass());
			statement.setString(5, user.getRole().getValue());
			statement.setInt(6, user.getId());

			int updatedRows = statement.executeUpdate();
			myConn.commit();

			if (updatedRows == 0) {
				throw new SQLException("Falha ao atualizar usuário. Nenhum registro criado");
			}

			if (!user.getPhones().isEmpty()) {
				addPhones(user);
			}

		} catch (SQLException e) {
			if (myConn != null) {
				try {
					myConn.rollback();
					throw new SQLException("Falha ao conectar com banco de dados. " + e.getMessage());
				} catch (SQLExceptionList sqle) {
					throw new SQLException("Falha ao conectar com banco de dados. " + sqle.getMessage());
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public void addPhones(User user) throws SQLException {
		int updatedRows;
		try {
			PreparedStatement statementDeleteAllPhones = myConn.prepareStatement(SQL_DELETE_PHONES_BY_USER_ID);
			statementDeleteAllPhones.setInt(1, user.getId());
			statementDeleteAllPhones.executeUpdate();

			PreparedStatement statement = myConn.prepareStatement(SQL_INSERT_PHONE);
			for (Phone phone : user.getPhones()) {
				statement.setInt(1, phone.getDdd());
				statement.setInt(2, phone.getNumber());
				statement.setString(3, phone.getType().getValue());
				statement.setInt(4, user.getId());
				updatedRows = statement.executeUpdate();
				if (updatedRows == 0) {
					myConn.rollback();
					throw new SQLException("Nenhum registro criado");
				}
				
			}
			myConn.commit();

		} catch (SQLException e) {
			if (myConn != null) {
				try {
					myConn.rollback();
					throw new SQLException("Falha ao inserir telefone. " + e.getMessage());
				} catch (SQLExceptionList sqle) {
					throw new SQLException("Falha ao inserir telefone. " + sqle.getMessage());
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(User user) throws SQLException {
		try {
			myConn.setAutoCommit(false);
			PreparedStatement statementDeleteUser = myConn.prepareStatement(SQL_DELETE_USER_BY_ID);
			statementDeleteUser.setInt(1, user.getId());
			int DeletedUserRows = statementDeleteUser.executeUpdate();
			if (DeletedUserRows == 0) {
				myConn.rollback();
				throw new SQLException("Falha ao excluir usuário. Nenhum registro excluído");

			}
			PreparedStatement statementDeletePhones = myConn.prepareStatement(SQL_DELETE_PHONES_BY_USER_ID);
			statementDeletePhones.setInt(1, user.getId());
			statementDeletePhones.executeUpdate();

		} catch (SQLException e) {
			if (myConn != null) {
				try {
					myConn.rollback();
					throw new SQLException("Falha ao conectar com banco de dados. " + e.getMessage());
				} catch (SQLExceptionList sqle) {
					throw new SQLException("Falha ao conectar com banco de dados. " + sqle.getMessage());
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public User login(String email, String pass) throws SQLException {

		User user = null;
		Phone phone = null;

		if (email == null || email.equals("") || pass == null || pass.equals("")) {
			throw new SQLException("Falha no login campos obrigatórios");
		}

		PreparedStatement statement = myConn.prepareStatement(SQL_SELECT_USER_BY_CREDENTIALS);
		statement.setString(1, email);
		statement.setString(2, pass);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			user = new User();
			user.setFirstName(result.getString("firstname"));
			user.setLastName(result.getString("lastname"));
			user.setEmail(result.getString("email"));
			user.setRole(User.Role.getRole(result.getString("role")));
			user.setPass(result.getString("pass"));
			user.setId(result.getInt("id"));
			PreparedStatement statementPhones = myConn.prepareStatement(SQL_SELECT_PHONES_BY_USER_ID);
			statementPhones.setInt(1, user.getId());
			ResultSet resultPhones = statementPhones.executeQuery();
			while (resultPhones.next()) {
				phone = new Phone(resultPhones.getInt("id"), resultPhones.getInt("ddd"), resultPhones.getInt("number"),
						Phone.PhoneType.getPhoneType(resultPhones.getString("type")));
				user.addPhone(phone);
			}
		}
		return user;
	}

	public User findByEmail(String email) throws SQLException {

		User user = null;
		Phone phone = null;

		if (email == null || email.equals("")) {
			throw new SQLException("Falha no login campos obrigatórios");
		}

		PreparedStatement statement = myConn.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			user = new User();
			user.setFirstName(result.getString("firstname"));
			user.setLastName(result.getString("lastname"));
			user.setEmail(result.getString("email"));
			user.setRole(User.Role.getRole(result.getString("role")));
			user.setId(result.getInt("id"));
			if(result.getInt("Phones.id")!=0) {
				phone = new Phone(result.getInt("Phones.id"), result.getInt("ddd"), result.getInt("number"),
						Phone.PhoneType.getPhoneType(result.getString("type")));
				user.addPhone(phone);	
			}
		}
		return user;
	}

	public void addPhone(Integer userId, Phone phone) throws SQLException {
		try {
			myConn.setAutoCommit(false);
			PreparedStatement statement = myConn.prepareStatement(SQL_INSERT_PHONE);
			statement.setInt(1, phone.getDdd());
			statement.setInt(2, phone.getNumber());
			statement.setString(3, phone.getType().getValue());
			statement.setInt(4, userId);
			int updatedRows = statement.executeUpdate();
			if (updatedRows == 0) {
				myConn.rollback();
				throw new SQLException("Falha ao criar usuário. Nenhum registro criado");
			}

			myConn.commit();

		} catch (

		SQLException e) {
			if (myConn != null) {
				try {
					myConn.rollback();
					throw new SQLException("Falha ao conectar com banco de dados. " + e.getMessage());
				} catch (SQLExceptionList sqle) {
					throw new SQLException("Falha ao conectar com banco de dados. " + sqle.getMessage());
				}
			}
			e.printStackTrace();
		}

	}

	public void deletPhone(Integer phoneId) throws SQLException {
		try {
			myConn.setAutoCommit(false);
			PreparedStatement statement = myConn.prepareStatement(SQL_DELETE_PHONES_BY_ID);
			statement.setInt(1, phoneId);
			int updatedRows = statement.executeUpdate();
			if (updatedRows == 0) {
				myConn.rollback();
				throw new SQLException("Falha ao excluir telefone. Nenhum registro excluído");
			}
			myConn.commit();

		} catch (

		SQLException e) {
			if (myConn != null) {
				try {
					myConn.rollback();
					throw new SQLException("Falha ao conectar com banco de dados. " + e.getMessage());
				} catch (SQLExceptionList sqle) {
					throw new SQLException("Falha ao conectar com banco de dados. " + sqle.getMessage());
				}
			}
			e.printStackTrace();
		}

	}

}
