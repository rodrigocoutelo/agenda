package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import utils.HsqlConnection;
import utils.MyConnectionMysql;

public class DBConnection {


//	@BeforeAll
//	public static void deveCriarUmaTabelaSemRetornarExcecao() {
//		Connection myCon = null;
//		Statement stmt = null;
//		int result = 0;
//		try {
//			myCon = HsqlConnection.getInstance().getConnection();
//			stmt = myCon.createStatement();
//
//			result = stmt.executeUpdate(
//					"DROP TABLE teste; CREATE TABLE teste (id INT NOT NULL, title VARCHAR(50) NOT NULL)");
//			assertEquals(0, result);
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
	
	@Test
	public void deveConectarMYSQLSemRetornarExcecao() {
		Connection myCon = null;
		Statement stmt = null;
		int result = 0;
		try {
			myCon = MyConnectionMysql.getInstance().getConnection();
			assertNotNull(myCon);
			MyConnectionMysql.getInstance().closeConnetion();
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void deveCriarUmaTabelUsersMYSQLSemRetornarExcecao() {
		Connection myCon = null;
		Statement stmt = null;
		int result = -1;
		try {
			myCon = MyConnectionMysql.getInstance().getConnection();
			stmt = myCon.createStatement();
			//result = stmt.executeUpdate("DROP TABLE Phones");
			result = stmt.executeUpdate("DROP TABLE Users");
			result = stmt.executeUpdate(
					"CREATE TABLE Users (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n"
					+ "firstname VARCHAR(50) NOT NULL,\n"
					+ "lastname VARCHAR(50) NOT NULL,\n"
					+ "email VARCHAR(50),\n"
					+ "pass VARCHAR(20),\n"
					+ "role VARCHAR(20),\n"
					+ "reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)");
			assertEquals(0, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deveCriarUmaTabelaPhonesSemRetornarExcecao() {
		Connection myCon = null;
		Statement stmt = null;
		int result = -1;
		try {
			myCon = MyConnectionMysql.getInstance().getConnection();
			stmt = myCon.createStatement();
		//	result = stmt.executeUpdate("DROP TABLE Phones");
			result = stmt.executeUpdate(
					"CREATE TABLE Phones (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n"
					+ "ddd INT(3) NOT NULL,\n"
					+ "number INT(10) NOT NULL,\n"
					+ "type VARCHAR(20),\n"
					+ "user INT(6) UNSIGNED,\n"
					+ "reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
					+ "FOREIGN KEY(user) REFERENCES Users(id))");
			assertEquals(0, result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	

//	@Test
//	public void deveRetornaraConexaoComOHSQLDBDiferentedeNulo() {
//		Connection myCon = null;
//		try {
//			myCon = HsqlConnection.getInstance().getConnection();
//			assertNotNull(myCon);
//			HsqlConnection.getInstance().closeConnetion();
//		} catch (Exception e) {
//			fail();
//		}
//	}
//
//	@Test
//	public void deveInserirDoisRegistosNaTabelaTesteSemRetornarExcecao() {
//		Connection myCon = null;
//		Statement stmt = null;
//		int result = -1;
//		try {
//			myCon = HsqlConnection.getInstance().getConnection();
//			stmt = myCon.createStatement();
//			result = stmt
//					.executeUpdate("INSERT INTO teste (id, title) VALUES ((1,'teste1'),(2,'teste2'),(3,'teste3'))");
//			assertEquals(3, result);
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void deveAlterarUmRegistoNaTabelaTesteSemRetornarExcecao() {
//		Connection myCon = null;
//		Statement stmt = null;
//		int result = -1;
//		try {
//			myCon = HsqlConnection.getInstance().getConnection();
//			stmt = myCon.createStatement();
//			result = stmt.executeUpdate("UPDATE teste set title = 'teste alterado' WHERE id = 2");
//			assertEquals(1, result);
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void deveRecuperarUmRegistoNaTabelaTesteERetornarOValordoCampoTitleSemRetornarExcecao() {
//		Connection myCon = null;
//		Statement stmt = null;
//		ResultSet resultSet = null;
//		try {
//			myCon = HsqlConnection.getInstance().getConnection();
//			stmt = myCon.createStatement();
//			resultSet = stmt.executeQuery("SELECT title FROM teste WHERE id = 2");
//			while (resultSet.next()) {
//				assertEquals("teste alterado", resultSet.getString("title"));
//			}
//			HsqlConnection.getInstance().closeConnetion();
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void deveDeletarUmRegistoNaTabelaTesteSemRetornarExcecao() {
//		Connection myCon = null;
//		Statement stmt = null;
//		int result = -1;
//		ResultSet resultSet = null;
//		try {
//			myCon = HsqlConnection.getInstance().getConnection();
//			stmt = myCon.createStatement();
//			result = stmt.executeUpdate("DELETE FROM teste WHERE id = 2");
//			assertEquals(1, result);
//			resultSet = stmt.executeQuery("SELECT COUNT(*) from teste");
//			resultSet.next();
//			assertEquals(2, resultSet.getInt(1));
//			HsqlConnection.getInstance().closeConnetion();
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}

}
