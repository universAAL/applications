package org.universAAL.AALapplication.db.config;

import org.universAAL.AALapplication.db.config.FileLoader;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;

import java.lang.System;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Properties properties;

	//static final String CONFIGURATION_DIRECTORY = getConfigurationDirectory();
	static final String CONFIGURATION_FILE = getConfigurationFile();
	
	public Configuration(){
	}

	private static void initConfiguration() {
		//configuration should only be initialized once
		if (properties != null){
			System.out.println("Configuration ALREADY initialized!");
			return;
		}

		try {
			System.out.println("try to initialize configuration");
			properties = new Properties();

		    String curDir = System.getProperty("user.dir");
			System.out.println("*** "+curDir);
			
			File confHome = new File(new BundleConfigHome("safety_home").getAbsolutePath());
			System.out.println("*** "+confHome.getAbsolutePath());
	        properties.load(new FileInputStream(new File(confHome,"safety_home.properties")));
            System.out.println("properties initialized ok");
			
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
			throw new RuntimeException("configuration file not found");
		} catch (IOException e) {
			throw new RuntimeException("error reading configuration file");
		}
		System.out.println(properties.keys());
	}

	public static String get(String property) {
		if (properties == null){
			initConfiguration();
		}
		return (String) properties.get(property);	
	}

	public static String getConfigurationFile(){
		//dynamically load file
		String file = null;
		//try {
			//file = FileLoader.getResourcePath("portal.properties", Class.forName("org.universAAL.AALapplication.food_shopping.service.db.config.Configuration"));
			file = "./food.properties";
		//} catch (ClassNotFoundException e) {	e.printStackTrace(); }
		return file;
	}

	public static String getConfigurationDirectory() {
		System.out.println("configuration directory");
		
        String OS = System.getProperty("os.name").toLowerCase();
        
        if (OS.indexOf("nt") > -1 || OS.indexOf("windows") > -1){
            return "C:/food/conf/";
        }
        else 
            return "/opt/portal/tomcat/food/conf/";
        
	}
}
