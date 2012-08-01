package org.universAAL.apps.biomedicalsensors.uiclient.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.apps.biomedicalsensors.uiclient.SharedResources;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator {

	SharedResources sr;

	public void start(BundleContext context) throws Exception {
		SharedResources.moduleContext = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });

		sr = new SharedResources();
		sr.start();
	}

	public void stop(BundleContext context) throws Exception {
	}
}
