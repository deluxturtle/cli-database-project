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
	//int maxDisplayLogin = 10;

	public void mainMenu() {
		exit = false;
		
		//load logins
		logins = new ArrayList<Login>();
		marshalLogins();
		
		
		do {
			System.out.println(
				  "\n-----------------------------------\n"
				+ "Logins"
				+ "\n-----------------------------------");
			printLogins();
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
			while(rs.next()) {
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
	
	void printLogins() {
		if(logins.size() > 0) {
			System.out.printf("#  ) %-20.20s %-15.15s %-15.15s\n", "Website", "UserName", "Password");
			for(Login login : logins) {
				System.out.printf("%-3d) %-20.20s %-15.15s %-15.30s\n", logins.indexOf(login), login.getLogin_url(), login.getLogin_username(), login.getLogin_password());
			}
		}
		else {
			System.out.println("No login records.");
		}
		System.out.println();
	}
	
	void mainMenuBranch(int option) {
		switch(option) {
			case 1: createLoginMenu();
				break;
			case 2: 
				break;
			case 3: deleteLoginMenu();
				break;
			case 4: exit();
				break;
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	void createLoginMenu() {
		
		

		System.out.println("Enter your login username:");
		String username = Main.in.nextLine();

		System.out.println("Enter login password:");
		String password = Main.in.nextLine();
		
		System.out.println("Enter website url:");
		String website = Main.in.nextLine();
		
		System.out.println("Enter note about login:");
		String note = Main.in.nextLine();
		
		System.out.println("Name your login:");
		String loginName = Main.in.nextLine();
		
		Login newLogin = new Login(loginName, username, password, website, note);
		
		if(createLogin(newLogin)){
			logins.add(newLogin);
			System.out.println("New login recorded!");
		}


		
		
	}

	boolean createLogin(Login login){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DriverManager.getConnection(Database.dbUrl,Database.dbUser, Database.dbPassword);
			ps = conn.prepareStatement("INSERT INTO login VALUES ( ?, ?, ?, ?, ?)");
			ps.setString(1, login.getLogin_name());
			ps.setString(2, login.getLogin_username());
			ps.setString(3, login.getLogin_password());
			ps.setString(4, login.getLogin_url());
			ps.setString(5, login.getLogin_note());
		}
	}
	
	void deleteLoginMenu() {
		System.out.println("Enter what record number you would like to delete:");
		String input = Main.in.nextLine();
		int inputNum = Integer.parseInt(input);
		
		if(deleteLogin(inputNum)) {
			System.out.println("Login record deleted.");
			logins.remove(inputNum);
			printLogins();
		}
		

	}
	
	boolean deleteLogin(int loginIndex) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			ps = conn.prepareStatement("DELETE FROM has_login WHERE login_name = ?");
			ps.setString(1, logins.get(loginIndex).getLogin_name());

			if(ps.executeUpdate() > 0)
				return true;
			else
				return false;
		    
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
	
	void exit() {
		exit = true;
	}
}
