package group10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Main Login Menu Able to login/view user names/ and add accounts.
 * @author aseba
 *
 */
public class Account {
	
	static String username = "";
	boolean exitMenu = false;
	static boolean loggedin = false;
	
	public void mainMenu() {
		
		exitMenu = false;
		do {
			System.out.println(
				  "\n-----------------------------------\n"
				+ "Password Manager"
				+ "\n-----------------------------------\n");
			System.out.println(
				  "1. Login\n"
				+ "2. New Account\n"
			    + "3. Display Users\n"
				+ "4. Exit");
			
			String input = Main.in.nextLine();
			int inputNum = Integer.parseInt(input);
			loginBranch(inputNum);
			
		}while(!exitMenu);
	}
	
	void loginBranch(int option) {
		switch(option) {
			case 1: accountMenu();
				break;
			case 2: newAccountMenu();
				break;
			case 3: displayUserMenu();
				break;
			case 4: exit();
				break;
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	/**
	 * Asks for username and password and sends the account credentials to check on the database.
	 * sees if username and login are equivalent on the db.
	 */
	void accountMenu() {
		username = "";
		String password = "";
		
		System.out.println(
			  "\n-----------------------------------\n"
			+ "Login"
			+ "\n-----------------------------------\n");
		System.out.print("username:");
		username = Main.in.nextLine();
		
		System.out.print("password:");
		password = Main.in.nextLine();
		
		//as long as the user has entered something try to login.
		if(!username.isBlank() && !password.isBlank() && login(username, password)){
			System.out.println("SUCCESS");
			loggedin = true;
			exitMenu = true;
		}
		else {
			System.out.println("\nInvalid Username/Password.");
		}
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
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
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
	
	
	void displayUserMenu() {
		System.out.println(
			  "\n-----------------------------------\n"
			+ "Users"
			+ "\n-----------------------------------\n");
		//grab a list of users
		List<String> users = getUserList();
		for(String user : users) {
			System.out.println(user);
		}
	}
	
	void newAccountMenu() {
		String input;//temp value for user input
		String username = "";
		String password = "";
		System.out.println(
			  "\n-----------------------------------\n"
			+ "New Account Menu"
			+ "\n-----------------------------------\n");
		do {
			//Get username
			System.out.println("-----Creating new account-----");
			System.out.println("Please enter your new username.");
			System.out.print("username:");
			username = Main.in.nextLine();
			
			do {
				//double check your name
				System.out.println("Review Username:\n"+username+"\nAre you sure you want to use this name?\ny/n");
				input = Main.in.nextLine();
				
				//Go for it
				if(input.equalsIgnoreCase("y")) {
					//Check for duplicate users in the db.
					if(!duplicateUser(username)) {
						//System.out.println("Success!");
						break;
					}
					else {
						System.out.println("User already exists. Please use a different name.");
						input = "n";
						username = "";
					}			
				}
				else if(input.equalsIgnoreCase("n")) {
					username = "";
				}
			}
			while(!input.equalsIgnoreCase("n"));
			
			//if got username  get the password from the user.
			if(username.length() > 0) {
				do {
					System.out.println("Please enter your new password. (can't be blank)");
					System.out.print("password:");
					password = Main.in.nextLine();
					
					//password passes requirements
					if(password.length() > 0) {
						System.out.println("Password is acceptable!\n");
						break;
					}
					else {
						System.out.println("Password must be > 0 characters.");
						password = "";
					}
					//System.out.println("Password Length "+ password.length());
				}while(password.length() < 1);
				
				//actually insert into db
				if(insertNewUser(username, password))
				{
					System.out.println("--New User Account Created!--");
				}
				else {
					System.out.println("Problem creating new user account try again later.");
				}
				
				//finally break out of this prison.
				break;
			}
			
		}while(true);
		
		
		delay(1);

	}
	
	/**
	 * Sets the exit boolean to true and exits back to main.
	 */
	void exit() {
		System.out.println("Goodbye!");
		exitMenu = true;
		Main.in.close();
		Main.exitProgram();
		//let program exit out to main to close all resources.
	}
	
	/**
	 * Pauses command line to help the user read feedback.
	 * @param sec
	 */
	void delay(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void logOut() {
		loggedin = false;
		username = "";
	}
	
	/**
	 * Inserts new user into user table.
	 * @param username
	 * @param dbPassword
	 */
	public boolean insertNewUser(String username, String newPassword) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			ps = conn.prepareStatement("INSERT INTO `user` VALUES (?, ?)");
			ps.setString(1, username);
			ps.setString(2, newPassword);

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
	
	/**
	 * Checks for duplicate user in the db.
	 * @param username
	 * @return true if found user with same name. False if doesn't exist.
	 */
	public static boolean duplicateUser(String username) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			stmt = conn.prepareStatement("SELECT username FROM user WHERE user.username = ?");
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			
			//Found the username... return true.
			if(rs.next())
				return true;
			else {
				return false;
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
		return false;
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
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
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
	

	

	

	
}
