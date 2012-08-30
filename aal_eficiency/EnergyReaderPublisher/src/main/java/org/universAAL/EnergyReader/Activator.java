package org.universAAL.EnergyReader;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;


public class Activator implements BundleActivator {
    public static BundleContext osgiContext = null;
    public static ModuleContext context = null;
    private Timer t,t1;

    public void start(BundleContext bcontext) throws Exception {
    	System.out.print("Starting publisher\n");
    	t = new Timer();
		//t.schedule(new MinutePublisher(bcontext), 0, 60*1000);
		t1 = new Timer();
		t1.scheduleAtFixedRate(new DailyPublisher(bcontext), get1115am(), 1000*60*60*24);
    }

    public void stop(BundleContext arg0) throws Exception {
    	t.cancel();
    }

    private static Date get1115am(){
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, 0);
        Calendar result = new GregorianCalendar(
          tomorrow.get(Calendar.YEAR),
          tomorrow.get(Calendar.MONTH),
          tomorrow.get(Calendar.DATE),
          9,
          28
        );
        return result.getTime();
      }
    
}
