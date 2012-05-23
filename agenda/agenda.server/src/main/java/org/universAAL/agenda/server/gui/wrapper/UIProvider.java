package org.universAAL.agenda.server.gui.wrapper;

import java.util.Locale;

import org.universAAL.agenda.server.osgi.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class UIProvider extends UICaller {
    public static final String ROOT_USER = User.MY_URI + "__rootUser__"; //$NON-NLS-1$
    private static String CALL_PREFIX = "urn:ui.dm:OutputPublisher"; //$NON-NLS-1$
    static final String TURN_OFF_REMINDER = CALL_PREFIX + "#turnOffReminder"; //$NON-NLS-1$
    static final String SNOOZE_REMINDER = CALL_PREFIX + "#snoozeReminder"; //$NON-NLS-1$
    static final String CALENDAR_URI = CALL_PREFIX + "#calendarURI"; //$NON-NLS-1$
    static final String EVENT_ID = CALL_PREFIX + "#eventID"; //$NON-NLS-1$

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /**
     * @param mcontext
     */
    UIProvider(ModuleContext moduleContext) {
	super(moduleContext);
	mcontext = moduleContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL
     * .middleware.ui.UIResponse)
     */
    public void handleUIResponse(UIResponse event) {
	System.out.println(">>>got InputEvent in prof.server<<<");
	// removed when transferring from IO to UI Bus (InputEvent->UIResponse)
	// if (event.hasDialogInput()) {
	System.out.println("Dialog ID: " + event.getDialogID());
	String submissionID = event.getSubmissionID();
	if (submissionID == null) {
	    LogUtils.logWarn(mcontext, this.getClass(), "handleUIResponse",
		    new Object[] { "submission ID null" }, null);
	    return;
	}

	if (SNOOZE_REMINDER.equals(submissionID)) {
	    // get values and update profile
	    System.out.println("Snooze reminder....");
	    // do nothing
	    return;
	}

	if (TURN_OFF_REMINDER.equals(submissionID)) {
	    System.out.println("Turn off reminder...");
	    // stopReminder
	    try {
		String calendarURI = (String) event.getSubmittedData()
			.getProperty(CALENDAR_URI);
		Integer eventId = (Integer) event.getSubmittedData()
			.getProperty(EVENT_ID);
		Activator.getAgendaProvider().cancelReminder(calendarURI,
			eventId.intValue());
	    } catch (ClassCastException cce) {
		LogUtils.logError(mcontext, this.getClass(),
			"handleUIResponse",
			new Object[] { "Class cast exception." }, cce);

	    }

	    return;
	}
	// }
    }

    public void showReminderConfirmationDialog(String message,
	    String calendarURI, int eventID, Resource user) {
	if (user == null) {
	    LogUtils.logWarn(mcontext, this.getClass(),
		    "showReminderConfirmationDialog",
		    new Object[] { "no user profile specified." }, null);
	    return;
	} else {
	    LogUtils.logWarn(mcontext, this.getClass(),
		    "showReminderConfirmationDialog",
		    new Object[] { "time to go." }, null);
	}

	Form form = Form.newMessage(Messages
		.getString("SimpleOutputPublisher.Reminder"), message); //$NON-NLS-1$
	Group submit = form.getSubmits();

	new Submit(
		submit,
		new Label(
			Messages.getString("SimpleOutputPublisher.TurnOff"), null), TURN_OFF_REMINDER); //$NON-NLS-1$
	new Submit(
		submit,
		new Label(
			Messages.getString("SimpleOutputPublisher.Snooze"), null), SNOOZE_REMINDER); //$NON-NLS-1$

	// secret field to hold the calendar URI and event id
	Group g = form.getIOControls();
	// new InputField(g, new Label("", null),
	// new PropertyPath(null, false, new String[]{CALENDAR_URI}),
	// Restriction.getAllValuesRestrictionWithCardinality(
	// CALENDAR_URI, TypeMapper.getDatatypeURI(String.class), 1, 1),
	// calendarURI).setSecret();
	//		
	// new InputField(g, new Label("", null),
	// new PropertyPath(null, false, new String[]{EVENT_ID}),
	// Restriction.getAllValuesRestrictionWithCardinality(
	// EVENT_ID, TypeMapper.getDatatypeURI(Integer.class), 1, 1), new
	// Integer(eventID)).setSecret();

	new SimpleOutput(
		g,
		new Label("", null), new PropertyPath(null, false, new String[] { CALENDAR_URI }), calendarURI); //$NON-NLS-1$
	new SimpleOutput(
		g,
		new Label("", null), new PropertyPath(null, false, new String[] { EVENT_ID }), new Integer(eventID)); //$NON-NLS-1$
	UIRequest oe = new UIRequest(user, form, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.personal);
	// before UIBus
	// WrapperActivator.getInputSubscriber().subscribe(form.getDialogID());

	sendUIRequest(oe);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.ui.UICaller#dialogAborted(java.lang.String)
     */
    public void dialogAborted(String dialogID) {
	// TODO: check database/profile consistency?
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.ui.UICaller#communicationChannelBroken()
     */
    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    // removed when transferring from IO to UI Bus
    // void subscribe(String dialogID) {
    // addNewRegParams(dialogID);
    // }
    //
    // void unsubscribe(String dialogID) {
    // removeMatchingRegParams(dialogID);
    // }

}
