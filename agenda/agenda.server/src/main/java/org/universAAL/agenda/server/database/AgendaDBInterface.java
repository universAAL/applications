package org.universAAL.agenda.server.database;

import java.util.List;

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.profile.User;

public interface AgendaDBInterface {
    Calendar[] getCalendars();

    Calendar addCalendar(Calendar c, User owner, boolean commit);

    int addEventToCalendar(String calendarURI, Event event, boolean commit);

    Event getEventFromCalendar(String calendarURI, int eventID, boolean commit);

    List getAllEvents(String calendarURI, boolean commit);

    String removeEvent(int eventID, boolean commit);

    boolean updateEvent(String calendarURI, Event event, boolean commit);

    int updateReminder(String calendarURI, int eventId, Reminder reminder,
	    boolean commit);

    boolean updateReminderType(String calendarURI, int eventId,
	    ReminderType reminderType, boolean commit);

    boolean cancelReminder(String calendarURI, int eventId, boolean commit);

    List getCurrentReminders(int time);

    List getStartingEvents(int time);

    List getEndingEvents(int time);
}
