package org.universAAL.agenda.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * 
 * @author eandgrg
 * 
 */
public class Activator implements BundleActivator {

    private AgendaConsumer ac;
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
	BundleContext[] bc = { context };
	mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
	ac = new AgendaConsumer(mcontext);
	
	LogUtils.logInfo(mcontext, this.getClass(), "start",
		    new Object[] { "Agenda client consumer started." }, null);
    }

    /**
     * 
     * 
     * @return
     */
    public AgendaConsumer getAgendaConsumer() {
	return this.ac;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	LogUtils.logInfo(mcontext, this.getClass(), "stop",
		    new Object[] { "Agenda client consumer stopped." }, null);
    }
}
