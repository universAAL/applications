package nna.utils;

import nna.SharedResources;

import org.universAAL.middleware.container.utils.LogUtils;

public class Utils {

	
	
	
	/**
	 * Prints a message to the console with a header, useful for following the service output.
	 *
	 * @param msg The message to be printed
	 */
	public static void println(String msg) {
	    
	    LogUtils.logDebug(SharedResources.moduleContext, Utils.class, "Nutritional Advisor", new Object[]{msg},null);
	    
//		System.out.println("NutriUI: "+msg);
	}
	
	
	
}
