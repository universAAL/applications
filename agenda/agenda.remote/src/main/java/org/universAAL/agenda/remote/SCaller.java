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
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.profile.service.ProfilingService;

/**
 * 
 * @author alfiva
 * @author eandgrg
 */
public class SCaller {
    DefaultServiceCaller caller;
    private static final String OUTPUT_LIST_OF_USERS = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "listOfUsers";
    private static final String OUTPUT_USER = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "user";
    private static final String OUTPUT_LIST_OF_CALENDARS = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "listOfCalendars";
    private static final String OUTPUT_ADDED_EVENT_ID = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "addedEventId";
    private static final String OUTPUT_CALENDAR = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "calendarOfUser";

    // SC2011 added OUTPUT_CALENDAR_EVENT_LIST
    private static final String OUTPUT_CALENDAR_EVENT_LIST = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "listOfEvents";

    private final static Logger log = LoggerFactory.getLogger(SCaller.class);

    public SCaller(ModuleContext mcontext) {
	caller = new DefaultServiceCaller(mcontext);
    }

    public List getUserProfiles() {
	List users = new ArrayList();
	log.info("Agenda Remote calls with getUserProfiles");
	ServiceResponse sr = caller.call(getUsers());

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object value = getReturnValue(sr.getOutputs(),
			OUTPUT_LIST_OF_USERS);
		if (value instanceof List)
		    users = (List) value;
		else
		    users.add(value);
	    } catch (Exception e) {
		log.error("List of Users corrupt!: {}", e);
	    }
	} else
	    log.error("Status of getUserProfiles() failed: {}", sr
		    .getCallStatus());

	return users;
    }

    //TODO commented when transfered to new prof ont (no ElderlyProfile)
//    public Boolean getUserType(User user) {
//	User retUser = null;
//	log.info("Agenda Remote calls with getUserProfiles");
//	ServiceResponse sr = this.caller.call(getGetUser(user));
//	if (sr.getCallStatus() == CallStatus.succeeded) {
//	    try {
//		Object o = getReturnValue(sr.getOutputs(), OUTPUT_USER);
//		if (o instanceof User) {
//		    retUser = (User) o;
//		    UserProfile prof = ((User) o).getProfile();
//		    if (prof != null) {
//			if (prof instanceof ElderlyProfile) {
//			    if (((ElderlyProfile) prof)
//				    .getPersonalPreferenceProfile()
//				    .getXactionModality().equals(Modality.gui)) {
//				return new Boolean(true);
//			    } else {
//				return new Boolean(false);
//			    }
//			} else {
//			    log
//				    .error(
//					    "The user {} has no associated Elderly profile. Cannot determine type.",
//					    user.getURI());
//			    log
//				    .error(
//					    "The user {} hasProfile {}. Interpreting as Elder ()",
//					    new Object[] {
//						    user.getURI(),
//						    prof
//							    .getProperty(Resource.PROP_RDF_TYPE) });
//			    return new Boolean(true);
//			}
//		    } else {
//			log
//				.error(
//					"The user {} has no associated profile. Cannot determine type.",
//					user.getURI());
//		    }
//		}
//		if (retUser == null)
//		    log
//			    .error("There is not any user with URI: ", user
//				    .getURI());
//	    } catch (Exception e) {
//		log.error("User corrupt!: " + e.getMessage());
//		return null;
//	    }
//	} else {
//	    log.error("Status of getUserProfiles() failed: {}", sr
//		    .getCallStatus());
//	}
//	return null;
//    }

    /**
     * 
     */
    public List getCalendarsByOwnerService(User owner) {
	List allCalendars = new ArrayList();
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller
		.call(getCalendarsByOwnerRequest(owner));
	long endTime = System.currentTimeMillis();
	log.info("Agenda\tService called: \'get calendars by owner\' ("
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
		    log.info("Calendar List was not retrieved");
		else
		    log.info("Calendar List was retrieved. Size = : "
			    + allCalendars.size());
	    } catch (Exception e) {
		log.info(" Exception: " + e.getMessage());
		return null;
	    }
	} else {
	    log.info("Calendar list was not retrieved");
	    log.info(sr.getCallStatus().toString());
	}
	return allCalendars;
    }

    public int addEventToCalendarService(Calendar c, Event event) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAddEventToCalendar(c, event));
	long endTime = System.currentTimeMillis();
	log
		.info("Service called: \'add event to calendar\' (" + startTime
			+ ")" + "\n"
			+ "Service returned: \'add event to calendar\' ("
			+ endTime + ")" + "\n" + "Time delay: "
			+ (endTime - startTime));

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
		    log
			    .info("Event was not added to calendar for unknown reason");
		} else {
		    log.info("Event was added to calendar");
		}
		return eventId;
	    } catch (Exception e) {
		log.info("Exception: " + e.getMessage());
		return -1;
	    }
	}

	log.info("Event was not added to calendar");
	log.info(sr.getCallStatus().toString());
	return -1;
    }

    private Object getReturnValue(List outputs, String expectedOutput) {
	Object returnValue = null;
	if (outputs == null)
	    log.error("SCaller: {} not found!", expectedOutput);
	else
	    for (Iterator i = outputs.iterator(); i.hasNext();) {
		ProcessOutput output = (ProcessOutput) i.next();
		if (output.getURI().equals(expectedOutput))
		    if (returnValue == null)
			returnValue = output.getParameterValue();
		    else
			log.error("SCaller: redundant return value!");
		else
		    log.error("SCaller - output ignored: {}", output.getURI());
	    }
	return returnValue;
    }

    public List getAllCalendarsService() {
	List allCalendars = new ArrayList();

	long endTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAllCalendarsRequest());
	long startTime = System.currentTimeMillis();
	log.info("Agenda\tService called: \'get all Calendars\' (" + startTime
		+ ")" + "\n"
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
		    log.info("Calendar List was not retrieved");
		else
		    log.info("Calendar List was retrieved. Size = : "
			    + allCalendars.size());
	    } catch (Exception e) {
		log.info(" Exception: " + e.getMessage());
		return null;
	    }
	} else {
	    log.info("Calendar list was not retrieved");
	    log.info(sr.getCallStatus().toString());
	}
	return allCalendars;
    }

    public Calendar getCalendarByNameAndOwnerService(String calendarName,
	    User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getCalendarByNameAndOwner(
		calendarName, owner));
	long endTime = System.currentTimeMillis();
	log
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
		    log.info("Calendar URI was not retrieved");
		else
		    log
			    .info("Calendar URI was retrieved: "
				    + calendar.getURI());
	    } catch (Exception e) {
		log.error(" Exception: " + e.getMessage());
		return null;
	    }
	} else {
	    log.info("Calendar was not retrieved");
	    log.debug(sr.getCallStatus().toString());
	}
	return calendar;
    }

    public Calendar addNewCalendarService(Calendar c, User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAddNewCalendar(c, owner));
	long endTime = System.currentTimeMillis();
	log.info("Agenda\tService called: \'add new calendar\' (" + startTime
		+ ")" + "\n"
		+ "Agenda\tService returned: \'add new calendar\' (" + endTime
		+ ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR);
		if (o instanceof Calendar) {
		    log.info("Calendar was added");
		    return (Calendar) o;
		} else {
		    log
			    .info("Calendar may not have been added - Wrong service output");
		}
	    } catch (Exception e) {
		log.error(" Exception: " + e.getMessage());
	    }
	} else {
	    log.info("Calendar was not added");
	    log.debug(sr.getCallStatus().toString());
	}
	return null;

    }

    // ----- Service Requests
    private ServiceRequest getUsers() {
	ServiceRequest getUsers = new ServiceRequest(
		new ProfilingService(null), null);
	getUsers.addSimpleOutputBinding(
		new ProcessOutput(OUTPUT_LIST_OF_USERS), (new PropertyPath(
			null, false,
			new String[] { ProfilingService.PROP_CONTROLS })
			.getThePath()));
	return getUsers;
    }

    private ServiceRequest getGetUser(User user) {
	ServiceRequest getuser = new ServiceRequest(new ProfilingService(null),
		null);
	MergedRestriction res = MergedRestriction.getFixedValueRestriction(
		ProfilingService.PROP_CONTROLS, user);
	getuser.getRequestedService().addInstanceLevelRestriction(res,
		new String[] { ProfilingService.PROP_CONTROLS });

	ProcessOutput outUserProfile = new ProcessOutput(OUTPUT_USER);
	PropertyPath ppUserProfile = new PropertyPath(null, true,
		new String[] { ProfilingService.PROP_CONTROLS });
	getuser.addSimpleOutputBinding(outUserProfile, ppUserProfile
		.getThePath());

	return getuser;
    }

    private ServiceRequest getAllCalendarsRequest() {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null);
	listCalendars.addSimpleOutputBinding(new ProcessOutput(
		OUTPUT_LIST_OF_CALENDARS), (new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS }).getThePath()));
	return listCalendars;
    }

    private ServiceRequest getCalendarsByOwnerRequest(User owner) {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null);

	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
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

    private ServiceRequest getAddEventToCalendar(Calendar c, Event event) {
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
	ProcessOutput output = new ProcessOutput(OUTPUT_ADDED_EVENT_ID);
	PropertyPath ppEventID = new PropertyPath(null, true, new String[] {
		CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
		Event.PROP_ID });

	addEventToCalendar.addSimpleOutputBinding(output, ppEventID
		.getThePath());
	return addEventToCalendar;
    }

    private ServiceRequest getCalendarByNameAndOwner(String calendarName,
	    User owner) {
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
	ProcessOutput out = new ProcessOutput(OUTPUT_CALENDAR);
	getCalendarByName.addSimpleOutputBinding(out, ppCalendar.getThePath());

	return getCalendarByName;
    }

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

    // SC2011 four method added for WEB handler event list

    /*
     * public List requestEventsService(FilterParams params) { List allEvents =
     * new ArrayList();
     * 
     * long startTime = System.currentTimeMillis(); ServiceResponse sr =
     * this.caller .call(getEventsRequest(params)); long endTime =
     * System.currentTimeMillis();
     * log.info("Agenda\tService called: \'get events(IGOR) by owner\' (" +
     * startTime + ")" + "\n" +
     * "Agenda\tService returned: \'get events(IGOR) by owner\' (" + endTime +
     * ")" + "\n" + "Agenda\tTime delay: " + (endTime - startTime));
     * 
     * if (sr.getCallStatus() == CallStatus.succeeded) { try { Object o =
     * getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR_EVENT_LIST); if (o
     * instanceof List) allEvents = (List) o; else if (o instanceof Event) {
     * allEvents.add((Event) o); } if (o == null)
     * log.info("Events List was not retrieved(IGOR)"); else
     * log.info("Events List was retrieved(IGOR). Size = : " +
     * allEvents.size()); } catch (Exception e) { log.info(" Exception: " +
     * e.getMessage()); return null; } } else {
     * log.info("Events list was not retrieved(IGOR)");
     * log.info(sr.getCallStatus().toString()); } return allEvents; }
     * 
     * private ServiceRequest getEventsRequest(FilterParams params) { //
     * ServiceRequest listEvents = new ServiceRequest(new CalendarAgenda( //
     * null), null); // // MergedRestriction r1 =
     * MergedRestriction.getFixedValueRestriction( //
     * EventSelectionTool.PROP_HAS_FILTER_PARAMS, params); //
     * listEvents.getRequestedService().addInstanceLevelRestriction( // r1, //
     * new String[] { // EventSelectionTool.PROP_HAS_FILTER_PARAMS});
     * ServiceRequest listEvents = new ServiceRequest(new CalendarAgenda( null),
     * null); listEvents.addSimpleOutputBinding(new ProcessOutput(
     * OUTPUT_CALENDAR_EVENT_LIST), (new PropertyPath(null, true, new String[] {
     * CalendarAgenda.PROP_CONTROLS }).getThePath())); return listEvents;
     */

    // / nove stvariii

    public List<Event> requestEventListService(User owner, Calendar cal) {
	// if method don't have argument calendar
	// List<Calendar> calendars = getCalendarsByOwnerService(owner);
	//	
	// Calendar c= calendars.get(0);
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getGetCalendarEventList(cal));
	long endTime = System.currentTimeMillis();
	log.info("Service called: \'get calendar event list\' (" + startTime
		+ ")" + "\n"
		+ "Service returned: \'get calendar event list\' (" + endTime
		+ ")" + "\n" + "Time delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		List events = (List) getReturnValue(sr.getOutputs(),
			OUTPUT_CALENDAR_EVENT_LIST);
		if (events == null || events.size() == 0) {
		    log
			    .info("Event List has been retreived, but it's empty or NULL");
		    return new ArrayList();
		}
		log.info("Event List was retreived");
		return events;
	    } catch (Exception e) {
		log.info("Exception: " + e.getMessage());
		return new ArrayList(0);
	    }
	}
	log.info("Event List was not retreived");
	log.debug(sr.getCallStatus().toString());
	return new ArrayList(0);

    }

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
	MergedRestriction r = MergedRestriction.getFixedValueRestriction(
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

    // public int removeEventFromCalendarService(Calendar c, Event event) {
    //
    // long startTime = System.currentTimeMillis();
    // ServiceResponse sr = this.caller.call(getRemoveEventFromCalendar(c,
    // event));
    // long endTime = System.currentTimeMillis();
    // log
    // .info("Service called: \'remove event from calendar\' (" + startTime
    // + ")" + "\n"
    // + "Service returned: \'remove event from calendar\' ("
    // + endTime + ")" + "\n" + "Time delay: "
    // + (endTime - startTime));
    //
    // int eventId;
    // if (sr.getCallStatus() == CallStatus.succeeded) {
    // try {
    // Object o = getReturnValue(sr.getOutputs(),
    // OUTPUT_REMOVED_EVENT_ID);
    // if (o == null)
    // eventId = -1;
    // else
    // eventId = ((Integer) o).intValue();
    //
    // if (eventId <= 0) {
    // log
    // .info("Event was not removed from calendar for unknown reason");
    // } else {
    // log.info("Event was removed from calendar");
    // }
    // return eventId;
    // } catch (Exception e) {
    // log.info("Exception: " + e.getMessage());
    // return -1;
    // }
    // }
    //
    // log.info("Event was not removed from calendar");
    // log.info(sr.getCallStatus().toString());
    // return -1;
    // }
    //	
    // private ServiceRequest getRemoveEventFromCalendar(Calendar c, Event
    // event) {
    // ServiceRequest deleteEvent = new ServiceRequest(
    // new CalendarAgenda(null), null);
    // if (c == null) {
    // c = new Calendar();
    // }
    //		
    //		
    // MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
    // CalendarAgenda.PROP_CONTROLS, c);
    // deleteEvent.getRequestedService().addInstanceLevelRestriction(
    // r1, new String[] { CalendarAgenda.PROP_CONTROLS });
    //
    // PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
    // CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
    // //deleteEvent.addAddEffect(ppEvent.getThePath(), event);
    //		
    // deleteEvent.addRemoveEffect(ppEvent.getThePath());
    // ProcessOutput output = new ProcessOutput(OUTPUT_REMOVED_EVENT_ID);
    // PropertyPath ppEventID = new PropertyPath(null, true, new String[] {
    // CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT,
    // Event.PROP_ID });
    //
    // deleteEvent.addSimpleOutputBinding(output, ppEventID
    // .getThePath());
    //		
    // return deleteEvent;
    // }
    public boolean deleteCalendarEventService(Calendar c, int eventId) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(getDeleteCalendarEvent(c, eventId));
	long endTime = System.currentTimeMillis();
	log.info("Agenda\tService called: \'delete calendar event\' ("
		+ startTime + ")" + "\n"
		+ "Agenda\tService returned: \'delete calendar event\' ("
		+ endTime + ")" + "\n" + "Agenda\tTime delay: "
		+ (endTime - startTime));
	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		log.debug("Event was deleted");
	    } catch (Exception e) {
		log.debug("Exception: " + e.getMessage());
		return false;
	    }
	    return true;
	}

	log.debug("Event was not deleted");
	log.debug(sr.getCallStatus().toString());
	return false;
    }

    private ServiceRequest getDeleteCalendarEvent(Calendar c, int eventId) {
	ServiceRequest deleteCalendarEvent = new ServiceRequest(
		new CalendarAgenda(null), null);
	if (c == null) {
	    c = new Calendar();
	}

	// MergedRestriction r1 =
	// MergedRestriction.getFixedValueRestriction(CalendarAgenda.PROP_CONTROLS,
	// c);
	MergedRestriction r2 = MergedRestriction.getFixedValueRestriction(Event.PROP_ID,
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
}
