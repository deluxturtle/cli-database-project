package group10;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Displays command line menus for operating on our database program
 * Modeled after the SER334 Module 2 Project.
 * @author aseba, 
 *
 */
public class CommandLineInterface {
	
	Database database;
	Scanner in;
	boolean exit = false;
	
	/**
	 * Begins menus for command line interface
	 * @param database to use
	 */
	public void start(Database database) {
		this.database = database;
		
		in = new Scanner(System.in);
		
		welcomeMenu();
		
		//Shows up if a function just exits
		System.out.println("Password Manager Exited");
	}
	
	/**
	 * Welcome Splash
	 */
	void welcomeMenu() {
		do {
			System.out.println(
					  "\n-----------------------------------\n"
					+ "Password Manager"
					+ "\n-----------------------------------\n");
			System.out.println(
					  "1. Login\n"
					+ "2. New Account\n"
					+ "3. Exit");
			
			String input = in.nextLine();
			int inputNum = Integer.parseInt(input);
			welcomeMenuBranch(inputNum);
			
		}while(!exit);
	}
	
	/**
	 * Welcome options.
	 * @param option
	 */
	void welcomeMenuBranch(int option) {
		switch(option) {
			case 1: loginMenu();
				break;
			case 2: newAccountMenu();
				break;
			case 3: exit();
				break;
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	/**
	 * Asks for username and password and sends the account credentials to check on the database.
	 */
	void loginMenu() {
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
		if(!username.isBlank() && !password.isBlank() && database.login(username, password)){
			System.out.println("SUCCESS");
			delay(1);
			mainMenu();
		}
		else {
			System.out.println("\nInvalid Username/Password.");
			delay(1);
		}
	}
	
	void newAccountMenu() {
		
	}

	
	
	void mainMenu() {
		do {
			System.out.println(
					  "\n-----------------------------------\n"
					+ "Main Menu"
					+ "\n-----------------------------------\n");
			System.out.println(
					  "1. Records\n"
					+ "2. Manage Password\n"
					+ "3. Exit");
			String input = in.nextLine();
			int inputNum = Integer.parseInt(input);
			mainMenuBranch(inputNum);

		}while(!exit);
	}
	
	/*
	 * Handles menu options of the main menu
	 */
	void mainMenuBranch(int option) {
		switch(option) {
			case 1: recordsMenu();
				break;
			case 2: managesPasswordMenu();
				break;
			case 3: exit();
				break;
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	/*
	 * Displays Menu for editing or showing menus
	 */
	void recordsMenu() {
		int input_num;
		do {
			System.out.println(
					"\n-----------------------------------\n"
					+ "Records Menu"
					+ "\n-----------------------------------\n"
					+ "-1 RETURN TO PREVIOUS MENU\n"
					+ "-2 TERMINATE PROGRAM\n"
					+ "Please enter your choice ---> ");
			input_num = Integer.parseInt(in.nextLine());
			recordsMenuBranch(input_num);
			
		}while(!exit);
	}
	
	void recordsMenuBranch(int option) {
		if(option == -1) {
			System.out.println("Returning...");
			mainMenu();
		}
		else if( option == -2) {
			System.out.println("Terminating program...");
			exit();
		}
	}
	
	//Displays Menu for managing master password.
	void managesPasswordMenu() {
		
	}
	
	//Displays Menu for logging out. (Asking if you want to log out.
	void logoutMenu() {
		System.out.println("Are you sure? (yes/no)");
		String input = in.nextLine();
		if(input == "yes") {
			
		}
		else if(input == " no") {
			
		}
		else {
			logoutMenu();
		}
	}
	
	/**
	 * Sets the exit boolean to true and exits back to main.
	 */
	void exit() {
		System.out.println("Goodbye!");
		exit = true;
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
	
//	void loginMenu() {
//	System.out.print("Login:\nusername: ");
//	
//	String input = in.nextLine();
//	
//	if(loginAs(input)) {
//		mainMenu();
//	}
//	
//	
//}
	
}
