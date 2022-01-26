package utils;


import java.sql.Connection;
import java.sql.Statement;

public class CreateTables {
	
	public void createUserTable() throws Exception {
		Connection myCon =  HsqlConnection.getInstance().getConnection();
		Statement stmt = null;
		int result = 0;
		try {
			myCon = HsqlConnection.getInstance().getConnection();
			stmt = myCon.createStatement();

			result = stmt.executeUpdate(
					"DROP TABLE user; CREATE TABLE user ("
					+ "id INT NOT NULL, "
					+ "firstname VARCHAR(100) NOT NULL, "
					+ "lastname VARCHAR(100) NOT NULL, "
					+ "email VARCHAR(150) NOT NULL, "
					+ "role VARCHAR(1) NOT NULL, "
					+ " PRIMARY KEY (ID)"
					+ ") ");
		} catch (Exception e) {
			throw e;
		} finally {
			HsqlConnection.getInstance().closeConnetion();
		}
	}
	
	public void createPhoneTable() throws Exception {
		Connection myCon =  HsqlConnection.getInstance().getConnection();
		Statement stmt = null;
		int result = 0;
		try {
			myCon = HsqlConnection.getInstance().getConnection();
			stmt = myCon.createStatement();

			result = stmt.executeUpdate(
					"DROP TABLE phone; CREATE TABLE phone ("
					+ "id INT NOT NULL, "
					+ "ddd INT NOT NULL, "
					+ "number INT NOT NULL, "
					+ "type VARCHAR(1) NOT NULL, "
					+ "user_id INT ) NOT NULL, "
					+ " PRIMARY KEY (ID),"
					+ "FOREIGN KEY (user_id) REFERENCES user(id)"
					+ ") ");
		} catch (Exception e) {
			throw e;
		} finally {
			HsqlConnection.getInstance().closeConnetion();
		}
	}
	
	public static void main(String[] args) {
		try {
			CreateTables cut = new CreateTables();
			cut.createUserTable();
			cut.createPhoneTable();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	

}
