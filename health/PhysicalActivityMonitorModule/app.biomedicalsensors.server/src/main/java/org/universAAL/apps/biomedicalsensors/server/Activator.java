package org.universAAL.apps.biomedicalsensors.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

/**
 * @author joemoul, billyk
 * 
 */
public class Activator implements BundleActivator {

	private BiomedicalSensorsCallee provider = null;
	public static ModuleContext mc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(final BundleContext context) throws Exception {
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		new Thread() {
			public void run() {
				provider = new BiomedicalSensorsCallee(mc);
			}
		}.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if (provider != null) {
			provider.close();
			provider = null;
		}
	}
}
