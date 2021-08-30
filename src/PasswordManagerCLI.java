import java.util.Scanner;

//Main Project Class
//Login
//Menu System
public class PasswordManagerCLI {
	
	Scanner in;
	
	public void Start() {
		in = new Scanner(System.in);
		LoginMenu();
		
		//Shows up if a function just exits 
		System.out.println("Exited Unexpectedly.");
	}
	
	void LoginMenu() {
		System.out.print("Login:\nusername: ");
		
		String input = in.nextLine();
		
		if(LoginAs(input)) {
			MainMenu();
		}
		
		
	}
	
	
	void MainMenu() {
		System.out.println();
		System.out.println("Main Menu");
		System.out.println(
				  "1. Records\n"
				+ "2. Manage Password\n"
				+ "3. Logout");
		String input = in.nextLine();
		int inputNum = Integer.parseInt(input);
		
		switch(inputNum) {
			case 1: RecordsMenu();
				break;
			case 2: ManagePasswordMenu();
				break;
			case 3: LogoutMenu();
				break;
			default : System.out.println("Invalid Option\n");
				MainMenu();
				break;
		}
	}
	
	//Displays Menu for editing or showing menus
	void RecordsMenu() {
		
	}
	
	//Displays Menu for managing master password.
	void ManagePasswordMenu() {
		
	}
	
	//Displays Menu for logging out. (Asking if you want to log out.
	void LogoutMenu() {
		System.out.println("Are you sure? (yes/no)");
		String input = in.nextLine();
		if(input == "yes") {
			
		}
		else if(input == " no") {
			
		}
		else {
			LogoutMenu();
		}
	}
	
	//Takes input as username
	boolean LoginAs(String username) {
		//TODO Implement login system.
		System.out.println("Logging in as " + username);
		return true;
	}
	
	void Exit() {
		System.out.println("Goodbye!");
	}
}
