package org.universAAL.Application.personal_safety.sw.panic;


import org.osgi.framework.BundleContext;
import org.universAAL.Application.personal_safety.sw.panic.osgi.Activator;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.risk.PanicButton;

public class ISubscriber extends InputSubscriber{
	
	public static String PANICACTION = "panic";
	static PanicButton panic = new PanicButton(PanicButton.MY_URI+"SWPanic");
	public ISubscriber(BundleContext context) {
		super(context);
		panic.setActivated(false);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
		
	}

	public void handleInputEvent(InputEvent event) {
		// TODO Auto-generated method stub
		if (event.getSubmissionID().startsWith(PANICACTION)) {
			panic.setPressedBy((User)event.getUser());
			panic.setActivated(true);
			ContextEvent panicCE = new ContextEvent(panic, PanicButton.PROP_ACTIVATED);
			Activator.cpublisher.publish(panicCE);
		}
		else {
			panic.setActivated(false);
		}
		
		
		
	}
	public void subscribe(String dialogID) {
		addNewRegParams(dialogID);
	}
}
