package group10;


import java.util.Scanner;



public class Main {
	
	static String configFile = "config.properties";
	
	public static void main(String args[]) {
		
		Database database = new Database();
		
		if(args.length > 0) {
			if(args.length < 3) {
				System.out.println("USAGE: <url> <user> <password>");
				System.exit(0);
			}
			else {
				database.connect(args[0], args[1], args[2]);
			}
		}
		else {
			//read from config
			Config config = new Config(configFile);
			//Connect to database
			if(config.url != null)
				database.connect(config.url, config.user, config.password);
			else
				System.exit(0);
		}
		
		//start doing the command line interface menus
		CommandLineInterface pwmanager = new CommandLineInterface();
		pwmanager.start(database);
		
		database.closeResources();
		
	}

}


