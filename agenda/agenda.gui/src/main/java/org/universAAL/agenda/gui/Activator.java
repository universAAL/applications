package org.universAAL.agenda.gui;

import java.util.Locale;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.universAAL.agenda.gui.wrappers.SimpleServiceCallee;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.ElderlyUser;

public class Activator implements BundleActivator {

    public static String ICON_PATH_PREFIX = "/lang/icons_"
	    + Locale.getDefault().getLanguage().toLowerCase();
    static LogService log;
    private static BundleContext CONTEXT;
    public static final ElderlyUser testUser = new ElderlyUser(
	    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied");
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     *      )
     */
    public void start(BundleContext context) throws Exception {

	BundleContext[] bc = { context };
	mcontext = uAALBundleContainer.THE_CONTAINER.registerModule(bc);
	System.out.println("Agenda GUI bundle started");

	log = (LogService) context.getService(context
		.getServiceReference(LogService.class.getName()));
	CONTEXT = context;
	new SimpleServiceCallee(mcontext);

	// uncomment the following line, if you want to start agenda gui
	// automatically, and not through the universAAL Main Menu

	// new CalendarGUI(context);
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
	System.out.println("Agenda GUI is stopped (v2)");
    }

    public static BundleContext getBundleContext() {
	return CONTEXT;
    }
}
