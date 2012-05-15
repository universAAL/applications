/**
 * 
 */
package org.universAAL.agenda.server.gui.wrapper;

import java.util.Locale;

import org.osgi.service.log.LogService;
import org.universAAL.agenda.server.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.io.owl.PrivacyLevel;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Group;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.SimpleOutput;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.output.OutputPublisher;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.ontology.profile.User;

/**
 * @author KAgnantis
 * 
 */
public class SimpleOutputPublisher extends OutputPublisher {
    public static final String ROOT_USER = User.MY_URI + "__rootUser__"; //$NON-NLS-1$
    private static String CALL_PREFIX = "urn:ui.dm:OutputPublisher"; //$NON-NLS-1$
    static final String TURN_OFF_REMINDER = CALL_PREFIX + "#turnOffReminder"; //$NON-NLS-1$
    static final String SNOOZE_REMINDER = CALL_PREFIX + "#snoozeReminder"; //$NON-NLS-1$
    static final String CALENDAR_URI = CALL_PREFIX + "#calendarURI"; //$NON-NLS-1$
    static final String EVENT_ID = CALL_PREFIX + "#eventID"; //$NON-NLS-1$

    SimpleOutputPublisher(ModuleContext mcontext) {
	super(mcontext);
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    public void showReminderConfirmationDialog(String message,
	    String calendarURI, int eventID, Resource user) {
	if (user == null) {
	    Activator.log
		    .log(LogService.LOG_WARNING,
			    "OutputPublisher@ui.dm - showMenu: no user profile specified!"); //$NON-NLS-1$
	    return;
	} else {
	    Activator.log.log(LogService.LOG_WARNING,
		    "OutputPublisher@ui.dm - showMenu: time to go!"); //$NON-NLS-1$
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
	OutputEvent oe = new OutputEvent(user, form, LevelRating.middle,
		Locale.ENGLISH, PrivacyLevel.personal);
	WrapperActivator.getInputSubscriber().subscribe(form.getDialogID());
	publish(oe);
    }
}
