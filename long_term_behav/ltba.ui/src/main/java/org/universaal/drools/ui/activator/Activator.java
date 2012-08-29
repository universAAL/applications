package org.universaal.drools.ui.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universaal.drools.ui.impl.LTBAUIProvider;

public class Activator implements BundleActivator {

	private ModuleContext context;
	private LTBAUIProvider service;

	public void start(BundleContext context) throws Exception {
		// SharedResources sr;
		// SharedResources.moduleContext = uAALBundleContainer.THE_CONTAINER
		// .registerModule(new Object[] { context });
		//
		// sr = new SharedResources();
		// sr.start();

		this.context = uAALBundleContainer.THE_CONTAINER
				.registerModule(new Object[] { context });
		this.context.logDebug("Initialising Project", null);

		/*
		 * uAAL stuff
		 */
		service = new LTBAUIProvider(this.context);

		this.context.logInfo("Project started", null);

	}

	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
