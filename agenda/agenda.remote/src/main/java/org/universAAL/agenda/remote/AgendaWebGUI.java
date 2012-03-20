/*
t	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
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

import java.util.Collections;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import org.universAAL.middleware.io.rdf.ChoiceItem;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Group;
import org.universAAL.middleware.io.rdf.InputField;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.Range;
import org.universAAL.middleware.io.rdf.Select1;
import org.universAAL.middleware.io.rdf.SimpleOutput;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.io.rdf.TextArea;
import org.universAAL.middleware.owl.OrderingRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.agendaEventSelectionTool.ont.FilterParams;

public class AgendaWebGUI {

    private static final String uAAL_NAMESPACE_PREFIX = "http://ontology.aal-uAAL.org/"; //$NON-NLS-1$
    public static final String uAAL_INPUT_NAMESPACE = uAAL_NAMESPACE_PREFIX
	    + "Input.owl#"; //$NON-NLS-1$
    public static final String SCREEN_TITLE = "Agenda Event Editor";
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

    public static HashMap<Integer, Event> map;

    public Form getMainScreenMenuForm() {
	Form f = Form.newDialog(SCREEN_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	new SimpleOutput(controls, null, null, Messages
		.getString("AgendaWebGUI.1"));

	List calendars = Activator.guicaller
		.getCalendarsByOwnerService(new User(
			Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied"));
	// TODO Change this in the future for multiuser

	if (calendars.isEmpty()) {
	    new SimpleOutput(controls, null, null, Messages
		    .getString("AgendaWebGUI.2"));
	} else {
	    java.util.Calendar now = java.util.Calendar.getInstance();
	    java.util.Calendar nowrem = java.util.Calendar.getInstance();
	    now.add(java.util.Calendar.DAY_OF_YEAR, 1);
	    nowrem.add(java.util.Calendar.DAY_OF_YEAR, 1);
	    nowrem.add(java.util.Calendar.MINUTE, -15);

	    // Select Calendar control
	    Select1 calselect = new Select1(
		    controls,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.4"), (String) null),
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
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.5"), (String) null), null,
		    null, (Resource) null);
	    Group invisiblegroupdate = new Group(dategroup, null, null, null,
		    (Resource) null);// This group is for ordering inputs
	    // vertically
	    new SimpleOutput(invisiblegroupdate, null, null, Messages
		    .getString("AgendaWebGUI.6"));
	    // Day
	    new Range(invisiblegroupdate,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.7"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_DAY }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(31), Integer.valueOf(1), true, true,
			    REF_DAY), new Integer(now
			    .get(java.util.Calendar.DAY_OF_MONTH)));
	    // Month
	    new Range(invisiblegroupdate,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.8"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_MONTH }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(12), Integer.valueOf(1), true, true,
			    REF_MONTH), new Integer(now
			    .get(java.util.Calendar.MONTH) + 1));
	    // Year
	    Select1 yearselect = new Select1(invisiblegroupdate,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.9"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_YEAR }),
		    null, null);
	    for (int i = 2011; i < 2020; i++) {
		yearselect.addChoiceItem(new ChoiceItem(Integer.toString(i),
			(String) null, new Integer(i)));
	    }
	    // Hour
	    new Range(dategroup, new org.universAAL.middleware.io.rdf.Label(
		    Messages.getString("AgendaWebGUI.10"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_HOUR }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(23), Integer.valueOf(0), true, true,
			    REF_HOUR), new Integer(now
			    .get(java.util.Calendar.HOUR_OF_DAY)));
	    // Minute
	    new Range(dategroup, new org.universAAL.middleware.io.rdf.Label(
		    Messages.getString("AgendaWebGUI.11"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_MIN }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(59), Integer.valueOf(0), true, true,
			    REF_MIN), new Integer(now
			    .get(java.util.Calendar.MINUTE)));

	    // Input Info Control
	    Group infogroup = new Group(controls,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.12"), (String) null),
		    null, null, (Resource) null);
	    Group invisiblegroup = new Group(infogroup, null, null, null,
		    (Resource) null);// This group is for ordering inputs
	    // vertically
	    new SimpleOutput(invisiblegroup, null, null, Messages
		    .getString("AgendaWebGUI.13"));
	    new InputField(invisiblegroup, new Label(Messages
		    .getString("AgendaWebGUI.14"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_TYPE }),
		    null, "");
	    new InputField(invisiblegroup, new Label(Messages
		    .getString("AgendaWebGUI.15"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_PLACE }),
		    null, "");
	    new TextArea(invisiblegroup, new Label(Messages
		    .getString("AgendaWebGUI.16"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_DESC }),
		    null, "");

	    // Reminder Group controls
	    Group remindergroup = new Group(controls,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.17"), (String) null),
		    null, null, (Resource) null);
	    Group invisiblegroup2 = new Group(remindergroup, null, null, null,
		    (Resource) null);// This group is for ordering inputs
	    // vertically
	    new SimpleOutput(invisiblegroup2, null, null, Messages
		    .getString("AgendaWebGUI.18"));
	    new InputField(
		    invisiblegroup2,
		    new Label(Messages.getString("AgendaWebGUI.19"),
			    (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_MSG }),
		    null, "");
	    Group remdategroup = new Group(invisiblegroup2,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.5"), (String) null), null,
		    null, (Resource) null);
	    Group remdateinvisiblegroup = new Group(remdategroup, null, null,
		    null, (Resource) null);// This group is for ordering inputs
	    // vertically
	    // Day
	    new Range(
		    remdateinvisiblegroup,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.7"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_DAY }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(31), Integer.valueOf(1), true, true,
			    REF_REM_DAY), new Integer(nowrem
			    .get(java.util.Calendar.DAY_OF_MONTH)));
	    // Month
	    new Range(remdateinvisiblegroup,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.8"), (String) null),
		    new PropertyPath(null, false,
			    new String[] { REF_REM_MONTH }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(12), Integer.valueOf(1), true, true,
			    REF_REM_MONTH), new Integer(nowrem
			    .get(java.util.Calendar.MONTH) + 1));
	    // Year
	    Select1 remyearselect = new Select1(
		    remdateinvisiblegroup,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.9"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_YEAR }),
		    null, null);
	    for (int i = 2011; i < 2020; i++) {
		remyearselect.addChoiceItem(new ChoiceItem(Integer.toString(i),
			(String) null, new Integer(i)));
	    }
	    // Hour
	    new Range(
		    remdategroup,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.10"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_HOUR }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(23), Integer.valueOf(0), true, true,
			    REF_REM_HOUR), new Integer(nowrem
			    .get(java.util.Calendar.HOUR_OF_DAY)));
	    // Minute
	    new Range(
		    remdategroup,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.11"), (String) null),
		    new PropertyPath(null, false, new String[] { REF_REM_MIN }),
		    OrderingRestriction.newOrderingRestriction(Integer
			    .valueOf(59), Integer.valueOf(0), true, true,
			    REF_REM_MIN), new Integer(nowrem
			    .get(java.util.Calendar.MINUTE)));

	    Group remrepeatgroup = new Group(invisiblegroup2,
		    new org.universAAL.middleware.io.rdf.Label("Repeat",
			    (String) null), null, null, (Resource) null);
	    Select1 remrepeatselect = new Select1(remrepeatgroup,
		    new org.universAAL.middleware.io.rdf.Label("Times    ",
			    (String) null), new PropertyPath(null, false,
			    new String[] { REF_REM_REP }), null, null);
	    for (int i = 1; i < 10; i++) {
		remrepeatselect.addChoiceItem(new ChoiceItem(Integer
			.toString(i), (String) null, new Integer(i)));
	    }
	    Select1 remintervalselect = new Select1(remrepeatgroup,
		    new org.universAAL.middleware.io.rdf.Label(
			    "Interval(min) ", (String) null), new PropertyPath(
			    null, false, new String[] { REF_REM_INT }), null,
		    null);
	    for (int i = 1; i < 60; i++) {
		remintervalselect.addChoiceItem(new ChoiceItem(Integer
			.toString(i), (String) null, new Integer(i)));
	    }

	    // Submit
	    new Submit(submits, new org.universAAL.middleware.io.rdf.Label(
		    Messages.getString("AgendaWebGUI.24"), (String) null),
		    "Events");
	    new Submit(submits, new org.universAAL.middleware.io.rdf.Label(
		    Messages.getString("AgendaWebGUI.20"), (String) null),
		    "submit");

	    // new Submit(submits, new org.universAAL.middleware.io.rdf.Label(
	    // Messages.getString("AgendaWebGUI.28"), (String) null),
	    // "google");
	    new Submit(submits, new org.universAAL.middleware.io.rdf.Label(
		    Messages.getString("AgendaWebGUI.21"), (String) null),
		    "home");
	}
	return f;

    }

    public Form getMessageForm(String msg) {
	Form f = Form.newDialog(SCREEN_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	new SimpleOutput(controls, null, null, msg);
	new SimpleOutput(controls, null, null, Messages
		.getString("AgendaWebGUI.22"));
	new Submit(submits, new org.universAAL.middleware.io.rdf.Label(Messages
		.getString("AgendaWebGUI.23"), (String) null), "add");
	// new Submit(submits, new
	// org.universAAL.middleware.io.rdf.Label(Messages
	// .getString("AgendaWebGUI.21"), (String) null), "home");
	return f;
    }

    // SC2011 Events form
    public Form getEventsForm(Calendar cal) {
	Form f = Form.newDialog(SCREEN_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();
	map = new HashMap();

	// List calendars = Activator.guicaller
	// .getCalendarsByOwnerService(new User(
	// Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied"));
	// //// TODO Change this in the future for multiuser
	//
	// if (calendars.isEmpty()) {
	// new SimpleOutput(controls, null, null, Messages
	// .getString("AgendaWebGUI.2"));
	//	
	// }

	FilterParams params = new FilterParams();
	List<Event> events = Activator.guicaller.requestEventListService(
		new User(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied"),
		cal);

	Collections.sort(events, new MyEventComparator());

	new Submit(submits, new org.universAAL.middleware.io.rdf.Label(Messages
		.getString("AgendaWebGUI.26"), (String) null), "Event_editor");

	// Pocetak stvaranja forme

	Group eventsGroup = new Group(controls,
		new org.universAAL.middleware.io.rdf.Label("Events",
			(String) null), null, null, (Resource) null);
	Group invisiblegroup = new Group(eventsGroup, null, null, null,
		(Resource) null);

	Submit submit;

	for (int i = 0; i < events.size(); i++) {
	    boolean remExists = false;
	    Event event = new Event();
	    event = (Event) events.get(i);
	    Group eventG = new Group(invisiblegroup,
		    new org.universAAL.middleware.io.rdf.Label("Event: "
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
	    new SimpleOutput(invisiblegroup1, null, null, "Category: "
		    + event.getEventDetails().getCategory());
	    new SimpleOutput(invisiblegroup1, null, null, "Description: "
		    + event.getEventDetails().getDescription());
	    new SimpleOutput(invisiblegroup1, null, null, "Place Name: "
		    + event.getEventDetails().getPlaceName());
	    new SimpleOutput(invisiblegroup1, null, null, "Begin time: "
		    + hour(event.getEventDetails().getTimeInterval()
			    .getStartTime().getHour())
		    + ":"
		    + minute(event.getEventDetails().getTimeInterval()
			    .getStartTime().getMinute()));

	    if (event.getEventDetails().getTimeInterval().getEndTime() != null) {
		new SimpleOutput(invisiblegroup1, null, null, "End time: "
			+ event.getEventDetails().getTimeInterval()
				.getEndTime().getHour()
			+ ":"
			+ event.getEventDetails().getTimeInterval()
				.getEndTime().getMinute());
	    }

	    if (event.getReminder().getReminderTime() != null) {
		remExists = true;
		Group invisiblegroup2 = new Group(eventG,
			new org.universAAL.middleware.io.rdf.Label("Reminder",
				(String) null), null, null, (Resource) null);
		new SimpleOutput(invisiblegroup2, null, null, "Message: "
			+ event.getReminder().getMessage());

		new SimpleOutput(invisiblegroup2, null, null, "Start: "
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
		new SimpleOutput(invisiblegroup2, null, null, "Repeat Time: "
			+ event.getReminder().getTimesToBeTriggered());
		new SimpleOutput(invisiblegroup2, null, null,
			"Repeat Interval: "
				+ event.getReminder().getRepeatInterval()
				/ 60000);
	    }

	    Group delG;
	    if (remExists)
		delG = eventG;
	    else
		delG = invisiblegroup1;

	    submit = new Submit(delG,
		    new org.universAAL.middleware.io.rdf.Label(Messages
			    .getString("AgendaWebGUI.27"), (String) null),
		    "delete" + i);

	    map.put(i, event);

	}

	return f;
    }

    // SC2011 Events form
    public Form getGoogleForm(Calendar cal) {

	Form f = Form.newDialog(SCREEN_TITLE, (String) null);
	Group controls = f.getIOControls();
	Group submits = f.getSubmits();

	FilterParams params = new FilterParams();
	List<Event> events = Activator.guicaller.requestEventListService(
		new User(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied"),
		cal);
	// Pocetak stvaranja forme

	Group GoogleGroup = new Group(controls,
		new org.universAAL.middleware.io.rdf.Label("Google",
			(String) null), null, null, (Resource) null);
	Group invisiblegroup = new Group(GoogleGroup, null, null, null,
		(Resource) null);

	new SimpleOutput(invisiblegroup, null, null, "Evo igore to je to ");

	return f;

    }

    public String hour(int hour) {

	if (hour < 10) {

	    return ("0" + (new Integer(hour)).toString());
	}

	else
	    return ((new Integer(hour)).toString());
    }

    public String minute(int minute) {

	if (minute < 10) {

	    return ("0" + (new Integer(minute)).toString());
	}

	else
	    return ((new Integer(minute)).toString());
    }

}