package org.universAAL.AALapplication.nutritional.utils;

import org.universAAL.AALapplication.nutritional.SharedResources;
import org.universAAL.middleware.container.utils.LogUtils;

public class Utils {

    /**
     * Prints a message to the console with a header, useful for following the
     * service output.
     * 
     * @param msg
     *            The message to be printed
     */
    public static void println(String msg) {
	try {
	    LogUtils.logDebug(SharedResources.getMContext(), Utils.class,
		    "Nutritional Advisor", new Object[] { msg }, null);
	} catch (Exception e) {
	    System.out.println("Nutritional Advisor (fallback log): " + msg);
	}
    }

}
