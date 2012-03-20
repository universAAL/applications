/**
 * 
 */
package org.universAAL.agenda.server;

//j2se packages
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.ontology.agenda.CEType;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.agenda.server.database.Scheduler;
import org.universAAL.agenda.server.gui.wrapper.WrapperActivator;
import org.universAAL.agenda.server.unit_impl.AgendaStateListener;
import org.universAAL.agenda.server.unit_impl.MyAgenda;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.ElderlyUser;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class AgendaProvider extends ServiceCallee implements
	AgendaStateListener {
    static final File confHome = new File(new BundleConfigHome("agenda")
	    .getAbsolutePath());
    // static final String CAL_URI_PREFIX =
    // ProvidedAgendaService.AGENDA_SERVER_NAMESPACE + "controlledAgenda";
    // //TODO: change name
    static final String LOCATION_URI_PREFIX = "urn:aal_space:everywhere#"; //$NON-NLS-1$
    static final Logger mainLogger = LoggerFactory
	    .getLogger(AgendaProvider.class);

    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    // private static final ServiceResponse settingFailed = new
    // ServiceResponse(CallStatus.serviceSpecificFailure);
    private static final ServiceResponse deletingFailed = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    private static final ServiceResponse noSuchEvent = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    private static final ServiceResponse existingCalendar = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    private static final ServiceResponse noSuchEventOrCalendar = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    private static final ServiceResponse notExistingCalendar = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    private static final ServiceResponse notSpecifiedOwner = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    private static final ServiceResponse failure = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    static final ElderlyUser testUser = new ElderlyUser(
	    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + "saied"); //$NON-NLS-1$

    static {
	invalidInput.addOutput(new ProcessOutput(
		ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, Messages
			.getString("AgendaProvider.InvalidInput"))); //$NON-NLS-1$
	// settingFailed.addOutput(new
	// ProcessOutput(ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
	// "The given calendar couldn't be set as the current one!"));
	deletingFailed
		.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			Messages
				.getString("AgendaProvider.TheGivenEventCouldNotBeDeleted"))); //$NON-NLS-1$
	noSuchEvent
		.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			Messages
				.getString("AgendaProvider.NoEventWithTheGivenIdWasFound"))); //$NON-NLS-1$
	existingCalendar
		.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			Messages
				.getString("AgendaProvider.TheGivenCalendarAlreadyExists"))); //$NON-NLS-1$
	noSuchEventOrCalendar
		.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			Messages
				.getString("AgendaProvider.TheGivenCalendarOrEventDoesNotExistOrThereIsNoCorrelationBetweenThem"))); //$NON-NLS-1$
	notExistingCalendar
		.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			Messages
				.getString("AgendaProvider.TheGivenCalendarDoesNotExist"))); //$NON-NLS-1$
	failure.addOutput(new ProcessOutput(
		ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, Messages
			.getString("AgendaProvider.InvalidUserInput"))); //$NON-NLS-1$
	notSpecifiedOwner
		.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			Messages
				.getString("AgendaProvider.YouMustSpecifyAnOwnerForTheCalendar"))); //$NON-NLS-1$
    }

    private MyAgenda theServer;
    private Scheduler theScheduler;
    private ContextPublisher cp;

    AgendaProvider(ModuleContext mcontext) throws FileNotFoundException,
	    IOException {
	super(mcontext, ProvidedAgendaService.profiles);

	// prepare for context publishing
	ContextProvider info = new ContextProvider(
		ProvidedAgendaService.AGENDA_SERVER_NAMESPACE
			+ "AgendaContextProvider"); //$NON-NLS-1$
	info.setType(ContextProviderType.controller);
	cp = new DefaultContextPublisher(mcontext, info);

	// start the server
	Properties prop = new Properties();
	mainLogger.info("agendaProvider reading credentials from confHome: "
		+ confHome);
	InputStream in = new FileInputStream(new File(confHome,
		"credentials.properties"));
	prop.load(in);

	theServer = new MyAgenda(
		prop.getProperty("database"), prop.getProperty("username"), prop.getProperty("password")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	theScheduler = new Scheduler(theServer, this);
    }

    /**
     * @see org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken()
     */
    public void communicationChannelBroken() {

    }

    /**
     * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL.
     *      middleware.service.ServiceCall)
     */
    public ServiceResponse handleCall(ServiceCall call) {
	if (call == null)
	    return null;

	String operation = call.getProcessURI();
	if (operation == null)
	    return null;

	mainLogger.info("Agenda Service requested");
	if (operation.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDARS)) {
	    return getControlledCalendars();
	}

	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_ALL_CATEGORIES)) {
	    return getAllEventCategories();
	}

	Object inCalendar = call
		.getInputValue(ProvidedAgendaService.INPUT_CALENDAR);
	Object inEvent = call.getInputValue(ProvidedAgendaService.INPUT_EVENT);
	Object inEventList = call
		.getInputValue(ProvidedAgendaService.INPUT_EVENT_LIST);
	Object inEventId = call
		.getInputValue(ProvidedAgendaService.INPUT_EVENT_ID);
	Object inEventReminder = call
		.getInputValue(ProvidedAgendaService.INPUT_EVENT_REMINDER);
	Object inReminderType = call
		.getInputValue(ProvidedAgendaService.INPUT_EVENT_REMINDER_TYPE);
	Object inCalendarName = call
		.getInputValue(ProvidedAgendaService.INPUT_CALENDAR_NAME);
	Object inCalendarOwner = call
		.getInputValue(ProvidedAgendaService.INPUT_CALENDAR_OWNER);

	// 'get calendars of a user' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_BY_OWNER)) {
	    if (inCalendarOwner == null || !(inCalendarOwner instanceof User))
		return null;
	    mainLogger.info("\"get calendars of a user\" service requested");
	    return getControlledCalendars((User) inCalendarOwner);
	}

	// 'add a new calendar' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_ADD_NEW_CALENDAR)) {
	    if (inCalendar == null)
		return null;

	    if (inCalendarOwner != null && !(inCalendarOwner instanceof User)) {
		return null;
	    }

	    mainLogger.info("\"add a new calendar\" service requested");
	    return addNewCalendar((Calendar) inCalendar, (User) inCalendarOwner);
	}

	// 'remove Existing calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_REMOVE_CALENDAR)) {
	    if (inCalendar == null)
		return null;

	    mainLogger.info("\"remove existing calendar\" service requested");
	    return removeCalendar((Calendar) inCalendar);
	}

	// 'get calendar's event list' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_EVENT_LIST)) {
	    if (inCalendar == null)
		return null;

	    mainLogger.info("\"get calendar's event list\" service requested");
	    return getCalendarEventList(((Calendar) inCalendar).getURI());
	}

	// 'add event to calendar' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_ADD_EVENT_TO_CALENDAR)) {
	    if (inCalendar == null)
		return null;

	    mainLogger.info("\"add event to calendar\" service requested");
	    if (inEvent instanceof Event) {
		return addEventToCalendar(((Calendar) inCalendar).getURI(),
			(Event) inEvent);
	    }
	    if (inEvent instanceof List)
		return addEventListToCalendar(((Calendar) inCalendar).getURI(),
			(List) inEvent);

	    return null;
	}

	// 'add event list to calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_ADD_EVENT_LIST)) {
	    if ((inCalendar == null) || !(inEventList instanceof List))
		return null;

	    mainLogger.info("\"add event list to calendar\" service requested");
	    if (inEvent instanceof List)
		return addEventListToCalendar(((Calendar) inCalendar).getURI(),
			(List) inEvent);

	    List temp = new ArrayList();
	    temp.add(inEvent);
	    return addEventListToCalendar(((Calendar) inCalendar).getURI(),
		    temp);
	}

	// 'retrieve an event from calendar' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_EVENT)) {
	    if ((inCalendar == null) || !(inEventId instanceof Integer))
		return null;

	    mainLogger
		    .info("\"retrieve an event from calendar\" service requested");
	    return getCalendarEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue());
	}

	// 'delete an event from calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_DELETE_EVENT)) {
	    if (!(inEventId instanceof Integer)) {
		return null;
	    }

	    mainLogger
		    .info("\"delete an event from calendar\" service requested");
	    return deleteCalendarEvent(((Integer) inEventId).intValue());
	}

	// 'update an event from calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_UPDATE_EVENT)) {
	    if ((inCalendar == null) || !(inEventId instanceof Integer)
		    || (inEvent == null))
		return null;

	    mainLogger
		    .info("\"update an event from calendar\" service requested");
	    return updateCalendarEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue(), (Event) inEvent);
	}

	// 'set a reminder to an event from calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_SET_REMINDER)) {
	    if ((inCalendar == null) || !(inEventId instanceof Integer)
		    || (inEventReminder == null))
		return null;

	    mainLogger
		    .info("\"set a reminder to an event from calendar\" service requested");
	    return setReminderToEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue(),
		    (Reminder) inEventReminder);
	}

	// 'set a reminder type to an event from calendar' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_SET_REMINDER_TYPE)) {
	    if ((inCalendar == null) || !(inEventId instanceof Integer)
		    || (inReminderType == null))
		return null;

	    mainLogger
		    .info("\"set a reminder type to an event from calendar\" service requested");
	    return setReminderTypeToEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue(),
		    (ReminderType) inReminderType);
	}

	// 'cancel reminder' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_CANCEL_REMINDER)) {
	    if ((inCalendar == null) || (inEvent == null))
		return null;

	    mainLogger.info("\"cancel reminder\" service requested");
	    return cancelReminder((Calendar) inCalendar, (Event) inEvent);
	}

	// 'get Calendar URI by the name of it, and its owner' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_BY_NAME_AND_OWNER)) {
	    if (inCalendarName == null)
		return null;

	    if (!(inCalendarOwner instanceof User))
		return null;

	    mainLogger
		    .info("\"get Calendar URI by the name of it, and its owner\" service requested");
	    return getCalendarByName((String) inCalendarName,
		    (User) inCalendarOwner);
	}

	return null;
    }

    /**
     * Adds a new {@Calendar} to user's database
     * 
     * @param calendarURI
     *            a calendar URI
     * @return a service response to the specific service
     */
    private ServiceResponse addNewCalendar(Calendar calendar, User owner) {
	try {
	    Calendar newC = theServer.addCalendar(calendar, owner,
		    MyAgenda.COMMIT);
	    if (newC != null) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(
			ProvidedAgendaService.OUTPUT_CALENDAR, newC));
		return sr;
	    } else {
		return existingCalendar;
	    }
	} catch (InvalidParameterException ipe) {
	    return notSpecifiedOwner;
	} catch (Exception e) {
	    Activator.log.log(LogService.LOG_ERROR,
		    "Exception: " + e.getMessage()); //$NON-NLS-1$
	    return invalidInput;
	}
    }

    /**
     * Remove a {@Calendar} from user's database
     * 
     * @param calendarURI
     *            a calendar URI
     * @return a service response to the specific service
     */
    private ServiceResponse removeCalendar(Calendar calendar) {
	try {
	    return theServer.removeCalendar(calendar, MyAgenda.COMMIT) ? new ServiceResponse(
		    CallStatus.succeeded)
		    : notExistingCalendar;
	} catch (Exception e) {
	    Activator.log.log(LogService.LOG_ERROR,
		    "Exception: " + e.getMessage()); //$NON-NLS-1$
	    return invalidInput;
	}
    }

    /**
     * Cancels any previous defined reminder for the <code>event</event>.
     * 
     * @param calendarURI
     *            a calendar URI
     * @return a service response to the specific service
     */
    private ServiceResponse cancelReminder(Calendar calendar, Event event) {
	try {
	    if (cancelReminder(calendar.getURI(), event.getEventID())) {
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return noSuchEventOrCalendar;
	} catch (Exception e) {
	    Activator.log.log(LogService.LOG_ERROR,
		    "Exception: " + e.getMessage()); //$NON-NLS-1$
	    return invalidInput;
	}
    }

    public boolean cancelReminder(String calendarURI, int eventID) {
	if (theServer.cancelReminder(calendarURI, eventID, MyAgenda.COMMIT)) {
	    System.out.println("Cancel reminder: " + eventID); //$NON-NLS-1$
	    theScheduler.removeReminderTask(eventID);
	    return true;
	}
	return false;
    }

    /**
     * Given the name of the calendar, it returns the URI, wrapped in a Calendar
     * object
     * 
     * @param calendarName
     *            a calendar name
     * @return a service response to the specific service
     */
    private ServiceResponse getCalendarByName(String calendarName, User owner) {
	try {
	    Calendar calendar = theServer.getCalendarByNameAndOwner(
		    calendarName, owner, MyAgenda.COMMIT);
	    if (calendar != null) {
		System.out.println("Calendar URI: " + calendar.getURI()); //$NON-NLS-1$
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(
			ProvidedAgendaService.OUTPUT_CALENDAR, calendar));
		return sr;
	    }
	    return noSuchEventOrCalendar;
	} catch (Exception e) {
	    Activator.log.log(LogService.LOG_ERROR,
		    "Exception: " + e.getMessage()); //$NON-NLS-1$
	    return invalidInput;
	}
    }

    /**
     * Adds an {@link Event} to the {@link Calendar} with the specified
     * <code>calendarURI</code> and creates a {@link ServiceResponse}.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param event
     *            an event
     * @return a service response to the specific service
     */
    private ServiceResponse addEventToCalendar(String calendarURI, Event event) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	try {
	    // INFO: db call
	    int eventId = theServer.addEventToCalendar(calendarURI, event,
		    MyAgenda.COMMIT);
	    if (eventId > 0) {
		event = theServer.getEventFromCalendar(calendarURI, eventId,
			MyAgenda.COMMIT);
		if (event == null) {
		    Activator.log.log(LogService.LOG_ERROR,
			    "Error retrieving event: "); //$NON-NLS-1$
		    throw new Exception();
		}
		// send a context event: new event
		// event.setEventID(eventId);
		// theScheduler.updateReminderTask(event);
		theScheduler.updateScheduler(event);
		eventAdded(new Calendar(calendarURI), event);
		sr.addOutput(new ProcessOutput(
			ProvidedAgendaService.OUTPUT_EVENT_ID, new Integer(
				eventId)));
		return sr;
	    }
	    return invalidInput;
	} catch (Exception e) {
	    Activator.log.log(LogService.LOG_ERROR,
		    "Exception: " + e.getMessage()); //$NON-NLS-1$
	    return invalidInput;
	}
    }

    /**
     * Adds a list of {@link Event}s to the {@link Calendar} with the specified
     * <code>calendarURI</code> and creates a {@link ServiceResponse}.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventList
     *            a list with events
     * @return a service response to the specific service
     */
    private ServiceResponse addEventListToCalendar(String calendarURI,
	    List eventList) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	try {
	    // INFO: prior db
	    for (Iterator it = eventList.iterator(); it.hasNext();) {
		Object o = it.next();
		if (o instanceof Event) {
		    Event event = (Event) o;
		    // INFO: db call
		    int eventID = theServer.addEventToCalendar(calendarURI,
			    event, MyAgenda.COMMIT);
		    if (eventID > 0) {
			event.setEventID(eventID);
			// theScheduler.updateReminderTask(event);
			theScheduler.updateScheduler(event);
		    }
		}
	    }
	    return sr;
	} catch (Exception e) {
	    Activator.log.log(LogService.LOG_ERROR,
		    "Exception: " + e.getMessage()); //$NON-NLS-1$
	    return invalidInput;
	}
    }

    /**
     * Retrieves <i>all</i> events, stored in the {@link Calendar} with the
     * specified <code>calendarURI</code> and creates a {@link ServiceResponse}.
     * 
     * @param calendarURI
     *            a calendar URI
     * @return a service response to the specific service
     */
    private ServiceResponse getCalendarEventList(String calendarURI) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	try {
	    // INFO: db call
	    List<Event> eventList = theServer.getAllEvents(calendarURI,
		    MyAgenda.COMMIT);
	    sr
		    .addOutput(new ProcessOutput(
			    ProvidedAgendaService.OUTPUT_CALENDAR_EVENT_LIST,
			    eventList));
	    return sr;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return invalidInput;
	}
    }

    /**
     * Retrieves the event with the specified <code>eventId</code>, stored in
     * the {@link Calendar} with the specified <code>calendarURI</code> and
     * creates a {@link ServiceResponse}.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventId
     *            an event id to retrieve the whole event
     * @return a service response to the specific service
     */
    private ServiceResponse getCalendarEvent(String calendarURI, int eventId) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	try {
	    // INFO: db call
	    Event event = theServer.getEventFromCalendar(calendarURI, eventId,
		    MyAgenda.COMMIT);
	    sr.addOutput(new ProcessOutput(
		    ProvidedAgendaService.OUTPUT_CALENDAR_EVENT, event));
	    return sr;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return invalidInput;
	}
    }

    /**
     * Deletes the event with the specified <code>eventId</code>, stored in the
     * {@link Calendar} with the specified <code>calendarURI</code> and creates
     * a {@link ServiceResponse}.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventId
     *            an event id; to delete the whole event
     * @return a service response to the specific service
     */
    private ServiceResponse deleteCalendarEvent(int eventId) {
	try {
	    // INFO: db call
	    String deleteOK = theServer.removeEvent(eventId, MyAgenda.COMMIT);
	    if (deleteOK != null) {
		theScheduler.removeReminderTask(eventId);
		// send a context event: event deleted
		eventDeleted(new Calendar(deleteOK), eventId);
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return deletingFailed;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return invalidInput;
	}
    }

    /**
     * Retrieves a {@link List} of the {@link Calendar}s which are controlled by
     * the server to <code>ownerName</code> and creates a
     * {@link ServiceResponse}
     * 
     * @return a service response to the specific service
     */
    private ServiceResponse getControlledCalendars() {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);

	sr.addOutput(new ProcessOutput(
		ProvidedAgendaService.OUTPUT_CONTROLLED_CALENDARS, theServer
			.getCalendarsWithInfo()));
	return sr;
    }

    /**
     * Retrieves a {@link List} of the {@link Calendar}s that belong to the
     * specified to <code>owner</code> and creates a {@link ServiceResponse}
     * 
     * @param owner
     *            the owner of the calendar
     * @return a service response to the specific service
     */
    private ServiceResponse getControlledCalendars(User owner) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);

	sr.addOutput(new ProcessOutput(
		ProvidedAgendaService.OUTPUT_CONTROLLED_CALENDARS, theServer
			.getCalendarsWithInfo(owner)));
	return sr;
    }

    /**
     * Retrieves a {@link List} of all defined categories of the events stored
     * in db
     * 
     * @return a service response to the specific service
     */
    private ServiceResponse getAllEventCategories() {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	List categories = theServer.getAllEventCategories(MyAgenda.COMMIT);
	// dArrayList al = new ArrayList(calendars.length);
	// for (int i = 0; i < calendars.length; i++)
	// al.add(calendars[i]);
	sr.addOutput(new ProcessOutput(
		ProvidedAgendaService.OUTPUT_EVENT_CATEGORIES, categories));
	return sr;
    }

    /**
     * Updates the event with the specified <code>eventId</code>, stored in the
     * {@link Calendar} with the specified <code>calendarURI</code> and creates
     * a {@link ServiceResponse}.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventId
     *            the event id of the updated event
     * @param updatedEvent
     *            the updated event
     * @return a service response to the specific service
     */
    private ServiceResponse updateCalendarEvent(String calendarURI,
	    int eventId, Event updatedEvent) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	try {
	    // INFO: db call
	    updatedEvent.setEventID(eventId);
	    boolean updated = theServer.updateEvent(calendarURI, updatedEvent,
		    MyAgenda.COMMIT);
	    if (updated) {
		// theScheduler.updateReminderTask(updatedEvent);
		theScheduler.updateScheduler(updatedEvent);
		// send a context event: event updated
		eventUpdated(new Calendar(calendarURI), updatedEvent);
		return sr;
	    } else {
		return noSuchEvent;
	    }
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return invalidInput;
	}
    }

    /**
     * Adds a reminder to the event with the specified <code>eventId</code>,
     * stored in the {@link Calendar} with the specified
     * <code>calendarURI</code> and creates a {@link ServiceResponse}. If the
     * specific event already contains a reminder, the reminder is being
     * updated.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventId
     *            the event id of the event
     * @param reminder
     *            the reminder to be added to the event
     * @return a service response to the specific service
     */
    private ServiceResponse setReminderToEvent(String calendarURI, int eventID,
	    Reminder reminder) {
	try {
	    // INFO: db call
	    int isSet = theServer.updateReminder(calendarURI, eventID,
		    reminder, MyAgenda.COMMIT);
	    if (isSet != 0) {
		theScheduler.updateReminderTask(eventID, reminder);
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return noSuchEvent;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return invalidInput;
	}
    }

    /**
     * Adds a reminder to the event with the specified <code>eventId</code>,
     * stored in the {@link Calendar} with the specified
     * <code>calendarURI</code> and creates a {@link ServiceResponse}. If the
     * specific event already contains a reminder, the reminder is being
     * updated.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventId
     *            the event id of the event
     * @param reminder
     *            the reminder to be added to the event
     * @return a service response to the specific service
     */
    private ServiceResponse setReminderTypeToEvent(String calendarURI,
	    int eventID, ReminderType reminderType) {
	try {
	    // INFO db call
	    boolean isSet = theServer.updateReminderType(calendarURI, eventID,
		    reminderType, MyAgenda.COMMIT);
	    if (isSet) {
		theScheduler.updateReminderTask(eventID, reminderType);
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return noSuchEvent;
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    return invalidInput;
	}
    }

    /*********************
     * Context Events
     *********************/
    public void eventAdded(Calendar calendar, Event event) {
	Activator.log.log(LogService.LOG_INFO,
		"AgendaProvider: publishing a context event (event added)"); //$NON-NLS-1$

	event.setCEType(CEType.newEvent);
	calendar.addEvent(event);
	ContextEvent ce = new ContextEvent(calendar, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	mainLogger.info("Agenda\tContext Event sent: \'add calendar event\' ("
		+ startTime + ")");
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event added)");
    }

    public void eventDeleted(Calendar calendar, int eventId) {
	Activator.log.log(LogService.LOG_INFO,
		"AgendaProvider: publishing a context event (event deleted)"); //$NON-NLS-1$

	Event e = new Event(null);
	e.setEventID(eventId);
	e.setCEType(CEType.deletedEvent);
	calendar.addEvent(e);
	ContextEvent ce = new ContextEvent(calendar, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	mainLogger
		.info("Agenda\tContext Event sent: \'delete calendar event\' ("
			+ startTime + ")");
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event deleted)");
    }

    public void eventUpdated(Calendar calendar, Event e) {
	Activator.log.log(LogService.LOG_INFO,
		"AgendaProvider: publishing a context event (event updated)"); //$NON-NLS-1$
	e.setCEType(CEType.updatedEvent);
	calendar.addEvent(e);
	ContextEvent ce = new ContextEvent(calendar, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	mainLogger
		.info("Agenda\tContext Event sent: \'update calendar event\' ("
			+ startTime + ")");

	// MessageDialog(null,
	// "AgendaProvider: publishing a context event (event updated)");
    }

    public void reminderTime(Event e) {
	Activator.log.log(LogService.LOG_INFO,
		"AgendaProvider: publishing a context event (event reminder)"); //$NON-NLS-1$
	e.setCEType(CEType.reminder);
	Calendar c = e.getParentCalendar();
	c.addEvent(e);
	ContextEvent ce = new ContextEvent(c, Calendar.PROP_HAS_EVENT);

	long startTime = System.currentTimeMillis();
	cp.publish(ce);

	mainLogger.info("Agenda\tContext Event sent: \'reminder triggered\' ("
		+ startTime + ")");
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event reminder)");
	Reminder rem = e.getReminder();
	String message;
	if (rem != null) {
	    message = rem.getMessage() + "\n"; //$NON-NLS-1$
	} else {
	    message = Messages.getString("AgendaProvider.ReminderTriggered") + ".\n"; //$NON-NLS-1$
	}

	message += Messages
		.getString("AgendaProvider.DoYouWantToBeRemindedAgain"); //$NON-NLS-1$

	// int answer = JOptionPane.showConfirmDialog(null, message, "Reminder",
	// JOptionPane.OK_OPTION);
	// if (answer != JOptionPane.OK_OPTION) {
	// Activator.log.log(LogService.LOG_INFO,
	// "Reminder won't be triggered again");
	// cancelReminder(c, e);
	// } else {
	// Activator.log.log(LogService.LOG_INFO,
	// "Reminder will be triggered again");
	// }

	// TODO: find out why the exception is thrown
	try {
	    WrapperActivator.getOutputPublisher()
		    .showReminderConfirmationDialog(message, c.getURI(),
			    e.getEventID(), testUser);
	} catch (Exception e1) {
	    Activator.log.log(LogService.LOG_ERROR, Messages
		    .getString("AgendaProvider.30")); //$NON-NLS-1$
	    e1.printStackTrace();
	}

    }

    public void startEventTime(Event e) {
	Activator.log.log(LogService.LOG_INFO, Messages
		.getString("AgendaProvider.31")); //$NON-NLS-1$
	e.setCEType(CEType.startEvent);
	Calendar c = e.getParentCalendar();
	c.addEvent(e);
	ContextEvent ce = new ContextEvent(c, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	mainLogger
		.info("Agenda\tContext Event sent: \'calendar event started\' ("
			+ startTime + ")");
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event started)");
    }

    public void endEventTime(Event e) {
	Activator.log.log(LogService.LOG_INFO, Messages
		.getString("AgendaProvider.32")); //$NON-NLS-1$
	e.setCEType(CEType.endEvent);
	Calendar c = e.getParentCalendar();
	c.addEvent(e);
	ContextEvent ce = new ContextEvent(c, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	mainLogger
		.info("Agenda\tContext Event sent: \'calendar event finished\' ("
			+ startTime + ")");
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event ended)");
    }

    public static void main(String[] str) {
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event ended)");
	JTextArea area = new JTextArea();
	area.setText(Messages.getString("AgendaProvider.33")); //$NON-NLS-1$
	area.setRows(5);
	area.setColumns(10);
	area.setEditable(true);
	JScrollPane scrollpane = new JScrollPane(area);

	Object[] array = { new JLabel(Messages.getString("AgendaProvider.34")), //$NON-NLS-1$
		scrollpane, };
	JOptionPane.showConfirmDialog(null, array, Messages
		.getString("AgendaProvider.Reminder"), JOptionPane.OK_OPTION); //$NON-NLS-1$
    }
}
