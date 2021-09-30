package group10;

import java.io.File;
import java.io.FileInputStream;
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
	
	//property tags in config file
	final String USER = "user";
	final String PASSWORD = "password";
	final String URL = "url";
	
	/**
	 * Reads from config file. if no file found it will create one for the next run.
	 * @param fileName
	 * @return Returns true if found and read from config. Else returns false.
	 */
	public boolean readConfig(String fileName) {
		
		//Try reading from config file
		try {
			File configFile = new File(fileName);
			if(configFile.exists()) {
				Properties prop = new Properties();
				FileInputStream reader = new FileInputStream(fileName);
				prop.load(reader);
				user = prop.getProperty(USER);
				password = prop.getProperty(PASSWORD);
				url = prop.getProperty(URL);
				reader.close();		
				return true;
			}
		} catch(Exception ex) {
			System.out.println("Problem reading from config.");
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Creates an empty config file just in case the build folder doesn't have one.
	 * @param file
	 */
	public void createEmptyConfig(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(USER + "=username\n" + PASSWORD + "=password\n" + URL + "=url\n");
			writer.close();
		} catch(IOException e) {
			System.out.println("Error writing config file.");
			e.printStackTrace();
		}
		
	}
	
	
}
