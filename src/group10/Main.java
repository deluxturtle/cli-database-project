package group10;


import java.util.Scanner;



public class Main {
	
	static String configFilePath = "config.properties";
	
	public static void main(String args[]) {
		
		String url = null;
		String user = null;
		String password = null;
		
		//Get arguments for database connection.
		if(args.length == 3) {
			url = args[0];
			user = args[1];
			password = args[2];			
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
		
		
		Database database = new Database();
		
		if(database.attemptConnection(url, user, password)) {
			//start doing the command line interface menus
			CommandLineInterface pwmanager = new CommandLineInterface();
			pwmanager.start(database);			
		}
		else {
			System.out.println("Unable to connect to given database in given arguments.");
			System.out.println("USAGE: java -jar password_manager.jar <url> <user> <password>");
			System.out.println("Or use optional config file.");
		}
		
	}

}


