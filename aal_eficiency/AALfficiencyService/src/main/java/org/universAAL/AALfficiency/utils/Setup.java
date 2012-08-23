package org.universAAL.AALfficiency.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {
public static final String SETUP_FILENAME = "AALfficiencyService.properties";
	
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
		
	
	public int gettotalScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("totalScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 0;
	}
	
	public int gettodayScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("todayScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 0;
	}
	public int gettotalElectricScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("totalElectricScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 0;
	}
	public int gettodayElectricScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("todayElectricScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 0;
	}
	public int gettotalActivityScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("totalActivityScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 0;
	}
	public int gettodayActivityScore(){
		Properties properties = new Properties();
		try {
			String setup = getSetupFileName();
//			System.out.println("setup is in: "+setup);
		    properties.load(new FileInputStream(setup));
		    String url = properties.getProperty("todayActivityScore");
		    if (url==null) {
		    	return 0;
		    } else {
		    	return new Integer(url).intValue();
		    }
		} catch (IOException e) {
		}
		
		return 0;
	}
}
