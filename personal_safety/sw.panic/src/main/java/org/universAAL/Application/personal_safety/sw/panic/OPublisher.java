package org.universAAL.Application.personal_safety.sw.panic;


import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.universAAL.Application.personal_safety.sw.panic.osgi.Activator;
import org.universAAL.middleware.io.owl.PrivacyLevel;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.SimpleOutput;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.output.OutputPublisher;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;

public class OPublisher extends OutputPublisher{

	public OPublisher(BundleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
	
	public void confirmPanic(Resource user) {
		Form f = newComfimDialog();
		Activator.isubscriber.subscribe(f.getDialogID());
		publish(new OutputEvent(user, f, LevelRating.high, Locale.getDefault(), PrivacyLevel.insensible));
	}
	
	protected Form newComfimDialog() {
		//Form f = Form.newMessage("Comfirm Panic", "Do you really whant to send a panic alert?");
		//f.setProperty(Form.LABEL_MESSAGE_KEEP, (Object) new Label("YES!",null));
		Form f = Form.newDialog("Comfirm Panic", "");
		new Submit(f.getSubmits(), new Label("YES!", (String) null), ISubscriber.PANICACTION);
		new Submit(f.getSubmits(), new Label("NO", (String) null), "nopanic");
		new SimpleOutput(f.getIOControls(), new Label("Do you really whant to send a panic alert?", ""),
				null,"");
		return f;
	}

}
