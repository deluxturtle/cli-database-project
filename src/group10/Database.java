package group10;



import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {
	
	String url;
	String user;
	String password;
	
	ResultSet rs = null;
	Statement stmt = null;
	Connection conn = null;
	
	public Database(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;		
		
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
				se.printStackTrace();
			}
		}
	}
	
	
}
