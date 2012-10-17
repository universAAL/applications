package org.universAAL.FitbitPublisher.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {

	public static final String SETUP_FILENAME = "FitbitPublisher.properties";
	
	private static String setupFileName = null;
	
	static public String getSetupFileName() {
		if (setupFileName != null) {
			return setupFileName;
		}
		File dir1 = new File(".");
		try {
			setupFileName = "C:\\AALfficiency\\"+SETUP_FILENAME;
            System.out.println("Fichero: "+setupFileName);
			return setupFileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAccessTokenSecret(){
		
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String user = properties.getProperty("access_token_secret");
		    return user;
		} catch (IOException e) {
		}
		return null;
	}
	
	public String getAccessToken(){
		
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String user = properties.getProperty("access_token");
		    return user;
		} catch (IOException e) {
		}
		return null;
	}
	
	public void setAccessToken(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("access_token",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setAccessTokenSecret(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("access_token_secret",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPin(){
		
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String user = properties.getProperty("pin");
		    return user;
		} catch (IOException e) {
		}
		return null;
	}
	
	public void setPin(String t){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    properties.put("pin",t);
		    properties.store(new FileOutputStream(setup), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public String getDBUser(){
		
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String user = properties.getProperty("db_user");
		    return user;
		} catch (IOException e) {
		}
		return null;
	}
	
	public String getDBPwd(){
		
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String user = properties.getProperty("db_pwd");
		    return user;
		} catch (IOException e) {
		}
		return null;
	}
	
	public int getElectricityChallengeDays(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("days_duration_activity_challenge");
		    if (url==null) {
		    	return 7;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 7;
	}
	
}


