package group10;

import java.util.List;

/**
 * Menu to add/delete/edit/show logins.
 * @author aseba
 *
 */
public class LoginMenu {
	
	List<Login> logins;
	boolean exit;
	int maxDisplayLogin = 10;

	public void mainMenu() {
		exit = false;
		
		//load logins
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
	 */
	void marshalLogins() {
		System.out.println("Loading logins...");
		
		
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
