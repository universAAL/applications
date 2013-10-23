/**
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
		
	2012 Ericsson Nikola Tesla d.d., www.ericsson.com/hr
		
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.agenda.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.universAAL.agenda.remote.osgi.Activator;
import org.universAAL.middleware.owl.IntRestriction;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.ui.rdf.TextArea;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.profile.User;

/**
 * 
 * @author alfiva
 * @author eandgrg
 * 
 */
public class AgendaWebGUI {

    private static final String uAAL_NAMESPACE_PREFIX = "http://ontology.ent.hr/"; //$NON-NLS-1$
    public static final String uAAL_INPUT_NAMESPACE = uAAL_NAMESPACE_PREFIX
	    + "AgendaInput.owl#"; //$NON-NLS-1$
    public static final String REF_CALENDAR = uAAL_INPUT_NAMESPACE + "calendar";
    public static final String REF_DAY = uAAL_INPUT_NAMESPACE + "day";
    public static final String REF_TYPE = uAAL_INPUT_NAMESPACE + "type";
    public static final String REF_DESC = uAAL_INPUT_NAMESPACE + "desc";
    public static final String REF_PLACE = uAAL_INPUT_NAMESPACE + "place";
    public static final String REF_REM_MSG = uAAL_INPUT_NAMESPACE + "remmsg";
    public static final String REF_REM_INT = uAAL_INPUT_NAMESPACE + "remint";
    public static final String REF_REM_REP = uAAL_INPUT_NAMESPACE + "remrep";
    public static final String REF_REM_YEAR = uAAL_INPUT_NAMESPACE + "remyear";
    public static final String REF_REM_MONTH = uAAL_INPUT_NAMESPACE
	    + "remmonth";
    public static final String REF_REM_DAY = uAAL_INPUT_NAMESPACE + "remday";
    public static final String REF_YEAR = uAAL_INPUT_NAMESPACE + "year";
    public static final String REF_MONTH = uAAL_INPUT_NAMESPACE + "month";
    public static final String REF_HOUR = uAAL_INPUT_NAMESPACE + "hour";
    public static final String REF_MIN = uAAL_INPUT_NAMESPACE + "min";
    public static final String REF_REM_MIN = uAAL_INPUT_NAMESPACE + "remmin";
    public static final String REF_REM_HOUR = uAAL_INPUT_NAMESPACE + "remhour";

    public static final String REF_USER = uAAL_INPUT_NAMESPACE + "user";

    // needs to be publicly available because of event delete option
    public static HashMap<Integer, Event> map;

    /**
     * @return {@link Form} for selecting a {@link User} that has accessed
     *         Agenda service at some point
     */
    public Form getSelectUserMenuForm() {
	Form f = Form.newDialog(Messages
		.getString("AgendaWebGUI.AgendaScreenTitle"), (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	new SimpleOutput(controls, null, null, Messages
		.getString("AgendaWebGUI.SelectUser"));

	List calendars = Activator.sCaller.getAllCalendarsService();

	if (calendars.isEmpty()) {
	    new SimpleOutput(controls, null, null, Messages
		    .getString("AgendaWebGUI.NoCalendarsFound"));
	} else {

	    Set<String> ownerNames = new HashSet<String>();

	    Iterator iter = calendars.iterator();
	    for (; iter.hasNext();) {
		Calendar cal = (Calendar) iter.next();

		String tempOwner = Activator.sCaller
			.getCalendarOwnerNameService(cal.getName());

		if (tempOwner != null && !ownerNames.contains(tempOwner)) {
		    ownerNames.add(tempOwner);

		}

	    }

	    // Select u control
	    Select1 userSelect = new Select1(controls,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.User"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_USER }),
		    null, null);

	    Iterator iter2 = ownerNames.iterator();
	    while (iter2.hasNext()) {
		String owner = (String) iter2.next();

		userSelect.addChoiceItem(new ChoiceItem(owner, null, owner));
	    }

	    new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(
		    Messages.getString("AgendaWebGUI.GetEventList"),
		    (String) null), "get_event_list");
	    new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(
		    Messages.getString("AgendaWebGUI.AddNewEvent"),
		    (String) null), "add");

	}

	new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(Messages
		.getString("AgendaWebGUI.Home"), (String) null), "agenda_home");

	return f;

    }

    public Form getMainScreenMenuForm(User calOwner) {
	Form f = Form.newDialog(Messages
		.getString("AgendaWebGUI.AgendaScreenTitle"), (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	new org.universAAL.middleware.ui.rdf.Label(Messages
		.getString("AgendaWebGUI.SelectCalendar"), (String) null);

	List calendars = Activator.sCaller.getCalendarsByOwnerService(calOwner);

	if (calendars.isEmpty()) {
	    new SimpleOutput(controls, null, null, Messages
		    .getString("AgendaWebGUI.NoCalendarsFound"));
	} else {
	    java.util.Calendar now = java.util.Calendar.getInstance();
	    java.util.Calendar nowrem = java.util.Calendar.getInstance();
	    now.add(java.util.Calendar.DAY_OF_YEAR, 0);
	    nowrem.add(java.util.Calendar.DAY_OF_YEAR, 0);
	    nowrem.add(java.util.Calendar.MONTH, 1);
	    nowrem.add(java.util.Calendar.MINUTE, 4);

	    // Select Calendar control
	    Select1 calselect = new Select1(
		    controls,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Calendar"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_CALENDAR }),
		    null, null);
	    Iterator iter = calendars.iterator();
	    for (; iter.hasNext();) {
		Calendar cal = (Calendar) iter.next();
		calselect
			.addChoiceItem(new ChoiceItem(cal.getName(), null, cal));
	    }

	    // Select Start Date control
	    Group dategroup = new Group(controls,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Date"), (String) null),
		    null, null, (Resource) null);
	    Group invisiblegroupdate = new Group(dategroup, null, null, null,
		    (Resource) null);// This group is for ordering inputs
	    // vertically
	    new org.universAAL.middleware.ui.rdf.Label(Messages
		    .getString("AgendaWebGUI.SelectDate"), (String) null);
	    // Day
	    new InputField(invisiblegroupdate,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Day"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_DAY }),
		    MergedRestriction.getAllValuesRestriction(REF_DAY,
			    new IntRestriction(1, true, 31, true)),
		    new Integer(now.get(java.util.Calendar.DAY_OF_MONTH)));

	    // Month
	    new InputField(invisiblegroupdate,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Month"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_MONTH }),
		    MergedRestriction.getAllValuesRestriction(REF_MONTH,
			    new IntRestriction(1, true, 12, true)),
		    new Integer(now.get(java.util.Calendar.MONTH) + 1));

	    // Year
	    Select1 yearselect = new Select1(invisiblegroupdate,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Year"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_YEAR }),
		    null, null);
	    int currentYear = java.util.Calendar.getInstance().get(
		    java.util.Calendar.YEAR);
	    for (int i = currentYear; i < currentYear + 15; i++) {
		yearselect.addChoiceItem(new ChoiceItem(Integer.toString(i),
			(String) null, new Integer(i)));
	    }
	    // Hour
	    new InputField(dategroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Hour"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_HOUR }),
		    MergedRestriction.getAllValuesRestriction(REF_HOUR,
			    new IntRestriction(0, true, 23, true)),
		    new Integer(now.get(java.util.Calendar.HOUR_OF_DAY)));

	    // Minute
	    new InputField(dategroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Minute"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_MIN }),
		    MergedRestriction.getAllValuesRestriction(REF_MIN,
			    new IntRestriction(0, true, 59, true)),
		    new Integer(now.get(java.util.Calendar.MINUTE)));

	    // Input Info Control
	    Group infogroup = new Group(controls,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Event"), (String) null),
		    null, null, (Resource) null);
	    Group invisiblegroup = new Group(infogroup, null, null, null,
		    (Resource) null);// This group is for ordering inputs
	    // vertically
	    new org.universAAL.middleware.ui.rdf.Label(Messages
		    .getString("AgendaWebGUI.Details"), (String) null);
	    new InputField(invisiblegroup, new Label(Messages
		    .getString("AgendaWebGUI.Type"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_TYPE }),
		    null, "");
	    new InputField(invisiblegroup, new Label(Messages
		    .getString("AgendaWebGUI.Place"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_PLACE }),
		    null, "");
	    new TextArea(invisiblegroup, new Label(Messages
		    .getString("AgendaWebGUI.Desc"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_DESC }),
		    null, "");

	    // ////////////////////////////////////
	    // Reminder Group controls START
	    // ////////////////////////////////////

	    Group remindergroup = new Group(
		    controls,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Reminder"), (String) null),
		    null, null, (Resource) null);
	    Group invisiblegroup2 = new Group(remindergroup, null, null, null,
		    (Resource) null);

	    // This group is for ordering inputs vertically
	    // Message
	    new org.universAAL.middleware.ui.rdf.Label(Messages
		    .getString("AgendaWebGUI.InfoToSetReminder"), (String) null);
	    InputField remMsg = new InputField(
		    invisiblegroup2,
		    new Label(Messages.getString("AgendaWebGUI.Message"),
			    (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_MSG }),
		    null, "");
	    Group remdategroup = new Group(invisiblegroup2,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Date"), (String) null),
		    null, null, (Resource) null);
	    Group remdateinvisiblegroup = new Group(remdategroup, null, null,
		    null, (Resource) null);// This group is for ordering inputs
	    // vertically
	    // Day
	    new InputField(
		    remdateinvisiblegroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Day"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_DAY }),
		    MergedRestriction.getAllValuesRestriction(REF_REM_DAY,
			    new IntRestriction(1, true, 31, true)),
		    new Integer(nowrem.get(java.util.Calendar.DAY_OF_MONTH)));

	    // Month
	    new InputField(remdateinvisiblegroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Month"), (String) null),
		    new PropertyPath(null, false,
			    new String[] { REF_REM_MONTH }), MergedRestriction
			    .getAllValuesRestriction(REF_REM_MONTH,
				    new IntRestriction(1, true, 12, true)),
		    new Integer(nowrem.get(java.util.Calendar.MONTH)));

	    // Year
	    Select1 remyearselect = new Select1(
		    remdateinvisiblegroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Year"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_YEAR }),
		    null, null);
	    for (int i = currentYear; i < currentYear + 10; i++) {
		remyearselect.addChoiceItem(new ChoiceItem(Integer.toString(i),
			(String) null, new Integer(i)));
	    }
	    // Hour
	    new InputField(
		    remdategroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Hour"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_HOUR }),
		    MergedRestriction.getAllValuesRestriction(REF_REM_HOUR,
			    new IntRestriction(0, true, 23, true)),
		    new Integer(nowrem.get(java.util.Calendar.HOUR_OF_DAY)));

	    // Minute
	    new InputField(
		    remdategroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Minute"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_MIN }),
		    MergedRestriction.getAllValuesRestriction(REF_REM_MIN,
			    new IntRestriction(0, true, 59, true)),
		    new Integer(nowrem.get(java.util.Calendar.MINUTE)));
	    // Repeat group
	    Group remrepeatgroup = new Group(invisiblegroup2,
		    new org.universAAL.middleware.ui.rdf.Label("Repeat",
			    (String) null), null, null, (Resource) null);
	    // Repeat group -> Times
	    Select1 remrepeatselect = new Select1(remrepeatgroup,
		    new org.universAAL.middleware.ui.rdf.Label("Times",
			    (String) null), new PropertyPath(null, false,
			    new String[] { REF_REM_REP }), null, null);
	    for (int i = 1; i <= 5; i++) {
		remrepeatselect.addChoiceItem(new ChoiceItem(Integer
			.toString(i), (String) null, new Integer(i)));
	    }
	    // Repeat group -> Interval
	    Select1 remintervalselect = new Select1(
		    remrepeatgroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Interval"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_INT }),
		    null, null);
	    for (int i = 1; i <= 4; i++) {
		remintervalselect.addChoiceItem(new ChoiceItem(Integer
			.toString(i), (String) null, new Integer(i)));
	    }
	    for (int i = 5; i <= 60; i += 5) {
		remintervalselect.addChoiceItem(new ChoiceItem(Integer
			.toString(i), (String) null, new Integer(i)));
	    }

	    // ////////////////////////////////////
	    // Reminder Group controls END
	    // ////////////////////////////////////

	    // Submit
	    new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(
		    Messages.getString("AgendaWebGUI.GetEventList"),
		    (String) null), "get_event_list");
	    Submit s = new Submit(submits,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Submit"), (String) null),
		    "submit");
	    // make Reminder Message mandatory (no submit can be done while this
	    // message is empty)
	    s.addMandatoryInput(remMsg);

	    // new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(
	    // Messages.getString("AgendaWebGUI.28"), (String) null),
	    // "google");

	    new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(
		    Messages.getString("AgendaWebGUI.Home"), (String) null),
		    "agenda_home");
	}
	return f;

    }

    /**
     * @param msg
     *            message to put in the {@link Form}
     * @return {@link Form} containing given message
     */
    public Form getMessageForm(String msg) {
	Form f = Form.newDialog(Messages
		.getString("AgendaWebGUI.AgendaScreenTitle"), (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	new SimpleOutput(controls, null, null, msg);
	new SimpleOutput(controls, null, null, Messages
		.getString("AgendaWebGUI.InfoAfterAddingEvent"));
	new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(Messages
		.getString("AgendaWebGUI.AddNewEvent"), (String) null), "add");
	new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(Messages
		.getString("AgendaWebGUI.Home"), (String) null), "agenda_home");
	return f;
    }

    /**
     * 
     * @param calOwner
     *            calendar owner
     * @return form containing all events from all calendars of given user
     */
    public Form getEventsForm(User calOwner) {
	Form f = Form.newDialog(Messages
		.getString("AgendaWebGUI.AgendaScreenTitle"), (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();
	map = new HashMap<Integer, Event>();

	List<Calendar> calendars = Activator.sCaller
		.getCalendarsByOwnerService(calOwner);

	List<Event> events = new ArrayList<Event>();

	Iterator<Calendar> iter = calendars.iterator();
	for (; iter.hasNext();) {
	    Calendar cal = (Calendar) iter.next();

	    List<Event> tempEvents = Activator.sCaller
		    .requestEventListService(cal);

	    events.addAll(tempEvents);

	}

	Collections.sort(events, new MyEventComparator());

	new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(Messages
		.getString("AgendaWebGUI.AddNewEvent"), (String) null), "add");

	new Submit(submits, new org.universAAL.middleware.ui.rdf.Label(Messages
		.getString("AgendaWebGUI.Home"), (String) null), "agenda_home");

	// create Form
	Group eventsGroup = new Group(controls,
		new org.universAAL.middleware.ui.rdf.Label(Messages
			.getString("AgendaWebGUI.EventList.Events"),
			(String) null), null, null, (Resource) null);
	Group invisiblegroup = new Group(eventsGroup, null, null, null,
		(Resource) null);

	for (int i = 0; i < events.size(); i++) {
	    boolean remExists = false;
	    Event event = new Event();
	    event = (Event) events.get(i);
	    Group eventG = new Group(invisiblegroup,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.EventList.Event")
			    + ": "
			    + event.getEventDetails().getTimeInterval()
				    .getStartTime().getDay()
			    + "/"
			    + event.getEventDetails().getTimeInterval()
				    .getStartTime().getMonth()
			    + "/"
			    + event.getEventDetails().getTimeInterval()
				    .getStartTime().getYear(), (String) null),
		    null, null, (Resource) null);
	    Group invisiblegroup1 = new Group(eventG, null, null, null,
		    (Resource) null);
	    new SimpleOutput(invisiblegroup1, null, null, Messages
		    .getString("AgendaWebGUI.EventList.BelongsToCalendar")
		    + ": " + event.getParentCalendar());
	    new SimpleOutput(invisiblegroup1, null, null, Messages
		    .getString("AgendaWebGUI.EventList.Category")
		    + ": " + event.getEventDetails().getCategory());
	    new SimpleOutput(invisiblegroup1, null, null, Messages
		    .getString("AgendaWebGUI.EventList.Description")
		    + ": " + event.getEventDetails().getDescription());
	    new SimpleOutput(invisiblegroup1, null, null, Messages
		    .getString("AgendaWebGUI.EventList.Place")
		    + ": " + event.getEventDetails().getPlaceName());
	    new SimpleOutput(invisiblegroup1, null, null, Messages
		    .getString("AgendaWebGUI.EventList.BeginTime")
		    + ": "
		    + hour(event.getEventDetails().getTimeInterval()
			    .getStartTime().getHour())
		    + ":"
		    + minute(event.getEventDetails().getTimeInterval()
			    .getStartTime().getMinute()));

	    if (event.getEventDetails().getTimeInterval().getEndTime() != null) {
		new SimpleOutput(invisiblegroup1, null, null, Messages
			.getString("AgendaWebGUI.EventList.EndTime")
			+ ": "
			+ event.getEventDetails().getTimeInterval()
				.getEndTime().getHour()
			+ ":"
			+ event.getEventDetails().getTimeInterval()
				.getEndTime().getMinute());
	    }

	    if (event.getReminder().getReminderTime() != null) {
		remExists = true;
		Group invisiblegroup2 = new Group(eventG,
			new org.universAAL.middleware.ui.rdf.Label(Messages
				.getString("AgendaWebGUI.EventList.Reminder"),
				(String) null), null, null, (Resource) null);
		new SimpleOutput(invisiblegroup2, null, null, Messages
			.getString("AgendaWebGUI.EventList.Message")
			+ ": " + event.getReminder().getMessage());

		new SimpleOutput(invisiblegroup2, null, null, Messages
			.getString("AgendaWebGUI.EventList.Start")
			+ ": "
			+ event.getReminder().getReminderTime().getDay()
			+ "/"
			+ event.getReminder().getReminderTime().getHour()
			+ "/"
			+ event.getReminder().getReminderTime().getYear()
			+ " "
			+ hour(event.getReminder().getReminderTime().getHour())
			+ ":"
			+ minute(event.getReminder().getReminderTime()
				.getMinute()));
		new SimpleOutput(invisiblegroup2, null, null, Messages
			.getString("AgendaWebGUI.EventList.RepeatTime")
			+ ": " + event.getReminder().getTimesToBeTriggered());
		new SimpleOutput(invisiblegroup2, null, null, Messages
			.getString("AgendaWebGUI.EventList.RepeatInterval")
			+ ": "
			+ event.getReminder().getRepeatInterval()
			/ 60000);
	    }

	    Group delG;
	    if (remExists)
		delG = eventG;
	    else
		delG = invisiblegroup1;

	    Submit submit = new Submit(delG,
		    new org.universAAL.middleware.ui.rdf.Label(Messages
			    .getString("AgendaWebGUI.Delete"), (String) null),
		    "delete" + i);
	    map.put(i, event);
	}
	return f;
    }

    // FIXME Events form; never used
    // public Form getGoogleForm(Calendar cal) {
    //
    // Form f = Form.newDialog(Messages
    // .getString("AgendaWebGUI.AgendaScreenTitle"), (String) null);
    // Group controls = f.getIOControls();
    // // Group submits = f.getSubmits();
    //
    // // FilterParams params = new FilterParams();
    // List<Event> events = Activator.sCaller.requestEventListService(cal);
    //
    // Group GoogleGroup = new Group(controls,
    // new org.universAAL.middleware.ui.rdf.Label("Google",
    // (String) null), null, null, (Resource) null);
    // Group invisiblegroup = new Group(GoogleGroup, null, null, null,
    // (Resource) null);
    //
    // new SimpleOutput(invisiblegroup, null, null, "Here this is it ");
    //
    // return f;
    //
    // }

    public String hour(int hour) {
	if (hour < 10) {
	    return ("0" + (new Integer(hour)).toString());
	} else
	    return ((new Integer(hour)).toString());
    }

    public String minute(int minute) {
	if (minute < 10) {
	    return ("0" + (new Integer(minute)).toString());
	} else
	    return ((new Integer(minute)).toString());
    }

}