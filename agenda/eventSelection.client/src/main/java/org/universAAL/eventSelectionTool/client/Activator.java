package org.universAAL.eventSelectionTool.client;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

public class Activator implements BundleActivator {
	
	public static LogService log;
	private EventSelectionToolConsumer ec;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		
		System.out.println("EventSelectionToolConsumer client bundle has started... ");
		
		log = (LogService) context.getService(
				context.getServiceReference(LogService.class.getName()));
		ec = new EventSelectionToolConsumer(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception 
	{
		Activator.log.log(LogService.LOG_INFO,
		"EventSelectionToolConsumer client bundle has stopped... " );
	}
	
	public EventSelectionToolConsumer getConsumer() {
		return this.ec;
	}
}
