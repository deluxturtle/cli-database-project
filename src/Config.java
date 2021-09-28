import java.io.FileInputStream;
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
			FileInputStream stream = new FileInputStream(fileName);
			prop.load(stream);
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
		}
		catch(Exception ex) {
			System.out.println("Unable to load config file.");
		}

	}
	
	
	
	
}
