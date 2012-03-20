package org.universAAL.agenda.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator {

    static LogService log;
    AgendaConsumer ac;
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /**
     * * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
     * BundleContext)
     */
    public void start(BundleContext context) throws Exception {
	System.out.println("Agenda client bundle just started (v2)");
	log = (LogService) context.getService(context
		.getServiceReference(LogService.class.getName()));

	BundleContext[] bc = { context };
	mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
	ac = new AgendaConsumer(mcontext);
	// Activator.log.log(LogService.LOG_INFO, "Debug test ended...");
    }

    public AgendaConsumer getAgendaConsumer() {
	return this.ac;
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	System.out.println("Agenda client is stopped (v2)");
    }
}
