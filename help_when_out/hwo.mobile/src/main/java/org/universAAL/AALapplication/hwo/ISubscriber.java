package org.universAAL.AALapplication.hwo;

import java.util.ArrayList;
import java.util.Calendar;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;
import org.universAAL.ontology.profile.User;

import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class ISubscriber extends InputSubscriber {
    private final static Logger log = LoggerFactory
	    .getLogger(ISubscriber.class);

    protected ISubscriber(BundleContext context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    public void dialogAborted(String dialogID) {
	// TODO Auto-generated method stub

    }

    public void handleInputEvent(InputEvent event) {
	User user = (User) event.getUser();
	log.info("Received an Input Event from user {}", user.getURI());
	String submit = event.getSubmissionID();

	try {
	    if (submit.equals(OPublisher.SUBMIT_HOME)) {
		log.debug("Input received was go Home");
		// do nothing-> return to main menu
	    } else if (submit.equals(OPublisher.SUBMIT_MANUAL)) {
		log.debug("Input received was manual panic button");
		boolean sent = sendPanicButtonSMSText();
		Activator.opublisher.showSMSForm(user, sent);
	    } else if (submit.equals(OPublisher.SUBMIT_TAKE)) {
		log.debug("Input received was start take me home");
		startActivity();
		// do nothing-> return to main menu
	    }
	} catch (Exception e) {
	    log.error("Error while processing the user input: {}", e);
	}
    }

    public void subscribe(String dialogID) {
	addNewRegParams(dialogID);
    }

    //__ANDROID________
    
    public static boolean sendPanicButtonSMSText() {
	String txt = Activator.getProperties().getProperty(Activator.TEXT,
		"universAAL SMS Alert. Contact relative.");
	String num = Activator.getProperties().getProperty(Activator.NUMBER,
		"123456789");
	Calendar now = Calendar.getInstance();

	txt += txt + "  (" + now.get(Calendar.HOUR_OF_DAY) + ":"
		+ now.get(Calendar.MINUTE) + ")";

	SmsManager sms = SmsManager.getDefault();
	// If too long, we trim the message
	if (SmsMessage.calculateLength(txt, false)[0] > 1) {
	    ArrayList<String> messages = sms.divideMessage(txt);
	    txt = messages.get(0);
	}
	sms.sendTextMessage(num, null, txt, null, null);
	return true;
    }
    
    private void startActivity() {
	Intent i = new Intent(
		Intent.ACTION_VIEW,
		Uri
			.parse("google.navigation:q="
				+ Activator
					.getProperties()
					.getProperty(Activator.GPSTO,
						"Rue Wiertz 60, 1047 Bruxelles, Belqique")
					.replace(" ", "+") + "&mode=w"));
	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	Activator.activityHandle.startActivity(i);
    }
}
