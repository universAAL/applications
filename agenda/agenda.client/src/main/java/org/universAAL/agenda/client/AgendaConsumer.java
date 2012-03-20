/**
 * 
 */
package org.universAAL.agenda.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.location.address.Address;
import org.universAAL.ontology.location.outdoor.City;
import org.universAAL.ontology.location.outdoor.CityPlace;
import org.universAAL.ontology.location.outdoor.CityQuarter;
import org.universAAL.ontology.location.outdoor.Country;
import org.universAAL.ontology.location.outdoor.Region;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * 
 * @author eandgrg
 * 
 */
public class AgendaConsumer extends ContextSubscriber {
    private static final String AGENDA_CLIENT_NAMESPACE = "http://ontology.universAAL.org/AgendaClient.owl#";

    private static final String OUTPUT_LIST_OF_CALENDARS = AGENDA_CLIENT_NAMESPACE
	    + "oListOfCalendars";
    private static final String OUTPUT_CALENDAR_EVENT_LIST = AGENDA_CLIENT_NAMESPACE
	    + "oCalendarEventList";
    private static final String OUTPUT_ADDED_EVENT_ID = AGENDA_CLIENT_NAMESPACE
	    + "oAddedEventId";
    private static final String OUTPUT_CALENDAR_EVENT = AGENDA_CLIENT_NAMESPACE
	    + "oCalendarEvent";
    private static final String OUTPUT_EVENT_CATEGORIES = AGENDA_CLIENT_NAMESPACE
	    + "oEventCategories";
    private static final String OUTPUT_CALENDAR = AGENDA_CLIENT_NAMESPACE
	    + "oCalendar";

    private static final Logger mainLogger = LoggerFactory
	    .getLogger(AgendaConsumer.class);

    private static ContextEventPattern[] getContextSubscriptionParams() {
	// I am interested in all events with a calendars as subject
	ContextEventPattern cep1 = new ContextEventPattern();
	ContextEventPattern cep2 = new ContextEventPattern();
	cep1.addRestriction(Restriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, Event.MY_URI));
	cep2.addRestriction(Restriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, Calendar.MY_URI));
	return new ContextEventPattern[] { cep1, cep2 };
    }

    private ServiceCaller caller;
    private ModuleContext mcontext;

    public AgendaConsumer(ModuleContext mcontext) {
	super(mcontext, getContextSubscriptionParams());
	this.mcontext = mcontext;
	caller = new DefaultServiceCaller(mcontext);

	// debugTest();
	Event event = createEvent(13, 40, 00);
	// Calendar c = getCalendarByNameAndOwnerService("my cal", new
	// User(User.MY_URI + "kostas"));

	// ServiceResponse sr = this.caller.call(getAddEventToCalendar(c,
	// event));

    }

    public void debugTest() {

	Calendar c = new Calendar();
	c.setName("my cal");
	c = addNewCalendarService(c, new User(User.MY_URI + "kostas"));
	if (c == null) {
	    System.out.println("No calendar:(");
	    return;
	}
	System.out.println("NEW CALENDAR");
	System.out.println("URI: " + c.getURI());
	System.out.println("Name: " + c.getName());
	// System.out.println("Owner: " + c.getOwner().get getOwner().getURI());

	Calendar cc = new Calendar(c.getURI());
	// cc.setName("my cal");
	// cc.setOwner(new User(User.MY_URI + "kostas"));
	// boolean succeed = removeCalendarService(new Calendar(c.getURI()));
	boolean succeed = removeCalendarService(cc);
	if (succeed)
	    System.out.println("Calendar has been removed!");
	else
	    System.out.println("Calendar hasn't been removed");
	// List l = getCalendarsByOwnerService(new User(User.MY_URI +
	// "kostas"));
	// System.out.println("Number of user's calendars: " + l.size());
	// for (Iterator it = l.iterator(); it.hasNext();) {
	// System.out.println(((Calendar)it.next()).getName());
	// }
	// removeCalendarService(c);
    }

    // just local code
    public List getAllCalendarsService() {
	List allCalendars = new ArrayList();

	long endTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAllCalendarsRequest());
	long startTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'get all Calendars\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'get all Calendars\' (" + endTime
		+ ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_LIST_OF_CALENDARS);
		if (o instanceof List)
		    allCalendars = (List) o;
		else if (o instanceof Calendar) {
		    allCalendars.add((Calendar) o);
		}
		if (o == null)
		    Activator.log.log(LogService.LOG_INFO,
			    "Calendar List was not retrieved");
		else
		    Activator.log.log(LogService.LOG_INFO,
			    "Calendar List was retrieved. Size = : "
				    + allCalendars.size());
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_INFO, " Exception: "
			+ e.getMessage());
		return null;
	    }
	} else {
	    Activator.log.log(LogService.LOG_INFO,
		    "Calendar list was not retrieved");
	    Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus()
		    .toString());
	}
	return allCalendars;
    }

    // just local code
    public List getCalendarsByOwnerService(User owner) {
	List allCalendars = new ArrayList();

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller
		.call(getCalendarsByOwnerRequest(owner));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'get calendars by owner\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'get calendars by owner\' ("
		+ endTime + ")" + "\n" + "Agenda\tTime delay: "
		+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_LIST_OF_CALENDARS);
		if (o instanceof List)
		    allCalendars = (List) o;
		else if (o instanceof Calendar) {
		    allCalendars.add((Calendar) o);
		}
		if (o == null)
		    Activator.log.log(LogService.LOG_INFO,
			    "Calendar List was not retrieved");
		else
		    Activator.log.log(LogService.LOG_INFO,
			    "Calendar List was retrieved. Size = : "
				    + allCalendars.size());
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_INFO, " Exception: "
			+ e.getMessage());
		return null;
	    }
	} else {
	    Activator.log.log(LogService.LOG_INFO,
		    "Calendar list was not retrieved");
	    Activator.log.log(LogService.LOG_INFO, sr.getCallStatus()
		    .toString());
	}
	return allCalendars;
    }

    // just local code
    public Calendar addNewCalendarService(Calendar c, User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAddNewCalendar(c, owner));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'add new calendar\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'add new calendar\' (" + endTime
		+ ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR);
		if (o instanceof Calendar) {
		    Activator.log
			    .log(LogService.LOG_INFO, "Calendar was added");
		    return (Calendar) o;
		} else {
		    Activator.log
			    .log(LogService.LOG_INFO,
				    "Calendar may not have been added - Wrong service output");
		}
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_INFO, " Exception: "
			+ e.getMessage());
	    }
	} else {
	    Activator.log.log(LogService.LOG_INFO, "Calendar was not added");
	    Activator.log.log(LogService.LOG_INFO, sr.getCallStatus()
		    .toString());
	}
	return null;

    }

    // just local code
    public boolean removeCalendarService(Calendar c) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getRemoveCalendar(c));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'remove calendar\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'remove calendar\' (" + endTime
		+ ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    Activator.log.log(LogService.LOG_INFO,
		    "Calendar -and its assosiated events- were removed");
	    return true;
	}

	Activator.log.log(LogService.LOG_INFO, "Calendar was not removed");
	Activator.log.log(LogService.LOG_INFO, sr.getCallStatus().toString());
	// Activator.log.log(LogService.LOG_INFO,
	// getReturnValue(sr.getOutputs(),
	// ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR).toString());
	return false;
    }

    // just local code
    public Calendar getCalendarByNameAndOwnerService(String calendarName,
	    User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getCalendarByNameAndOwner(
		calendarName, owner));
	long endTime = System.currentTimeMillis();
	mainLogger
		.info("Agenda\tService called: \'get calendar by name and owner\' ("
			+ startTime
			+ ")"
			+ "\n"
			+ "Agenda\tService returned: \'get calendar by name and owner\' ("
			+ endTime
			+ ")"
			+ "\n"
			+ "Agenda\tTime delay: "
			+ (endTime - startTime));

	Calendar calendar = null;
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR);
		if (o instanceof Calendar)
		    calendar = (Calendar) o;
		if (calendar == null)
		    Activator.log.log(LogService.LOG_INFO,
			    "Calendar URI was not retrieved");
		else
		    Activator.log.log(LogService.LOG_INFO,
			    "Calendar URI was retrieved: " + calendar.getURI());
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_INFO, " Exception: "
			+ e.getMessage());
		return null;
	    }
	} else {
	    Activator.log
		    .log(LogService.LOG_INFO, "Calendar was not retrieved");
	    Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus()
		    .toString());
	}
	return calendar;
    }

    // just local code
    public Event getCalendarEventService(Calendar c, int eventId) {
	Event event = null;

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getGetCalendarEvent(c, eventId));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'get calendar event\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'get calendar event\' ("
		+ endTime + ")" + "\n" + "Agenda\tTime delay: "
		+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_CALENDAR_EVENT);
		if (o instanceof Event)
		    event = (Event) o;
		else if (o instanceof List && !((List) o).isEmpty()
			&& ((List) o).get(0) instanceof Event)
		    event = (Event) ((List) o).get(0);
		if (event == null)
		    Activator.log.log(LogService.LOG_INFO, "Calendar: " + c
			    + ": there is not any event with id " + eventId);
		else
		    Activator.log.log(LogService.LOG_INFO, "Calendar: " + c
			    + ":\nEvent id info:\n\n" + o.toString());
		Activator.log.log(LogService.LOG_INFO,
			"Event has been retrieved");
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_INFO, " Exception: "
			+ e.getMessage());
		return null;
	    }
	} else {
	    Activator.log.log(LogService.LOG_INFO,
		    "Event has not been retrieved");
	    Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus()
		    .toString());
	}
	return event;
    }

    // just local code
    public List getAllEventCategories() {
	List allCategories = new ArrayList();

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getGetAllEventCategories());
	long endTime = System.currentTimeMillis();
	mainLogger
		.info("Agenda\tService called: \'get all event categories\' ("
			+ startTime
			+ ")"
			+ "\n"
			+ "Agenda\tService returned: \'get all event categories\' ("
			+ endTime + ")" + "\n" + "Agenda\tTime delay: "
			+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_EVENT_CATEGORIES);
		if (o instanceof String)
		    allCategories.add((String) o);
		else if (o instanceof List && !((List) o).isEmpty())
		    allCategories = (List) (List) o;

		if (o == null)
		    Activator.log.log(LogService.LOG_INFO,
			    "There is not any stored event category");
		else
		    Activator.log.log(LogService.LOG_INFO,
			    "There are stored event categories");
		Activator.log.log(LogService.LOG_INFO,
			"Categories were retrieved");
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_INFO, " Exception: "
			+ e.getMessage());
		return null;
	    }
	} else {
	    Activator.log.log(LogService.LOG_INFO,
		    "Event categories were not retrieved");
	    Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus()
		    .toString());
	}
	return allCategories;
    }

    // just local code
    public boolean cancelReminderService(Calendar c, int eventId) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getCancelReminder(c, eventId));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'cancel reminder\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'cancel reminder\' (" + endTime
		+ ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));

	if (!(sr.getCallStatus() == CallStatus.succeeded)) {
	    Activator.log.log(LogService.LOG_INFO, "Reminder was canceled");
	    Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus()
		    .toString());
	    return false;
	}
	Activator.log.log(LogService.LOG_INFO, "Reminder was canceled");
	return true;
    }

    // just local code
    public boolean updateCalendarEventService(Calendar c, int eventId,
	    Event event) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getUpdateCalendarEvent(c,
		eventId, event));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'update calendar event\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'update calendar event\' ("
		+ endTime + ")" + "\n" + "Agenda\tTime delay: "
		+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Activator.log.log(LogService.LOG_INFO, "Event was updated");
	    } catch (Exception e) {
		System.out.println("Exception1: " + e.getMessage());
		return false;
	    }
	    return true;
	}
	Activator.log.log(LogService.LOG_INFO, "Event was not updated");
	Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus().toString());
	return false;

    }

    // just local code
    public boolean setEventReminderService(Calendar c, int eventID,
	    Reminder reminder) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getSetEventReminder(c, eventID,
		reminder));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'set event reminder\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'set event reminder\' ("
		+ endTime + ")" + "\n" + "Agenda\tTime delay: "
		+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Activator.log.log(LogService.LOG_INFO,
			"Event reminder has been set");
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_DEBUG, "Exception: "
			+ e.getMessage());
		return false;
	    }
	    return true;
	}
	Activator.log.log(LogService.LOG_INFO,
		"Event reminder has not been set");
	Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus().toString());
	return false;
    }

    // just local code
    public boolean setReminderTypeService(Calendar c, int eventID,
	    ReminderType reminderType) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getSetReminderType(c, eventID,
		reminderType));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'set reminder type\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'set reminder type\' (" + endTime
		+ ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Activator.log.log(LogService.LOG_INFO,
			"Event reminder type has been set");
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_DEBUG, "Exception: "
			+ e.getMessage());
		return false;
	    }
	    return true;
	}

	Activator.log.log(LogService.LOG_INFO,
		"Event reminder type has not been set");
	Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus().toString());
	return false;
    }

    // just local code
    public boolean deleteCalendarEventService(Calendar c, int eventId) {
	long startTime = System.currentTimeMillis();
	caller = new DefaultServiceCaller(mcontext);
	ServiceRequest srq = getDeleteCalendarEvent(c, eventId);
	ServiceResponse sr = caller.call(srq);

	long endTime = System.currentTimeMillis();
	mainLogger.info("Agenda\tService called: \'delete calendar event\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'delete calendar event\' ("
		+ endTime + ")" + "\n" + "Agenda\tTime delay: "
		+ (endTime - startTime));
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Activator.log.log(LogService.LOG_INFO, "Event was deleted");
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_DEBUG, "Exception: "
			+ e.getMessage());
		return false;
	    }
	    return true;
	}

	Activator.log.log(LogService.LOG_INFO, "Event was not deleted");
	Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus().toString());
	return false;
    }

    // just local coding
    public List requestEventListService(Calendar c) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getGetCalendarEventList(c));
	long endTime = System.currentTimeMillis();
	mainLogger.info("Service called: \'get calendar event list\' ("
		+ startTime + ")" + "\n"
		+ "Service returned: \'get calendar event list\' (" + endTime
		+ ")" + "\n" + "Time delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		List events = (List) getReturnValue(sr.getOutputs(),
			OUTPUT_CALENDAR_EVENT_LIST);
		if (events == null || events.size() == 0) {
		    Activator.log
			    .log(LogService.LOG_INFO,
				    "Event List has been retreived, but it's empty or NULL");
		    return new ArrayList();
		}
		Activator.log.log(LogService.LOG_INFO,
			"Event List was retreived");
		return events;
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_DEBUG, "Exception: "
			+ e.getMessage());
		return new ArrayList(0);
	    }
	}
	Activator.log.log(LogService.LOG_INFO, "Event List was not retreived");
	Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus().toString());
	return new ArrayList(0);

    }

    // just local coding
    public int addEventToCalendarService(Calendar c, Event event) {

	long startTime = System.currentTimeMillis();

	ServiceResponse sr = this.caller.call(getAddEventToCalendar(c, event));

	long endTime = System.currentTimeMillis();
	mainLogger.info("Service called: \'add event to calendar\' ("
		+ startTime + ")" + "\n"
		+ "Service returned: \'add event to calendar\' (" + endTime
		+ ")" + "\n" + "Time delay: " + (endTime - startTime));

	int eventId;
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_ADDED_EVENT_ID);
		if (o == null)
		    eventId = -1;
		else
		    eventId = ((Integer) o).intValue();

		if (eventId <= 0) {
		    Activator.log
			    .log(LogService.LOG_INFO,
				    "Event was not added to calendar for unknown reason");
		} else {
		    Activator.log.log(LogService.LOG_INFO,
			    "Event was added to calendar");
		}
		return eventId;
	    } catch (Exception e) {
		Activator.log.log(LogService.LOG_DEBUG, "Exception: "
			+ e.getMessage());
		return -1;
	    }
	}

	Activator.log.log(LogService.LOG_INFO,
		"Event was not added to calendar");
	Activator.log.log(LogService.LOG_DEBUG, sr.getCallStatus().toString());
	return -1;
    }

    // just local coding
    public boolean addCalendarEventListService(Calendar c, List eventList) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller
		.call(getAddEventListToCalendar(c, eventList));
	long endTime = System.currentTimeMillis();
	mainLogger
		.info("Service called: \'add event list to calendar\' ("
			+ startTime + ")" + "\n"
			+ "Service returned: \'add event list to calendar\' ("
			+ endTime + ")" + "\n" + "Time delay: "
			+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    Activator.log.log(LogService.LOG_INFO,
		    "Event List was added to calendar");
	    return true;
	}

	Activator.log.log(LogService.LOG_INFO,
		"Event List was not added to calendar");
	return false;
    }

    /************************************************************************
     * REAL SERVICE CALLS *
     ************************************************************************/
    // real calls

    /**
     * Sends to logger a formated representation of all <code>events</code>,
     * assuming they belong to the {@link Calendar} <code>c</code>.
     * 
     * @param calendarURI
     *            a calendarURI
     * @param events
     *            a list of events
     */
    public void printEvents(String calendarURI, List events) {
	Activator.log.log(LogService.LOG_INFO, "Calendar " + calendarURI
		+ " has " + events.size() + " event(s) stored:");
	for (Iterator it = events.listIterator(); it.hasNext();) {
	    Event e = (Event) it.next();
	    Activator.log.log(LogService.LOG_INFO, e.toString());
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.persona.middleware.context.ContextSubscriber#communicationChannelBroken
     * ()
     */
    public void communicationChannelBroken() {

    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and retrieve <i>all</i> events (as a
     * {@link List}) of the calendar with the specified URI
     * <code>calendarURI</code>.
     * 
     * @param calendarURI
     *            the URI of the calendar
     * @return a service request for the specific service
     */
    private ServiceRequest getGetCalendarEventList(Calendar c) {
	ServiceRequest getCalendarEventList = new ServiceRequest(
		new CalendarAgenda(null), null); // need
	// a
	// service
	// from
	// Calendar/Agenda
	if (c == null) {
	    c = new Calendar();
	}
	Restriction r = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	getCalendarEventList.getRequestedService().addInstanceLevelRestriction(
		r, new String[] { CalendarAgenda.PROP_CONTROLS });

	ProcessOutput po = new ProcessOutput(OUTPUT_CALENDAR_EVENT_LIST);
	po.setParameterType(Event.MY_URI);
	getCalendarEventList.addSimpleOutputBinding(po, (new PropertyPath(null,
		true, new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT }).getThePath()));
	return getCalendarEventList;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and get an event</code> from the calendar
     * with the specified URI <code>calendarURI</code>.
     * 
     * @param calendarURI
     *            the URI of the calendar
     * @param eventId
     *            the id of the event to be deleted
     * @return a service request for the specific service
     */
    private ServiceRequest getDeleteCalendarEvent(Calendar c, int eventId) {
	ServiceRequest deleteCalendarEvent = new ServiceRequest(
		new CalendarAgenda(null), null);

	if (c == null) {
	    c = new Calendar();
	}

	// Restriction r1 =
	// Restriction.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS,
	// c);
	Restriction r2 = Restriction.getFixedValueRestriction(Event.PROP_ID,
		new Integer(eventId));

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
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and add an <code>
	 * event</code> to the calendar
     * with the specified URI <code>calendarURI</code>.
     * 
     * @param calendarURI
     *            the URI of the calendar
     * @param event
     *            the event to be stored
     * @return a service request for the specific service
     */
    private ServiceRequest getAddEventToCalendar(Calendar c, Event event) {
	ServiceRequest addEventToCalendar = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar();
	}
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	addEventToCalendar.getRequestedService().addInstanceLevelRestriction(
		r1, new String[] { CalendarAgenda.PROP_CONTROLS });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	addEventToCalendar.addAddEffect(ppEvent.getThePath(), event);
	ProcessOutput output = new ProcessOutput(OUTPUT_ADDED_EVENT_ID);
	PropertyPath ppEventID = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_ID });

	addEventToCalendar.addSimpleOutputBinding(output, ppEventID
		.getThePath());
	return addEventToCalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and add an <code>
	 * event</code> list to the
     * calendar with the specified URI <code>calendarURI</code>.
     * 
     * @param calendarURI
     *            the URI of the calendar
     * @param eventList
     *            the event list to be stored
     * @return a service request for the specific service
     */
    private ServiceRequest getAddEventListToCalendar(Calendar c, List eventList) {
	ServiceRequest addEventToCalendar = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar();
	}
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	addEventToCalendar.getRequestedService().addInstanceLevelRestriction(
		r1, new String[] { CalendarAgenda.PROP_CONTROLS });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	addEventToCalendar.addAddEffect(ppEvent.getThePath(), eventList);

	return addEventToCalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and retrieve <i>all</i> {@link Calendar}
     * which are managed by he server.
     * 
     * @return a service request for the specific service
     */
    private ServiceRequest getAllCalendarsRequest() {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null); // need
	// a
	// service
	// from
	// Calendar/Agenda
	listCalendars.addSimpleOutputBinding(new ProcessOutput(
		OUTPUT_LIST_OF_CALENDARS), (new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS }).getThePath()));
	return listCalendars;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and retrieve <i>all</i> {@link Calendar}
     * which are managed by he server.
     * 
     * @return a service request for the specific service
     */
    private ServiceRequest getCalendarsByOwnerRequest(User owner) {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null);

	Restriction r1 = Restriction.getFixedValueRestriction(
		Calendar.PROP_HAS_OWNER, owner);
	listCalendars.getRequestedService().addInstanceLevelRestriction(
		r1,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_OWNER });

	listCalendars.addSimpleOutputBinding(new ProcessOutput(
		OUTPUT_LIST_OF_CALENDARS), (new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS }).getThePath()));
	return listCalendars;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and create and store a new
     * {@link Calendar} with the specified URI <code>calendarURI</code>.
     * 
     * @param calendar
     *            the new calendar
     * @return a service request for the specific service
     */
    private ServiceRequest getGetCalendarEvent(Calendar calendar, int eventId) {
	if (calendar == null) {
	    calendar = new Calendar(null);
	}
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);
	Restriction r2 = Restriction.getFixedValueRestriction(Event.PROP_ID,
		new Integer(eventId));

	CalendarAgenda ca = new CalendarAgenda(null);
	ca.addInstanceLevelRestriction(r1,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	ca.addInstanceLevelRestriction(r2, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_ID });

	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	ProcessOutput po = new ProcessOutput(OUTPUT_CALENDAR_EVENT);
	po.setCardinality(1, 1);

	ServiceRequest getCalendarEvent = new ServiceRequest(ca, null);
	getCalendarEvent.addSimpleOutputBinding(po, ppEvent.getThePath());
	return getCalendarEvent;
    }

    private ServiceRequest getGetAllEventCategories() {
	PropertyPath ppEventCategory = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_HAS_EVENT_DETAILS,
			EventDetails.PROP_CATEGORY });
	ProcessOutput po = new ProcessOutput(OUTPUT_EVENT_CATEGORIES);
	po.setParameterType(TypeMapper.getDatatypeURI(String.class));
	ServiceRequest getEventCategory = new ServiceRequest(
		new CalendarAgenda(null), null);
	getEventCategory.addSimpleOutputBinding(po, ppEventCategory
		.getThePath());

	return getEventCategory;
    }

    private ServiceRequest getCancelReminder(Calendar calendar, int eventId) {
	if (calendar == null) {
	    calendar = new Calendar(null);
	}
	Restriction r1 = Restriction.getFixedValueRestriction(
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
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and create and store a new Calendar.
     * 
     * @param calendar
     *            the calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @return a service request for the specific service
     */
    private ServiceRequest getAddNewCalendar(Calendar calendar, User owner) {
	ServiceRequest addNewcalendar = new ServiceRequest(new CalendarAgenda(
		null), null);
	PropertyPath ppCalendar = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	PropertyPath ppCalendarOwner = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_OWNER });

	addNewcalendar.addAddEffect(ppCalendar.getThePath(), calendar);
	addNewcalendar.addAddEffect(ppCalendarOwner.getThePath(), owner);
	ProcessOutput outCalendar = new ProcessOutput(OUTPUT_CALENDAR);
	addNewcalendar.addSimpleOutputBinding(outCalendar, ppCalendar
		.getThePath());

	return addNewcalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and delete an existing calendar and all
     * events associated with it
     * 
     * @param calendar
     *            the calendar to be removed
     * @param eventId
     *            the id of the event to be retrieved
     * @return a service request for the specific service
     */
    private ServiceRequest getRemoveCalendar(Calendar calendar) {
	ServiceRequest removeCalendar = new ServiceRequest(new CalendarAgenda(
		null), null);

	PropertyPath ppCalendar = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);
	removeCalendar.getRequestedService().addInstanceLevelRestriction(r1,
		ppCalendar.getThePath());

	removeCalendar.addRemoveEffect(ppCalendar.getThePath());

	return removeCalendar;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service to get the URI of a calendar (wrapped in a
     * calendar object) given the name of it
     * 
     * @param calendarName
     *            the name of calendar
     * @return a service request for the specific service
     */
    private ServiceRequest getCalendarByNameAndOwner(String calendarName,
	    User owner) {
	Restriction r1 = Restriction.getFixedValueRestriction(
		Calendar.PROP_NAME, calendarName);
	Restriction r2 = Restriction.getFixedValueRestriction(
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
	ProcessOutput out = new ProcessOutput(OUTPUT_CALENDAR);
	getCalendarByName.addSimpleOutputBinding(out, ppCalendar.getThePath());

	return getCalendarByName;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and get an <code>event</code> from the
     * calendar with the specified URI <code>calendarURI</code>.
     * 
     * @param calendar
     *            the calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @return a service request for the specific service
     */
    private ServiceRequest getUpdateCalendarEvent(Calendar calendar,
	    int eventId, Event event) {
	ServiceRequest getUpdateEvent = new ServiceRequest(new CalendarAgenda(
		null), null);
	if (calendar == null) {
	    calendar = new Calendar(null);
	}
	System.out.println(calendar.getURI());
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, calendar);
	Restriction r2 = Restriction.getFixedValueRestriction(Event.PROP_ID,
		new Integer(eventId));

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
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and get an event</code> from the calendar
     * with the specified URI <code>calendarURI</code>.
     * 
     * @param calendarURI
     *            the URI of the calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @return a service request for the specific service
     */
    private ServiceRequest getSetEventReminder(Calendar c, int eventId,
	    Reminder reminder) {
	ServiceRequest getSetReminder = new ServiceRequest(new CalendarAgenda(
		null), null);
	if (c == null) {
	    c = new Calendar(null);
	}
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	Restriction r2 = Restriction.getFixedValueRestriction(Event.PROP_ID,
		new Integer(eventId));

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
     * Creates a {@link ServiceRequest} object in order to use a
     * {@link CalendarAgenda} service and get an event</code> from the calendar
     * with the specified URI <code>calendarURI</code>.
     * 
     * @param calendarURI
     *            the URI of the calendar
     * @param eventId
     *            the id of the event to be retrieved
     * @return a service request for the specific service
     */
    private ServiceRequest getSetReminderType(Calendar c, int eventId,
	    ReminderType reminderType) {
	ServiceRequest getSetReminderType = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar(null);
	}
	Restriction r1 = Restriction.getFixedValueRestriction(
		CalendarAgenda.PROP_CONTROLS, c);
	Restriction r2 = Restriction.getFixedValueRestriction(Event.PROP_ID,
		new Integer(eventId));

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

    private Object getReturnValue(List outputs, String expectedOutput) {
	Object returnValue = null;
	int testCount = 0;
	if (outputs == null)
	    Activator.log.log(LogService.LOG_INFO,
		    "AgendaConsumer_backup: No info found!");
	else
	    for (Iterator i = outputs.iterator(); i.hasNext();) {
		testCount++;
		ProcessOutput output = (ProcessOutput) i.next();
		if (output.getURI().equals(expectedOutput))
		    if (returnValue == null) {
			returnValue = output.getParameterValue();
			if ((returnValue instanceof Resource)
				&& ((Resource) returnValue).getURI().equals(
					Resource.RDF_EMPTY_LIST))
			    returnValue = new ArrayList(0);
		    } else
			Activator.log
				.log(LogService.LOG_INFO,
					"AgendaConsumer_backup: redundant return value!");
		else
		    Activator.log.log(LogService.LOG_INFO,
			    "AgendaConsumer_backup - output ignored: "
				    + output.getURI());
	    }
	return returnValue;
    }

    // just a dummy method to create a simple event
    private Event createEvent(int hour, int min, int seconds) {
	// start Event Details
	EventDetails ed = new EventDetails();
	ed.setCategory("No interest");
	ed.setDescription("No interest");
	ed.setPlaceName("Her Home");
	// start Address
	// Address pa = new Address("Thessalia", "Kiprou 21", "b3");
	// pa.setCountryName(new String[]{"Hellas", "Greece"});
	// pa.setExtendedAddress("Neapoli");
	// pa.setPostalCode("41 500");
	// pa.setRegion("Nea politia");
	Address pa = new Address();

	// pa.setStreet("Krapinska");
	// pa.setCountry("Hrvatska");
	// pa.setCity("Neapoli");
	// pa.setProvince("Bilogora");
	// pa.setState("Nea politia");
	pa.setCityPlace(new CityPlace("Krapinska"));
	pa.setCountry(new Country("Hrvatska"));
	pa.setCity(new City("Zagreb"));
	pa.setCityQuarter(new CityQuarter("Tresnjevka"));
	pa.setRegion(new Region("Sjeverozapadna"));
	// end Address
	ed.setAddress(pa);
	// end Event Details
	// start Reminder
	Reminder r = new Reminder();
	r.setMessage("Probna poruka da vidimo jel radi.");
	r
		.setReminderTime(TypeMapper.getDataTypeFactory()
			.newXMLGregorianCalendar(2009, 2, 20, hour, min,
				seconds, 0, 2));
	// // end Reminder

	TimeInterval ti = new TimeInterval();
	XMLGregorianCalendar startTime = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2009, 2, 20, 16, 30, 0, 0, 2);
	XMLGregorianCalendar endTime = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2009, 2, 20, 18, 30, 0, 0, 2);
	ti.setStartTime(startTime);
	ti.setEndTime(endTime);
	ed.setTimeInterval(ti);

	Event event = new Event();
	event.setEventDetails(ed);
	event.setReminder(r);

	return event;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.persona.middleware.context.ContextSubscriber#handleContextEvent(org
     * .persona.middleware.context.ContextEvent)
     */
    public void handleContextEvent(ContextEvent event) {
	java.util.Calendar c = java.util.Calendar.getInstance();
	System.out.println(c.get(java.util.Calendar.HOUR_OF_DAY) + ":"
		+ c.get(java.util.Calendar.MINUTE) + ":"
		+ c.get(java.util.Calendar.SECOND));

	Activator.log.log(LogService.LOG_INFO, "Received1 context event:\n"
		+ "    Subject      = "
		+ event.getSubjectURI()
		+ "\n"
		+ "    Subject type = "
		+ event.getSubjectTypeURI()
		+ "\n"
		+ "    Predicate    = "
		+ event.getRDFPredicate()
		+ "\n"
		+ "    Object       = OK\n"
		+ "    Reason = "
		+ ((Event) ((List) event.getRDFObject()).get(0)).getCEType()
			.name() + "\n LIST SIZE = "
		+ ((List) event.getRDFObject()).size());

	// Event e = (Event)((List)event.getRDFObject()).get(0);
	// if (e.getCEType() == CEType.reminder) {
	// JOptionPane.showMessageDialog(null, e.getReminder().getMessage() +
	// "\nEvent begins at: " +
	// e.getEventDetails().getTimeInterval().getStartTime().toString());
	// } else {
	// JOptionPane.showMessageDialog(null, "Testing1...");
	// }
    }
}
