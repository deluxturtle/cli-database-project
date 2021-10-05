package group10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu to add/delete/edit/show logins.
 * @author aseba
 *
 */
public class LoginMenu {
	
	List<Login> logins;
	static boolean exit;
	int maxDisplayLogin = 10;

	public void mainMenu() {
		exit = false;
		
		//load logins
		logins = new ArrayList<Login>();
		marshalLogins();
		
		
		do {
			System.out.println(
				  "\n-----------------------------------\n"
				+ "Logins"
				+ "\n-----------------------------------\n");
			System.out.println(
				  "1. Create\n"
				+ "2. Edit\n"
				+ "3. Delete\n"
				+ "4. Previous Menu");
			String input = Main.in.nextLine();
			int inputNum = Integer.parseInt(input);
			mainMenuBranch(inputNum);
	
		}while(!exit);
	}
	

	/**
	 * Retrieve logins and store them in a list to help display them easier.
	 * @return true if able to load logins false if not.
	 */
	boolean marshalLogins() {
		System.out.println("Loading logins...");
		
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			ps = conn.prepareStatement(
					  "SELECT login.login_name, login.login_username, login.login_password, login.login_url, login.login_note "
					+ "FROM login JOIN has_login on has_login.login_name = login.login_name "
					+ "WHERE has_login.username = ?");
			ps.setString(1, Account.username);
			rs = ps.executeQuery();
			//ResultSetMetaData metaData = rs.getMetaData();
			//for(int i=1; i <= metaData.getColumnCount(); i++) {
				//System.out.print(metaData.getColumnLabel(i) + ", ");
			//}
			//System.out.println();
			while(rs.next()) {
				//System.out.printf("%-20.20s %-15.15s %15.15s\n", rs.getString("login_url"), rs.getString("login_name"), rs.getString("login_password"));
				Login temp = new Login(rs.getString("login_name"), rs.getString("login_username"), rs.getString("login_password"), rs.getString("login_url"), rs.getString("login_note"));
				logins.add(temp);
			}
		    
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(ps != null)
					ps.close();
				if(conn != null)
					conn.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
		}
		return false;
	}
	
	void mainMenuBranch(int option) {
		switch(option) {
			case 1: 
				break;
			case 2: 
				break;
			case 3: 
				break;
			case 4: exit();
				break;
			//OTHER OPTIONS
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	void exit() {
		exit = true;
	}
}
