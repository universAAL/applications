package org.universAAL.AALapplication.safety_home.service.server;

//import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author dimokas
 *
 */
public class Activator implements BundleActivator {
	
    private SafetyProvider provider = null;
    public static ModuleContext mc;
	
	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[] { context });
		System.out.println("Safety at Home Service started ...");
		new Thread() {
			public void run() {
				provider = new SafetyProvider(mc);
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
