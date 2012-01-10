package org.universAAL.agenda.server;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.universAAL.agenda.server.gui.wrapper.WrapperActivator;

import com.mysql.jdbc.*;

public class Activator implements BundleActivator {
	//testing...
	public static LogService log;
	private static AgendaProvider provider = null;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		log = (LogService) context.getService(
				context.getServiceReference(LogService.class.getName()));
		
		WrapperActivator.initiateInstance(context);
		
		if (provider == null)
			provider = new AgendaProvider(context);
		
		System.err.println("Agenda server started!");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		
		
	}
	
	public static AgendaProvider getProvider() {
		return Activator.provider;
	}
}
