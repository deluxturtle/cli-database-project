package group10;

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
		Scanner in = new Scanner(System.in);
		
		do {
			System.out.println(
				  "\n-----------------------------------\n"
				+ "Account Manager"
				+ "\n-----------------------------------\n");
			System.out.println(
				  "1. Edit Master Password\n"
				+ "2. Delete Account\n"
			    + "3. Exit");
			
			String input = in.nextLine();
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
		
	}
	
	static void editPasswordMenu() {
		
	}
	
	static void exit() {
		exit = true;
	}
}
