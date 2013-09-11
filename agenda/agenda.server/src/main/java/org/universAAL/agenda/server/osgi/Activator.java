package org.universAAL.agenda.server.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.universAAL.agenda.server.AgendaProvider;
import org.universAAL.agenda.server.gui.wrapper.WrapperActivator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class Activator implements BundleActivator {

    /**  */
    private static AgendaProvider provider = null;
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

	WrapperActivator.initiateInstance(mcontext);

	if (provider == null)
	    provider = new AgendaProvider(mcontext);
	LogUtils.logInfo(mcontext, this.getClass(), "start",
		new Object[] { "agenda.server bundle has started." }, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	LogUtils.logInfo(mcontext, this.getClass(), "stop",
		new Object[] { "agenda.server bundle has stopped." }, null);
    }

    /**
     * 
     * 
     * @return
     */
    public static AgendaProvider getAgendaProvider() {
	return Activator.provider;
    }

    /**
     * @return the mcontext
     */
    public static ModuleContext getMcontext() {
        return mcontext;
    }
}
