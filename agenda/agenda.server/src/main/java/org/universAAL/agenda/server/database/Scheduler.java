package org.universAAL.agenda.server.database;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.datatype.XMLGregorianCalendar;

import org.osgi.service.log.LogService;
import org.universAAL.agenda.ont.*;
import org.universAAL.agenda.server.Activator;
import org.universAAL.agenda.server.unit_impl.AgendaStateListener;
import org.universAAL.agenda.server.unit_impl.CheckDatabaseTask;
import org.universAAL.agenda.server.unit_impl.CheckEndingEventsTask;
import org.universAAL.agenda.server.unit_impl.CheckReminderTask;
import org.universAAL.agenda.server.unit_impl.CheckStartingEventsTask;
import org.universAAL.agenda.server.unit_impl.MyAgenda;
import org.universAAL.agenda.server.unit_impl.SendContextEventTask;
import org.universAAL.agenda.server.unit_impl.SendEndEventTask;
import org.universAAL.agenda.server.unit_impl.SendReminderTask;
import org.universAAL.agenda.server.unit_impl.SendStartEventTask;
import org.universAAL.middleware.rdf.TypeMapper;

public class Scheduler {
	public static final int DEFAULT_TRIGGER_TIMES = 3;
	public static final int DEFAULT_REPEAT_INTERVAL = 30 * 1000; // half-min
	// (ten
	// minutes)
	// repeat
	// interval
	private Timer dbPeriodicalCheck;
	private TimerTask checkForReminders, checkForStartingEvents,
			checkForEndingEvents;
	private Timer publishContextEvents;
	private AgendaDBInterface dbServer;
	private AgendaStateListener agendaListener;
	private Map forthcomingReminders, forthcomingStartEvents,
			forthcomingEndEvents;

	public Scheduler(AgendaDBInterface dbServer,
			AgendaStateListener agendaListener) {
		this.dbServer = dbServer;
		this.agendaListener = agendaListener;
		this.forthcomingReminders = new HashMap();
		this.forthcomingStartEvents = new HashMap();
		this.forthcomingEndEvents = new HashMap();
		initTimers(dbServer);
	}

	private void initTimers(AgendaDBInterface dbServer) {
		dbPeriodicalCheck = new Timer();
		publishContextEvents = new Timer();

		checkForReminders = new CheckReminderTask(dbServer, this);
		checkForStartingEvents = new CheckStartingEventsTask(dbServer, this);
		checkForEndingEvents = new CheckEndingEventsTask(dbServer, this);
		dbPeriodicalCheck.schedule(checkForReminders, Calendar.getInstance()
				.getTime(), CheckDatabaseTask.DB_CHECK_INTERVAL);
		dbPeriodicalCheck.schedule(checkForStartingEvents, Calendar
				.getInstance().getTime(), CheckDatabaseTask.DB_CHECK_INTERVAL);
		dbPeriodicalCheck.schedule(checkForEndingEvents, Calendar.getInstance()
				.getTime(), CheckDatabaseTask.DB_CHECK_INTERVAL);
	}

	private void updateTask() {
		// executeReminder.
	}

	public void addBatchReminders(List reminders) {
		if (reminders == null) {
			return;
		}

		for (Iterator it = reminders.iterator(); it.hasNext();) {
			Object o = it.next();
			if (o instanceof Integer) {
				try {
					// if already exists do nothing; cause nothing changed for
					// sure. Otherwise updateReminderTask would have been called
					if (!(forthcomingReminders.containsKey((Integer) o))) {
						Event e = dbServer.getEventFromCalendar(null,
								((Integer) o).intValue(), MyAgenda.COMMIT);
						SendContextEventTask tt;
						Date d = e.getReminder().getReminderTime()
								.toGregorianCalendar().getTime();
						if (e.getReminder().getTimesToBeTriggered() < 0) {
							tt = new SendReminderTask(agendaListener, e,
									DEFAULT_TRIGGER_TIMES);
						} else {
							tt = new SendReminderTask(agendaListener, e, e
									.getReminder().getTimesToBeTriggered());
						}
						if (e.getReminder().getRepeatInterval() <= 0) {
							publishContextEvents.schedule(tt, d,
									DEFAULT_REPEAT_INTERVAL);
						} else {
							publishContextEvents.schedule(tt, d, e
									.getReminder().getRepeatInterval());
						}

						forthcomingReminders.put(new Integer(e.getEventID()),
								new TimerTaskEntry(tt, d));
					}
				} catch (NullPointerException npe) {
					Activator.log.log(LogService.LOG_ERROR,
							"Event Reminder is null");
				}
			}
		}
	}

	public void addBatchStartEvents(List reminders) {
		if (reminders == null) {
			return;
		}

		for (Iterator it = reminders.iterator(); it.hasNext();) {
			Object o = it.next();
			if (o instanceof Integer) {
				try {
					// if already exists do nothing; cause nothing changed for
					// sure. Otherwise updateReminderTask would have been called
					if (!(forthcomingStartEvents.containsKey((Integer) o))) {
						Event e = dbServer.getEventFromCalendar(null,
								((Integer) o).intValue(), MyAgenda.COMMIT);
						SendContextEventTask tt;
						Date d = e.getEventDetails().getTimeInterval()
								.getStartTime().toGregorianCalendar().getTime();
						tt = new SendStartEventTask(agendaListener, e);
						publishContextEvents.schedule(tt, d);
						forthcomingStartEvents.put(new Integer(e.getEventID()),
								new TimerTaskEntry(tt, d));
					}
				} catch (NullPointerException npe) {
					Activator.log.log(LogService.LOG_ERROR,
							"Event Start time is null");
				}
			}
		}
	}

	public void addBatchEndEvents(List reminders) {
		if (reminders == null) {
			return;
		}

		for (Iterator it = reminders.iterator(); it.hasNext();) {
			Object o = it.next();
			if (o instanceof Integer) {
				try {
					// if already exists do nothing; cause nothing changed for
					// sure. Otherwise updateReminderTask would have been called
					if (!(forthcomingEndEvents.containsKey((Integer) o))) {
						Event e = dbServer.getEventFromCalendar(null,
								((Integer) o).intValue(), MyAgenda.COMMIT);
						SendContextEventTask tt;
						Date d = e.getEventDetails().getTimeInterval()
								.getEndTime().toGregorianCalendar().getTime();
						tt = new SendEndEventTask(agendaListener, e);
						publishContextEvents.schedule(tt, d);
						forthcomingEndEvents.put(new Integer(e.getEventID()),
								new TimerTaskEntry(tt, d));
					}
				} catch (NullPointerException npe) {
					Activator.log.log(LogService.LOG_ERROR,
							"Event End time is null");
				}
			}
		}
	}

	public void updateReminderTask(Event e) {
		int eventID = e.getEventID();
		Integer newEventID = new Integer(eventID);
		long now = Calendar.getInstance().getTimeInMillis();
		boolean hasReminderNew;

		long newReminder = -1;
		try {
			newReminder = e.getReminder().getReminderTime()
					.toGregorianCalendar().getTimeInMillis();
			hasReminderNew = true;
		} catch (NullPointerException npe) {
			hasReminderNew = false;
		}

		removeReminderTask(eventID);
		// if it does not have a reminder then delete any previous reference and
		// return
		if (!hasReminderNew) {
			return;
		}

		// start time has passed but exists
		if (newReminder <= now) {
			return;
		}

		// start time is poly makrinos gia na orisw reminder
		if (newReminder > now + CheckDatabaseTask.DB_CHECK_TIME_PERIOD) {
			return;
		}

		int triggerTimes = e.getReminder().getTimesToBeTriggered();
		
		if (triggerTimes < 0)
			triggerTimes = DEFAULT_TRIGGER_TIMES;
		SendReminderTask task = new SendReminderTask(agendaListener, e,
				triggerTimes);
		Date d = new Date(newReminder);
		forthcomingReminders.put(newEventID, new TimerTaskEntry(task, d));
		int repeatInterval = e.getReminder().getRepeatInterval();
		if (repeatInterval <= 0) {
			repeatInterval = DEFAULT_REPEAT_INTERVAL;
		}
		publishContextEvents.schedule(task, d, repeatInterval);

	}

	/*
	 * public void updateReminderTask(Event e) { try { Date d =
	 * e.getReminder().getReminderTime().toGregorianCalendar().getTime(); long
	 * now = Calendar.getInstance().getTimeInMillis(); int remainingRepeatTimes
	 * = MAX_REPEAT_TIMES; // has to be added if ((now <= d.getTime()) &&
	 * (d.getTime() < (now + CheckDatabaseTask.DB_CHECK_TIME_PERIOD))) { //
	 * check if already exists if (forthcomingReminders.containsKey(new
	 * Integer(e.getEventID()))) { TimerTaskEntry tte =
	 * removeReminderTask(e.getEventID()); if (d.equals(tte.executionDate)) {
	 * remainingRepeatTimes = tte.tt.getRemainingRepeatTimes(); } } else {
	 * remainingRepeatTimes = MAX_REPEAT_TIMES; } SendContextEventTask tt = new
	 * SendReminderTask(agendaListener, e, remainingRepeatTimes);
	 * forthcomingReminders.put(new Integer(e.getEventID()), new
	 * TimerTaskEntry(tt, d)); if (e.getReminder().getRepeatTime() != 0) {
	 * publishContextEvents.schedule(tt, d, e.getReminder().getRepeatTime()); }
	 * else { publishContextEvents.schedule(tt, d); } } else {
	 * removeReminderTask(e.getEventID()); } } catch (NullPointerException npe)
	 * { Activator.log.log(LogService.LOG_ERROR, "Event Reminder is null"); } }
	 */
	public void updateReminderTask(int eventID, Reminder reminder) {
		try {
			SendContextEventTask task = (SendContextEventTask) forthcomingReminders
					.get(new Integer(eventID));
			if (task != null) {
				// cached search
				Event prior = task.event;
				prior.setReminder(reminder);
				updateReminderTask(prior);
			} else {
				// retrieve it from database
				Event e = dbServer.getEventFromCalendar(null, eventID,
						MyAgenda.COMMIT);
				if (e != null) {
					updateReminderTask(e);
				}
			}
		} catch (NullPointerException npe) {
			Activator.log.log(LogService.LOG_ERROR, "Event Reminder is null");
		}
	}

	public void updateReminderTask(int eventID, ReminderType reminderType) {
		try {
			SendContextEventTask task = (SendContextEventTask) forthcomingReminders
					.get(new Integer(eventID));
			if (task != null) {
				// cached search
				Event prior = task.event;
				Reminder reminder = prior.getReminder();
				reminder.setReminderType(reminderType);
				prior.setReminder(reminder);
				updateReminderTask(prior);
			} else {
				// retrieve it from database
				Event e = dbServer.getEventFromCalendar(null, eventID,
						MyAgenda.COMMIT);
				if (e != null) {
					updateReminderTask(e);
				}
			}
		} catch (NullPointerException npe) {
			Activator.log.log(LogService.LOG_ERROR, "Event Reminder is null");
		}
	}

	public void updateStartEventTask(Event e) {
		int eventID = e.getEventID();
		Integer newEventID = new Integer(eventID);
		long now = Calendar.getInstance().getTimeInMillis();
		boolean hasStartTimeNew;

		long newStartTime = -1;
		try {
			newStartTime = e.getEventDetails().getTimeInterval().getStartTime()
					.toGregorianCalendar().getTimeInMillis();
			hasStartTimeNew = true;
		} catch (NullPointerException npe) {
			hasStartTimeNew = false;
		}

		removeStartEventTask(eventID);
		// if it does not have a start time then delete any previous reference
		// and return
		if (!hasStartTimeNew) {
			return;
		}

		// start time has passed but exists
		if (newStartTime <= now) {
			return;
		}

		// start time is poly makrinos gia na orisw reminder
		if (newStartTime > now + CheckDatabaseTask.DB_CHECK_TIME_PERIOD) {
			return;
		}

		removeStartEventTask(eventID);
		SendStartEventTask task = new SendStartEventTask(agendaListener, e);
		Date d = new Date(newStartTime);
		forthcomingStartEvents.put(newEventID, new TimerTaskEntry(task, d));
		publishContextEvents.schedule(task, d);

	}

	public void updateEndEventTask(Event e) {
		int eventID = e.getEventID();
		Integer newEventID = new Integer(eventID);
		long now = Calendar.getInstance().getTimeInMillis();
		boolean hasEndTimeNew;

		long newEndTime = -1;
		try {
			newEndTime = e.getEventDetails().getTimeInterval().getEndTime()
					.toGregorianCalendar().getTimeInMillis();
			hasEndTimeNew = true;
		} catch (NullPointerException npe) {
			hasEndTimeNew = false;
		}

		removeEndEventTask(eventID);
		// if it does not have an end time then delete any previous reference
		// and return
		if (!hasEndTimeNew) {
			return;
		}

		// end time has passed but exists
		if (newEndTime <= now) {
			return;
		}

		// end time is poly makrinos gia na orisw reminder
		if (newEndTime > now + CheckDatabaseTask.DB_CHECK_TIME_PERIOD) {
			return;
		}

		removeEndEventTask(eventID);
		SendEndEventTask task = new SendEndEventTask(agendaListener, e);
		Date d = new Date(newEndTime);
		forthcomingEndEvents.put(newEventID, new TimerTaskEntry(task, d));
		publishContextEvents.schedule(task, d);

	}

	public void updateScheduler(Event e) {
		updateReminderTask(e);
		updateStartEventTask(e);
		updateEndEventTask(e);
	}

	public TimerTaskEntry removeReminderTask(int eventId) {
		System.out.println("Remove event reminder: " + eventId);
		TimerTaskEntry tte = (TimerTaskEntry) forthcomingReminders
				.remove(new Integer(eventId));
		if (tte != null) {
			tte.tt.cancel();
		}
		return tte;
	}

	public TimerTaskEntry removeStartEventTask(int eventId) {
		System.out.println("Remove start event task: " + eventId);
		TimerTaskEntry tte = (TimerTaskEntry) forthcomingStartEvents
				.remove(new Integer(eventId));
		if (tte != null) {
			System.out.println("cancel start reminder: " + eventId);
			tte.tt.cancel();
		}
		return tte;
	}

	public TimerTaskEntry removeEndEventTask(int eventId) {
		System.out.println("Remove end event task: " + eventId);
		TimerTaskEntry tte = (TimerTaskEntry) forthcomingEndEvents
				.remove(new Integer(eventId));
		if (tte != null) {
			System.out.println("cancel end reminder: " + eventId);
			tte.tt.cancel();
		}
		return tte;
	}

	public static void main(String[] str) {
		// XMLGregorianCalendar grCal1 =
		// TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009, 2, 11,
		// 16, 00, 00, 0, 2);
		// XMLGregorianCalendar grCal2 =
		// TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009, 2, 15,
		// 20, 00, 00, 0, 2);
		// XMLGregorianCalendar grCal3 =
		// TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009, 2, 28,
		// 11, 34, 38, 0, 2);
		XMLGregorianCalendar grCal4 = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 2, 28, 18, 00, 10, 0, 2);
		XMLGregorianCalendar grCal5 = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 3, 12, 18, 00, 00, 0, 2);
		XMLGregorianCalendar grCal6 = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 2, 27, 18, 00, 00, 0, 3);
		XMLGregorianCalendar grCal7 = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 2, 27, 16, 30, 00, 0, 2);
		XMLGregorianCalendar grCal8 = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 3, 29, 23, 23, 45, 0, 3);
		XMLGregorianCalendar grCal9 = TypeMapper.getDataTypeFactory()
				.newXMLGregorianCalendar(2009, 3, 30, 00, 23, 00, 0, 3);

		// System.out.println(grCal1.toGregorianCalendar().getTimeInMillis());
		// System.out.println(new
		// Date(grCal1.toGregorianCalendar().getTimeInMillis()));
		// System.out.println(grCal2.toGregorianCalendar().getTimeInMillis());
		// System.out.println(new
		// Date(grCal2.toGregorianCalendar().getTimeInMillis()));
		// System.out.println(grCal3.toGregorianCalendar().getTimeInMillis());
		// System.out.println(new
		// Date(grCal3.toGregorianCalendar().getTimeInMillis()));
		System.out.println(grCal4.toGregorianCalendar().getTimeInMillis());
		System.out.println(new Date(grCal4.toGregorianCalendar()
				.getTimeInMillis()));
		System.out.println(grCal5.toGregorianCalendar().getTimeInMillis());
		System.out.println(new Date(grCal5.toGregorianCalendar()
				.getTimeInMillis()));
		System.out.println(grCal6.toGregorianCalendar().getTimeInMillis());
		System.out.println(new Date(grCal6.toGregorianCalendar()
				.getTimeInMillis()));
		System.out.println(grCal7.toGregorianCalendar().getTimeInMillis());
		System.out.println(new Date(grCal7.toGregorianCalendar()
				.getTimeInMillis()));
		System.out.println(grCal8.toGregorianCalendar().getTimeInMillis());
		System.out.println(new Date(grCal8.toGregorianCalendar()
				.getTimeInMillis()));
		System.out.println(grCal9.toGregorianCalendar().getTimeInMillis());
		System.out.println(new Date(grCal9.toGregorianCalendar()
				.getTimeInMillis()));

		java.util.Calendar cc = java.util.Calendar.getInstance();
		cc.set(java.util.Calendar.YEAR, 2009);
		cc.set(java.util.Calendar.MONTH, 5);
		cc.set(java.util.Calendar.DAY_OF_MONTH, 1);

		TimeZone tz = cc.getTimeZone();
		Boolean b = null;
		System.out.println(b.booleanValue());
		System.out.println("IS DT: " + tz.useDaylightTime());
		System.out
				.println("IS ta: " + tz.inDaylightTime(new Date(2009, 10, 1)));
		System.out.println("TZ: " + tz.getRawOffset() / (1000 * 3600));
	}

	private static class TimerTaskEntry {
		SendContextEventTask tt;
		Date executionDate;

		public TimerTaskEntry(SendContextEventTask tt, Date executionDate) {
			this.tt = tt;
			this.executionDate = executionDate;
		}
	}

}
