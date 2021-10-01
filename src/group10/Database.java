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

	static String url;
	static String user;
	static String password;
	
	public static void connect(String pUrl, String pUser, String pPassword) {
		url = pUrl;
		user = pUser;
		password = pPassword;
		
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
	
	public static boolean attemptConnection(String pUrl, String pUser, String pPassword) {
		url = pUrl;
		user = pUser;
		password = pPassword;
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			if(conn != null) {
				System.out.println("Successfully Connected to " + url + "!");
				return true;
			}
		}
		catch(SQLException ex) {
			System.out.println("Unable to connect to " + url);
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
	
	/**
	 * 
	 * @param loginUsername
	 * @param loginPassword
	 * @return true if found login 
	 */
	public static boolean login(String loginUsername, String loginPassword) {
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
	
	/**
	 * Retrieves all users and returns a list of user names as Strings.
	 * @return user names
	 */
	public static List<String> getUserList(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<String> users = new ArrayList<String>();
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM user");
		    while(rs.next()) {
		    	users.add(rs.getString(1));
		    }
		    
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
		}
		return users;
	}
	
	/**
	 * Checks for duplicate user in the db.
	 * @param username
	 * @return true if found user with same name. False if doesn't exist.
	 */
	public static boolean duplicateUser(String username) {
		return false;
	}
}
