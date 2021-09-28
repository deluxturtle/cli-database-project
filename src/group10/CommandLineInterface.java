package group10;

import java.util.Scanner;

/*
 * Displays command line menus for operating on our database program
 * Modeled after the SER334 Module 2 Project.
 */
public class CommandLineInterface {
	
	Database database;
	Scanner in;
	boolean exit = false;
	
	/**
	 * Begins menus for command line interface
	 * @param database
	 */
	public void start(Database database) {
		this.database = database;
		
		in = new Scanner(System.in);
		
		mainMenu();
		
		//Shows up if a function just exits
		System.out.println("CLI Exited.");
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
	
	//Takes input as username
	boolean loginAs(String username) {
		//TODO Implement login system.
		System.out.println("Logging in as " + username);
		return true;
	}
	
	void exit() {
		System.out.println("Goodbye!");
		exit = true;
		//let program exit out to main to close all resources.
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
