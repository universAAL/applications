package org.universAAL.agenda.gui.wrappers;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.agenda.gui.CalendarGUI;
import org.universAAL.agenda.gui.osgi.Activator;

/**
 * 
 */
public class SimpleServiceCallee extends ServiceCallee {

    private static ModuleContext moduleContext;

    private static final ServiceResponse failure = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    /**
     * 
     * 
     * @param mcontext
     */
    public SimpleServiceCallee(ModuleContext mcontext) {
	super(mcontext, ProvidedCalendarUIService.profiles);
	moduleContext = mcontext;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken
     * ()
     */
    public void communicationChannelBroken() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
     * .middleware.service.ServiceCall)
     */
    public ServiceResponse handleCall(ServiceCall call) {
	if (call == null)
	    return null;

	String operation = call.getProcessURI();
	if (operation == null)
	    return null;

	if (operation.startsWith(ProvidedCalendarUIService.SERVICE_START_UI)) {
	    return showCalendarUI(call.getInvolvedUser());
	}

	return null;
    }

    /**
     * 
     * 
     * @param resource
     * @return
     */
    private ServiceResponse showCalendarUI(Resource resource) {
	try {
	    new CalendarGUI(Activator.getBundleContext(), moduleContext);

	    // FIXME removed when trasferring to UI Bus (no
	    // InputEvent.uAAL_MAIN_MENU_REQUEST)

	    // DefaultInputPublisher ip = new
	    // DefaultInputPublisher(moduleContext);
	    //
	    // // test the main menu
	    // ip.publish(new UIResponse(resource, null,
	    // UIResponse.uAAL_MAIN_MENU_REQUEST));

	    return new ServiceResponse(CallStatus.succeeded);
	} catch (Exception e) {
	    return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}
    }
}
