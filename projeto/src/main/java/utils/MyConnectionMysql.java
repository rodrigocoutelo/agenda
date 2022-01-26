package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnectionMysql {

	private static MyConnectionMysql instance;
	private Connection con;

	private MyConnectionMysql() {
	}

	// Singleton - Design Pattern
	public static MyConnectionMysql getInstance() {
		if (instance == null) {
			instance = new MyConnectionMysql();
		}

		return instance;
	}

	// Método de Conexão//

	public Connection getConnection() {

		try {

			String serverName = "localhost:3306";
			String mydatabase = "desafio";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "root"; 
			String password = "2808"; 
			Class.forName("com.mysql.jdbc.Driver"); 
			this.con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		return this.con;
	}

	public void closeConnetion() {
		if (this.con != null) {
			try {
				this.con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
