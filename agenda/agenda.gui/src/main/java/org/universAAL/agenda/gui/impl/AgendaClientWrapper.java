package org.universAAL.agenda.gui.impl;

import org.universAAL.agenda.client.AgendaConsumer;
import org.universAAL.agenda.ont.Calendar;
import org.universAAL.agenda.ont.Event;
import org.universAAL.agendaEventSelectionTool.client.EventSelectionToolConsumer;
import org.universAAL.agendaEventSelectionTool.ont.FilterParams;
import org.universAAL.agendaEventSelectionTool.ont.TimeSearchType;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

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

	static {
		tz = TimeZone.getDefault();
		timeZone = tz.getRawOffset() / (1000 * 3600);
		useDaylightSaving = tz.useDaylightTime();
		calendarNo = 5;

	}

	public AgendaClientWrapper(AgendaConsumer ac, EventSelectionToolConsumer ec) {
		this.ac = ac;
		this.ec = ec;
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
		return ac
				.getCalendarEventService(
						new org.universAAL.agenda.ont.Calendar(
								org.universAAL.agenda.ont.Calendar.MY_URI
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
		System.out.println("removing event...");
		boolean reply = ac.deleteCalendarEventService(c, eventId);
		System.out.println("Event removed?: " + reply);

	}

	public List<String> getAllEventCategories() {
		System.out.println("getting event categories...");
		@SuppressWarnings("unchecked")
		List<String> allEvents = (List<String>) ac.getAllEventCategories();
		System.out.println("Event category list: " + allEvents.size());
		return allEvents;
	}

	// change to work with a list of calendars
	@SuppressWarnings("unchecked")
	public List<Event> getFilteredEvents(List<Calendar> calendars,
			String category, String description, int year, int month,
			boolean notPastEvents, int eventMaxNo) {
		FilterParams filter = new FilterParams();
		if ((category != null) && (category.length() != 0)) {
			filter.setCategory(category);
			System.out.println("category: " + category);
		}
		if ((description != null) && (description.length() != 0)) {
			filter.setDescription(description);
			System.out.println("description: " + description);
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
		System.out.println("getting all calendars ...");
		@SuppressWarnings("unchecked")
		List<Calendar> allCalendars = (List<Calendar>) ac
				.getAllCalendarsService();
		if (allCalendars == null) {
			System.out.println("Calendar list is empty (null value)");
			return new ArrayList<Calendar>(0);
		}
		System.out.println("Calendar list size: " + allCalendars.size());
		return allCalendars;
	}

	public List<Calendar> getCalendarsByOwner(User owner) {
		System.out.println("getting user's calendars ...");
		@SuppressWarnings("unchecked")
		List<Calendar> allCalendars = (List<Calendar>) ac
				.getCalendarsByOwnerService(owner);
		if (allCalendars == null) {
			System.out.println("Calendar list is empty (null value)");
			return new ArrayList<Calendar>(0);
		}
		System.out.println("Calendar list size: " + allCalendars.size());
		return allCalendars;
	}

	public Calendar addNewCalendar(String name, User owner) {
		System.out.println("adding new Calendar ...");
		Calendar c = new Calendar();
		c.setName(name);
		c = ac.addNewCalendarService(c, owner);
		System.out.println((c != null) ? "Calendar was added "
				: "Calendar was not added");
		return c;
	}

	public boolean removeCalendar(Calendar cal) {
		System.out.println("removing Calendar ...");
		Calendar c = new Calendar(cal.getURI());
		c.setName(cal.getName());
		c.setOwner(c.getOwner());
		boolean succeeded = ac.removeCalendarService(c);
		System.out.println(succeeded ? "Calendar was removed "
				: "Calendar was not removed");
		return succeeded;
	}
}
