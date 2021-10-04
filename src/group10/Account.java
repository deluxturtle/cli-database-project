package group10;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Main Login Menu Able to login/view user names/ and add accounts.
 * @author aseba
 *
 */
public class Account {
	
	Scanner in;
	boolean exitMenu = false;
	static boolean loggedin = false;
	
	public Account() {
		in = new Scanner(System.in);
	}
	
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
			
			String input = in.nextLine();
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
	 */
	void accountMenu() {
		String username = "";
		String password = "";
		
		System.out.println(
			  "\n-----------------------------------\n"
			+ "Login"
			+ "\n-----------------------------------\n");
		System.out.print("username:");
		username = in.nextLine();
		
		System.out.print("password:");
		password = in.nextLine();
		
		//as long as the user has entered something try to login.
		if(!username.isBlank() && !password.isBlank() && Database.login(username, password)){
			System.out.println("SUCCESS");
			loggedin = true;
			exitMenu = true;
		}
		else {
			System.out.println("\nInvalid Username/Password.");
		}
	}
	
	void displayUserMenu() {
		System.out.println(
			  "\n-----------------------------------\n"
			+ "Users"
			+ "\n-----------------------------------\n");
		//grab a list of users
		List<String> users = Database.getUserList();
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
			username = in.nextLine();
			
			do {
				//double check your name
				System.out.println("Review Username:\n"+username+"\nAre you sure you want to use this name?\ny/n");
				input = in.nextLine();
				
				//Go for it
				if(input.equalsIgnoreCase("y")) {
					//Check for duplicate users in the db.
					if(!Database.duplicateUser(username)) {
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
					password = in.nextLine();
					
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
				if(Database.insertNewUser(username, password))
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
	}
	
	
}
