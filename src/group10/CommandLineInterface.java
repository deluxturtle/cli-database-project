package group10;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Displays command line menus for operating on our database program
 * Modeled after the SER334 Module 2 Project.
 * 
 * This class got to big implementing all of the functionality so it is now
 * broken into different menus. I left it in to get examples on how to
 * implement menus.
 * @author aseba, 
 *
 */
public class CommandLineInterface {
	
	
//	/**
//	 * Begins menus for command line interface
//	 * @param database to use
//	 */
//	public void start(Database database) {
//		this.database = database;
//		
//		in = new Scanner(System.in);
//		
//		welcomeMenu();
//		
//		//Shows up if a function just exits
//		System.out.println("Password Manager Exited");
//	}

	
	
//	void mainMenu() {
//		do {
//			System.out.println(
//				  "\n-----------------------------------\n"
//				+ "Main Menu"
//				+ "\n-----------------------------------\n");
//			System.out.println(
//				  "1. Records\n"
//				+ "2. Manage Password\n"
//				+ "3. Exit");
//			String input = in.nextLine();
//			int inputNum = Integer.parseInt(input);
//			mainMenuBranch(inputNum);
//
//		}while(!exit);
//	}

	
//	/*
//	 * Handles menu options of the main menu
//	 */
//	void mainMenuBranch(int option) {
//		switch(option) {
//			case 1: recordsMenu();
//				break;
//			case 2: managesPasswordMenu();
//				break;
//			case 3: exit();
//				break;
//			default : System.out.println("Invalid Option try again...");
//				break;
//		}
//	}

	
	
//	/*
//	 * Displays Menu for editing or showing menus
//	 */
//	void recordsMenu() {
//		int input_num;
//		do {
//			System.out.println(
//				"\n-----------------------------------\n"
//				+ "Records Menu"
//				+ "\n-----------------------------------\n"
//				+ "-1 RETURN TO PREVIOUS MENU\n"
//				+ "-2 TERMINATE PROGRAM\n"
//				+ "Please enter your choice ---> ");
//			input_num = Integer.parseInt(in.nextLine());
//			recordsMenuBranch(input_num);
//			
//		}while(!exit);
//	}

	
	
	
//	void recordsMenuBranch(int option) {
//		if(option == -1) {
//			System.out.println("Returning...");
//			mainMenu();
//		}
//		else if( option == -2) {
//			System.out.println("Terminating program...");
//			exit();
//		}
//	}
	

	

	
}
