package org.universAAL.AALapplication.hwo;

import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.io.owl.PrivacyLevel;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Group;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.MediaObject;
import org.universAAL.middleware.io.rdf.SimpleOutput;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.output.OutputPublisher;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.ontology.profile.User;

public class OPublisher extends OutputPublisher {

    public static final String HWO_INPUT_NAMESPACE = "http://ontology.universAAL.org/Input.owl#"; //$NON-NLS-1$
    public static final String SUBMIT_HOME = HWO_INPUT_NAMESPACE + "home"; //$NON-NLS-1$
    public static final String SUBMIT_MANUAL = HWO_INPUT_NAMESPACE
	    + "manual"; //$NON-NLS-1$
    public static final String SUBMIT_TAKE = HWO_INPUT_NAMESPACE + "take"; //$NON-NLS-1$
    public static final String SMS_TITLE = Messages.getString("HwOGUI.1"); //$NON-NLS-1$
    public static final String HOME_SUBMIT = Messages.getString("HwOGUI.2"); //$NON-NLS-1$
    public static final String SMS_IMG_LABEL = Messages.getString("HwOGUI.3"); //$NON-NLS-1$
    public static final String SMS_TEXT = Messages.getString("HwOGUI.4"); //$NON-NLS-1$
    public static final String SMS_NO_TEXT = Messages.getString("HwOGUI.5"); //$NON-NLS-1$
    public static final String BUTTON_MANUAL_TITLE = Messages
	    .getString("HwOGUI.6"); //$NON-NLS-1$
    public static final String BUTTON_MANUAL_LABEL = Messages
	    .getString("HwOGUI.7"); //$NON-NLS-1$
    public static final String TAKE_HOME_TITLE = Messages
	    .getString("HwOGUI.8"); //$NON-NLS-1$
    public static final String TAKE_HOME_LABEL = Messages
	    .getString("HwOGUI.9"); //$NON-NLS-1$
    public static final String TAKE_HOME_TEXT = Messages
	    .getString("HwOGUI.10"); //$NON-NLS-1$

    private static final String imgroot = "android.handler/"; //$NON-NLS-1$ //$NON-NLS-2$

    private final static Logger log = LoggerFactory.getLogger(OPublisher.class);

    protected OPublisher(BundleContext context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    public void showButtonManualForm(User user) {
	log.debug("Show manual panic button screen");
	Form f = getPanicButtonForm();
	OutputEvent oe = new OutputEvent(user, f, LevelRating.high,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.isubscriber.subscribe(f.getDialogID());
	publish(oe);
    }

    public void showTakeHomeForm(User user) {
	log.debug("Show take me home screen");
	Form f = getTakeHomeForm();
	OutputEvent oe = new OutputEvent(user, f, LevelRating.high,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.isubscriber.subscribe(f.getDialogID());
	publish(oe);
    }

    public void showSMSForm(User user, boolean smsSuccess) {
	log.debug("Show SMS screen");
	Form f = getSMSForm(smsSuccess);
	OutputEvent oe = new OutputEvent(user, f, LevelRating.full,
		Locale.ENGLISH, PrivacyLevel.insensible);
	Activator.isubscriber.subscribe(f.getDialogID());
	publish(oe);
	// playWarning();
    }

    // ___FORMS________________________

    public Form getPanicButtonForm() {
	log.debug("Generating panic button form");
	Form f = Form.newDialog(BUTTON_MANUAL_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();
	Label labelBoton = new Label(BUTTON_MANUAL_LABEL, null);
	new Submit(controls, labelBoton, SUBMIT_MANUAL);
	new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
	return f;
    }

    public Form getTakeHomeForm() {
	log.debug("Generating take home form");
	Form f = Form.newDialog(TAKE_HOME_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();
	new SimpleOutput(controls, null, null, TAKE_HOME_TEXT);
	Label labelBoton = new Label(TAKE_HOME_LABEL, null);
	new Submit(controls, labelBoton, SUBMIT_TAKE);
	new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);
	return f;
    }

    public Form getSMSForm(boolean sent) {
	log.debug("Generating sms form");
	Form f = Form.newDialog(SMS_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	new Submit(submits, new Label(HOME_SUBMIT, (String) null), SUBMIT_HOME);

	new MediaObject(controls, new Label(SMS_IMG_LABEL, imgroot
		+ (sent ? "enviarSMS.jpg" : "smsNoEnviado.gif")), "image",
		imgroot + (sent ? "enviarSMS.jpg" : "smsNoEnviado.gif"));
	new SimpleOutput(controls, new Label("", (String) null), null,
		sent ? SMS_TEXT : SMS_NO_TEXT);

	return f;
    }

}
