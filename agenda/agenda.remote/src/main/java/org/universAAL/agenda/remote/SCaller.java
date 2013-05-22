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

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
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
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;
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

    private static final String OUTPUT_CALENDAR_OWNER_NAME = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "calendarOwnerName";

    // SC2011 added OUTPUT_CALENDAR_EVENT_LIST
    private static final String OUTPUT_CALENDAR_EVENT_LIST = ProvidedService.CALENDAR_UI_NAMESPACE
	    + "listOfEvents";

    // private static final String OUTPUT_LIST_OF_CALENDAR_USERS =
    // ProvidedService.CALENDAR_UI_NAMESPACE
    // + "listOfCalendarUsers";

    public SCaller(ModuleContext mcontext) {
	SCaller.mcontext = mcontext;

	caller = new DefaultServiceCaller(mcontext);
    }

    public List getUserProfiles() {
	List users = new ArrayList();

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
		LogUtils.logError(mcontext, this.getClass(), "getUserProfiles",
			new Object[] { "List of Users corrupt." }, e);

	    }
	} else
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getUserProfiles",
			    new Object[] { "Call failed: " + sr.getCallStatus() },
			    null);

	return users;
    }

    // ///////////////////////////////////////////////////////////////////
    // ----- Service Requests
    // ///////////////////////////////////////////////////////////////////
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

    // TODO commented when transfered to new prof ont (no ElderlyProfile)
    // public Boolean getUserType(User user) {
    // User retUser = null;
    // log.info("Agenda Remote calls with getUserProfiles");
    // ServiceResponse sr = this.caller.call(getGetUser(user));
    // if (sr.getCallStatus() == CallStatus.succeeded) {
    // try {
    // Object o = getReturnValue(sr.getOutputs(), OUTPUT_USER);
    // if (o instanceof User) {
    // retUser = (User) o;
    // UserProfile prof = ((User) o).getProfile();
    // if (prof != null) {
    // if (prof instanceof ElderlyProfile) {
    // if (((ElderlyProfile) prof)
    // .getPersonalPreferenceProfile()
    // .getXactionModality().equals(Modality.gui)) {
    // return new Boolean(true);
    // } else {
    // return new Boolean(false);
    // }
    // } else {
    // log
    // .error(
    // "The user {} has no associated Elderly profile. Cannot determine type.",
    // user.getURI());
    // log
    // .error(
    // "The user {} hasProfile {}. Interpreting as Elder ()",
    // new Object[] {
    // user.getURI(),
    // prof
    // .getProperty(Resource.PROP_RDF_TYPE) });
    // return new Boolean(true);
    // }
    // } else {
    // log
    // .error(
    // "The user {} has no associated profile. Cannot determine type.",
    // user.getURI());
    // }
    // }
    // if (retUser == null)
    // log
    // .error("There is not any user with URI: ", user
    // .getURI());
    // } catch (Exception e) {
    // log.error("User corrupt!: " + e.getMessage());
    // return null;
    // }
    // } else {
    // log.error("Status of getUserProfiles() failed: {}", sr
    // .getCallStatus());
    // }
    // return null;
    // }

    // new
    public String getCalendarOwnerNameService(String calendarName) {
	String calOwnerName = null;
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller
		.call(getCalendarOwnerNameRequest(calendarName));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarOwnerNameService",
			new Object[] { "Agenda\tService called: \'get calendar owner\' ("
				+ startTime
				+ ")"
				+ "\n"
				+ "Agenda\tService returned: \'get calendar owner\' ("
				+ endTime
				+ ")"
				+ "\n"
				+ "Agenda\tTime delay: "
				+ (endTime - startTime) }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(),
			OUTPUT_CALENDAR_OWNER_NAME);
		if (o instanceof String) {
		    calOwnerName = (String) o;

		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarOwnerNameService",
			    new Object[] { "Calendar owner: " + calOwnerName
				    + " sucessfully retrieved for calendar: "
				    + calendarName }, null);

		}
		if (o == null)
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getCalendarOwnerNameService",
				    new Object[] { "Calendar owner was not retrieved" },
				    null);

	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"getCalendarOwnerNameService",
			new Object[] { "Exception! " }, e);

		return null;
	    }
	} else {
	    LogUtils.logError(mcontext, this.getClass(),
		    "getCalendarOwnerNameService",
		    new Object[] { "Calendar owner was not retrieved because: "
			    + sr.getCallStatus().toString() }, null);
	}
	return calOwnerName;
    }

    // FIXME june 2012; check!

    private ServiceRequest getCalendarOwnerNameRequest(String calendarName) {
	ServiceRequest getOwner = new ServiceRequest(new CalendarAgenda(null),
		null);

	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		Calendar.PROP_NAME, calendarName);
	getOwner.getRequestedService()
		.addInstanceLevelRestriction(
			r1,
			new String[] { CalendarAgenda.PROP_CONTROLS,
				Calendar.PROP_NAME });

	getOwner.addSimpleOutputBinding(new ProcessOutput(
		OUTPUT_CALENDAR_OWNER_NAME), (new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS }).getThePath()));
	// System.err.println("getCalendarOwnerServiceRequest: "+
	// getOwner.toStringRecursive());
	return getOwner;
    }

    // ///////////////////////////////////////////////////
    // all following Service Requests are the same as in agenda.client
    // ////////////////////////////////////////////////////
    public List getCalendarsByOwnerService(User owner) {
	List allCalendars = new ArrayList();
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller
		.call(getCalendarsByOwnerRequest(owner));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarsByOwnerService",
			new Object[] { "Agenda\tService called: \'get calendars by owner\' ("
				+ startTime
				+ ")"
				+ "\n"
				+ "Agenda\tService returned: \'get calendars by owner\' ("
				+ endTime
				+ ")"
				+ "\n"
				+ "Agenda\tTime delay: "
				+ (endTime - startTime) }, null);

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
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getCalendarsByOwnerService",
			    new Object[] { "Calendar List was not retrieved" },
			    null);

		else
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getCalendarsByOwnerService",
				    new Object[] { "Calendar List was retrieved. Size = : "
					    + allCalendars.size() }, null);

	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"getCalendarsByOwnerService",
			new Object[] { "Exception! " }, e);

		return null;
	    }
	} else {
	    LogUtils.logError(mcontext, this.getClass(),
		    "getCalendarsByOwnerService",
		    new Object[] { "Calendar list was not retrieved because: "
			    + sr.getCallStatus().toString() }, null);

	}
	return allCalendars;
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

    public int addEventToCalendarService(Calendar c, Event event) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAddEventToCalendar(c, event));
	long endTime = System.currentTimeMillis();
	LogUtils.logInfo(mcontext, this.getClass(),
		"addEventToCalendarService",
		new Object[] { "Service called: \'add event to calendar\' ("
			+ startTime + ")" + "\n"
			+ "Service returned: \'add event to calendar\' ("
			+ endTime + ")" + "\n" + "Time delay: "
			+ (endTime - startTime) }, null);

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
			new Object[] { "Exception! " }, e);

		return -1;
	    }
	}
	LogUtils.logError(mcontext, this.getClass(),
		"addEventToCalendarService",
		new Object[] { "Event was not added to calendar because: "
			+ sr.getCallStatus().toString() }, null);

	return -1;
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

    public Calendar getCalendarByNameAndOwnerService(String calendarName,
	    User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getCalendarByNameAndOwner(
		calendarName, owner));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getCalendarByNameAndOwnerService",
			new Object[] { "Agenda\tService called: \'get calendar by name and owner\' ("
				+ startTime
				+ ")"
				+ "\n"
				+ "Agenda\tService returned: \'get calendar by name and owner\' ("
				+ endTime
				+ ")"
				+ "\n"
				+ "Agenda\tTime delay: "
				+ (endTime - startTime) }, null);

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
			    "getCalendarByNameAndOwnerService",
			    new Object[] { "Calendar URI was retrieved: "
				    + calendar.getURI() }, null);

	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"getCalendarByNameAndOwnerService",
			new Object[] { "Exception! " }, e);

		return null;
	    }
	} else {
	    LogUtils.logError(mcontext, this.getClass(),
		    "getCalendarByNameAndOwnerService",
		    new Object[] { "Calendar was not retrieved because: "
			    + sr.getCallStatus().toString() }, null);

	}
	return calendar;
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

    public Calendar addNewCalendarService(Calendar c, User owner) {

	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAddNewCalendar(c, owner));
	long endTime = System.currentTimeMillis();
	LogUtils.logInfo(mcontext, this.getClass(), "addNewCalendarService",
		new Object[] { "Agenda\tService called: \'add new calendar\' ("
			+ startTime + ")" + "\n"
			+ "Agenda\tService returned: \'add new calendar\' ("
			+ endTime + ")" + "\n" + "Agenda\tTime delay: "
			+ (endTime - startTime) }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		Object o = getReturnValue(sr.getOutputs(), OUTPUT_CALENDAR);
		if (o instanceof Calendar) {
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "addNewCalendarService",
			    new Object[] { "Calendar was added" }, null);

		    return (Calendar) o;
		} else {
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "addNewCalendarService",
				    new Object[] { "Calendar may not have been added - Wrong service output" },
				    null);

		}
	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"addNewCalendarService",
			new Object[] { "Exception! " }, e);

	    }
	} else {

	    LogUtils.logError(mcontext, this.getClass(),
		    "addNewCalendarService",
		    new Object[] { "Calendar was not added because: "
			    + sr.getCallStatus().toString() }, null);

	}
	return null;

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

    public List<Event> requestEventListService(Calendar cal) {
	// if method don't have argument calendar
	// List<Calendar> calendars = getCalendarsByOwnerService(owner);
	//	
	// Calendar c= calendars.get(0);
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getGetCalendarEventList(cal));
	long endTime = System.currentTimeMillis();
	LogUtils.logInfo(mcontext, this.getClass(), "requestEventListService",
		new Object[] { "Service called: \'get calendar event list\' ("
			+ startTime + ")" + "\n"
			+ "Service returned: \'get calendar event list\' ("
			+ endTime + ")" + "\n" + "Time delay: "
			+ (endTime - startTime) }, null);

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
			new Object[] { "Exception! " }, e);

		return new ArrayList(0);
	    }
	}
	LogUtils.logError(mcontext, this.getClass(), "requestEventListService",
		new Object[] { "Event List was not retreived: "
			+ sr.getCallStatus().toString() }, null);

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
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"deleteCalendarEventService",
			new Object[] { "Agenda\tService called: \'delete calendar event\' ("
				+ startTime
				+ ")"
				+ "\n"
				+ "Agenda\tService returned: \'delete calendar event\' ("
				+ endTime
				+ ")"
				+ "\n"
				+ "Agenda\tTime delay: "
				+ (endTime - startTime) }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    try {
		LogUtils.logInfo(mcontext, this.getClass(),
			"deleteCalendarEventService",
			new Object[] { "Event was deleted" }, null);

	    } catch (Exception e) {
		LogUtils.logError(mcontext, this.getClass(),
			"deleteCalendarEventService",
			new Object[] { "Exception" }, e);

		return false;
	    }
	    return true;
	}
	LogUtils.logError(mcontext, this.getClass(),
		"deleteCalendarEventService",
		new Object[] { "Event was not deleted"
			+ sr.getCallStatus().toString() }, null);

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

    public List getAllCalendarsService() {
	List allCalendars = new ArrayList();

	long endTime = System.currentTimeMillis();
	ServiceResponse sr = this.caller.call(getAllCalendarsRequest());
	long startTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getAllCalendarsService",
			new Object[] { "Agenda\tService called: \'get all Calendars\' ("
				+ startTime
				+ ")"
				+ "\n"
				+ "Agenda\tService returned: \'get all Calendars\' ("
				+ endTime
				+ ")"
				+ "\n"
				+ "Agenda\tTime delay: "
				+ (endTime - startTime) }, null);

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
		    LogUtils
			    .logInfo(
				    mcontext,
				    this.getClass(),
				    "getAllCalendarsService",
				    new Object[] { "Calendar List was retrieved! Size = "
					    + allCalendars.size() }, null);

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
	    LogUtils.logError(mcontext, this.getClass(),
		    "getAllCalendarsService",
		    new Object[] { "Service call status: "
			    + sr.getCallStatus().toString() }, null);

	}
	return allCalendars;
    }

    protected ServiceRequest getAllCalendarsRequest() {
	ServiceRequest listCalendars = new ServiceRequest(new CalendarAgenda(
		null), null); // need a service from Calendar/Agenda
	listCalendars.addSimpleOutputBinding(new ProcessOutput(
		OUTPUT_LIST_OF_CALENDARS), (new PropertyPath(null, true,
		new String[] { CalendarAgenda.PROP_CONTROLS }).getThePath()));
	return listCalendars;
    }

    private Object getReturnValue(List outputs, String expectedOutput) {
	Object returnValue = null;
	if (outputs == null)
	    LogUtils.logError(mcontext, this.getClass(), "getReturnValue",
		    new Object[] { "SCaller: {} not found!", expectedOutput },
		    null);
	else
	    for (Iterator i = outputs.iterator(); i.hasNext();) {
		ProcessOutput output = (ProcessOutput) i.next();
		if (output.getURI().equals(expectedOutput))
		    if (returnValue == null)
			returnValue = output.getParameterValue();
		    else
			LogUtils.logError(mcontext, this.getClass(),
				"getReturnValue",
				new Object[] { "Redundant return value!" },
				null);

		else
		    LogUtils.logError(mcontext, this.getClass(),
			    "getReturnValue", new Object[] {
				    "SCaller - output ignored: {}",
				    output.getURI() }, null);

	    }
	return returnValue;
    }
}
