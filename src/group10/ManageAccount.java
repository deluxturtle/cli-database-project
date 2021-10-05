package group10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Menu for deleting and editing password.
 * @author deluxturtle
 *
 */
public class ManageAccount {
	
	static boolean exit = false;
	
	
	public static void mainMenu() {
		
		exit = false;
		
		do {
			System.out.println(
				  "\n-----------------------------------\n"
				+ "Account Manager"
				+ "\n-----------------------------------\n");
			System.out.println(
				  "1. Edit Master Password\n"
				+ "2. Delete Account\n"
			    + "3. Exit");
			
			String input = Main.in.nextLine();
			int inputNum = Integer.parseInt(input);
			mainBranch(inputNum);
			
		}while(!exit);
	}
	
	static void mainBranch(int option) {
		switch(option) {
			case 1: editPasswordMenu();
				break;
			case 2: deleteAccountMenu();
				break;
			case 3: exit();
				break;
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	static void deleteAccountMenu() {
		System.out.println("---!You are about to delete your account!---");
		do {
			System.out.println("Delete account? y/n");
			String input = Main.in.nextLine();
			if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
				if(deleteUser(Account.username)) {
					Account.logOut();
					System.out.println("Account deleted.");
					exit();
					break;
				}
				else {
					System.out.println("Problem Deleting Account, try again later...");
					break;
				}
			}
			else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
				break;
			}
			else {
				System.out.println("Invalid input please type y/yes or n/no for yes or no.");
			}
		}while(true);
	}
	
	/**
	 * Deletes user from db.
	 * @param username
	 * @return
	 */
	public static boolean deleteUser(String username) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			ps = conn.prepareStatement("DELETE FROM user WHERE username = ?");
			ps.setString(1, username);

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
	
	static void editPasswordMenu() {
		do {
			System.out.println("Would you like to change your password? y/n");
			String input = Main.in.nextLine();
			if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
				System.out.println("new password:");
				input = Main.in.nextLine();
				if(input.length() > 0) {
					if(changePassword(input)) {
						System.out.println("Password Changed!");
						break;
					}
					else {
						System.out.println("Problem changing password. Try again later...");
						break;
					}
				}
				else {
					System.out.println("Password needs to be at least 1 character long.");
				}

			}
			else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
				break;
			}
			else {
				System.out.println("Invalid input please type y/yes or n/no for yes or no.");
			}
		}while(true);
		
	}
	
	static boolean changePassword(String newPassword) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
			
			ps = conn.prepareStatement("UPDATE user SET master_password = ? WHERE username = ?");
			ps.setString(1, newPassword);
			ps.setString(2, Account.username);

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
	
	static void exit() {
		exit = true;
	}
}
