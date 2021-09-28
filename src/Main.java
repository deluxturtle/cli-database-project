
import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		
		String configFile = "config.properties";
		
		//read from config
		Config config = new Config(configFile);
		
		//Connect to database
		Database database = new Database(config.url, config.user, config.password);
		
		CommandLineInterface pwmanager = new CommandLineInterface();
		pwmanager.start(database);
		
	}

}


