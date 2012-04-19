package org.universAAL.AALApplication.health.motivation.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALApplication.health.motivation.MotivationManager;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator{
	public void start(BundleContext arg0) throws Exception {
		MotivationManager.start(uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { arg0 }));
		
	}

	public void stop(BundleContext arg0) throws Exception {
		MotivationManager.stop(uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { arg0 }));		
	}
	



}
