package org.universAAL.agenda.server;

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

import org.universAAL.ontology.agenda.CEType;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.agenda.server.database.Scheduler;
import org.universAAL.agenda.server.gui.wrapper.WrapperActivator;
import org.universAAL.agenda.server.unit_impl.AgendaStateListener;
import org.universAAL.agenda.server.unit_impl.AgendaDB;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.User;

/**
 * Receives service calls for Agenda server and obtaines requested informations
 * from Agenda database.
 * 
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class AgendaProvider extends ServiceCallee implements
	AgendaStateListener {
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    static final File confHome = new File(new BundleConfigHome("agenda")
	    .getAbsolutePath());
    // static final String CAL_URI_PREFIX =
    // ProvidedAgendaService.AGENDA_SERVER_NAMESPACE + "controlledAgenda";
    // //TODO: change name

    static final String LOCATION_URI_PREFIX = "urn:aal_space:everywhere#"; //$NON-NLS-1$

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

    /**  */
    private AgendaDB theServer;

    /**  */
    private Scheduler theScheduler;

    /**  */
    private ContextPublisher cp;

    /**
     * Helper method to construct the ontological declaration of context events
     * published by AgendaProvider.
     */
    private static ContextEventPattern[] getProvidedContextEvents() {

	ContextEventPattern cep1 = new ContextEventPattern();
	ContextEventPattern cep2 = new ContextEventPattern();
	cep1.addRestriction(MergedRestriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, Event.MY_URI));
	cep2.addRestriction(MergedRestriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, Calendar.MY_URI));
	return new ContextEventPattern[] { cep1, cep2 };
    }

    /**
     * 
     * 
     * @param mcontext
     * @throws FileNotFoundException
     * @throws IOException
     */
    public AgendaProvider(ModuleContext moduleContext)
	    throws FileNotFoundException, IOException {
	super(moduleContext, ProvidedAgendaService.profiles);
	mcontext = moduleContext;
	// prepare for context publishing
	ContextProvider info = new ContextProvider(
		ProvidedAgendaService.AGENDA_SERVER_NAMESPACE
			+ "AgendaContextProvider"); //$NON-NLS-1$
	info.setType(ContextProviderType.controller);

	// define ContextEventPattern of provided context events
	info.setProvidedEvents(getProvidedContextEvents());
	cp = new DefaultContextPublisher(mcontext, info);

	// load credentials from config folder
	Properties prop = new Properties();
	LogUtils.logInfo(mcontext, this.getClass(), "constructor",
		new Object[] {
			"AgendaProvider reading credentials from confHome: ",
			confHome }, null);
	InputStream in = new FileInputStream(new File(confHome,
		"credentials.properties"));
	prop.load(in);

	// start the server
	theServer = new AgendaDB(
		moduleContext,
		prop.getProperty("database"), prop.getProperty("username"), prop.getProperty("password")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	theScheduler = new Scheduler(theServer, this, mcontext);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken
     * ()
     */
    public void communicationChannelBroken() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.ServiceCallee#handleCall(org.universAAL
     * .middleware.service.ServiceCall)
     */
    public ServiceResponse handleCall(ServiceCall call) {
	if (call == null)
	    return null;

	String operation = call.getProcessURI();

	if (operation == null)
	    return null;

	LogUtils.logInfo(mcontext, this.getClass(), "handleCall",
		new Object[] { "AgendaProvider received service call. operation: " +operation}, null);

	if (operation.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDARS)) {
	    LogUtils
	    .logInfo(
		    mcontext,
		    this.getClass(),
		    "handleCall",
		    new Object[] { "Requested service: Get all calendars" },
		    null);
	    return getControlledCalendars();
	}

	
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_ALL_CATEGORIES)) {
	    LogUtils
	    .logInfo(
		    mcontext,
		    this.getClass(),
		    "handleCall",
		    new Object[] { "Requested service: Get all categories" },
		    null);
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
	

	//TODO new
	if (operation.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_OWNER_NAME)) {
	    if (inCalendarName == null)
		return null;
	    LogUtils
	    .logInfo(
		    mcontext,
		    this.getClass(),
		    "handleCall",
		    new Object[] { "Requested service: Get Calendar owner name for calendar name: " +inCalendarName},
		    null);
	       
	    return getCalendarOwnerName((String) inCalendarName);
	}

	// 'get calendars of a user' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_BY_OWNER)) {
	    if (inCalendarOwner == null || !(inCalendarOwner instanceof User))
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Get calendars of a user" },
			    null);
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
	    LogUtils.logInfo(mcontext, this.getClass(), "handleCall",
		    new Object[] { "Requested service: Add a new calendar" },
		    null);
	    return addNewCalendar((Calendar) inCalendar, (User) inCalendarOwner);
	}

	// 'remove Existing calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_REMOVE_CALENDAR)) {
	    if (inCalendar == null)
		return null;

	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Remove existing calendar" },
			    null);
	    return removeCalendar((Calendar) inCalendar);
	}

	// 'get calendar's event list' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_EVENT_LIST)) {
	    if (inCalendar == null)
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Get calendar's event list" },
			    null);
	    return getCalendarEventList(((Calendar) inCalendar).getURI());
	}

	// 'add event to calendar' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_ADD_EVENT_TO_CALENDAR)) {
	    if (inCalendar == null)
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Add event to calendar" },
			    null);
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
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Add event list to calendar" },
			    null);
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
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Retrieve an event from calendar" },
			    null);
	    return getCalendarEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue());
	}

	// 'delete an event from calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_DELETE_EVENT)) {
	    if (!(inEventId instanceof Integer)) {
		return null;
	    }
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Delete an event from calendar" },
			    null);

	    return deleteCalendarEvent(((Integer) inEventId).intValue());
	}

	// 'update an event from calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_UPDATE_EVENT)) {
	    if ((inCalendar == null) || !(inEventId instanceof Integer)
		    || (inEvent == null))
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Update an event from calendar" },
			    null);
	    return updateCalendarEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue(), (Event) inEvent);
	}

	// 'set a reminder to an event from calendar' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_SET_REMINDER)) {
	    if ((inCalendar == null) || !(inEventId instanceof Integer)
		    || (inEventReminder == null))
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Set a reminder to an event from calendar" },
			    null);
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
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Set a reminder type to an event from calendar" },
			    null);
	    return setReminderTypeToEvent(((Calendar) inCalendar).getURI(),
		    ((Integer) inEventId).intValue(),
		    (ReminderType) inReminderType);
	}

	// 'cancel reminder' service
	if (operation.startsWith(ProvidedAgendaService.SERVICE_CANCEL_REMINDER)) {
	    if ((inCalendar == null) || (inEvent == null))
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Cancel reminder" },
			    null);
	    return cancelReminder((Calendar) inCalendar, (Event) inEvent);
	}

	// 'get Calendar URI by the name of it, and its owner' service
	if (operation
		.startsWith(ProvidedAgendaService.SERVICE_GET_CALENDAR_BY_NAME_AND_OWNER)) {
	    if (inCalendarName == null)
		return null;

	    if (!(inCalendarOwner instanceof User))
		return null;
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Requested service: Get Calendar URI by its name, and its owner" },
			    null);
	    return getCalendarByName((String) inCalendarName,
		    (User) inCalendarOwner);
	}

	return null;
    }// end handleCall

    /**
     * Adds a new {@Calendar} to user's database.
     * 
     * @param calendar
     * @param owner
     * @return a service response to the specific service
     */
    private ServiceResponse addNewCalendar(Calendar calendar, User owner) {
	try {
	    Calendar newC = theServer.addCalendar(calendar, owner,
		    AgendaDB.COMMIT);
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
	    LogUtils.logError(mcontext, this.getClass(), "addNewCalendar",
		    new Object[] { "Exception while adding new calendar." }, e);
	    return invalidInput;
	}
    }

    /**
     * Remove a {@Calendar} from user's database.
     * 
     * @param calendar
     * @return a service response to the specific service
     */
    private ServiceResponse removeCalendar(Calendar calendar) {
	try {
	    return theServer.removeCalendar(calendar, AgendaDB.COMMIT) ? new ServiceResponse(
		    CallStatus.succeeded)
		    : notExistingCalendar;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "removeCalendar",
		    new Object[] { "Exception while removing calendar." }, e);
	    return invalidInput;
	}
    }

    /**
     * Cancels any previous defined reminder for the <code>event</event>.
     * 
     * @param calendar
     * @param event
     * @return a service response to the specific service
     */
    private ServiceResponse cancelReminder(Calendar calendar, Event event) {
	try {
	    if (cancelReminder(calendar.getURI(), event.getEventID())) {
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return noSuchEventOrCalendar;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "cancelReminder",
		    new Object[] { "Exception while canceling reminder." }, e);
	    return invalidInput;
	}
    }

    /**
     * Cancel reminder
     * 
     * @param calendarURI
     *            calendar URI
     * @param eventID
     *            event ID
     * @return status if it is canceled or not
     */
    public boolean cancelReminder(String calendarURI, int eventID) {
	if (theServer.cancelReminder(calendarURI, eventID, AgendaDB.COMMIT)) {
	    LogUtils.logInfo(mcontext, this.getClass(), "cancelReminder",
		    new Object[] { "Cancel reminder: " + eventID }, null);

	    theScheduler.removeReminderTask(eventID);
	    return true;
	}
	return false;
    }

    /**
     * Given the name of the calendar, it returns the URI, wrapped in a Calendar
     * object.
     * 
     * @param calendarName
     *            a calendar name
     * @param owner
     * @return a service response to the specific service
     */
    private ServiceResponse getCalendarByName(String calendarName, User owner) {
	try {
	    Calendar calendar = theServer.getCalendarByNameAndOwner(
		    calendarName, owner, AgendaDB.COMMIT);
	    if (calendar != null) {
		//		System.out.println("Calendar URI: " + calendar.getURI()); //$NON-NLS-1$
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(
			ProvidedAgendaService.OUTPUT_CALENDAR, calendar));
		return sr;
	    }
	    return noSuchEventOrCalendar;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getCalendarByName",
			    new Object[] { "Exception while getting calendar by name." },
			    e);
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
		    AgendaDB.COMMIT);
	    if (eventId > 0) {
		event = theServer.getEventFromCalendar(calendarURI, eventId,
			AgendaDB.COMMIT);
		if (event == null) {
		    LogUtils.logError(mcontext, this.getClass(),
			    "addEventToCalendar",
			    new Object[] { "Error while retrieving event." },
			    null);
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
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "addEventToCalendar",
			    new Object[] { "Exception while adding event to calendar." },
			    e);
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
			    event, AgendaDB.COMMIT);
		    if (eventID > 0) {
			event.setEventID(eventID);
			// theScheduler.updateReminderTask(event);
			theScheduler.updateScheduler(event);
		    }
		}
	    }
	    return sr;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "addEventListToCalendar",
			    new Object[] { "Exception while adding event list to calendar." },
			    e);
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
		    AgendaDB.COMMIT);
	    sr
		    .addOutput(new ProcessOutput(
			    ProvidedAgendaService.OUTPUT_CALENDAR_EVENT_LIST,
			    eventList));
	    return sr;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getCalendarEventList",
			    new Object[] { "Exception while getting calendar event list." },
			    e);
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
		    AgendaDB.COMMIT);
	    sr.addOutput(new ProcessOutput(
		    ProvidedAgendaService.OUTPUT_CALENDAR_EVENT, event));
	    return sr;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "getCalendarEvent",
		    new Object[] { "Exception while getting calendar event." },
		    e);
	    return invalidInput;
	}
    }

    /**
     * Deletes the event with the specified <code>eventId</code>, stored in the.
     * 
     * @param eventId
     *            an event id; to delete the whole event
     * @return a service response to the specific service {@link Calendar} with
     *         the specified <code>calendarURI</code> and creates a
     *         {@link ServiceResponse}.
     */
    private ServiceResponse deleteCalendarEvent(int eventId) {
	try {
	    // INFO: db call
	    String deleteOK = theServer.removeEvent(eventId, AgendaDB.COMMIT);
	    if (deleteOK != null) {
		theScheduler.removeReminderTask(eventId);
		// send a context event: event deleted
		eventDeleted(new Calendar(deleteOK), eventId);
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return deletingFailed;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "deleteCalendarEvent",
			    new Object[] { "Exception while deleting calendar event." },
			    e);
	    return invalidInput;
	}
    }

    /**
     * Retrieves a {@link List} of the {@link Calendar}s which are controlled by
     * the server to <code>ownerName</code> and creates a @link ServiceResponse}.
     * 
     * @return a service response to the specific service
     *         {@link ServiceResponse}
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
     * specified to <code>owner</code> and creates a {@link ServiceResponse}.
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
     * in db.
     * 
     * @return a service response to the specific service
     */
    private ServiceResponse getAllEventCategories() {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	List categories = theServer.getAllEventCategories(AgendaDB.COMMIT);
	// dArrayList al = new ArrayList(calendars.length);
	// for (int i = 0; i < calendars.length; i++)
	// al.add(calendars[i]);
	sr.addOutput(new ProcessOutput(
		ProvidedAgendaService.OUTPUT_EVENT_CATEGORIES, categories));
	return sr;
    }

    //TODO new
    /**
     * Retrieves a owner username for a given calendar name
     * 
     * @return a service response to the specific service
     */
    private ServiceResponse getCalendarOwnerName( String calendarName) {
	
	    String ownerName = theServer.getCalendarOwnerName(
		    calendarName);
	    if (ownerName != null) {
		
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		sr.addOutput(new ProcessOutput(
			ProvidedAgendaService.OUTPUT_CALENDAR_OWNER_NAME, ownerName));
		return sr;
	    }
	    else return new ServiceResponse(CallStatus.serviceSpecificFailure);

    }
    /**
     * Updates the event with the specified <code>eventId</code>, stored in the.
     * 
     * @param calendarURI
     *            a calendar URI
     * @param eventId
     *            the event id of the updated event
     * @param updatedEvent
     *            the updated event
     * @return a service response to the specific service {@link Calendar} with
     *         the specified <code>calendarURI</code> and creates a
     *         {@link ServiceResponse}.
     */
    private ServiceResponse updateCalendarEvent(String calendarURI,
	    int eventId, Event updatedEvent) {
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	try {
	    // INFO: db call
	    updatedEvent.setEventID(eventId);
	    boolean updated = theServer.updateEvent(calendarURI, updatedEvent,
		    AgendaDB.COMMIT);
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
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "updateCalendarEvent",
			    new Object[] { "Exception while updating calendar event." },
			    e);
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
     * @param eventID
     * @param reminder
     *            the reminder to be added to the event
     * @return a service response to the specific service
     */
    private ServiceResponse setReminderToEvent(String calendarURI, int eventID,
	    Reminder reminder) {
	try {
	    // INFO: db call
	    int isSet = theServer.updateReminder(calendarURI, eventID,
		    reminder, AgendaDB.COMMIT);
	    if (isSet != 0) {
		theScheduler.updateReminderTask(eventID, reminder);
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return noSuchEvent;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "setReminderToEvent",
			    new Object[] { "Exception while adding reminder to an event." },
			    e);
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
     * @param eventID
     * @param reminderType
     * @return a service response to the specific service
     */
    private ServiceResponse setReminderTypeToEvent(String calendarURI,
	    int eventID, ReminderType reminderType) {
	try {
	    // INFO db call
	    boolean isSet = theServer.updateReminderType(calendarURI, eventID,
		    reminderType, AgendaDB.COMMIT);
	    if (isSet) {
		theScheduler.updateReminderTask(eventID, reminderType);
		return new ServiceResponse(CallStatus.succeeded);
	    }
	    return noSuchEvent;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "setReminderTypeToEvent",
			    new Object[] { "Exception while setting reminder type to an event." },
			    e);
	    return invalidInput;
	}
    }

    /**
     * ******************* Context Events *******************.
     * 
     * @param calendar
     * @param event
     */
    public void eventAdded(Calendar calendar, Event event) {
	event.setCEType(CEType.newEvent);
	calendar.addEvent(event);
	ContextEvent ce = new ContextEvent(calendar, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);

	LogUtils.logInfo(mcontext, this.getClass(), "eventAdded", new Object[] {
		"Context Event sent: \'add calendar event\' with startTime:",
		startTime }, null);

	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event added)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.agenda.server.unit_impl.AgendaStateListener#eventDeleted
     * (org.universAAL.ontology.agenda.Calendar, int)
     */
    public void eventDeleted(Calendar calendar, int eventId) {
	Event e = new Event(null);
	e.setEventID(eventId);
	e.setCEType(CEType.deletedEvent);
	calendar.addEvent(e);
	ContextEvent ce = new ContextEvent(calendar, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"eventDeleted",
			new Object[] {
				"Context Event sent: \'delete calendar event\' with startTime:",
				startTime }, null);

	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event deleted)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.agenda.server.unit_impl.AgendaStateListener#eventUpdated
     * (org.universAAL.ontology.agenda.Calendar,
     * org.universAAL.ontology.agenda.Event)
     */
    public void eventUpdated(Calendar calendar, Event e) {

	e.setCEType(CEType.updatedEvent);
	calendar.addEvent(e);
	ContextEvent ce = new ContextEvent(calendar, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);

	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"eventUpdated",
			new Object[] {
				"Context Event sent: \'update calendar event\' with startTime:",
				startTime }, null);

	// MessageDialog(null,
	// "AgendaProvider: publishing a context event (event updated)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.agenda.server.unit_impl.AgendaStateListener#reminderTime
     * (org.universAAL.ontology.agenda.Event)
     */
    public void reminderTime(Event e) {

	e.setCEType(CEType.reminder);
	Calendar c = e.getParentCalendar();
	c.addEvent(e);
	ContextEvent ce = new ContextEvent(c, Calendar.PROP_HAS_EVENT);

	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"reminderTime",
			new Object[] {
				"Context Event sent: \'reminder triggered\' with startTime:",
				startTime }, null);

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

	//see in DB who should receive this reminder (who is the owner)
	String calOwnerName = theServer.getCalendarOwnerName(e.getParentCalendar().toString());
	LogUtils
	    .logInfo(
		    mcontext,
		    this.getClass(),
		    "reminderTime",
		    new Object[] { "Reminder triggered for user: "+calOwnerName+" for calendar: "+e.getParentCalendar().toString()+" with message: "+message },
		    null);
	try {
	    WrapperActivator.getMyUICaller().showReminderConfirmationDialog(
		    message, c.getURI(), e.getEventID(), new User (Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX+ calOwnerName));//TESTUSER WAS HERE
	} catch (Exception e1) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "reminderTime",
			    new Object[] { "Exception while showing reminder confirmation dialog, via UI bus" },
			    e1);
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.agenda.server.unit_impl.AgendaStateListener#startEventTime
     * (org.universAAL.ontology.agenda.Event)
     */
    public void startEventTime(Event e) {
	e.setCEType(CEType.startEvent);
	Calendar c = e.getParentCalendar();
	c.addEvent(e);
	ContextEvent ce = new ContextEvent(c, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"startEventTime",
			new Object[] {
				"Context Event sent: \'calendar event started\' with startTime:",
				startTime }, null);

	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event started)");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.agenda.server.unit_impl.AgendaStateListener#endEventTime
     * (org.universAAL.ontology.agenda.Event)
     */
    public void endEventTime(Event e) {
	e.setCEType(CEType.endEvent);
	Calendar c = e.getParentCalendar();
	c.addEvent(e);
	ContextEvent ce = new ContextEvent(c, Calendar.PROP_HAS_EVENT);
	long startTime = System.currentTimeMillis();
	cp.publish(ce);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"endEventTime",
			new Object[] {
				"Context Event sent: \'calendar event ended\' with startTime:",
				startTime }, null);
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event ended)");
    }

    /**
     * 
     * 
     * @param str
     */
    public static void main(String[] str) {
	// JOptionPane.showMessageDialog(null,
	// "AgendaProvider: publishing a context event (event ended)");
	JTextArea area = new JTextArea();
	area.setText(Messages.getString("AgendaProvider.33")); //$NON-NLS-1$
	area.setRows(5);
	area.setColumns(10);
	area.setEditable(true);
	JScrollPane scrollpane = new JScrollPane(area);

	Object[] array = {
		new JLabel(Messages.getString("AgendaProvider.EnterSomeText")), //$NON-NLS-1$
		scrollpane, };
	JOptionPane.showConfirmDialog(null, array, Messages
		.getString("AgendaProvider.Reminder"), JOptionPane.OK_OPTION); //$NON-NLS-1$
    }
}
