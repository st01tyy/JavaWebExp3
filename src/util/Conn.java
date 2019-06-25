package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	private static String urlAndDatabase = "192.168.43.201:3306/questionnaire";
	private static String username = "root";
	private static String passwd = "Leafee98";
	private static Connection connection = null;
	
	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mariadb://" + urlAndDatabase, username, passwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("the database's jdbc driver class not found!");
			System.exit(-1);
		} catch (SQLException e) {
			System.out.println("Unknown SQLException!");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Connection getConnection() {
		/* Connection connection = null;
		try {
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unknown SQLException!");
			System.exit(-2);
		}
		return connection;*/

		return connection;
	}
}