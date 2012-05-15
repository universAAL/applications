/**
 * 
 */
package org.universAAL.agenda.gui.wrappers;

//j2se packages

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.input.DefaultInputPublisher;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.profile.User;
import org.universAAL.agenda.gui.Activator;
import org.universAAL.agenda.gui.CalendarGUI;

public class SimpleServiceCallee extends ServiceCallee {

    private ContextPublisher cp;
    ModuleContext moduleContext;
    private static final ServiceResponse failure = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    public SimpleServiceCallee(ModuleContext mcontext) {
	super(mcontext, ProvidedCalendarUIService.profiles);
	moduleContext = mcontext;

    }

    public void communicationChannelBroken() {

    }

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

    private ServiceResponse showCalendarUI(Resource resource) {
	try {
	    new CalendarGUI(Activator.getBundleContext());
	    DefaultInputPublisher ip = new DefaultInputPublisher(moduleContext);

	    // test the main menu
	    ip.publish(new InputEvent(resource, null,
		    InputEvent.uAAL_MAIN_MENU_REQUEST));
	    return new ServiceResponse(CallStatus.succeeded);
	} catch (Exception e) {
	    return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}
    }
}
