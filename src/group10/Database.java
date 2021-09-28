package group10;



import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Holds database functions for connecting
 * @author group10
 *
 */
public class Database {
	
	Connection conn = null;
	
	public void connect(String url, String user, String password) {
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			if(conn != null) {
				System.out.println("Successfully Connected to " + url +"!");
			}
		}
		catch(SQLException ex) {
			System.out.println("Unable to connect to " + url);
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
	
	public void closeResources() {
		try {
			if(conn != null) {
				conn.close();
			}				
		} catch(SQLException se) {
			se.printStackTrace();
			System.out.println("Not all DB resources freed!");
		}
	}
	
	
}
