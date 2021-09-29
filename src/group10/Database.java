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

	String url;
	String user;
	String password;
	
	public void connect(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		
		Connection conn = null;
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
	
	/**
	 * 
	 * @param loginUsername
	 * @param loginPassword
	 * @return true if found login 
	 */
	public boolean login(String loginUsername, String loginPassword) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement(
					"SELECT username, master_password \n" +
					"FROM user u \n" +
					"WHERE u.username = ? AND u.master_password = ?");
			ps.setString(1, loginUsername);
			ps.setString(2, loginPassword);
			rs = ps.executeQuery();
			
			//If we got a match return true!
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				if(rs != null)
					rs.close();
				if(ps != null)
					ps.close();
				if(conn != null) 
					conn.close();
			} catch (SQLException e) {
				System.out.println("Problem closing database resources.");
				e.printStackTrace();
			}
		}
		
		
	}
	
	
}
