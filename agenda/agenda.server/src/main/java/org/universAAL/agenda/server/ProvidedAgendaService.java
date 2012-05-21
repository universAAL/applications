package org.universAAL.agenda.server;

//j2se packages
//IZMJENE:
//	- dodano getThePath() kod svakog PropertyPath-a koji vraca String[]

import java.util.Hashtable;

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class ProvidedAgendaService extends CalendarAgenda {
    // define service namespace + class uri
    public static final String AGENDA_SERVER_NAMESPACE = "http://ontology.universAAL.org/AgendaServer.owl#";
    public static final String MY_URI = AGENDA_SERVER_NAMESPACE
	    + "AgendaService";

    // define the uri for each service provided
    private static final int PROVIDED_SERVICES = 18; // The number of services
    // provided by this class
    static final String SERVICE_GET_CALENDARS = AGENDA_SERVER_NAMESPACE
	    + "getControlledCalendars1";
    static final String SERVICE_GET_CALENDAR_OWNER = AGENDA_SERVER_NAMESPACE
	    + "getCalendarOwner2";
    static final String SERVICE_CHANGE_CALENDAR_OWNER = AGENDA_SERVER_NAMESPACE
	    + "changeCalendarOwner3";
    static final String SERVICE_ADD_EVENT_TO_CALENDAR = AGENDA_SERVER_NAMESPACE
	    + "addEvent4";
    static final String SERVICE_GET_CALENDAR_EVENT_LIST = AGENDA_SERVER_NAMESPACE
	    + "getCalendarEventList5";
    static final String SERVICE_SET_ACTIVE_CALENDAR = AGENDA_SERVER_NAMESPACE
	    + "setActiveCalendar6";
    static final String SERVICE_GET_CALENDAR_EVENT = AGENDA_SERVER_NAMESPACE
	    + "getCalendarEvent7";
    static final String SERVICE_ADD_EVENT_LIST = AGENDA_SERVER_NAMESPACE
	    + "addEventList8";
    static final String SERVICE_UPDATE_EVENT = AGENDA_SERVER_NAMESPACE
	    + "updateEvent9";
    static final String SERVICE_DELETE_EVENT = AGENDA_SERVER_NAMESPACE
	    + "deleteEvent10";
    static final String SERVICE_SET_REMINDER = AGENDA_SERVER_NAMESPACE
	    + "setReminder11";
    static final String SERVICE_SET_REMINDER_TYPE = AGENDA_SERVER_NAMESPACE
	    + "setReminderType12";
    static final String SERVICE_ADD_NEW_CALENDAR = AGENDA_SERVER_NAMESPACE
	    + "addCalendar13";
    static final String SERVICE_CANCEL_REMINDER = AGENDA_SERVER_NAMESPACE
	    + "cancelReminder14";
    static final String SERVICE_GET_ALL_CATEGORIES = AGENDA_SERVER_NAMESPACE
	    + "getAllCategories15";
    static final String SERVICE_GET_CALENDAR_BY_NAME_AND_OWNER = AGENDA_SERVER_NAMESPACE
	    + "getCalendarByName16";
    static final String SERVICE_REMOVE_CALENDAR = AGENDA_SERVER_NAMESPACE
	    + "removeCalendar17";
    static final String SERVICE_START_UI = AGENDA_SERVER_NAMESPACE
	    + "startUserInterface18";
    static final String SERVICE_GET_CALENDAR_BY_OWNER = AGENDA_SERVER_NAMESPACE
	    + "getCalendarByOwner19";

    // define the uri of every input, which is needed from the service
    // e.g. we have to call serviceX with one or more arguments,
    // then we have to define one or more input_uri, respectively
    static final String INPUT_CALENDAR_NAME = AGENDA_SERVER_NAMESPACE
	    + "iCalendarName";
    static final String INPUT_CALENDAR = AGENDA_SERVER_NAMESPACE
	    + "iCalendarURI";
    static final String INPUT_EVENT_URI = AGENDA_SERVER_NAMESPACE + "iEventURI";
    static final String INPUT_CALENDAR_OWNER = AGENDA_SERVER_NAMESPACE
	    + "iCalendarOwner";
    static final String INPUT_EVENT = AGENDA_SERVER_NAMESPACE + "iEvent"; // addEvent()
    static final String INPUT_EVENT_LIST = AGENDA_SERVER_NAMESPACE
	    + "iEventList"; // addEvent()
    static final String INPUT_EVENT_ID = AGENDA_SERVER_NAMESPACE + "iEventId";
    static final String INPUT_EVENT_REMINDER = AGENDA_SERVER_NAMESPACE
	    + "iEventReminder";
    static final String INPUT_EVENT_REMINDER_TYPE = AGENDA_SERVER_NAMESPACE
	    + "iEventReminderType";

    // define the uri of every output, which is needed from the service
    // e.g. if serviceX returns one or more arguments
    // then we have to define one or more output_uri, respectively
    static final String OUTPUT_CONTROLLED_CALENDARS = AGENDA_SERVER_NAMESPACE
	    + "oControlledCalendars";
    static final String OUTPUT_CALENDAR = AGENDA_SERVER_NAMESPACE + "oCalendar";
    static final String OUTPUT_CALENDAR_OWNER = AGENDA_SERVER_NAMESPACE
	    + "oCalendarOwner";
    static final String OUTPUT_EVENT_ID = AGENDA_SERVER_NAMESPACE + "oEventID";
    static final String OUTPUT_CALENDAR_EVENT_LIST = AGENDA_SERVER_NAMESPACE
	    + "oEventList";
    static final String OUTPUT_CALENDAR_EVENT_ID_LIST = AGENDA_SERVER_NAMESPACE
	    + "oEventIdList";
    static final String OUTPUT_CALENDAR_EVENT = AGENDA_SERVER_NAMESPACE
	    + "oCalendarEvent";
    static final String OUTPUT_EVENT_CATEGORIES = AGENDA_SERVER_NAMESPACE
	    + "oEventCategories";

    static final ServiceProfile[] profiles = new ServiceProfile[PROVIDED_SERVICES];
    private static Hashtable serverAgendaRestrictions = new Hashtable();

    static {
	OntologyManagement.getInstance().register(
		new SimpleOntology(MY_URI, CalendarAgenda.MY_URI,
			new ResourceFactoryImpl() {
			    @Override
			    public Resource createInstance(String classURI,
				    String instanceURI, int factoryIndex) {
				return new ProvidedAgendaService(instanceURI);
			    }
			}));

	// add restriction about what the service controls
	addRestriction((MergedRestriction) CalendarAgenda
		.getClassRestrictionsOnProperty(CalendarAgenda.MY_URI,
			CalendarAgenda.PROP_CONTROLS).copy(),
		new String[] { CalendarAgenda.PROP_CONTROLS },
		serverAgendaRestrictions);

	/**********************************************************************
	 * INPUT(S), OUTPUT(S), RESTRICTION(S), PROPERTY_PATH(S) DECLERATIONS *
	 **********************************************************************/
	ProcessInput inCalendar = new ProcessInput(INPUT_CALENDAR);
	inCalendar.setParameterType(Calendar.MY_URI);// (Calendar.MY_URI);
	inCalendar.setCardinality(1, 1);

	ProcessInput inEvent = new ProcessInput(INPUT_EVENT);
	inEvent.setParameterType(Event.MY_URI);
	inEvent.setCardinality(1, 1);

	ProcessInput inEventList = new ProcessInput(INPUT_EVENT_LIST);
	inEventList.setParameterType(Event.MY_URI);
	inEventList.setCardinality(0, 1);

	ProcessInput inEventReminder = new ProcessInput(INPUT_EVENT_REMINDER);
	inEventReminder.setParameterType(Reminder.MY_URI);
	inEventReminder.setCardinality(1, 1);

	ProcessInput inEventId = new ProcessInput(INPUT_EVENT_ID);
	inEventId.setParameterType(TypeMapper.getDatatypeURI(Integer.class));
	inEventId.setCardinality(1, 1);

	ProcessInput inReminderType = new ProcessInput(
		INPUT_EVENT_REMINDER_TYPE);
	inReminderType.setParameterType(ReminderType.MY_URI);
	inReminderType.setCardinality(1, 1);

	ProcessInput inCalendarName = new ProcessInput(INPUT_CALENDAR_NAME);
	inCalendarName
		.setParameterType(TypeMapper.getDatatypeURI(String.class));
	inCalendarName.setCardinality(1, 1);

	// service has an output: the list of controlled calendars
	ProcessOutput outCalList = new ProcessOutput(
		OUTPUT_CONTROLLED_CALENDARS);

	// output has a type: Calendar
	outCalList.setParameterType(Calendar.MY_URI);
	outCalList.setCardinality(0, 1);

	// service has an output: the list of controlled calendar
	ProcessOutput outCalendar = new ProcessOutput(OUTPUT_CALENDAR);
	// output has a type: Calendar
	outCalendar.setParameterType(Calendar.MY_URI);
	outCalendar.setCardinality(1, 1);

	ProcessOutput outEventId = new ProcessOutput(OUTPUT_EVENT_ID);
	outEventId.setParameterType(TypeMapper.getDatatypeURI(Integer.class));
	outEventId.setCardinality(1, 1);

	ProcessOutput outEvent = new ProcessOutput(OUTPUT_CALENDAR_EVENT);
	outEvent.setParameterType(Event.MY_URI);
	outEvent.setCardinality(1, 1);

	ProcessOutput outEventIdList = new ProcessOutput(
		OUTPUT_CALENDAR_EVENT_ID_LIST);
	outEventIdList.setParameterType(TypeMapper
		.getDatatypeURI(Integer.class));
	outEventIdList.setCardinality(0, 1);

	ProcessOutput outEventList = new ProcessOutput(
		OUTPUT_CALENDAR_EVENT_LIST);
	outEventList.setParameterType(TypeMapper.getDatatypeURI(Integer.class));
	outEventList.setCardinality(0, 1);

	ProcessOutput outEventCategories = new ProcessOutput(
		OUTPUT_EVENT_CATEGORIES);
	outEventCategories.setParameterType(TypeMapper
		.getDatatypeURI(String.class));
	outEventList.setCardinality(0, 1);

	PropertyPath ppCalendar = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS });
	// String[] ppCalendar = new String[] {CalendarAgenda.PROP_CONTROLS};
	PropertyPath ppCalendarName = new PropertyPath(
		null,
		true,
		new String[] { CalendarAgenda.PROP_CONTROLS, Calendar.PROP_NAME });
	PropertyPath ppCalendarOwner = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_OWNER });
	PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
	PropertyPath ppEventId = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_ID });
	PropertyPath ppReminder = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_HAS_REMINDER });
	PropertyPath ppReminderType = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_HAS_REMINDER,
			Reminder.PROP_HAS_TYPE });
	PropertyPath ppEventCategory = new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS,
			Calendar.PROP_HAS_EVENT, Event.PROP_HAS_EVENT_DETAILS,
			EventDetails.PROP_CATEGORY });

	MergedRestriction resCalendar = MergedRestriction
		.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS,
			inCalendar.asVariableReference());
	MergedRestriction resEventID = MergedRestriction
		.getFixedValueRestriction(Event.PROP_ID, inEventId
			.asVariableReference());

	/*********************************************
	 * service 1: List<Calendar> getCalendars() *
	 *********************************************/
	ProvidedAgendaService getCalendars = new ProvidedAgendaService(
		SERVICE_GET_CALENDARS);
	profiles[0] = getCalendars.getProfile(); // initialize the service
	// profile
	profiles[0].addOutput(outCalList); // connect the service with the
	// output
	// Declares that the output parameter <i>output</i> will reflect the
	// value of a property reachable by the given property path:
	// CalendarAgenda.PROP_CONTROLS
	profiles[0].addSimpleOutputBinding(outCalList, ppCalendar.getThePath());

	/***********************************************
	 * service 2: User getCalendarOwner(Calendar c)*
	 ***********************************************/
	ProvidedAgendaService getCalendarOwner = new ProvidedAgendaService(
		SERVICE_GET_CALENDAR_OWNER);
	ProcessOutput output = new ProcessOutput(OUTPUT_CALENDAR_OWNER);
	output.setParameterType(User.MY_URI);
	profiles[1] = getCalendarOwner.getProfile();
	profiles[1].addOutput(output);
	PropertyPath ownerPP = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_OWNER });
	profiles[1].addSimpleOutputBinding(output, ownerPP.getThePath());
	getCalendarOwner.addInstanceLevelRestriction(resCalendar, ppCalendar
		.getThePath());
	profiles[1].addInput(inCalendar);

	// unnecessary
	/********************************************************************
	 * service 3: boolean changeCalendarOwner(Calendar c, User newOwner)*
	 ********************************************************************/
	/*
	 * ProvidedAgendaService changeCalendarOwner = new
	 * ProvidedAgendaService(SERVICE_CHANGE_CALENDAR_OWNER);
	 * changeCalendarOwner.addInstanceLevelRestriction(resCalendar,
	 * ppCalendar.getThePath());
	 * 
	 * ProcessInput inOwnerName = new ProcessInput(INPUT_CALENDAR_OWNER);
	 * inOwnerName.setCardinality(1, 1);
	 * inOwnerName.setParameterType(TypeMapper
	 * .getDatatypeURI(String.class));
	 */

	/***********************************************************
	 * service 4: int addEvent(Calendar calendar, Event event) *
	 ***********************************************************/
	ProvidedAgendaService addEvent = new ProvidedAgendaService(
		SERVICE_ADD_EVENT_TO_CALENDAR);
	addEvent.addInstanceLevelRestriction(resCalendar, ppCalendar
		.getThePath());

	profiles[3] = addEvent.getProfile();
	profiles[3].addOutput(outEventId);
	profiles[3].addSimpleOutputBinding(outEventId, ppEventId.getThePath());
	profiles[3].addInput(inCalendar);
	profiles[3].addInput(inEvent);
	profiles[3].addAddEffect(ppEvent.getThePath(), inEvent
		.asVariableReference());

	/******************************************************************
	 * service 5: List<Event> getCalendarEventList(Calendar calendar) *
	 ******************************************************************/
	ProvidedAgendaService getCalendarEventList = new ProvidedAgendaService(
		SERVICE_GET_CALENDAR_EVENT_LIST);
	getCalendarEventList.addInstanceLevelRestriction(resCalendar,
		ppCalendar.getThePath());
	profiles[4] = getCalendarEventList.getProfile();
	profiles[4].addOutput(outEventList);
	profiles[4].addSimpleOutputBinding(outEventList, ppEvent.getThePath());
	profiles[4].addInput(inCalendar);

	/***********************************************************
	 * service 6: boolean setActiveCalendar(Calendar calendar) *
	 ***********************************************************/
	ProvidedAgendaService setActiveCalendar = new ProvidedAgendaService(
		SERVICE_SET_ACTIVE_CALENDAR);
	setActiveCalendar.addInstanceLevelRestriction(resCalendar, ppCalendar
		.getThePath());
	profiles[5] = setActiveCalendar.getProfile();
	profiles[5].addInput(inCalendar);
	/*
	 * Important note on this service
	 * 
	 * // the effect is missing; however, for adding the effect, we need to
	 * enhance the service // class CalendarAgenda in the ontology by adding
	 * a new property called PROP_CURRENT_CALENDAR // so, it may not match
	 * when really requested as well as it may may match when not requested
	 * at all
	 */

	/*************************************************************
	 * service 7: Event getEvent(Calendar calendar, int eventID) *
	 *************************************************************/
	ProvidedAgendaService getCalendarEvent = new ProvidedAgendaService(
		SERVICE_GET_CALENDAR_EVENT);
	getCalendarEvent
		.addInstanceLevelRestriction((MergedRestriction) resCalendar
			.copy(), ppCalendar.getThePath());
	getCalendarEvent.addInstanceLevelRestriction(resEventID, ppEventId
		.getThePath());
	profiles[6] = getCalendarEvent.getProfile();
	profiles[6].addInput(inCalendar);
	profiles[6].addInput(inEventId);
	profiles[6].addOutput(outEvent);
	profiles[6].addSimpleOutputBinding(outEvent, ppEvent.getThePath());

	/***********************************************************************
	 * service 8: void addEventList(Calendar calendar, List<Event> events) *
	 ***********************************************************************/
	ProvidedAgendaService addEventList = new ProvidedAgendaService(
		SERVICE_ADD_EVENT_LIST);
	addEvent.addInstanceLevelRestriction(resCalendar, ppCalendar
		.getThePath());

	profiles[7] = addEventList.getProfile();
	profiles[7].addInput(inCalendar);
	profiles[7].addInput(inEventList);
	profiles[7].addAddEffect(ppEvent.getThePath(), inEventList
		.asVariableReference());

	/****************************************************************************
	 * service 9: void updateEvent(Calendar calendar, int eventID, Event
	 * event) *
	 ****************************************************************************/
	ProvidedAgendaService updateEvent = new ProvidedAgendaService(
		SERVICE_UPDATE_EVENT);
	updateEvent.addInstanceLevelRestriction((MergedRestriction) resCalendar
		.copy(), ppCalendar.getThePath());
	updateEvent.addInstanceLevelRestriction(resEventID, ppEventId
		.getThePath());

	profiles[8] = updateEvent.getProfile();
	profiles[8].addInput(inCalendar);
	profiles[8].addInput(inEventId);
	profiles[8].addInput(inEvent);
	profiles[8].addChangeEffect(ppEvent.getThePath(), inEvent
		.asVariableReference());

	/*******************************************************************
	 * service 10: boolean deleteEvent(Calendar calendar, int eventID) *
	 *******************************************************************/
	ProvidedAgendaService deleteCalendarEvent = new ProvidedAgendaService(
		SERVICE_DELETE_EVENT);
	// deleteCalendarEvent.addInstanceLevelRestriction((MergedRestriction)
	// resCalendar.copy(), ppCalendar.getThePath());
	deleteCalendarEvent.addInstanceLevelRestriction(resEventID, ppEventId
		.getThePath());
	profiles[9] = deleteCalendarEvent.getProfile();
	// profiles[9].addInput(inCalendar);
	profiles[9].addInput(inEventId);
	profiles[9].addRemoveEffect(ppEvent.getThePath());

	/****************************************************************************************
	 * service 11: void setEventReminder(Calendar calendar, int eventID,
	 * Reminder reminder) *
	 ****************************************************************************************/
	ProvidedAgendaService setEventReminder = new ProvidedAgendaService(
		SERVICE_SET_REMINDER);
	setEventReminder
		.addInstanceLevelRestriction((MergedRestriction) resCalendar
			.copy(), ppCalendar.getThePath());
	setEventReminder.addInstanceLevelRestriction(resEventID, ppEventId
		.getThePath());
	profiles[10] = setEventReminder.getProfile();
	profiles[10].addInput(inCalendar);
	profiles[10].addInput(inEventId);
	profiles[10].addInput(inEventReminder);
	profiles[10].addChangeEffect(ppReminder.getThePath(), inEventReminder
		.asVariableReference());

	/****************************************************************************************************
	 * service 12: void setEventReminderType(Calendar calendar, int eventID,
	 * ReminderType reminderType) *
	 ****************************************************************************************************/
	ProvidedAgendaService setReminderType = new ProvidedAgendaService(
		SERVICE_SET_REMINDER_TYPE);
	setReminderType
		.addInstanceLevelRestriction((MergedRestriction) resCalendar
			.copy(), ppCalendar.getThePath());
	setReminderType.addInstanceLevelRestriction(resEventID, ppEventId
		.getThePath());
	profiles[11] = setReminderType.getProfile();
	profiles[11].addInput(inCalendar);
	profiles[11].addInput(inEventId);
	profiles[11].addInput(inReminderType);
	profiles[11].addChangeEffect(ppReminderType.getThePath(),
		inReminderType.asVariableReference());

	/******************************************************************
	 * service 13: void addNewCalendar(Calendar calendar, User owner) *
	 ******************************************************************/
	ProvidedAgendaService addNewCalendar = new ProvidedAgendaService(
		SERVICE_ADD_NEW_CALENDAR);

	addNewCalendar.addInputWithAddEffect(INPUT_CALENDAR, Calendar.MY_URI,
		1, 1, ppCalendar.getThePath());
	addNewCalendar.addInputWithAddEffect(INPUT_CALENDAR_OWNER, User.MY_URI,
		0, 1, ppCalendarOwner.getThePath());
	addNewCalendar.addOutput(OUTPUT_CALENDAR, Calendar.MY_URI, 0, 1,
		ppCalendar.getThePath());
	profiles[12] = addNewCalendar.myProfile;

	/*******************************************************************
	 * service 14: void cancelReminder(Calendar calendar, Event event) *
	 *******************************************************************/
	ProvidedAgendaService cancelReminder = new ProvidedAgendaService(
		SERVICE_CANCEL_REMINDER);
	cancelReminder
		.addInstanceLevelRestriction((MergedRestriction) resCalendar
			.copy(), ppCalendar.getThePath());
	profiles[13] = cancelReminder.getProfile();
	profiles[13].addInput(inCalendar);
	profiles[13].addInput(inEvent);
	profiles[13].addChangeEffect(ppEvent.getThePath(), inEvent
		.asVariableReference());

	/*************************************************
	 * service 15: List<Category> getAllCategories() *
	 *************************************************/
	ProvidedAgendaService getAllCategories = new ProvidedAgendaService(
		SERVICE_GET_ALL_CATEGORIES);
	profiles[14] = getAllCategories.getProfile(); // initialize the service
	// profile
	profiles[14].addOutput(outEventCategories); // connect the service with
	// the output
	profiles[14].addSimpleOutputBinding(outEventCategories, ppEventCategory
		.getThePath());

	/***************************************************************************
	 * service 16: Calendar getCalendarByNameAndOwner(String name, User
	 * owner) *
	 ***************************************************************************/
	ProvidedAgendaService getCalendarByName = new ProvidedAgendaService(
		SERVICE_GET_CALENDAR_BY_NAME_AND_OWNER);
	getCalendarByName.addFilteringInput(INPUT_CALENDAR_NAME, TypeMapper
		.getDatatypeURI(String.class), 1, 1, ppCalendarName
		.getThePath());
	getCalendarByName.addFilteringInput(INPUT_CALENDAR_OWNER, User.MY_URI,
		1, 1, ppCalendarOwner.getThePath());
	getCalendarByName.addOutput(OUTPUT_CALENDAR, Calendar.MY_URI, 1, 1,
		ppCalendar.getThePath());
	profiles[15] = getCalendarByName.myProfile; // initialize the service
	// profile

	/******************************************************
	 * service 17: void removeCalendar(Calendar calendar) *
	 ******************************************************/
	ProvidedAgendaService removeCalendar = new ProvidedAgendaService(
		SERVICE_REMOVE_CALENDAR);

	removeCalendar.addInstanceLevelRestriction(resCalendar, ppCalendar
		.getThePath());

	profiles[16] = removeCalendar.getProfile();
	profiles[16].addInput(inCalendar);
	profiles[16].addRemoveEffect(ppCalendar.getThePath());

	/********************************************************
	 * service 18: List<Calendar> getCalendars(User owner) *
	 ********************************************************/
	ProvidedAgendaService getCalendarsByOwner = new ProvidedAgendaService(
		SERVICE_GET_CALENDAR_BY_OWNER);

	getCalendarsByOwner.addFilteringInput(INPUT_CALENDAR_OWNER,
		User.MY_URI, 1, 1, ppCalendarOwner.getThePath());
	getCalendarsByOwner.addOutput(OUTPUT_CONTROLLED_CALENDARS,
		Calendar.MY_URI, 1, 0, ppCalendar.getThePath());

	// initialize the service profile
	profiles[17] = getCalendarsByOwner.myProfile;
	System.out.println("Profiles loaded...");

    }

    private ProvidedAgendaService(String uri) {
	super(uri);
    }
}
