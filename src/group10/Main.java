package group10;


import java.util.Scanner;



public class Main {
	
	static String configFilePath = "config.properties";
	static boolean exit = false;
	static Scanner in;
	
	public static void main(String args[]) {
		in = new Scanner(System.in);
		
		
		String url = null;
		String user = null;
		String password = null;
		
		//Get arguments for database connection.
		if(args.length == 3) {
			url = args[1];
			user = args[2];
			password = args[3];			
		}
		else {
			//optional use of config file
			Config config = new Config();
			if(config.readConfig(configFilePath)) {
				url = config.url;
				user = config.user;
				password = config.password;
			}
			else {
				config.createEmptyConfig(configFilePath);
				System.out.println("Created optional config file.");
				System.out.println("USAGE: java -jar password_manager.jar <url> <user> <password>");
				System.out.println("Or use optional config file.");
				System.exit(0);
			}
		}
		
		
		//Tests database and continues if able to connect.
		if(Database.attemptConnection(url, user, password)) {
			//start doing the command line interface menus
			Account accountLoginMenu = new Account();
			do {
				accountLoginMenu.mainMenu();
				while(Account.loggedin) {
					System.out.println(
							  "\n-----------------------------------\n"
							+ "Main Menu"
							+ "\n-----------------------------------\n");
					System.out.println(
						  "1. Cards\n"
						+ "2. Notes\n"
						+ "3. Logins\n"
						+ "4. Manage Account\n"
						+ "5. Log Out\n");
					String input = in.nextLine();
					int inputNum = Integer.parseInt(input);
					mainMenuBranch(inputNum);
					
				}		
			}while(!exit);

		}
		else {
			System.out.println("Unable to connect to given database in given arguments.");
			System.out.println("USAGE: java -jar password_manager.jar <url> <user> <password>");
			System.out.println("Or use optional config file.");
		}
		
		//close scanner
		in.close();
	}
	
	/*
	 * Handles menu options of the main menu
	 */
	static void mainMenuBranch(int option) {
		switch(option) {
			case 1: //CARDS
				break;
			case 2: //NOTES
				break;
			case 3: //LoginMenu.mainMenu();
				//testing instantiating the menu
				LoginMenu loginMenu = new LoginMenu();
				loginMenu.mainMenu();
				break;
			case 4: ManageAccount.mainMenu();
				break;
			case 5: Account.logOut();
				break;
			//OTHER OPTIONS
			default : System.out.println("Invalid Option try again...");
				break;
		}
	}
	
	public static void exitProgram() {
		exit = true;
	}

}


