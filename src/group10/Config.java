package group10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads config file for starting database connection.
 * @author aseba
 *
 */
public class Config {
	
	public String user;
	public String password;
	public String url;
	String fileName;
	
	//property tags in config file
	final String USER = "user";
	final String PASSWORD = "password";
	final String URL = "url";
	
	/**
	 * Reads the config file and saves the user, password, and database url.
	 * If no config is found it writes a template blank config file.
	 * @param fileName
	 */
	public Config(String fileName) {
		this.fileName = fileName;
		
		try {
			File configFile = new File(fileName);
			if(configFile.createNewFile()) {
				createEmptyConfig(configFile);
			}
			else {
				readConfig();

			}
		} catch(IOException e) {
			System.out.println("An Error occurred.");
			e.printStackTrace();
		}
		
		

	}
	
	void readConfig() {
		try {
			Properties prop = new Properties();
			FileInputStream reader = new FileInputStream(fileName);
			prop.load(reader);
			user = prop.getProperty(USER);
			password = prop.getProperty(PASSWORD);
			url = prop.getProperty(URL);
			reader.close();
			
		} catch(Exception ex) {
			System.out.println("Problem reading from config.");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates an empty config file just in case the build folder doesn't have one.
	 * @param file
	 */
	void createEmptyConfig(File file) {
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(USER + "=username\n" + PASSWORD + "=password\n" + URL + "=url\n");
			System.out.println("Use command line arguments <url> <user> <password> or use config file in current directory.");
		} catch(IOException e) {
			System.out.println("Error writing config file.");
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
