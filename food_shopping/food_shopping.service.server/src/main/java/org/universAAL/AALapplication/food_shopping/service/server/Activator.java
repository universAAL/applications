
package org.universAAL.AALapplication.food_shopping.service.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author dimokas
 *
 */
public class Activator implements BundleActivator {
	
    private FoodManagementProvider provider = null;
    public static ModuleContext mc;
	
	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		System.out.println("Food Shopping Service started ...");
		new Thread() {
			public void run() {
				provider = new FoodManagementProvider(mc);
			}
		}.start();
	}

	public void stop(BundleContext context) throws Exception {
		if (provider != null) {
		    provider.close();
		    provider = null;
		}
	}
}
