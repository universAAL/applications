package org.universAAL.agenda.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.universAAL.agenda.server.gui.wrapper.WrapperActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator {
	// testing...
	public static LogService log;
	private static AgendaProvider provider = null;
	/**
	 * {@link ModuleContext}
	 */
	private static ModuleContext mcontext;
	/**
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 *      )
	 */
	public void start(BundleContext context) throws Exception {

		log = (LogService) context.getService(context
				.getServiceReference(LogService.class.getName()));

		BundleContext[] bc = { context };
		mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
		
		WrapperActivator.initiateInstance(mcontext);

		if (provider == null)
			provider = new AgendaProvider(mcontext);

		System.out.println("Agenda server started!");
	}

	/**
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

	}

	public static AgendaProvider getProvider() {
		return Activator.provider;
	}
}
