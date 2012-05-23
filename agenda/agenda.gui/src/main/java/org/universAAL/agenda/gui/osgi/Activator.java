package org.universAAL.agenda.gui.osgi;

import java.util.Locale;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.universAAL.agenda.gui.wrappers.SimpleServiceCallee;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class Activator implements BundleActivator {

    public static String ICON_PATH_PREFIX = "/lang/icons_"
	    + Locale.getDefault().getLanguage().toLowerCase();

    private static BundleContext bcontext;

    public static final User testUser = new User(
	    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
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

	bcontext = context;
	new SimpleServiceCallee(mcontext);

	LogUtils.logInfo(mcontext, this.getClass(), "start",
		new Object[] { "agenda.gui bundle has started." }, null);

	// uncomment the following line, if you want to start agenda gui
	// automatically, and not through the universAAL Main Menu

	// new CalendarGUI(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	LogUtils.logInfo(mcontext, this.getClass(), "stop",
		new Object[] { "agenda.gui bundle has stopped." }, null);
    }

    /**
     * 
     * 
     * @return
     */
    public static BundleContext getBundleContext() {
	return bcontext;
    }
}
