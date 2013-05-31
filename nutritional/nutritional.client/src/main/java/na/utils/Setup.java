package na.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;

public class Setup {
    private static Log log = LogFactory.getLog(Setup.class);
    // default values
    // protected static final String DEFAULT_PATH_Nutritional_DESCRIPTOR =
    // Constants.getSpaceConfRoot() + "/NutritionalAdvisor";
    private static String absolutePath = new BundleConfigHome(
	    "nutritional.client").getAbsolutePath();

    protected static final String DEFAULT_PATH_Nutritional_DESCRIPTOR = absolutePath
	    + "/NutritionalAdvisor";
    private static final int DEFAULT_DOWNLOAD_ADVICES_EVERY_MINUTES = 60;
    private static final int START_SCREENSAVER_DELAY_IN_SECONDS = 10;
    // private static final String AMI_UserID = "itaca_01";
    public static String AMI_UserName = null;
    private static final String DEFAULT_LANGUAGE = "EN";
    private static final double DEFAULT_MAX_TEMPERATURE_VALUE = 25; // same as
								    // InitialSetup.max_temp_value!!
    private static String useScheduler = null;

    private static String WEB_SERVICE_ADDRESS = null;
    private static String setupFileName = null;
    private static String use_WS_toConnectToSocialCommunities = null;
    private static String token = null;
    private static int wait_to_download_advices = -1;

    static private String getSetupFileName() {
	File file = new File(ServiceInterface.DIR_NUTRITIONAL_ROOT + "\\"
		+ ServiceInterface.SETUP_FILENAME);
	// String setupFileName = file.getAbsolutePath() + "\\"+
	// ServiceInterface.DIR_NUTRITIONAL_ROOT
	// +"\\"+ServiceInterface.SETUP_FILENAME;
	String setupFileName = file.getAbsolutePath();
	return setupFileName;
	// if (setupFileName != null) {
	// return setupFileName;
	// }
	// File dir1 = new File(".");
	// try {
	// log.info("Current dir : " + dir1.getCanonicalPath());
	// // return dir1.getCanonicalPath()+"\\..\\setup.properties";
	// // return
	// dir1.getCanonicalPath()+"\\..\\"+ServiceInterface.SETUP_FILENAME;
	// // setupFileName =
	// dir1.getCanonicalPath()+"\\NutritionalAdvisor\\"+ServiceInterface.SETUP_FILENAME;
	// // setupFileName = dir1.getCanonicalPath()+ "\\" +
	// ServiceInterface.DIR_NUTRITIONAL_ROOT
	// +"\\"+ServiceInterface.SETUP_FILENAME;
	// setupFileName = ServiceInterface.DIR_NUTRITIONAL_ROOT
	// +"\\"+ServiceInterface.SETUP_FILENAME;
	// return setupFileName;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
    }

    static public String getServerIP() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String url = properties
		    .getProperty(InitialSetup.WEB_SERVER_IP_ADDRESS_KEY);
	    if (url == null) {
		log.info("Couldn't find setup.properties file at: " + setup);
		return null;
	    } else {
		log.info("Using server IP: " + url);
		return url;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return null;
    }

    static public String getWebServiceAddress() {
	if (Setup.WEB_SERVICE_ADDRESS != null)
	    return Setup.WEB_SERVICE_ADDRESS;
	else {
	    // load file
	    Properties properties = new Properties();
	    try {
		String setup = getSetupFileName();
		properties.load(new FileInputStream(setup));
		String server_ip = properties
			.getProperty(InitialSetup.WEB_SERVER_IP_ADDRESS_KEY);
		String server_port = properties
			.getProperty(InitialSetup.WEB_SERVER_PORT_KEY);
		String server_webService_address = properties
			.getProperty(InitialSetup.WEB_SERVER_SERVICE_ADDRESS_KEY);
		if ((server_ip == null) || (server_port == null)
			|| (server_webService_address == null)) {
		    log.info("Couldn't get web server properties. Found: ServerIP: "
			    + server_ip
			    + " ServerPort: "
			    + server_port
			    + " webServiceName: " + server_webService_address);
		} else {
		    String result = "http://" + server_ip + ":" + server_port
			    + "/" + server_webService_address;
		    log.info("Connecting to: " + result);
		    Setup.WEB_SERVICE_ADDRESS = result;
		    return result;
		}
	    } catch (IOException e) {
		log.error("Error, couldn't read file: " + e.toString());
	    }
	}

	return null;
    }

    public static boolean isMaximizedActive() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String url = properties
		    .getProperty(InitialSetup.MAXIMIZE_WINDOW_KEY);
	    if (url == null) {
		log.info("Couldn't find setup.properties file at: " + setup);
		return false;
	    } else {
		log.info("Maximize window: " + url);
		if (url.compareTo("true") == 0)
		    return true;
		else
		    return false;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    public static boolean isScreenSaverActive() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String url = properties
		    .getProperty(InitialSetup.SCREENSAVER_ENABLED_KEY);
	    if (url == null) {
		log.info("Couldn't find setup.properties file at: " + setup);
		return false;
	    } else {
		log.info("Screensaver enabled: " + url);
		if (url.compareTo("true") == 0)
		    return true;
		else
		    return false;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    public static int getAdvisesDelayFromServer() {
	if (Setup.wait_to_download_advices != -1)
	    return Setup.wait_to_download_advices;
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    // log.info("setup is in: "+setup);
	    properties.load(new FileInputStream(setup));
	    String url = properties
		    .getProperty(InitialSetup.CHECK_ADVISES_DELAY_MINUTES_KEY);
	    if (url == null) {
		log.info("Couldn't find check_advises_delay_minutes in setup.properties file: "
			+ setup);
		return DEFAULT_DOWNLOAD_ADVICES_EVERY_MINUTES;
	    } else {
		log.info("--------------------> Advises delay to ask server: "
			+ url);
		Setup.wait_to_download_advices = new Integer(url).intValue() * 60;
		return new Integer(url).intValue() * 60;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return DEFAULT_DOWNLOAD_ADVICES_EVERY_MINUTES;
    }

    public static double getMaxTemperatureAllowed() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    // log.info("setup is in: "+setup);
	    properties.load(new FileInputStream(setup));
	    String url = properties
		    .getProperty(InitialSetup.MAX_TEMPERATURE_KEY);
	    if (url == null) {
		log.info("Couldn't find max_temp in setup.properties file: "
			+ setup);
		return DEFAULT_MAX_TEMPERATURE_VALUE;
	    } else {
		log.info("--------------------> Advises delay to ask server: "
			+ url);
		return new Double(url).doubleValue();
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return DEFAULT_MAX_TEMPERATURE_VALUE;
    }

    public static int getScreensaverDelay() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String url = properties
		    .getProperty(InitialSetup.SCREENSAVER_DELAY_SECONDS_KEY);
	    if (url == null) {
		log.info("Couldn't find setup.properties file at: " + setup);
		return START_SCREENSAVER_DELAY_IN_SECONDS;
	    } else {
		log.info("Screensaver delay: " + url);
		return new Integer(url).intValue();
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}
	return START_SCREENSAVER_DELAY_IN_SECONDS;
    }

    public static String getLanguage() {
	// return "BU";
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String lang = properties
		    .getProperty(InitialSetup.LANGUAGE_INTERFACE_KEY);
	    if (lang != null)
		return lang;
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}
	log.info("Default language");
	return DEFAULT_LANGUAGE;
    }

    static public boolean getAvoidOASIS_AMI() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String value = properties.getProperty(InitialSetup.AVOID_AMI_KEY);
	    if (value == null) {
		return false;
	    } else {
		if (value.compareTo("yes") == 0)
		    return true;
		else
		    return false;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    public static boolean getShowEmptyCache() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String value = properties
		    .getProperty(InitialSetup.SHOW_EMTPY_CACHE_KEY);
	    if (value == null) {
		return false;
	    } else {
		if (value.compareTo("yes") == 0)
		    return true;
		else
		    return false;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    // public static String getAMI_UserID() {
    // // load file
    // Properties properties = new Properties();
    // try {
    // String setup = getSetupFileName();
    // properties.load(new FileInputStream(setup));
    // String id = properties.getProperty(InitialSetup.AMI_USERID_KEY);
    // if (id!=null)
    // return id;
    // } catch (IOException e) {
    // log.error("Error, couldn't read file: "+e.toString());
    // }
    // log.info("Default Ami user ID");
    // return AMI_UserID;
    // }

    public static String getAMI_UserName() {
	if (AMI_UserName != null)
	    return AMI_UserName;
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String id = properties.getProperty(InitialSetup.AMI_USERNAME_KEY);
	    if (id != null) {
		AMI_UserName = id;
		return id;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}
	log.info("Default Ami user name");
	return InitialSetup.AMI_USERNAME_VALUE;
    }

    public static String getPrinterConfFile() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String id = properties.getProperty(InitialSetup.PrinterConf_KEY);
	    if (id != null) {
		return id;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}
	log.info("Default printer conf file at: "
		+ InitialSetup.PrinterConf_VALUE);
	return InitialSetup.PrinterConf_VALUE;
    }

    public static boolean useTSF() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String value = properties.getProperty(InitialSetup.TSF_KEY);
	    if (value == null) {
		return true;
	    } else {
		if (value.compareTo("yes") == 0)
		    return true;
		else
		    return false;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return true;
    }

    static public boolean download_profile_on_start() {
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String value = properties
		    .getProperty(InitialSetup.DOWNLOAD_PROFILE_ON_START);
	    if (value == null) {
		return false;
	    } else {
		if (value.compareTo("yes") == 0)
		    return true;
		else
		    return false;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    static public boolean use_WS_toConnectToSocialCommunities() {
	if (Setup.use_WS_toConnectToSocialCommunities != null) {
	    if (Setup.use_WS_toConnectToSocialCommunities.compareTo("yes") == 0) {
		return true;
	    } else
		return false;
	}
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String value = properties
		    .getProperty(InitialSetup.SOCIAL_COMMUNITI_VIA_WEB_KEY);
	    if (value == null) {
		log.info("Social using web, unknown. Not for the moment");
		Setup.use_WS_toConnectToSocialCommunities = "no";
		return false;
	    } else {
		if (value.compareTo("yes") == 0) {
		    log.info("Social using web: YES");
		    Setup.use_WS_toConnectToSocialCommunities = "yes";
		    return true;
		} else {
		    log.info("Social NOT using web");
		    return false;
		}
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    static public boolean use_Scheduler() {
	if (Setup.useScheduler != null) {
	    if (Setup.useScheduler.compareTo("yes") == 0) {
		return true;
	    } else
		return false;
	}
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String value = properties
		    .getProperty(InitialSetup.USE_SCHEDULER_KEY);
	    if (value == null) {
		log.info("Unknown value. Using scheduler");
		Setup.useScheduler = "yes";
		return true;
	    } else {
		if (value.compareTo("yes") == 0) {
		    log.info("Social using web: YES");
		    Setup.useScheduler = "yes";
		    return true;
		} else {
		    Setup.useScheduler = "no";
		    log.info("Social NOT using web");
		    return false;
		}
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}

	return false;
    }

    public static String getCachedToken() {
	if (Setup.token != null)
	    return token;
	// load file
	Properties properties = new Properties();
	try {
	    String setup = getSetupFileName();
	    properties.load(new FileInputStream(setup));
	    String id = properties.getProperty(InitialSetup.TOKEN_KEY);
	    if (id != null) {
		log.info("Found token: " + id);
		return id;
	    }
	} catch (IOException e) {
	    log.error("Error, couldn't read file: " + e.toString());
	}
	return null;
    }

    public static void storeToken(String token2) {
	Properties properties = new Properties();
	try {
	    properties.load(new FileInputStream(Setup.getSetupFileName()));
	    properties.setProperty(InitialSetup.TOKEN_KEY, token2);
	    properties.store(new FileOutputStream(Setup.getSetupFileName()),
		    null);
	    log.info("token stored");
	} catch (IOException e) {
	    log.info("Error, writing token to setup file: " + e.toString());
	}

    }

}
