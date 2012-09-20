package org.universAAL.AALfficiency;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;


public class Activator implements BundleActivator {
    public static BundleContext osgiContext = null;
    public static  ModuleContext context = null;

	public static final AALfficiencyProvider scallee=null;


    public void start(BundleContext bcontext) throws Exception {
    	System.out.print("Starting AALfficiency Service");
	Activator.osgiContext = bcontext;
	Activator.context = uAALBundleContainer.THE_CONTAINER
		.registerModule(new Object[] { bcontext });
		//scallee=new AALfficiencyProvider(context);
		new Thread() {
		    public void run() {
			new AALfficiencyProvider(context);
			new ElectricityDataConsumer(context);
		    }
		}.start();

    }

    public void stop(BundleContext arg0) throws Exception {
    	System.out.print("Stopping AALfficiency Service");
		scallee.close();

    }

}
