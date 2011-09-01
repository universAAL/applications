package na.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginManager {
	private static Log log = LogFactory.getLog(LoginManager.class);
	private static final String filename = na.utils.ServiceInterface.PATH_USER_FILE;
	private static final String USERNAME_KEY = "username";
	private static final String PASSWORD_KEY = "password";
	private static final String TIMEZONE_KEY = "timezone";
	
	private static final String DEFAULT_Nutri_USERNAME = "David_Shopland"; //"itaca_user";
	private static final String DEFAULT_PASSWORD = "5a019bc5dad45f1652c141bc9004598b";
//	private static final String DEFAULT_TIMEZONE = "Europe/Madrid";
	private static String USER_TIMEZONE = null;
	
	static public String getNutriUsername() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String username = properties.getProperty(USERNAME_KEY);
		    if (username==null) {
//		    	return null;
		    	log.error("nutri user is null, using default username: "+ DEFAULT_Nutri_USERNAME);
				return DEFAULT_Nutri_USERNAME;
		    } else {
		    	log.info("Using username: "+username);
		    	return username;
		    }
		} catch (IOException e) {
			log.error("Error, couldn't read file: "+e.toString());
			log.error("using default username: "+ DEFAULT_Nutri_USERNAME);
			return DEFAULT_Nutri_USERNAME;
		}
	}
	
	static public String getNutriPassword() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String username = properties.getProperty(PASSWORD_KEY);
//		    log.info("password from file: "+filename + " field: "+ PASSWORD + " value: "+username);
		    if (username==null) {
		    	log.error("nutri password is null, using default password");
				return DEFAULT_PASSWORD;
		    } else {
		    	return username;
		    }
		} catch (IOException e) {
			log.error("Error, couldn't read file: "+e.toString());
			log.error("using default password");
			return DEFAULT_PASSWORD;
		}
	}
	
	static protected String getTimeZone() {
		if (LoginManager.USER_TIMEZONE != null)
			return LoginManager.USER_TIMEZONE; 
		// load file
		Properties properties = new Properties();
		String myTimezone = Calendar.getInstance().getTimeZone().getDisplayName();
//		TimeZone cetTime = TimeZone.getTimeZone("CET");
//		String myTimezone = cetTime.getDisplayName();
		try {
		    properties.load(new FileInputStream(filename));
		    String timezone = properties.getProperty(TIMEZONE_KEY);
		    if (timezone==null) {
//		    	Calendar cal = Calendar.getInstance();
//		    	String myTimezone = cal.getTimeZone().getDisplayName();
		    	
		    	log.info("Couldn't find users timezone preference. Using system default: "+myTimezone);
		    	LoginManager.USER_TIMEZONE = myTimezone;
		    	return myTimezone;
		    } else {
		    	return timezone;
		    }
		} catch (IOException e) {
			log.info("Error, couldn't read file: "+e.toString());
//	    	Calendar cal = new GregorianCalendar();
//	    	String myTimezone = cal.getTimeZone().getDisplayName();
	    	log.info("Couldn't find users timezone preference. Using system default: "+myTimezone);
	    	LoginManager.USER_TIMEZONE = myTimezone;
	    	return myTimezone;
		}
		
	}

	public static int getLanguage(String lang) {
		if (lang.compareTo("ES")==0)
			return 1;
		if (lang.compareTo("EN")==0 || lang.compareTo("GB")==0)
			return 2;
		if (lang.compareTo("BG")==0)
			return 7;
		if (lang.compareTo("RO")==0)
			return 8;
		// english by default
		return 2;
	}
	
	

}
