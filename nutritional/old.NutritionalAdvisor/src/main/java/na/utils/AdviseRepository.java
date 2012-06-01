package na.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AdviseRepository {
	private static boolean started = false;
	private static Log log = LogFactory.getLog(AdviseRepository.class);
	private static final String filename = na.utils.ServiceInterface.PATH_ADVISE_REPOSITORY;
	private static final String code = "advises";
	private static Map<Integer, MiniCalendar> mapAdvises = new HashMap<Integer, MiniCalendar>();
	
	public static boolean addAdvise(int adviseID, MiniCalendar showTime) {
		// load file
		Properties properties = new Properties();
		
		try {
		    properties.load(new FileInputStream(filename));
		    String advises = properties.getProperty(code);
		    String result = "";
		    // there are advises
		    boolean found = false;
		    if (advises != null) {
//		    	log.info("Hay advises previos");
		    	// advise was stored before
//		    	if (mapAdvises.containsKey(adviseID)) {
		    		String[] adviseArray = advises.split("@");
		    		// loop old advises
			    	for (String advise : adviseArray) {
//			    		log.info(" found: "+advise);
			    		String[] parts = advise.split(";");
			    		MiniCalendar m = new MiniCalendar(parts[1]);
			    		// substitue old value on map
			    		mapAdvises.put(new Integer(parts[0]).intValue(), m);
			    		// find current advise
						if (parts[0].compareTo(String.valueOf(adviseID))==0) {
//							log.info("The advise is already stored");
							String show = showTime.day + "-" + showTime.month + "-" + showTime.year+"-"+showTime.hour+"-"+showTime.minute;
							parts[1] = show;
							result += parts[0]+";"+parts[1]+"@";
							found = true;
						} else
							result += advise+"@";
			    	}
		    } 
		    if (advises ==null || found == false) {
		    	String show = showTime.day + "-" + showTime.month + "-" + showTime.year+"-"+showTime.hour+"-"+showTime.minute;
		    	result += ""+adviseID+";"+show+"@";
		    }
//	    	log.info("Storing new advise...");
//	    	String show = showTime.day + "-" + showTime.month + "-" + showTime.year+"-"+showTime.hour+"-"+showTime.minute;
			properties.setProperty(code, result);
		    try {
		        properties.store(new FileOutputStream(filename), null);
		        mapAdvises.put(adviseID, showTime);
		        log.info("Success! File: " + filename + " has one more advise.");
		        return true;
		    } catch (IOException e) {
		    	log.info("Error, couldn't write file: "+e.toString());
		    	return false;
		    }
		    
		} catch (IOException e) {
			log.info("Error, couldn't read file: "+e.toString());
		}
		
		return true;
	}
	
	static public MiniCalendar exists(int adviseID) {
		if (started==false) {
			//init mapping
			Properties properties = new Properties();
			try {
			    properties.load(new FileInputStream(filename));
			    String advises = properties.getProperty(code);
			    if (advises==null) {
			    	log.info("No advises found.");
			    } else {
			    	String[] adviseArray = advises.split("@");
			    	for (String advise : adviseArray) {
			    		String[] parts = advise.split(";");
			    		MiniCalendar m = new MiniCalendar(parts[1]);
			    		// substitue old value on map
			    		mapAdvises.put(new Integer(parts[0]).intValue(), m);
					}
			    }
			} catch (IOException e) {
				log.info("Error, couldn't read file: "+e.toString());
			}			
		}
		started = true;
		if (mapAdvises.containsKey(adviseID)) {
			return (MiniCalendar)mapAdvises.get(adviseID);
		}
		return null;
	}
	
	
}
