package group10;



import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 * Holds database functions for connecting
 * @author group10
 *
 */
public class Database {
	
	
	//db for database to prevent confusion creating new users and passwords.
	static String dbUrl;
	static String dbUser;
	static String dbPassword;
	
	public static void connect(String pUrl, String pUser, String pPassword) {
		dbUrl = pUrl;
		dbUser = pUser;
		dbPassword = pPassword;
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			if(conn != null) {
				System.out.println("Successfully Connected to " + dbUrl +"!");
			}
		}
		catch(SQLException ex) {
			System.out.println("Unable to connect to " + dbUrl);
			ex.printStackTrace();
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException se) {
				System.out.println("Problem closing connection!");
				se.printStackTrace();
			}
		}
	}
	
	public static boolean attemptConnection(String pUrl, String pUser, String pPassword) {
		dbUrl = pUrl;
		dbUser = pUser;
		dbPassword = pPassword;
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			if(conn != null) {
				System.out.println("Successfully Connected to " + dbUrl + "!");
				return true;
			}
		}
		catch(SQLException ex) {
			System.out.println("Unable to connect to " + dbUrl);
			return false;
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException se) {
				System.out.println("Problem closing connection!");
				se.printStackTrace();
			}
		}
		return false;
	}
	

	
	
}
