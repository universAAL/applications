package org.universAAL.agenda.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.ontology.location.address.PhysicalAddress;
import org.universAAL.ontology.location.outdoor.City;
import org.universAAL.ontology.location.outdoor.CityPlace;
import org.universAAL.ontology.location.outdoor.CityQuarter;
import org.universAAL.ontology.location.outdoor.Country;
import org.universAAL.ontology.location.outdoor.Region;
import org.universAAL.ontology.location.outdoor.State;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class AgendaConsumer extends ContextSubscriber {

    /**  */
    public static final String AGENDA_CLIENT_NAMESPACE = "http://ontology.universAAL.org/AgendaClient.owl#";

    /**  */
    public static final String OUTPUT_LIST_OF_CALENDARS = AGENDA_CLIENT_NAMESPACE
	    + "oListOfCalendars";

    /**  */
    public static final String OUTPUT_CALENDAR_EVENT_LIST = AGENDA_CLIENT_NAMESPACE
	    + "oCalendarEventList";

    /**  */
    public static final String OUTPUT_ADDED_EVENT_ID = AGENDA_CLIENT_NAMESPACE
	    + "oAddedEventId";

    /**  */
    public static final String OUTPUT_CALENDAR_EVENT = AGENDA_CLIENT_NAMESPACE
	    + "oCalendarEvent";

    /**  */
    public static final String OUTPUT_EVENT_CATEGORIES = AGENDA_CLIENT_NAMESPACE
	    + "oEventCategories";

    /**  */
    public static final String OUTPUT_CALENDAR = AGENDA_CLIENT_NAMESPACE
	    + "oCalendar";

    /**
     * @return context subscription parameters
     */
    private static ContextEventPattern[] getContextSubscriptionParams() {
	// I am interested in all events with a calendars as subject
	ContextEventPattern cep1 = new ContextEventPattern();
	ContextEventPattern cep2 = new ContextEventPattern();
	cep1.addRestriction(MergedRestriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, Event.MY_URI));
	cep2.addRestriction(MergedRestriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, Calendar.MY_URI));
	return new ContextEventPattern[] { cep1, cep2 };
    }

    /** Service caller. */
    private ServiceCaller caller;

    /** Module Context. */
    private ModuleContext mcontext;

    /**  */
    private ServiceRequestCreator serviceRequestCreator = null;

    /**
     * Constructor.
     * 
     * @param mcontext
     *            Module Context
     */
    public AgendaConsumer(ModuleContext mcontext) {
	super(mcontext, getContextSubscriptionParams());
	this.mcontext = mcontext;
	caller = new DefaultServiceCaller(mcontext);
	serviceRequestCreator = ServiceRequestCreator.getInstance();

	// localTestMethodForDebugging();
    }

    /**
     * Local method for testing. For use uncomment it is constructor.
     */
    private void localTestMethodForDebugging() {
	User user = new User(User.MY_URI + "andrej");

	// create and add new Calendar for user
	Calendar c = new Calendar();
	c.setName("my cal");
	c.setOwner(user);
	c = addNewCalendarService(c, user);
	if (c == null) {
	    System.out.println("No calendar:(");
	    return;
	}
	System.out.println("NEW CALENDAR added");
	System.out.println("URI: " + c.getURI());
	System.out.println("Name: " + c.getName());
	System.out.println("Owner: " + c.getOwner().getURI());

	// get all calendars for user
	List l = getCalendarsByOwnerService(user);
	System.out.println("Number of user's calendars: " + l.size());
	for (Iterator it = l.iterator(); it.hasNext();) {
	    System.out.println(((Calendar) it.next()).getName());
	}

	// remove calendar
	boolean succeed = removeCalendarService(c);
	if (succeed)
	    System.out.println("Calendar has been removed!");
	else
	    System.out.println("Calendar hasn't been removed");

	// create Event
	Event event = createEvent(2012, 5, 12, 13, 40, 00);

	// add event to calendar
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getAddEventToCalendarRequest(c, event));

    }

    /**
     * Local method for creating Event. Used as a helper in above
     * {@link #localTestMethodForDebugging()} method
     */
    private Event createEvent(int year, int month, int day, int hour, int min,
	    int seconds) {
	// create Event Details
	EventDetails ed = new EventDetails();
	ed.setCategory("Some category");
	ed.setDescription("Some description");
	ed.setPlaceName("Assisted person home");

	// create PhysicalAddress, add test data to it and add it to event
	// details
	PhysicalAddress pa = new PhysicalAddress();
	pa.setPostalCode("10 000");
	pa.setState(new State("some state"));
	pa.setCityPlace(new CityPlace("Krapinska"));
	pa.setCountry(new Country("Croatia"));
	pa.setCity(new City("Zagreb"));
	pa.setCityQuarter(new CityQuarter("Tresnjevka"));
	pa.setRegion(new Region("Sjeverozapadna"));
	ed.setAddress(pa);

	// create time interval with test data and add it to event details
	TimeInterval ti = new TimeInterval();
	XMLGregorianCalendar startTime = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2012, 2, 20, 16, 30, 0, 0, 2);
	XMLGregorianCalendar endTime = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2012, 2, 20, 18, 30, 0, 0, 2);
	ti.setStartTime(startTime);
	ti.setEndTime(endTime);
	ed.setTimeInterval(ti);

	// create reminder
	Reminder r = new Reminder();
	r.setMessage("Test message to see if it works.");
	r.setReminderTime(TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(day, month, day, hour, min, seconds,
			0, 2));

	// create Event and add created event details and reminder to it
	Event event = new Event();
	event.setEventDetails(ed);
	event.setReminder(r);

	return event;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @return list of calendars {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getAllCalendarsRequest()}
     */
    public List getAllCalendarsService() {
	List allCalendars = new ArrayList();

	long endTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getAllCalendarsRequest());
	long startTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getAllCalendarsService",
			new Object[] {
				"Agenda Service called: \'get all Calendars\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getAllCalendarsService",
			new Object[] {
				"Agenda Service returned: \'get all Calendars\' at endTime: ",
				endTime }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "getAllCalendarsService",
		new Object[] {
			"Agenda Service \'get all Calendars\' time delay: ",
			endTime - startTime }, null);

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
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getAllCalendarsService",
				    new Object[] { "Calendar List was not retrieved!" },
				    null);
		else
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getAllCalendarsService", new Object[] {
				    "Calendar List was retrieved! Size = ",
				    allCalendars.size() }, null);

	    } catch (Exception e) {
		LogUtils
			.logError(
				mcontext,
				this.getClass(),
				"getAllCalendarsService",
				new Object[] { "Exception while getting all calendars." },
				e);

		return null;
	    }
	} else {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getAllCalendarsService", new Object[] {
			    "Service call status: ",
			    sr.getCallStatus().toString() }, null);
	}
	return allCalendars;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param owner
     *            calendar owner
     * @return list of calendars {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getCalendarsByOwnerRequest()}
     */
    public List getCalendarsByOwnerService(User owner) {
	List allCalendars = new ArrayList();

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getCalendarsByOwnerRequest(owner));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarsByOwnerService",
			new Object[] {
				"Agenda Service called: \'get calendars by owner\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarsByOwnerService",
			new Object[] {
				"Agenda Service returned: \'get calendars by owner\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarsByOwnerService",
			new Object[] {
				"Agenda Service \'get calendars by owner\' time delay: ",
				endTime - startTime }, null);

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
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getCalendarsByOwnerService",
				    new Object[] { "Calendar List was not retrieved!" },
				    null);

		else
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarsByOwnerService", new Object[] {
				    "Calendar List was retrieved! Size = ",
				    allCalendars.size() }, null);

	    } catch (Exception e) {
		LogUtils
			.logError(
				mcontext,
				this.getClass(),
				"getCalendarsByOwnerService",
				new Object[] { "Exception while getting all calendars." },
				e);
		return null;
	    }
	} else {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getCalendarsByOwnerService", new Object[] {
			    "Service call status: ",
			    sr.getCallStatus().toString() }, null);
	}
	return allCalendars;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param owner
     *            calendar owner
     * @return calendar that was added or null if add operation failed
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getAddNewCalendarRequest()}
     */
    public Calendar addNewCalendarService(Calendar c, User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getAddNewCalendarRequest(c, owner));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addNewCalendarService",
			new Object[] {
				"Agenda Service called: \'get calendars by owner\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addNewCalendarService",
			new Object[] {
				"Agenda Service returned: \'get calendars by owner\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addNewCalendarService",
			new Object[] {
				"Agenda Service \'get calendars by owner\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR);
		if (o instanceof Calendar) {

		    LogUtils.logInfo(mcontext, this.getClass(),
			    "addNewCalendarService",
			    new Object[] { "Calendar was added!" }, null);

		    return (Calendar) o;
		} else {
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "addNewCalendarService",
				    new Object[] { "Calendar may not have been added - Wrong service output!" },
				    null);

		}
	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"addNewCalendarService",
			new Object[] { "Exception while adding calendar." }, e);
	    }
	} else {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "addNewCalendarService", new Object[] {
			    "Calendar was not added! Service call status: ",
			    sr.getCallStatus().toString() }, null);

	}
	return null;

    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @return true if calendar was removed or false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getRemoveCalendarRequest()}
     */
    public boolean removeCalendarService(Calendar c) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getRemoveCalendarRequest(c));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"removeCalendarService",
			new Object[] {
				"Agenda Service called: \'remove calendar\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"removeCalendarService",
			new Object[] {
				"Agenda Service returned: \'remove calendar\' at endTime: ",
				endTime }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "removeCalendarService",
		new Object[] {
			"Agenda Service \'remove calendar\' time delay: ",
			endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "Calendar -and its assosiated events- were removed",
			    new Object[] { "Calendar -and its assosiated events- were removed" },
			    null);

	    return true;
	}
	LogUtils.logInfo(mcontext, this.getClass(), "removeCalendarService",
		new Object[] {
			"Calendar was not removed. Service call status: ",
			sr.getCallStatus().toString() }, null);
	return false;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param calendarName
     *            calendar name
     * @param owner
     *            calendar owner
     * @return calendar {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getGetCalendarByNameAndOwnerRequest()}
     */
    public Calendar getCalendarByNameAndOwnerService(String calendarName,
	    User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getGetCalendarByNameAndOwnerRequest(calendarName, owner));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarByNameAndOwnerService",
			new Object[] {
				"Agenda Service called: \'get calendar by name and owner\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarByNameAndOwnerService",
			new Object[] {
				"Agenda Service returned: \'get calendar by name and owner\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarByNameAndOwnerService",
			new Object[] {
				"Agenda Service \'get calendar by name and owner\' time delay: ",
				endTime - startTime }, null);

	Calendar calendar = null;
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR);
		if (o instanceof Calendar)
		    calendar = (Calendar) o;
		if (calendar == null)
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarByNameAndOwnerService",
			    new Object[] { "Calendar URI was not retrieved" },
			    null);

		else
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarByNameAndOwnerService", new Object[] {
				    "Calendar URI was retrieved: ",
				    calendar.getURI() }, null);

	    } catch (Exception e) {

		LogUtils
			.logError(
				mcontext,
				this.getClass(),
				"getCalendarByNameAndOwnerService",
				new Object[] { "Exception while getting calendar." },
				e);
		return null;
	    }
	} else {
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getCalendarByNameAndOwnerService",
			    new Object[] {
				    "Calendar was not retrieved. Service call status: ",
				    sr.getCallStatus().toString() }, null);
	}
	return calendar;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     * @param eventId
     * @return {@link Event} {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getGetCalendarEventRequest()}
     *         {@link Calendar} {@link Event} id
     */
    public Event getCalendarEventService(Calendar c, int eventId) {
	Event event = null;

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getGetCalendarEventRequest(c, eventId));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarEventService",
			new Object[] {
				"Agenda Service called: \'get calendar event\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarEventService",
			new Object[] {
				"Agenda Service returned: \'get calendar event\' at endTime: ",
				endTime }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "getCalendarEventService",
		new Object[] {
			"Agenda Service \'get calendar event\' time delay: ",
			endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_CALENDAR_EVENT);
		if (o instanceof Event)
		    event = (Event) o;
		else if (o instanceof List && !((List) o).isEmpty()
			&& ((List) o).get(0) instanceof Event)
		    event = (Event) ((List) o).get(0);
		if (event == null) {
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarEventService", new Object[] {
				    "There is no event in calendar: " + c
					    + " with id: ", eventId }, null);

		} else {
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarEventService", new Object[] {
				    "Calendar: ", c }, null);
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarEventService", new Object[] {
				    "Event id info: ", o.toString() }, null);
		}

	    } catch (Exception e) {
		LogUtils
			.logError(
				mcontext,
				this.getClass(),
				"getCalendarEventService",
				new Object[] { "Exception while getting calendar event." },
				e);

		return null;
	    }
	} else {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getCalendarEventService", new Object[] {
			    "Event has not been retrieved. Call status: ",
			    sr.getCallStatus().toString() }, null);
	}
	return event;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @return list of calendars {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getGetAllEventCategoriesRequest()}
     */
    public List getAllEventCategories() {
	List allCategories = new ArrayList();

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getGetAllEventCategoriesRequest());
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getAllEventCategories",
			new Object[] {
				"Agenda Service called: \'get all event categories\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getAllEventCategories",
			new Object[] {
				"Agenda Service returned: \'get all event categories\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getAllEventCategories",
			new Object[] {
				"Agenda Service \'get all event categories\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_EVENT_CATEGORIES);
		if (o instanceof String)
		    allCategories.add((String) o);
		else if (o instanceof List && !((List) o).isEmpty())
		    allCategories = (List) (List) o;

		if (o == null)
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getAllEventCategories",
				    new Object[] { "There is not any stored event category" },
				    null);

		else
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getAllEventCategories",
				    new Object[] { "There are stored event categories" },
				    null);
		LogUtils.logInfo(mcontext, this.getClass(),
			"getAllEventCategories",
			new Object[] { "Categories were retrieved" }, null);

	    } catch (Exception e) {

		LogUtils
			.logError(
				mcontext,
				this.getClass(),
				"getAllEventCategories",
				new Object[] { "Exception while getting all calendars." },
				e);

		return null;
	    }
	} else {
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getAllEventCategories",
			    new Object[] {
				    "Event categories were not retrieved. Call status: ",
				    sr.getCallStatus().toString() }, null);

	}
	return allCategories;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param eventId
     *            event id
     * @return true if reminder was canceled or false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getCancelReminderRequest()}
     */
    public boolean cancelReminderService(Calendar c, int eventId) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getCancelReminderRequest(c, eventId));
	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"cancelReminderService",
			new Object[] {
				"Agenda Service called: \'cancel reminder\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"cancelReminderService",
			new Object[] {
				"Agenda Service returned: \'cancel reminder\' at endTime: ",
				endTime }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "cancelReminderService",
		new Object[] {
			"Agenda Service \'cancel reminder\' time delay: ",
			endTime - startTime }, null);

	if (!(sr.getCallStatus() == CallStatus.succeeded)) {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "cancelReminderService", new Object[] {
			    "Reminder was canceled. Service call status: ",
			    sr.getCallStatus().toString() }, null);

	    return false;
	}
	LogUtils.logInfo(mcontext, this.getClass(), "cancelReminderService",
		new Object[] { "Reminder was canceled" }, null);

	return true;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param eventId
     *            event id
     * @param event
     *            event
     * @return true if calendar event was updated or false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getUpdateCalendarEventRequest()}
     */
    public boolean updateCalendarEventService(Calendar c, int eventId,
	    Event event) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getUpdateCalendarEventRequest(c, eventId, event));
	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"updateCalendarEventService",
			new Object[] {
				"Agenda Service called: \'update calendar event\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"updateCalendarEventService",
			new Object[] {
				"Agenda Service returned: \'update calendar event\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"updateCalendarEventService",
			new Object[] {
				"Agenda Service \'update calendar event\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		LogUtils.logInfo(mcontext, this.getClass(),
			"updateCalendarEventService",
			new Object[] { "Event was updated" }, null);

	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"updateCalendarEventService",
			new Object[] { "Exception" }, e);

		return false;
	    }
	    return true;
	}
	LogUtils.logInfo(mcontext, this.getClass(),
		"updateCalendarEventService", new Object[] {
			"Event was not updated. Service call status: ",
			sr.getCallStatus().toString() }, null);

	return false;

    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param eventID
     *            event id
     * @param reminder
     *            reminder
     * @return true if reminder was set or false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getSetEventReminderRequest()}
     */
    public boolean setEventReminderService(Calendar c, int eventID,
	    Reminder reminder) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getSetEventReminderRequest(c, eventID, reminder));
	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"setEventReminderService",
			new Object[] {
				"Agenda Service called: \'set event reminder\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"setEventReminderService",
			new Object[] {
				"Agenda Service returned: \'set event reminder\' at endTime: ",
				endTime }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "setEventReminderService",
		new Object[] {
			"Agenda Service \'set event reminder\' time delay: ",
			endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		LogUtils.logInfo(mcontext, this.getClass(),
			"setEventReminderService",
			new Object[] { "Event reminder has been set" }, null);

	    } catch (Exception e) {

		LogUtils.logError(mcontext, this.getClass(),
			"setEventReminderService",
			new Object[] { "Exception." }, e);

		return false;
	    }
	    return true;
	}
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"setEventReminderService",
			new Object[] {
				"Event reminder has not been set. Service call status: ",
				sr.getCallStatus().toString() }, null);

	return false;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param eventID
     *            event
     * @param reminderType
     *            reminder type
     * @return true if calendar type was set of false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getSetReminderTypeRequest()}
     */
    public boolean setReminderTypeService(Calendar c, int eventID,
	    ReminderType reminderType) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getSetReminderTypeRequest(c, eventID, reminderType));
	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"setReminderTypeService",
			new Object[] {
				"Agenda Service called: \'set reminder type\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"setReminderTypeService",
			new Object[] {
				"Agenda Service returned: \'set reminder type\' at endTime: ",
				endTime }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "setReminderTypeService",
		new Object[] {
			"Agenda Service \'set reminder type\' time delay: ",
			endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		LogUtils.logInfo(mcontext, this.getClass(),
			"setReminderTypeService",
			new Object[] { "Event reminder type has been set" },
			null);
	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"setReminderTypeService",
			new Object[] { "Exception ." }, e);

		return false;
	    }
	    return true;
	}
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"setReminderTypeService",
			new Object[] {
				"Event reminder type has not been set. Service call status: ",
				sr.getCallStatus().toString() }, null);
	return false;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param eventId
     *            event id
     * @return true if calendar event was deleted of false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getDeleteCalendarEventRequest()}
     */
    public boolean deleteCalendarEventService(Calendar c, int eventId) {
	long startTime = System.currentTimeMillis();
	caller = new DefaultServiceCaller(mcontext);
	ServiceRequest srq = serviceRequestCreator
		.getDeleteCalendarEventRequest(c, eventId);
	ServiceResponse sr = caller.call(srq);

	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"deleteCalendarEventService",
			new Object[] {
				"Agenda Service called: \'delete calendar event\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"deleteCalendarEventService",
			new Object[] {
				"Agenda Service returned: \'delete calendar event\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"deleteCalendarEventService",
			new Object[] {
				"Agenda Service \'delete calendar event\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		LogUtils.logInfo(mcontext, this.getClass(),
			"deleteCalendarEventService",
			new Object[] { "Event was deleted" }, null);

	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"deleteCalendarEventService",
			new Object[] { "Exception ." }, e);

		return false;
	    }
	    return true;
	}
	LogUtils.logInfo(mcontext, this.getClass(),
		"deleteCalendarEventService", new Object[] {
			"Event was not deleted. Service call status: ",
			sr.getCallStatus().toString() }, null);
	return false;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @return list of events belonging to a calendar {@link ServiceRequest}
     *         object created in
     *         {@link ServiceRequestCreator#getGetCalendarEventListRequest()}
     */
    public List requestEventListService(Calendar c) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getGetCalendarEventListRequest(c));
	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"requestEventListService",
			new Object[] {
				"Agenda Service called: \'get calendar event list\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"requestEventListService",
			new Object[] {
				"Agenda Service returned: \'get calendar event list\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"requestEventListService",
			new Object[] {
				"Agenda Service \'get calendar event list\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		List events = (List) getReturnValue(sr.getOutputs(),
			OUTPUT_CALENDAR_EVENT_LIST);
		if (events == null || events.size() == 0) {
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "requestEventListService",
				    new Object[] { "Event List has been retreived, but it's empty or NULL" },
				    null);

		    return new ArrayList();
		}
		LogUtils.logInfo(mcontext, this.getClass(),
			"requestEventListService",
			new Object[] { "Event List was retreived" }, null);

		return events;
	    } catch (Exception e) {

		LogUtils.logError(mcontext, this.getClass(),
			"requestEventListService",
			new Object[] { "Exception ." }, e);

		return new ArrayList(0);
	    }
	}
	LogUtils.logInfo(mcontext, this.getClass(), "requestEventListService",
		new Object[] {
			"Event List was not retreived. Service call status: ",
			sr.getCallStatus().toString() }, null);

	return new ArrayList(0);

    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param event
     *            event
     * @return event id if the operation was sucessful or -1 otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getAddEventToCalendarRequest()}
     */
    public int addEventToCalendarService(Calendar c, Event event) {

	long startTime = System.currentTimeMillis();

	ServiceResponse sr = this.caller.call(serviceRequestCreator
		.getAddEventToCalendarRequest(c, event));

	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addEventToCalendarService",
			new Object[] {
				"Agenda Service called: \'add event to calendar\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addEventToCalendarService",
			new Object[] {
				"Agenda Service returned: \'add event to calendar\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addEventToCalendarService",
			new Object[] {
				"Agenda Service \'add event to calendar\' time delay: ",
				endTime - startTime }, null);

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
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "addEventToCalendarService",
				    new Object[] { "Event was not added to calendar for unknown reason" },
				    null);

		} else {
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "addEventToCalendarService",
			    new Object[] { "Event was added to calendar" },
			    null);

		}
		return eventId;
	    } catch (Exception e) {

		LogUtils.logError(mcontext, this.getClass(),
			"addEventToCalendarService",
			new Object[] { "Exception." }, e);

		return -1;
	    }
	}
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addEventToCalendarService",
			new Object[] {
				"Event was not added to calendar. Service call status: ",
				sr.getCallStatus().toString() }, null);
	return -1;
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) with.
     * 
     * @param c
     *            calendar
     * @param eventList
     *            event list
     * @return true if operation was sucessfull of false otherwise
     *         {@link ServiceRequest} object created in
     *         {@link ServiceRequestCreator#getAddEventListToCalendarRequest()}
     */
    public boolean addCalendarEventListService(Calendar c, List eventList) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(serviceRequestCreator
		.getAddEventListToCalendarRequest(c, eventList));
	long endTime = System.currentTimeMillis();

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addCalendarEventListService",
			new Object[] {
				"Agenda Service called: \'add event list to calendar\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addCalendarEventListService",
			new Object[] {
				"Agenda Service returned: \'add event list to calendar\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"addCalendarEventListService",
			new Object[] {
				"Agenda Service \'add event list to calendar\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "addCalendarEventListService",
		    new Object[] { "Event List was added to calendar" }, null);

	    return true;
	}
	LogUtils.logInfo(mcontext, this.getClass(),
		"addCalendarEventListService",
		new Object[] { "Event List was not added to calendar" }, null);

	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.context.ContextSubscriber#
     * communicationChannelBroken()
     */
    public void communicationChannelBroken() {

    }

    /**
     * Helper method. Sends to logger a formated representation of all
     * <code>events</code>, assuming they belong to the {@link Calendar}
     * <code>c</code>.
     * 
     * @param calendarURI
     *            a calendarURI
     * @param events
     *            a list of events
     */
    private void printEvents(String calendarURI, List events) {
	LogUtils.logInfo(mcontext, this.getClass(), "printEvents",
		new Object[] { "Calendar: ", calendarURI }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "printEvents",
		new Object[] { "Number of events stored in Calendar is: ",
			events.size() }, null);
	for (Iterator it = events.listIterator(); it.hasNext();) {
	    Event e = (Event) it.next();
	    LogUtils.logInfo(mcontext, this.getClass(), "printEvents",
		    new Object[] { "Event: ", e.toString() }, null);
	}
    }

    /**
     * Check all outputs for output that is equal to expected output and if it
     * is found return it, otherwise return null
     * 
     * @param outputs
     *            list of outputs
     * @param expectedOutput
     *            expected output
     * @return output that is equal to expected output or null if that output is
     *         not found
     */
    public Object getReturnValue(List outputs, String expectedOutput) {
	Object returnValue = null;
	int testCount = 0;
	if (outputs == null)
	    LogUtils.logInfo(mcontext, this.getClass(), "getReturnValue",
		    new Object[] { "No info found!" }, null);

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
			LogUtils
				.logInfo(
					mcontext,
					this.getClass(),
					"getReturnValue",
					new Object[] { "Redundant return value" },
					null);

		else
		    LogUtils
			    .logInfo(mcontext, this.getClass(),
				    "getReturnValue",
				    new Object[] { "output ignored: ",
					    output.getURI() }, null);

	    }
	return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.context.ContextSubscriber#handleContextEvent
     * (org.universAAL.middleware.context.ContextEvent)
     */
    public void handleContextEvent(ContextEvent event) {
	// java.util.Calendar c = java.util.Calendar.getInstance();
	// System.out.println(c.get(java.util.Calendar.HOUR_OF_DAY) + ":"
	// + c.get(java.util.Calendar.MINUTE) + ":"
	// + c.get(java.util.Calendar.SECOND));
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Received1 context event. Subject = ",
			event.getSubjectURI() }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Subject type = ", event.getSubjectTypeURI() },
		null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Predicate    = ", event.getRDFPredicate() },
		null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] {
			"Object= OK Reason = ",
			((Event) ((List) event.getRDFObject()).get(0))
				.getCEType().name() }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "List size = ",
			((List) event.getRDFObject()).size() }, null);

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
