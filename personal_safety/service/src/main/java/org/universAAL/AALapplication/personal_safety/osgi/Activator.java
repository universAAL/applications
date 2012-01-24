package org.universAAL.AALapplication.personal_safety.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.personal_safety.service.Main;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator {

	private ModuleContext mc;
	Main module;

	public void start(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		mc = uAALBundleContainer.THE_CONTAINER
				.registerModule(new BundleContext[] { arg0 });
		module = new Main();
		module.start(mc);
	}

	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		module.stop(mc);
	}

}
