package group10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	

	public Config(String fileName) {
		this.fileName = fileName;
		Properties prop = new Properties();
		
		
		try {
			FileInputStream reader = new FileInputStream(fileName);
			prop.load(reader);
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
			reader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("File not found.");
			System.exit(0);
		}
		catch(IOException ex) {
			System.out.println("Unable to load config file.");
			System.exit(0);
		}
		

	}
	
	
	
	
}
