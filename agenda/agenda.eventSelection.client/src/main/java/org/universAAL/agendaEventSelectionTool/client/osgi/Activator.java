package org.universAAL.agendaEventSelectionTool.client.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.agendaEventSelectionTool.client.EventSelectionToolConsumer;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * 
 * @author eandgrg
 */
public class Activator implements BundleActivator {

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    private EventSelectionToolConsumer ec;

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
	ec = new EventSelectionToolConsumer(mcontext);

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"start",
			new Object[] { "EventSelectionToolConsumer client bundle has started." },
			null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"stop",
			new Object[] { "EventSelectionToolConsumer client bundle has stopped." },
			null);
    }

    /**
     * 
     * @return EventSelectionToolConsumer
     */
    public EventSelectionToolConsumer getConsumer() {
	return this.ec;
    }
}
