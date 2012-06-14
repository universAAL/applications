package org.universAAL.agenda.client;

import java.util.List;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.profile.User;

/**
 * Used for composing service requests towards agenda server. For handling
 * calendars, events and reminders of some user.
 * 
 * @author eandgrg
 * 
 */
public class ServiceRequestCreator {

    private static ServiceRequestCreator instance = null;

    /**
     * Deny instantiation. Singleton.
     */
    protected ServiceRequestCreator() {
    }

    /**
     * 
     * @return ServiceRequestCreator singleton instance
     */
    public static ServiceRequestCreator getInstance() {
	if (instance == null) {
	    instance = new ServiceRequestCreator();
	}
	return instance;
    }

    /**
     * Creates a {@link ServiceRequest} object for getting all calendars.
     * 
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and retrieve <i>all</i>
     *         {@link Calendar} which are managed by the agenda server.
     */
    protected ServiceRequest getAllCalendarsRequest() {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null); // need a service from Calendar/Agenda
	listCalendars.addSimpleOutputBinding(new ProcessOutput(
		AgendaConsumer.OUTPUT_LIST_OF_CALENDARS), (new PropertyPath(
		null, true, new String[] { CalendarAgenda.PROP_CONTROLS })
		.getThePath()));
	return listCalendars;
    }

    /**
     * Creates a {@link ServiceRequest} object for getting all calendars of a
     * specific user.
     * 
     * @param owner
     *            owner of a calendar
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and retrieve <i>all</i>
     *         {@link Calendar} which are managed by the agenda server.
     */
    protected ServiceRequest getCalendarsByOwnerRequest(User owner) {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null);

	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		Calendar.PROP_HAS_OWNER, owner);
	listCalendars.getRequestedService().addInstanceLevelRestriction(
		r1,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_OWNER });

	listCalendars.addSimpleOutputBinding(new ProcessOutput(
		AgendaConsumer.OUTPUT_LIST_OF_CALENDARS), (new PropertyPath(
		null, true, new String[] { CalendarAgenda.PROP_CONTROLS })
		.getThePath()));
	return listCalendars;
    }

    /**
     * Creates a {@link ServiceRequest} object for adding a calendar to specific
     * user.
     * 
     * @param calendar
     *            the calendar
     * @param owner
     *            owner of a calendar
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and create and store a new
     *         Calendar.
     */
    protected ServiceRequest getAddNewCalendarRequest(Calendar calendar,
	    User owner) {
	ServiceRequest addNewcalendar = new ServiceRequest(new CalendarAgenda(
		null), null);
	PropertyPath ppCalendar = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	PropertyPath ppCalendarOwner = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_OWNER });

	addNewcalendar.addAddEffect(ppCalendar.getThePath(), calendar);
	addNewcalendar.addAddEffect(ppCalendarOwner.getThePath(), owner);
	ProcessOutput outCalendar = new ProcessOutput(
		AgendaConsumer.OUTPUT_CALENDAR);
	addNewcalendar.addSimpleOutputBinding(outCalendar, ppCalendar
		.getThePath());

	return addNewcalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object for deleting a calendar with all
     * events.
     * 
     * @param calendar
     *            the calendar to be removed
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and delete an existing calendar
     *         and all events associated with it
     */
    protected ServiceRequest getRemoveCalendarRequest(Calendar calendar) {
	ServiceRequest removeCalendar = new ServiceRequest(new CalendarAgenda(
		null), null);

	PropertyPath ppCalendar = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);
	removeCalendar.getRequestedService().addInstanceLevelRestriction(r1,
		ppCalendar.getThePath());

	removeCalendar.addRemoveEffect(ppCalendar.getThePath());

	return removeCalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object for getting a calendar.
     * 
     * @param calendarName
     *            the name of calendar
     * @param owner
     *            owner of a calendar
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service to get the URI of a calendar
     *         (wrapped in a calendar object) given the name of it
     */
    protected ServiceRequest getGetCalendarByNameAndOwnerRequest(
	    String calendarName, User owner) {
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		Calendar.PROP_NAME, calendarName);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(
		Calendar.PROP_HAS_OWNER, owner);
	PropertyPath ppCalendarName = new PropertyPath(
		null,
		true,
		new String[] { CalendarAgenda.PROP_CONTROLS, Calendar.PROP_NAME });
	PropertyPath ppCalendar = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	PropertyPath ppCalendarOwner = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_OWNER });
	CalendarAgenda ca = new CalendarAgenda(null);
	ca.addInstanceLevelRestriction(r1, ppCalendarName.getThePath());
	ca.addInstanceLevelRestriction(r2, ppCalendarOwner.getThePath());

	ServiceRequest getCalendarByName = new ServiceRequest(ca, null);
	ProcessOutput out = new ProcessOutput(AgendaConsumer.OUTPUT_CALENDAR);
	getCalendarByName.addSimpleOutputBinding(out, ppCalendar.getThePath());

	return getCalendarByName;
    }

    /**
     * Creates a {@link ServiceRequest} object for obtaining specific event from
     * specific calendar.
     * 
     * @param calendar
     *            the new calendar
     * @param eventId
     *            id of a event
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and create and store a new
     *         {@link Calendar} with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getGetCalendarEventRequest(Calendar calendar,
	    int eventId) {
	if (calendar == null) {
	    calendar = new Calendar(null);
	}
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(
		Event.PROP_ID, new Integer(eventId));

	CalendarAgenda ca = new CalendarAgenda(null);
	ca.addInstanceLevelRestriction(r1,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	ca.addInstanceLevelRestriction(r2, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_ID });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	ProcessOutput po = new ProcessOutput(
		AgendaConsumer.OUTPUT_CALENDAR_EVENT);
	po.setCardinality(1, 1);

	ServiceRequest getCalendarEvent = new ServiceRequest(ca, null);
	getCalendarEvent.addSimpleOutputBinding(po, ppEvent.getThePath());
	return getCalendarEvent;
    }

    /**
     * Creates a {@link ServiceRequest} object for obtaining all event
     * categories.
     * 
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and obtain event categories
     */
    protected ServiceRequest getGetAllEventCategoriesRequest() {
	PropertyPath ppEventCategory = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_HAS_EVENT_DETAILS,
			EventDetails.PROP_CATEGORY });
	ProcessOutput po = new ProcessOutput(
		AgendaConsumer.OUTPUT_EVENT_CATEGORIES);
	po.setParameterType(TypeMapper.getDatatypeURI(String.class));
	ServiceRequest getEventCategory = new ServiceRequest(
		new CalendarAgenda(null), null);
	getEventCategory.addSimpleOutputBinding(po, ppEventCategory
		.getThePath());

	return getEventCategory;
    }

    /**
     * Creates a {@link ServiceRequest} object for canceling a reminder.
     * 
     * @param calendar
     *            the new calendar
     * @param eventId
     *            id of a event
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and cancel a reminder
     */
    protected ServiceRequest getCancelReminderRequest(Calendar calendar,
	    int eventId) {
	if (calendar == null) {
	    calendar = new Calendar(null);
	}
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);

	CalendarAgenda ca = new CalendarAgenda(null);
	ca.addInstanceLevelRestriction(r1,
		new String[] { CalendarAgenda.PROP_CONTROLS });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });

	ServiceRequest cancelReminder = new ServiceRequest(ca, null);

	Event e = new Event();
	e.setEventID(eventId);
	cancelReminder.addChangeEffect(ppEvent.getThePath(), e);
	return cancelReminder;
    }

    /**
     * Creates a {@link ServiceRequest} object for updating a event.
     * 
     * @param calendar
     *            the calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @param event
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and get an <code>event</code> from
     *         the calendar with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getUpdateCalendarEventRequest(Calendar calendar,
	    int eventId, Event event) {
	ServiceRequest getUpdateEvent = new ServiceRequest(new CalendarAgenda(
		null), null);
	if (calendar == null) {
	    calendar = new Calendar(null);
	}
	System.out.println(calendar.getURI());
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(
		Event.PROP_ID, new Integer(eventId));

	PropertyPath pp = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	getUpdateEvent.addChangeEffect(pp.getThePath(), event);

	getUpdateEvent.getRequestedService().addInstanceLevelRestriction(r1,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	getUpdateEvent.getRequestedService().addInstanceLevelRestriction(
		r2,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_ID });
	return getUpdateEvent;
    }

    /**
     * Creates a {@link ServiceRequest} object for setting a reminder for an
     * event.
     * 
     * @param c
     *            calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @param reminder
     *            reminder
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and get an event</code> from the
     *         calendar with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getSetEventReminderRequest(Calendar c,
	    int eventId, Reminder reminder) {
	ServiceRequest getSetReminder = new ServiceRequest(new CalendarAgenda(
		null), null);
	if (c == null) {
	    c = new Calendar(null);
	}
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(
		Event.PROP_ID, new Integer(eventId));

	PropertyPath pp = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_HAS_REMINDER });
	getSetReminder.addChangeEffect(pp.getThePath(), reminder);

	getSetReminder.getRequestedService().addInstanceLevelRestriction(r1,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	getSetReminder.getRequestedService().addInstanceLevelRestriction(
		r2,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_ID });
	return getSetReminder;
    }

    /**
     * Creates a {@link ServiceRequest} object for setting a reminder type
     * 
     * @param c
     *            calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @param reminderType
     *            type of a reminder {@link ReminderType}
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and get an event</code> from the
     *         calendar with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getSetReminderTypeRequest(Calendar c, int eventId,
	    ReminderType reminderType) {
	ServiceRequest getSetReminderType = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar(null);
	}
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(
		Event.PROP_ID, new Integer(eventId));

	PropertyPath pp = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_HAS_REMINDER, Reminder.PROP_HAS_TYPE });
	getSetReminderType.addChangeEffect(pp.getThePath(), reminderType);

	getSetReminderType.getRequestedService().addInstanceLevelRestriction(
		r1, new String[] { CalendarAgenda.PROP_CONTROLS });
	getSetReminderType.getRequestedService().addInstanceLevelRestriction(
		r2,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_ID });
	return getSetReminderType;
    }

    /**
     * Creates a {@link ServiceRequest} object for deleting calendar event
     * 
     * @param c
     *            calendar
     * @param eventId
     *            the id of the event to be deleted
     * @return a {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and get an event</code> from the
     *         calendar with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getDeleteCalendarEventRequest(Calendar c,
	    int eventId) {
	ServiceRequest deleteCalendarEvent = new ServiceRequest(
		new CalendarAgenda(null), null);

	if (c == null) {
	    c = new Calendar();
	}

	// MergedRestriction r1 =
	// MergedRestriction.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS,
	// c);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(
		Event.PROP_ID, new Integer(eventId));

	// deleteCalendarEvent.getRequestedService().addInstanceLevelRestriction(r1,
	// new String[] { CalendarAgenda.PROP_CONTROLS });
	deleteCalendarEvent.getRequestedService().addInstanceLevelRestriction(
		r2,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_ID });
	Event e = new Event();
	e.setEventID(eventId);
	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });

	deleteCalendarEvent.addRemoveEffect(ppEvent.getThePath());
	return deleteCalendarEvent;
    }

    /**
     * Creates a {@link ServiceRequest} object for getting events of a given
     * calendar.
     * 
     * @param c
     *            calendar
     * @return {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and retrieve <i>all</i> events (as
     *         a {@link List}) of the calendar with the specified URI
     *         <code>calendarURI</code>.
     */
    protected ServiceRequest getGetCalendarEventListRequest(Calendar c) {
	ServiceRequest getCalendarEventList = new ServiceRequest(
		new CalendarAgenda(null), null); // need a service from
	// Calendar/Agenda
	if (c == null) {
	    c = new Calendar();
	}
	MergedRestriction r = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	getCalendarEventList.getRequestedService().addInstanceLevelRestriction(
		r, new String[] { CalendarAgenda.PROP_CONTROLS });

	ProcessOutput po = new ProcessOutput(
		AgendaConsumer.OUTPUT_CALENDAR_EVENT_LIST);
	po.setParameterType(Event.MY_URI);
	getCalendarEventList.addSimpleOutputBinding(po, (new PropertyPath(null,
		true, new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT }).getThePath()));
	return getCalendarEventList;
    }

    /**
     * Creates a {@link ServiceRequest} object for adding given event to given
     * calendar.
     * 
     * @param c
     *            calendar
     * @param event
     *            the event to be stored
     * @return {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and add an <code>
     * event</code> to the
     *         calendar with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getAddEventToCalendarRequest(Calendar c,
	    Event event) {
	ServiceRequest addEventToCalendar = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar();
	}
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	addEventToCalendar.getRequestedService().addInstanceLevelRestriction(
		r1, new String[] { CalendarAgenda.PROP_CONTROLS });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	addEventToCalendar.addAddEffect(ppEvent.getThePath(), event);
	ProcessOutput output = new ProcessOutput(
		AgendaConsumer.OUTPUT_ADDED_EVENT_ID);
	PropertyPath ppEventID = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_ID });

	addEventToCalendar.addSimpleOutputBinding(output, ppEventID
		.getThePath());
	return addEventToCalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object for adding list of events to
     * given calendar.
     * 
     * @param c
     *            calendar
     * @param eventList
     *            the event list to be stored
     * @return {@link ServiceRequest} object for the specific service
     *         {@link CalendarAgenda} service and add an <code>
     * event</code> list to
     *         the calendar with the specified URI <code>calendarURI</code>.
     */
    protected ServiceRequest getAddEventListToCalendarRequest(Calendar c,
	    List eventList) {
	ServiceRequest addEventToCalendar = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar();
	}
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	addEventToCalendar.getRequestedService().addInstanceLevelRestriction(
		r1, new String[] { CalendarAgenda.PROP_CONTROLS });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	addEventToCalendar.addAddEffect(ppEvent.getThePath(), eventList);

	return addEventToCalendar;
    }

}
