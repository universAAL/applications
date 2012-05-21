package org.universAAL.agendaEventSelectionTool.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
/***
 * 
 * @author eandgrg
 *
 */
public class Activator implements BundleActivator {

    private final static Logger log = LoggerFactory
	    .getLogger("agendaEventSelectionTool.client.Activator");

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;
    private EventSelectionToolConsumer ec;

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
	BundleContext[] bc = { context };
	mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
	ec = new EventSelectionToolConsumer(mcontext);
	log.info("EventSelectionToolConsumer client bundle has started... ");
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	log.info("EventSelectionToolConsumer client bundle has stopped... ");
    }

    public EventSelectionToolConsumer getConsumer() {
	return this.ec;
    }
}
