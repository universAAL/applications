package org.universAAL.AALapplication.food_shopping.service.client;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator {
	
	public static ModuleContext mc;

	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		new Thread() {
			public void run() {
				new FoodManagementClient(mc);
			}
		}.start();
	}

	public void stop(BundleContext context) throws Exception {
	}
}
/*
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.AALapplication.food_shopping.service.client.SharedResources;

public class Activator implements BundleActivator {
	
	public static ModuleContext mc;
	SharedResources sr;
	
	public void start(final BundleContext context) throws Exception {
		System.out.println("starting client......................................................");
		SharedResources.moduleContext = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		sr = new SharedResources();
		sr.start();
	}

	public void stop(BundleContext context) throws Exception {
	}
}
*/