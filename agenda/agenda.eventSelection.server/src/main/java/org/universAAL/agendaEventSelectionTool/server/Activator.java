package org.universAAL.agendaEventSelectionTool.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

public class Activator implements BundleActivator {
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /**
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     *      )
     */
    public void start(BundleContext context) throws Exception {
	BundleContext[] bc = { context };
	mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
	new EventProvider(mcontext);
    }

    /**
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
    }
}
