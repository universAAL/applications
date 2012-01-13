/**
 * 
 */
package org.universAAL.agenda.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.universAAL.agenda.ont.Calendar;
import org.universAAL.agenda.ont.Event;
import org.universAAL.agenda.ont.EventDetails;
import org.universAAL.agenda.ont.Reminder;
import org.universAAL.agenda.ont.ReminderType;
import org.universAAL.agenda.ont.TimeInterval;
import org.universAAL.agenda.ont.service.CalendarAgenda;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.PropertyPath;
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

/**
 * @author kagnantis
 * 
 */
class AgendaConsumer_backup extends ContextSubscriber {
	private static final String AGENDA_CLIENT_NAMESPACE = "http://ontology.universAAL.org/AgendaClient.owl#";

	// output of getListCalendarsRequest
	private static final String OUTPUT_LIST_OF_CALENDARS = AGENDA_CLIENT_NAMESPACE
			+ "oListOfCalendars";
	// output of getGetCalendarEventList
	private static final String OUTPUT_CALENDAR_EVENT_LIST = AGENDA_CLIENT_NAMESPACE
			+ "oCalendarEventList";
	// output of getAddEventToCalendar
	private static final String OUTPUT_ADDED_EVENT_ID = AGENDA_CLIENT_NAMESPACE
			+ "oAddedEventId";
	// output of getGetCalendarEvent
	private static final String OUTPUT_CALENDAR_EVENT = AGENDA_CLIENT_NAMESPACE
			+ "oCalendarEvent";

	/**
	 * return an array of context event patterns, to be used for context event
	 * filtering
	 */
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

	AgendaConsumer_backup(BundleContext context) {
		super(context, getContextSubscriptionParams());
		caller = new DefaultServiceCaller(context);

		Calendar cal1 = new Calendar("error");
		cal1.setName("Temporary");
		addingNewCalendar(caller, cal1);

		// test a call for getting the list of the lamps
		ServiceResponse sr = caller.call(getListCalendarsRequest());
		if (sr.getCallStatus() == CallStatus.succeeded) {
			Object value = getReturnValue(sr.getOutputs(),
					OUTPUT_LIST_OF_CALENDARS);

			List calendars = (List) value;
			System.out.println("Calendar URI Output");
			for (int i = 0; i < calendars.size(); ++i) {
				System.out.println(i + ": " + calendars.get(i));
			}

			Calendar firstCal = new Calendar(calendars.get(1).toString());

			// show event list
			requestEventList(caller, firstCal);

			// add event
			Event newEvent1 = createEvent(1, "Social", "Mary's birthday");
			addCalendarEvent(caller, firstCal, newEvent1);

			// add event
			Event newEvent2 = createEvent(2, "Athletics",
					"Aris Eurolegeue Game");
			addCalendarEvent(caller, firstCal, newEvent2);

			// get specific event
			gettingCalendarEvent(caller, firstCal, 1);

			List eventList = new ArrayList(2);
			eventList.add(createEvent(4, "Athletics", "3-on-3 match"));
			eventList.add(createEvent(8, "Social", "Wedding ceremony"));

			// add an event list
			addCalendarEventList(caller, firstCal, eventList);
			// get specific event
			gettingCalendarEvent(caller, firstCal, 3);
			// get specific event
			Event e = gettingCalendarEvent(caller, firstCal, 4);

			// update event
			if (e != null) {
				String oldPlaceName = e.getEventDetails().getPlaceName();
				String newPlaceName = "Tripoli";
				e.getEventDetails().setPlaceName(newPlaceName);
				updatingCalendarEvent(caller, firstCal, 4, e);
				String updatedEventLog = "Event (id:" + e.getEventID()
						+ ") updated:" + "\n\told place name: " + oldPlaceName
						+ "\n\tnew place name: " + newPlaceName;
				Activator.log.log(LogService.LOG_INFO, updatedEventLog);
			}
			// get specific event
			gettingCalendarEvent(caller, firstCal, 4);

			// delete event
			deletingCalendarEvent(caller, firstCal, 3);
			// show event list
			requestEventList(caller, firstCal);

			// set reminder
			Reminder r = new Reminder(Reminder.MY_URI + "WeddingReminder");
			r
					.setMessage("It's time for yout to get shaved. It's your marriage");
			r.setReminderTime(TypeMapper.getDataTypeFactory()
					.newXMLGregorianCalendar(2009, 2, 20, 15, 30, 0, 0, 2));
			settingEventReminder(caller, firstCal, 4, r);
			// get specific event
			gettingCalendarEvent(caller, firstCal, 4);

			// set reminder type
			settingReminderType(caller, firstCal, 4, ReminderType.visualMessage);
			// get specific event
			gettingCalendarEvent(caller, firstCal, 4);

			cancelingReminder(caller, firstCal, 4);

		} else
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of listCalendars(): "
							+ sr.getCallStatus());
	}

	// just local code
	private void addingNewCalendar(ServiceCaller caller, Calendar c) {
		ServiceResponse sr = caller.call(addNewCalendar(c));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			Activator.log.log(LogService.LOG_INFO, "Calendar added");
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of addNewCalendar(): "
							+ sr.getCallStatus());
		}
	}

	// just local code
	private Event gettingCalendarEvent(ServiceCaller caller, Calendar c,
			int eventId) {
		Event event = null;
		ServiceResponse sr = caller.call(getGetCalendarEvent(c, eventId));
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
			} catch (Exception e) {
				System.out.println("Exception1: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCalendarEvent(): "
							+ sr.getCallStatus());
		}
		return event;
	}

	// just local code
	private void cancelingReminder(ServiceCaller caller, Calendar c, int eventId) {
		ServiceResponse sr = caller.call(getCancelReminder(c, eventId));
		if (!(sr.getCallStatus() == CallStatus.succeeded)) {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCancelReminder(): "
							+ sr.getCallStatus());
		}
	}

	// just local code
	private void updatingCalendarEvent(ServiceCaller caller, Calendar c,
			int eventId, Event event) {
		ServiceResponse sr = caller.call(getUpdateCalendarEvent(c, eventId,
				event));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				Activator.log.log(LogService.LOG_INFO, "Event Updated");
			} catch (Exception e) {
				System.out.println("Exception1: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCalendarEvent(): "
							+ sr.getCallStatus());
		}
	}

	// just local code
	private void settingEventReminder(ServiceCaller caller, Calendar c,
			int eventID, Reminder reminder) {
		ServiceResponse sr = caller.call(getSetEventReminder(c, eventID,
				reminder));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				Activator.log.log(LogService.LOG_INFO,
						"Reminder added to event (id: " + eventID + ")");
			} catch (Exception e) {
				System.out.println("Exception1: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCalendarEvent(): "
							+ sr.getCallStatus());
		}
	}

	// just local code
	private void settingReminderType(ServiceCaller caller, Calendar c,
			int eventID, ReminderType reminderType) {
		ServiceResponse sr = caller.call(getSetReminderType(c, eventID,
				reminderType));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				Activator.log.log(LogService.LOG_INFO,
						"Reminder added to event (id: " + eventID + ")");
			} catch (Exception e) {
				System.out.println("Exception1: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCalendarEvent(): "
							+ sr.getCallStatus());
		}
	}

	// just local code
	private void deletingCalendarEvent(ServiceCaller caller, Calendar c,
			int eventId) {
		ServiceResponse sr = caller.call(getDeleteCalendarEvent(c, eventId));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				Activator.log.log(LogService.LOG_INFO, "Event deleted (id:"
						+ eventId + ")");
			} catch (Exception e) {
				System.out.println("Exception1: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCalendarEvent(): "
							+ sr.getCallStatus());
		}
	}

	/*
	 * just local coding private void settingActiveCalendar(ServiceCaller
	 * caller, Calendar c) { ServiceResponse sr =
	 * caller.call(getSetActiveCalendar(c)); if (sr.getCallStatus() ==
	 * CallStatus.succeeded) { try { Activator.log.log(LogService.LOG_INFO,
	 * "Calendar:\n\t" + c + ": failed to become the ACTIVE one"); } catch
	 * (Exception e) { System.out.println("Exception1: " + e.getMessage());
	 * e.printStackTrace(); } } else { Activator.log.log(LogService.LOG_INFO,
	 * "AgendaConsumer_backup - status of setActiveCalendar(): " +
	 * sr.getCallStatus()); } }
	 */

	// just local coding
	private void requestEventList(ServiceCaller caller, Calendar c) {
		ServiceResponse sr = caller.call(getGetCalendarEventList(c));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				List events = (List) getReturnValue(sr.getOutputs(),
						OUTPUT_CALENDAR_EVENT_LIST);
				if (events == null || events.size() == 0) {
					Activator.log.log(LogService.LOG_INFO, "Calendar:\n\t" + c
							+ ": No event is stored");
				} else {
					printEvents(c.getURI(), events);
				}
			} catch (Exception e) {
				System.out.println("Exception2: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of getCalendarEventList(): "
							+ sr.getCallStatus());
		}

	}

	// just local coding
	private void addCalendarEvent(ServiceCaller caller, Calendar c, Event event) {
		ServiceResponse sr = caller.call(getAddEventToCalendar(c, event));
		int eventId;
		if (sr.getCallStatus() == CallStatus.succeeded) {
			try {
				Object o = getReturnValue(sr.getOutputs(),
						OUTPUT_ADDED_EVENT_ID);
				if (o == null) {
					eventId = 0;
				} else {
					eventId = ((Integer) o).intValue();
				}
				if (eventId == 0) {
					Activator.log.log(LogService.LOG_INFO, "Calendar: " + c
							+ ":\tEvent save failed");
				} else {
					Activator.log.log(LogService.LOG_INFO, "Calendar: " + c
							+ ":\tEvent saved. id: " + eventId);
				}
			} catch (Exception e) {
				System.out.println("Exception3: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of addEventToCalendar(): "
							+ sr.getCallStatus());
		}
	}

	// just local coding
	private void addCalendarEventList(ServiceCaller caller, Calendar c,
			List eventList) {
		ServiceResponse sr = caller
				.call(getAddEventListToCalendar(c, eventList));
		if (sr.getCallStatus() == CallStatus.succeeded) {
			Activator.log.log(LogService.LOG_INFO, "Calendar: " + c
					+ ":\tEvent(s) saved.");
		} else {
			Activator.log.log(LogService.LOG_INFO,
					"AgendaConsumer_backup - status of addEventToCalendar(): "
							+ sr.getCallStatus());
		}
	}

	/**
	 * Sends to logger a formated representation of all <code>events</code>,
	 * assuming they belong to the {@link Calendar} <code>c</code>.
	 * 
	 * @param c
	 *            a calendarURI
	 * @param events
	 *            a list of events
	 */
	private void printEvents(String c, List events) {
		Activator.log.log(LogService.LOG_INFO, "Calendar " + c + " has "
				+ events.size() + " event(s) stored:");
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
		// TODO Auto-generated method stub

	}

	/*
	 * Creates a {@link ServiceRequest} object in order to use a {@link
	 * CalendarAgenda} service and set an active calendar to the server side.
	 * 
	 * @param calendarURI the URI of the calendar
	 * 
	 * @return a service request for the specific service private ServiceRequest
	 * getSetActiveCalendar(Calendar c) { ServiceRequest setActiveCalendar = new
	 * ServiceRequest(new CalendarAgenda(null), null); Restriction r1 =
	 * Restriction.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS, c);
	 * setActiveCalendar.getRequestedService().addInstanceLevelRestriction(r1,
	 * new String[] { CalendarAgenda.PROP_CONTROLS });
	 * 
	 * return setActiveCalendar; }
	 */

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
		Restriction r1 = Restriction.getFixedValueRestriction(
				CalendarAgenda.PROP_CONTROLS, c);
		Restriction r2 = Restriction.getFixedValueRestriction(Event.PROP_ID,
				new Integer(eventId));

		deleteCalendarEvent.getRequestedService().addInstanceLevelRestriction(
				r1, new String[] { CalendarAgenda.PROP_CONTROLS });
		deleteCalendarEvent.getRequestedService().addInstanceLevelRestriction(
				r2,
				new String[] { CalendarAgenda.PROP_CONTROLS,
						Calendar.PROP_HAS_EVENT, Event.PROP_ID });

		deleteCalendarEvent.addRemoveEffect((new PropertyPath(null, true,
				new String[] { CalendarAgenda.PROP_CONTROLS,
						Calendar.PROP_HAS_EVENT }).getThePath()));
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
	private ServiceRequest getListCalendarsRequest() {
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

	/*
	 * Creates a {@link ServiceRequest} object in order to use a {@link
	 * CalendarAgenda} service and retrieve the owner of the calendar with the
	 * specified URI <code>calendarURI</code>.
	 * 
	 * @param calendarURI the URI of the calendar
	 * 
	 * @return a service request for the specific service
	 * 
	 * private ServiceRequest getGetCalendarOwner(String calendarURI) {
	 * ServiceRequest getCalendarOwner = new ServiceRequest(new
	 * CalendarAgenda(null), null); Restriction r =
	 * Restriction.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS, new
	 * Calendar(calendarURI));
	 * getCalendarOwner.getRequestedService().addInstanceLevelRestriction(r,new
	 * String[] { CalendarAgenda.PROP_CONTROLS });
	 * 
	 * getCalendarOwner.addSimpleOutputBinding(new
	 * ProcessOutput(OUTPUT_CALENDAR_OWNER), new PropertyPath(null, true, new
	 * String[] {CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_OWNER,
	 * User.PROP_NAME})); return getCalendarOwner; }
	 */

	/*
	 * Creates a {@link ServiceRequest} object in order to use a {@link
	 * CalendarAgenda} service and change the name of the owner of the calendar
	 * with the specified URI <code>calendarURI</code>.
	 * 
	 * @param calendarURI the URI of the calendar
	 * 
	 * @param event the event to be stored
	 * 
	 * @return a service request for the specific service
	 * 
	 * private ServiceRequest getChangeCalendarOwnerName(String calendarURI,
	 * String name) { ServiceRequest changeCalendarOwnerName = new
	 * ServiceRequest(new CalendarAgenda(null), null); Restriction r1 =
	 * Restriction.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS, new
	 * Calendar(calendarURI)); changeCalendarOwnerName.getRequestedService()
	 * .addInstanceLevelRestriction(r1, new String[] {
	 * CalendarAgenda.PROP_CONTROLS });
	 * 
	 * PropertyPath ownerPP = new PropertyPath(null, true, new String[]
	 * {CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_OWNER, User.PROP_NAME
	 * }); changeCalendarOwnerName.addChangeEffect(ownerPP, name);
	 * 
	 * return changeCalendarOwnerName; }
	 */

	/**
	 * Creates a {@link ServiceRequest} object in order to use a
	 * {@link CalendarAgenda} service and create and store a new
	 * {@link Calnedar} with the specified URI <code>calendarURI</code>.
	 * 
	 * @param calendar
	 *            the new calendar
	 * @return a service request for the specific service
	 */
	private ServiceRequest getGetCalendarEvent(Calendar calendar, int eventId) {
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

	private ServiceRequest getCancelReminder(Calendar calendar, int eventId) {
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
	private ServiceRequest addNewCalendar(Calendar calendar) {
		ServiceRequest addNewcalendar = new ServiceRequest(new CalendarAgenda(
				null), null);
		PropertyPath ppCalendar = new PropertyPath(null, true,
				new String[] { CalendarAgenda.PROP_CONTROLS });

		addNewcalendar.addAddEffect(ppCalendar.getThePath(), calendar);

		return addNewcalendar;
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
					if (returnValue == null)
						returnValue = output.getParameterValue();
					else
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
	private Event createEvent(int id, String category, String description) {
		// start Event Details
		EventDetails ed = new EventDetails(EventDetails.MY_URI + "Detail" + id);
		ed.setCategory(category);
		ed.setDescription(description);// ("Mary's birthday");
		ed.setPlaceName("Her Home");
		// start Address
		// PhysicalAddress maryAddress = new PhysicalAddress(Address.MY_URI +
		// "MaryAddress" + id);
		// maryAddress.setCountry("Hellas");
		// maryAddress.setCity("Larisa");
		// maryAddress.setStreet("Kouma");
		// maryAddress.setStreetNo("12A");
		// PhysicalAddress pa = new PhysicalAddress("Thessalia", "Kiprou 21",
		// "b3");
		// pa.setCountryName(new String[]{"Hellas", "Greece"});
		// pa.setExtendedAddress("Neapoli");
		// pa.setPostalCode("41 500");
		// pa.setRegion("Nea politia");
		Address pa = new Address();
		// pa.setStreet("Kiporu");
		// pa.setCountry("Greece");
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
		// Reminder r = new Reminder(Reminder.MY_URI + "BirthRem" + id);
		// r.setMessage("You have to go to Mary's home. It's her birthday");
		// r.setReminderTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009,
		// 2, 20, 15, 30, 0, 0, 2));
		// // end Reminder

		TimeInterval ti = new TimeInterval(TimeInterval.MY_URI + "EventDur"
				+ id);
		XMLGregorianCalendar startTime = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 2, 20, 16, 30, 0, 0, 2);
		XMLGregorianCalendar endTime = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 2, 20, 18, 30, 0, 0, 2);
		ti.setStartTime(startTime);
		ti.setEndTime(endTime);
		ed.setTimeInterval(ti);

		Event event = new Event(Event.MY_URI + "MaryBirthday" + id);
		event.setEventDetails(ed);
		// event.setReminder(r);

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
		Activator.log.log(LogService.LOG_INFO, "Received1 context event:\n"
				+ "    Subject      = " + event.getSubjectURI() + "\n"
				+ "    Subject type = " + event.getSubjectTypeURI() + "\n"
				+ "    Predicate    = " + event.getRDFPredicate() + "\n"
				+ "    Object       = " + event.getRDFObject());
	}

}
