package na.services.shoppinglist.business;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShoppingData {
	private static Log log = LogFactory.getLog(ShoppingData.class);
	private static final String filename = na.utils.ServiceInterface.PATH_SHOPPING_DATA;
	private static final String code_selected = "selected";
	private static final String code_generated = "generated";
	private static final String code_numDays_selected = "selected_size";
	private static final String code_numDays_generated = "generated_size";

	/**
	 * Gets the number of days selected by the user.
	 * If an error occurs, returns 0
	 * 
	 * @return the number of days selected
	 */
	static public int getNumberOfDaysSelected() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String numDays = properties.getProperty(code_numDays_selected);
		    log.info("NumDays: "+numDays);
		    if (numDays==null) {
		    	return 0;
		    } else {
		    	return Integer.parseInt(numDays);
		    }
		} catch (IOException e) {
			log.error("Error, couldn't read file: "+e.toString());
		}
		
		return 0;
	}
	
	
	public static Calendar getFirstSelectedDay() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String selectedDay = properties.getProperty(code_selected);
//		    log.info("Selected day: "+selectedDay);
		    if (selectedDay==null) {
		    	return null;
		    } else {
		    	return Utils.Dates.getCalendarFromStringDate(selectedDay);
		    }
		} catch (IOException e) {
			log.error("Error, couldn't read file: "+e.toString());
		}
		
		return null;
	}
	
	/**
	 * Store the number of days selected to be loaded from the shopping list.
	 * 
	 * @param sizeSelection the size selection
	 * @param date the date
	 * @return true, if successful
	 */
	public static boolean storeSelectionChanges(int sizeSelection, String date) {
		// load file
		Properties properties = new Properties();
	    try {
	    	properties.load(new FileInputStream(filename));
	    	properties.setProperty(code_selected, date);
	    	properties.setProperty(code_numDays_selected, String.valueOf(sizeSelection));
	        properties.store(new FileOutputStream(filename), null);
//	        log.info("Success! File: " + filename + " was edited.");
	    } catch (IOException e) {
	    	log.error("Error, couldn't write file: "+e.toString());
	    	return false;
	    }
		return true;
	}
	
	  //////////////////////////////////////////////
	 /////////////////////////////////////////////
	/////////////////////////////////////////////


	static public int getNumberOfDaysGenerated() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String numDays = properties.getProperty(code_numDays_generated);
//		    log.info("NumDays Generated: "+numDays);
		    if (numDays==null) {
		    	return 0;
		    } else {
		    	return Integer.parseInt(numDays);
		    }
		} catch (IOException e) {
			log.error("Error, couldn't read file: "+e.toString());
		}
		
		return 0;
	}
	
	public static boolean storeGeneratedChanges(int sizeGeneration, String date) {
		// load file
		Properties properties = new Properties();
	    try {
	    	properties.load(new FileInputStream(filename));
	    	properties.setProperty(code_generated, date);
	    	properties.setProperty(code_numDays_generated, String.valueOf(sizeGeneration));
	        properties.store(new FileOutputStream(filename), null);
//	        log.info("Success! File: " + filename + " was edited.");
	    } catch (IOException e) {
	    	log.error("Error, couldn't write file: "+e.toString());
	    	return false;
	    }
		return true;
	}
	

	
	
	public static Calendar getFirstGeneratedDay() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String selectedDay = properties.getProperty(code_generated);
//		    log.info("Generated day: "+selectedDay);
		    if (selectedDay==null) {
		    	return null;
		    } else {
		    	return Utils.Dates.getCalendarFromStringDate(selectedDay);
		    }
		} catch (IOException e) {
			log.error("Error, couldn't read file: "+e.toString());
		}
		
		return null;
	}
}
