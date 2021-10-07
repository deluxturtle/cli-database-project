package group10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

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
			case 2: editLoginMenu();
				break;
			case 3: deleteLoginMenu();
				break;
			case 4: exit();
				break;
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}

	/**
	 * Sets up editing a login. Asks for which login to edit then what part and then you can just change it to what you want.
	 */
	void editLoginMenu(){

		System.out.println("Enter what number login you would like to edit.");
		String input = Main.in.nextLine();
		int inputNum = Integer.parseInt(input);

		if(inputNum >= 0 && inputNum < logins.size()){
			Login login = logins.get(inputNum);
			System.out.println("1. Login Name:     " + login.getLogin_name());
			System.out.println("2. Login username: " + login.getLogin_username());
			System.out.println("3. Login password: " + login.getLogin_password());
			System.out.println("4. Login url:      " + login.getLogin_url());
			System.out.println("5. Login note:     " + login.getLogin_note());
			System.out.println("Enter # of choice.");
			input = Main.in.nextLine();
			inputNum = Integer.parseInt(input);
			String chosen_att = getLoginAttribute(inputNum);
			if(!chosen_att.isBlank()){

				System.out.println("Type your new " + chosen_att + ":");
				String value = Main.in.nextLine();
				if(editLogin(login, chosen_att, value)){
					//update local login
					updateLocalLogin(login, inputNum, value);
					System.out.println("Login succesfully updated!");
				}
				else{
					System.out.println("Problem updating login. Try again later.");
				}

			}
			else{
				System.out.println("Please enter valid attribute. Returning to previous menu.");
			}
		}
		else{
			System.out.println("Please enter a valid login #");
		}
	}

	/**
	 * Getes the string attribute for editing the db directly.
	 * @param input
	 * @return
	 */
	String getLoginAttribute(int input){
		switch(input){
			case 1: return "login_name";
			case 2: return "login_username";
			case 3: return "login_password";
			case 4: return "login_url";
			case 5: return "login_note";
			default: return "";
		}
	}

	boolean editLogin(Login login, String attribute, String value){
		//building SQL statement.
		String change = "login." + attribute + " = \'" + value + "\'";
		
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			String sql = "UPDATE login SET " + change + " WHERE login_name = ?";
			ps = conn.prepareStatement(sql);
			// ps.setString(1, change);
			ps.setString(1, login.getLogin_name());

			if(ps.executeUpdate() > 0){
				return true;
			}
			else{
				return false;
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

	/**
	 * Updates local login so we don't have to query the database again just to retrieve what we 
	 * know has been updated. and save on bandwidth.
	 * @param login
	 * @param input
	 * @param value
	 */
	void updateLocalLogin(Login login, int input, String value){
		switch(input){
			case 1: login.setLogin_name(value);
			case 2: login.setLogin_username(value);
			case 3: login.setLogin_password(value);
			case 4: login.setLogin_url(value);
			case 5: login.setLogin_note(value);
		}
	}
	
	/**
	 * Asks for login information you want to add to the database.
	 */
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
		else{
			System.out.println("Error saving login. Please try again later.");
		}


		
		
	}

	/**
	 * Sends over the login to the database using SQL.
	 * @param login
	 * @return
	 */
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

			if(ps.executeUpdate() > 0){
				ps.close();
				//almost forgot to add it to the has_login table
				ps = conn.prepareStatement("INSERT INTO has_login VALUES ( ?, ?)");
				ps.setString(1, Account.username);
				ps.setString(2, login.getLogin_name());

				if(ps.executeUpdate() > 0){
					return true;
				}
				else{
					return false;
				}
				
			}
			else{
				return false;
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
	
	/**
	 * Sets-up deleting login record.
	 */
	void deleteLoginMenu() {
		System.out.println("Enter what record number you would like to delete:");
		String input = Main.in.nextLine();
		int inputNum = Integer.parseInt(input);
		
		if(inputNum > 0 && inputNum < logins.size() && deleteLogin(inputNum)) {
			System.out.println("Login record deleted.");
			logins.remove(inputNum);
			printLogins();
		}
		else{
			System.out.println("Please enter a valid login #");
		}
		

	}
	
	/**
	 * Deletes login from login and has_login table on the database.
	 * @param loginIndex
	 * @return
	 */
	boolean deleteLogin(int loginIndex) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			ps = conn.prepareStatement("DELETE FROM has_login WHERE login_name = ?");
			ps.setString(1, logins.get(loginIndex).getLogin_name());

			if(ps.executeUpdate() > 0){
				ps.close();
				//delete from actual login table as well.
				ps = conn.prepareStatement("DELETE FROM login WHERE login_name = ?");
				ps.setString(1, logins.get(loginIndex).getLogin_name());
				if(ps.executeUpdate() > 0){
					return true;
				}
				else{
					return false;
				}
			}
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
