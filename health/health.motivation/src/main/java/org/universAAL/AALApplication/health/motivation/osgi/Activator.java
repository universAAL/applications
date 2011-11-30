package org.universAAL.AALApplication.health.motivation.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALApplication.health.motivation.MotivationManager;

public class Activator implements BundleActivator{
	public static BundleContext context=null;

	public void start(BundleContext arg0) throws Exception {
		MotivationManager.start(arg0);
		
	}

	public void stop(BundleContext arg0) throws Exception {
		MotivationManager.stop(arg0);		
	}
	



}
