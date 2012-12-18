package org.universAAL.agenda.gui.impl;

import org.universAAL.agenda.client.AgendaConsumer;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agendaEventSelection.FilterParams;
import org.universAAL.ontology.agendaEventSelection.TimeSearchType;
import org.universAAL.agendaEventSelectionTool.client.EventSelectionToolConsumer;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.profile.User;

public class AgendaClientWrapper {
    private AgendaConsumer ac;
    private EventSelectionToolConsumer ec;
    private static final int calendarNo;

    private static final int timeZone;
    private static TimeZone tz;
    private static final boolean useDaylightSaving;
    private static final boolean VISIBLE_BY_DEFAULT = true;
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    static {
	tz = TimeZone.getDefault();
	timeZone = tz.getRawOffset() / (1000 * 3600);
	useDaylightSaving = tz.useDaylightTime();
	calendarNo = 5;

    }

    public AgendaClientWrapper(ModuleContext moduleContext, AgendaConsumer ac,
	    EventSelectionToolConsumer ec) {
	this.ac = ac;
	this.ec = ec;
	mcontext = moduleContext;
    }

    // changed to comply with a list of calendars, not a single one
    public List<Event> getDateEvents(List<Calendar> calendars, int year,
	    int month, int day) {
	FilterParams fp = new FilterParams(null);
	System.out.println("y: " + year);
	System.out.println("m: " + month);
	System.out.println("d: " + day);

	int currentTZ = timeZone;
	if (useDaylightSaving) {
	    ++currentTZ;
	}
	XMLGregorianCalendar gcStart = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendarDate(year, month + 1, day, currentTZ);
	GregorianCalendar c = gcStart.toGregorianCalendar();
	c.add(java.util.Calendar.DAY_OF_MONTH, 1);
	XMLGregorianCalendar gcEnd = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(c);

	fp.setDTbegin(gcStart);
	fp.setDTend(gcEnd);
	System.out.println(gcStart);
	System.out.println(gcEnd);
	fp.setTimeSearchType(TimeSearchType.startsBetween);
	List<Calendar> l = new ArrayList<Calendar>(calendars);
	// l.add(userCalendar);
	@SuppressWarnings("unchecked")
	List<Event> returnList = new ArrayList<Event>(ec
		.getSelectedEventsService(fp, l));
	return returnList;

    }

    // change to get an event
    public Event getEvent(int eventID) {
	return ac.getCalendarEventService(new Calendar(Calendar.MY_URI
		+ calendarNo), eventID);
	// return ac.getCalendarEventService(eventID);
    }

    public void updateEvent(Event e) {
	e.setVisible(VISIBLE_BY_DEFAULT);
	ac.updateCalendarEventService(e.getParentCalendar(), e.getEventID(), e);
    }

    public int saveNewEvent(Calendar c, Event e) {
	e.setVisible(VISIBLE_BY_DEFAULT);
	int id = ac.addEventToCalendarService(c, e);
	return id;
    }

    public void removeEvent(Calendar c, int eventId) {
	boolean reply = ac.deleteCalendarEventService(c, eventId);
	LogUtils.logInfo(mcontext, this.getClass(), "removeEvent",
		new Object[] { "Event with id: " + eventId + " from Calendar: "
			+ c.getName() + " remove status: " + reply }, null);

    }

    public List<String> getAllEventCategories() {
	@SuppressWarnings("unchecked")
	List<String> allEvents = (List<String>) ac.getAllEventCategories();
	LogUtils
		.logInfo(mcontext, this.getClass(), "getAllEventCategories",
			new Object[] { "Event category list size: "
				+ allEvents.size() }, null);
	return allEvents;
    }

    @SuppressWarnings("unchecked")
    public List<Event> getFilteredEvents(List<Calendar> calendars,
	    String category, String description, int year, int month,
	    boolean notPastEvents, int eventMaxNo) {
	FilterParams filter = new FilterParams();
	if ((category != null) && (category.length() != 0)) {
	    filter.setCategory(category);
	    LogUtils.logInfo(mcontext, this.getClass(), "getFilteredEvents",
		    new Object[] { "Gets filtered events from category: "
			    + category }, null);

	}
	if ((description != null) && (description.length() != 0)) {
	    filter.setDescription(description);
	    LogUtils.logInfo(mcontext, this.getClass(), "getFilteredEvents",
		    new Object[] { "Gets filtered events with description: "
			    + description }, null);
	}
	XMLGregorianCalendar calEnd = null;
	XMLGregorianCalendar calStart = null;
	boolean hasEnd = false;
	if (year != -1) {
	    if (month > 0) {
		calStart = TypeMapper.getDataTypeFactory()
			.newXMLGregorianCalendarDate(year, month, 1, 2);
		System.out.println("Cal start: " + calStart);
		if (month == 12) {
		    calEnd = TypeMapper.getDataTypeFactory()
			    .newXMLGregorianCalendarDate(year + 1, 1, 1, 2);
		    System.out.println("1.Cal End: " + calEnd);
		} else {
		    calEnd = TypeMapper.getDataTypeFactory()
			    .newXMLGregorianCalendarDate(year, month + 1, 1, 2);
		    System.out.println("2.Cal End: " + calEnd);
		}
	    } else {
		calStart = TypeMapper.getDataTypeFactory()
			.newXMLGregorianCalendarDate(year, 1, 1, 2);
		calEnd = TypeMapper.getDataTypeFactory()
			.newXMLGregorianCalendarDate(year + 1, 1, 1, 2);
		System.out.println("3.Cal Satrt: " + calStart);
		System.out.println("3.Cal End: " + calEnd);
	    }
	    filter.setDTbegin(calStart);
	    filter.setDTend(calEnd);
	    filter.setTimeSearchType(TimeSearchType.startsBetween);
	    hasEnd = true;
	}

	if (notPastEvents) {
	    GregorianCalendar c = (GregorianCalendar) java.util.Calendar
		    .getInstance();
	    XMLGregorianCalendar now = TypeMapper.getDataTypeFactory()
		    .newXMLGregorianCalendar(c);
	    if (calStart != null) {
		if (now.compare(calStart) == DatatypeConstants.GREATER) {
		    calStart = now;
		}
	    } else {
		calStart = now;
	    }
	    filter.setDTbegin(calStart);
	    System.out.println("4.Cal Satrt: " + calStart);
	    if (!hasEnd)
		filter.setTimeSearchType(TimeSearchType.allAfter);
	}
	List<Calendar> l = new ArrayList<Calendar>(calendars);
	// l.add(userCalendar);
	List<Event> events;

	if (eventMaxNo > 0) {
	    System.out.println("Event Max No: " + eventMaxNo);
	    events = (List<Event>) ec.getSelectedLimitedEventsService(filter,
		    l, eventMaxNo);
	} else {
	    System.out.println("Event Max No: " + eventMaxNo);
	    events = (List<Event>) ec.getSelectedEventsService(filter, l);
	}
	return events;
    }

    public List<Calendar> getAllCalendars() {
	@SuppressWarnings("unchecked")
	List<Calendar> allCalendars = (List<Calendar>) ac
		.getAllCalendarsService();
	if (allCalendars == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "getAllCalendars",
		    new Object[] { "Calendar list is empty." }, null);
	    return new ArrayList<Calendar>(0);
	}
	LogUtils.logInfo(mcontext, this.getClass(), "getAllCalendars",
		new Object[] { "Calendar list size: " + allCalendars.size() },
		null);
	return allCalendars;
    }

    public List<Calendar> getCalendarsByOwner(User owner) {
	@SuppressWarnings("unchecked")
	List<Calendar> allCalendars = (List<Calendar>) ac
		.getCalendarsByOwnerService(owner);
	if (allCalendars == null) {
	    LogUtils.logInfo(mcontext, this.getClass(), "getCalendarsByOwner",
		    new Object[] { "Calendar list is empty." }, null);
	    return new ArrayList<Calendar>(0);
	}
	LogUtils.logInfo(mcontext, this.getClass(), "getCalendarsByOwner",
		new Object[] { "Calendar list size: " + allCalendars.size() },
		null);
	return allCalendars;
    }


    public Calendar addNewCalendar(String name, User owner) {
	Calendar c = new Calendar();
	c.setName(name);
	c = ac.addNewCalendarService(c, owner);
	if (c != null)
	    LogUtils.logInfo(mcontext, this.getClass(), "addNewCalendar",
		    new Object[] { "Calendar was added." }, null);
	else
	    LogUtils.logInfo(mcontext, this.getClass(), "addNewCalendar",
		    new Object[] { "Calendar was not added." }, null);
	return c;
    }

    public boolean removeCalendar(Calendar cal) {
	Calendar c = new Calendar(cal.getURI());
	c.setName(cal.getName());
	c.setOwner(c.getOwner());
	boolean succeeded = ac.removeCalendarService(c);
	LogUtils
		.logInfo(mcontext, this.getClass(), "removeCalendar",
			new Object[] { "Calendar remove operation status: "
				+ succeeded }, null);
	return succeeded;
    }
}
