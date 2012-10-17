package org.universAAL.FitbitPublisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.FitbitPublisher.FitbitAPI.FitbitService;
import org.universAAL.FitbitPublisher.database.FitbitDBInterface;
import org.universAAL.FitbitPublisher.utils.Setup;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {
    public static BundleContext osgiContext = null;
    public static ModuleContext context = null;
    
    public static Setup s = new Setup();
    
    public static FitbitDBInterface db = new FitbitDBInterface();

    public void start(BundleContext bcontext) throws Exception {
	Activator.osgiContext = bcontext;
	Activator.context = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { bcontext });
	
	db.createDB();
	
	Timer t1 = new Timer();
	t1.scheduleAtFixedRate(new FitbitPublisher(bcontext), getTime(), 1000*60*60*24);
	
		
    }

    public void stop(BundleContext arg0) throws Exception {
    }

    public static Date getTime(){
    	 Calendar tomorrow = new GregorianCalendar();
         tomorrow.add(Calendar.DATE, 0);
         Calendar result = new GregorianCalendar(
           tomorrow.get(Calendar.YEAR),
           tomorrow.get(Calendar.MONTH),
           tomorrow.get(Calendar.DATE),
           13,
           33
         );
         return result.getTime();
    }
    
}
