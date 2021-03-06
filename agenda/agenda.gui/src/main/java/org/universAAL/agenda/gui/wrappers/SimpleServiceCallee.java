package org.universAAL.agenda.gui.wrappers;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.User;
import org.universAAL.agenda.gui.CalendarGUI;
import org.universAAL.agenda.gui.osgi.Activator;

/**
 * 
 */
public class SimpleServiceCallee extends ServiceCallee {

    private static ModuleContext moduleContext;

    /**
     * Constructor
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
	    // e.g. call.getInvolvedUser:
	    // urn:org.universAAL.aal_space:test_env#saied

	    return showCalendarUI((User) call.getInvolvedUser());
	}
	return new ServiceResponse(CallStatus.noMatchingServiceFound);
    }

    /**
     * 
     * 
     * @param resource
     * @return
     */
    private ServiceResponse showCalendarUI(User loggedInUser) {
	try {
	    new CalendarGUI(Activator.getBundleContext(), moduleContext,
		    loggedInUser);

	    return new ServiceResponse(CallStatus.succeeded);
	} catch (Exception e) {
	    return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}
    }
}
