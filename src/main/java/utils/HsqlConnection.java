//package utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//import org.hsqldb.Server;
//
//public class HsqlConnection {
//
//	private static HsqlConnection instance = new HsqlConnection();
//	private static Connection con;
//
//	private HsqlConnection() {
//
//	}
//
//	public static HsqlConnection getInstance() {
//		if (instance == null) {
//			instance = new HsqlConnection();
//		}
//		return instance;
//	}
//
//	public Connection getConnection() throws Exception {
//		if (con == null) {
//			Server server = new Server();
//			server.setDatabaseName(0, "desafioDB");
//			server.setDatabasePath(0, "file:./desafioDB");
//			try {
//				if (server.isNotRunning()) {
//					server.start();
//				}
//				con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/desafioDB", "SA", "");
//			} catch (Exception e) {
//				throw e;
//			}
//		} else {
//			if(con.isClosed()) {
//				con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/desafioDB", "SA", "");
//			} 
//		}
//		return con;
//	}
//
//	public void closeConnetion() throws Exception {
//		if (con != null) {
//			try {
//				con.close();
//			} catch (Exception e) {
//				throw e;
//			}
//		}
//	}
//
//}
