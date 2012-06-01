package na.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class InitialSetup {
	
	private static Log log = LogFactory.getLog(InitialSetup.class);
	private static final String OASIS_PATH_KEY = "oasis_path";
	private static final String OASIS_PATH_VALUE = "C:\\OASIS\\";
	
	private static final String NUTRITIONAL_FOLDER_KEY = "nutritional_folder";
	private static final String NUTRITIONAL_FOLDER_VALUE = "NutritionalAdvisor";
	
	protected static final String WEB_SERVER_IP_ADDRESS_KEY = "web_server_ip_address";
	private static final String WEB_SERVER_IP_ADDRESS_VALUE = "158.42.166.200"; 
	
	protected static final String WEB_SERVER_PORT_KEY = "web_server_port"; 
	private static final String WEB_SERVER_PORT_VALUE = "8080";
	
	protected static final String WEB_SERVER_SERVICE_ADDRESS_KEY = "web_server_service_address";
	private static final String WEB_SERVER_SERVICE_ADDRESS_VALUE = "NutriAdvisor_uAAL/NutritionalAdvisor";
	
	protected static final String MAXIMIZE_WINDOW_KEY = "maximize_window";
	private static final String MAXIMIZE_WINDOW_VALUE = "false";
	
	protected static final String SCREENSAVER_ENABLED_KEY = "screensaver_enabled";
	private static final String SCREENSAVER_ENABLED_VALUE = "true";

	protected static final String SCREENSAVER_DELAY_SECONDS_KEY = "screensaver_delay_second";
	private static final String SCREENSAVER_DELAY_SECONDS_VALUE = "90";
	
	protected static final String CHECK_ADVISES_DELAY_MINUTES_KEY = "check_advises_delay_minutes";
	private static final String CHECK_ADVISES_DELAY_MINUTES_VALUE = "60";

	protected static final String LANGUAGE_INTERFACE_KEY = "language_interface";
	private static final String LANGUAGE_INTERFACE_VALUE = "EN";
	
	protected static final String AVOID_AMI_KEY = "avoid_ami";
	private static final String AVOID_AMI_VALUE = "yes";
	
	protected static final String DOWNLOAD_PROFILE_ON_START = "download_profile_on_start";
	private static final String DOWNLOAD_PROFILE_ON_START_VALUE = "yes";
	
	protected static final String SHOW_EMTPY_CACHE_KEY = "empty_cache";
	private static final String SHOW_EMPTY_CACHE_VALUE = "no";
	
//	protected static final String AMI_USERID_KEY = "ami_user_id";
//	private static final String AMI_USERID_VALUE = "itaca_01";
	
	protected static final String AMI_USERNAME_KEY = "ami_user_name";
	public static final String AMI_USERNAME_VALUE = "David_Shopland"; // "itaca_user"
	
	protected static final String MAX_TEMPERATURE_KEY = "max_temperature";
	private static final String MAX_TEMPERATURE_VALUE = "25";
	
	protected static final String TSF_KEY = "TSF_active";
	protected static final String TSF_VALUE = "yes";
	
	protected static final String SOCIAL_COMMUNITI_VIA_WEB_KEY = "social_comunity_use_web_services";
	protected static final String SOCIAL_COMMUNITI_VIA_WEB_VALUE = "no";
	public static final String USE_SCHEDULER_KEY = "scheduler_active";
	public static final String USE_SCHEDULER_VALUE = "yes";
	
	public static final String PrinterConf_KEY = "printer_conf_file";
	public static final String PrinterConf_VALUE = "C:/Program Files/EPSON/JavaPOS/SetupPOS/jpos.xml";
	
	public static final String TOKEN_KEY = "token";
	public static final String TOKEN_VALUE = "";
	
	/**
	 * Inits the nutritional advisor folder.
	 * Creates ./NutritionalAdvisor
	 * and some needed files in it
	 */
	public static void initNutriAdvisorFolder() {
		log.info("Checking for nutritonal folder...");
		//check if base dir is readable
		File file=new File(ServiceInterface.DIR_USER_PROFILE+"\\");
		if (!file.canRead()) {
			log.fatal("directory: "+ file.getAbsolutePath() + " is not readable!");
		} else if (!file.canWrite()) {
			log.fatal("directory: "+ file.getAbsolutePath() + " is not writable!");
		} else {
			//check if NA directory exists
			String ruta = ServiceInterface.DIR_NUTRITIONAL;
			file=new File(ruta); //TODO
		    if (!file.exists()) {
		    	log.warn("File: " + file.getAbsolutePath() + " does not exist. Creating it...");
		    	// Create NA directory
		        boolean success = (new File(ruta)).mkdirs();
		        if (success) {
		          log.info("Directory: " + ruta + " created");
		          // create basic files
		          InitialSetup.createBasicFiles();
		          InitialSetup.createSetupPropertyFile();
		        } else {
		        	log.fatal("Couldn't create Nutrional Advisor folder: "+ ruta);
		        }
		      
		    }else{
		    	log.info("File: " + file.getAbsolutePath() + " does exist");
		    	// create basic files
		    	InitialSetup.createBasicFiles();
		    	InitialSetup.createSetupPropertyFile();
		    	log.info("Files created");
		    }
		}
	}
	
	static private void createSetupPropertyFile() {
		File file = new File(ServiceInterface.DIR_NUTRITIONAL_ROOT + ServiceInterface.SETUP_FILENAME);
		if (!file.exists()) {
			log.warn("Setup file is not present, creating one with default settings");
			// create file
			boolean created = InitialSetup.createFile(ServiceInterface.SETUP_FILENAME, ServiceInterface.DIR_NUTRITIONAL_ROOT);
			if (created) {
				InitialSetup.createProperty(file.getAbsolutePath(), OASIS_PATH_KEY, OASIS_PATH_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), NUTRITIONAL_FOLDER_KEY, NUTRITIONAL_FOLDER_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), WEB_SERVER_IP_ADDRESS_KEY, WEB_SERVER_IP_ADDRESS_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), WEB_SERVER_PORT_KEY, WEB_SERVER_PORT_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), WEB_SERVER_SERVICE_ADDRESS_KEY, WEB_SERVER_SERVICE_ADDRESS_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), MAXIMIZE_WINDOW_KEY, MAXIMIZE_WINDOW_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), SCREENSAVER_ENABLED_KEY, SCREENSAVER_ENABLED_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), SCREENSAVER_DELAY_SECONDS_KEY, SCREENSAVER_DELAY_SECONDS_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), CHECK_ADVISES_DELAY_MINUTES_KEY, CHECK_ADVISES_DELAY_MINUTES_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), LANGUAGE_INTERFACE_KEY, LANGUAGE_INTERFACE_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), AVOID_AMI_KEY, AVOID_AMI_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), DOWNLOAD_PROFILE_ON_START, DOWNLOAD_PROFILE_ON_START_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), SHOW_EMTPY_CACHE_KEY, SHOW_EMPTY_CACHE_VALUE);
//				InitialSetup.createProperty(file.getAbsolutePath(), AMI_USERID_KEY, AMI_USERID_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), AMI_USERNAME_KEY, AMI_USERNAME_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), MAX_TEMPERATURE_KEY, MAX_TEMPERATURE_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), TSF_KEY, TSF_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), SOCIAL_COMMUNITI_VIA_WEB_KEY, SOCIAL_COMMUNITI_VIA_WEB_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), USE_SCHEDULER_KEY, USE_SCHEDULER_VALUE);
				InitialSetup.createProperty(file.getAbsolutePath(), PrinterConf_KEY, PrinterConf_VALUE);
			} else {
				log.fatal("Couldn't create setup file: "+ServiceInterface.DIR_NUTRITIONAL_ROOT + ServiceInterface.SETUP_FILENAME);
			}
			
		} else { // assume atributes are in place
			
		}
	}
	
	static private void createBasicFiles() {
		InitialSetup.createFile(ServiceInterface.EXTRA_SHOPPING_ITEMS_FILENAME, ServiceInterface.DIR_NUTRITIONAL);
		InitialSetup.createFile(ServiceInterface.FOOD_INVENTORY_FILENAME, ServiceInterface.DIR_NUTRITIONAL);
		InitialSetup.createFile(ServiceInterface.SHARED_RECIPES_FILENAME, ServiceInterface.DIR_NUTRITIONAL);
		InitialSetup.createFile(ServiceInterface.SHOPPING_DATA_FILENAME, ServiceInterface.DIR_NUTRITIONAL);
		InitialSetup.createFile(ServiceInterface.ADVISE_REPOSITORY_FILENAME, ServiceInterface.DIR_NUTRITIONAL);
//		InitialSetup.createFile(ServiceInterface.SETUP_FILENAME, ServiceInterface.DIR_NUTRITIONAL);
		InitialSetup.createFile(ServiceInterface.FILENAME_USER_PROP, ServiceInterface.DIR_NUTRITIONAL);
		InitialSetup.createFile(ServiceInterface.TIP_File, ServiceInterface.DIR_NUTRITIONAL);
		// chache folder
		InitialSetup.createDir(ServiceInterface.CACHE_FOLDER_name, ServiceInterface.DIR_NUTRITIONAL);
		// downloaded images
		InitialSetup.createDir(ServiceInterface.DIR_IMAGES, ServiceInterface.DIR_NUTRITIONAL);
		// tips images
		InitialSetup.createDir(ServiceInterface.TIP, ServiceInterface.DIR_IMAGES_fulldir);
		// dish images
		InitialSetup.createDir(ServiceInterface.DISH, ServiceInterface.DIR_IMAGES_fulldir);
	}
	
	/*
	 * Crea un fichero en path si NO existe previamente
	 */
	static private boolean createFile(String name, String path) {
		File file = new File(path + name);
		if(!file.exists()){
	        try {
				boolean created = file.createNewFile();
				if (created) {
					log.info("File: "+path+name+ " created successfuly");
					return true;
				} else {
					log.warn("Couldn't create file: "+path+name);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.warn("Couldn't create file: "+path+name);
			}
		} else {
			return true;
		}
		return false;
	}
	
	/*
	 * Crea un directorio en path si NO existe previamente
	 */
	static private boolean createDir(String dirName, String path) {
		File file = new File(path + dirName);
		if(!file.exists()){
        	boolean success = (new File(path+dirName)).mkdir();
	        if (success) {
				log.info("File: "+path+dirName+ " created successfuly");
				return true;
			} else {
				log.warn("Couldn't create dir: "+path+dirName);
			}
		} else {
			return true;
		}
		return false;
	}
	
	static private boolean createProperty(String filename, String propertyName, String value) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(filename));
			properties.setProperty(propertyName, value);
			properties.store(new FileOutputStream(filename), null);
	        log.info("Success! File: " + filename + " edited.");
	        return true;
		} catch (FileNotFoundException e) {
			log.error("file not found: "+filename);
		} catch (IOException e) {
			log.error("file exception on "+filename);
		}
		return false;
	}
}
