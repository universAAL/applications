package org.universAAL.agenda.server.unit_impl;

import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.agenda.server.database.Scheduler;

public class CheckReminderTask extends CheckDatabaseTask {

	public CheckReminderTask(AgendaDBInterface dbServer, Scheduler scheduler) {
		super(dbServer, scheduler);
	}
	
	public void run() {
		System.err.println("qqq3: Check DB for reminders events");
		this.list = dbServer.getCurrentReminders(DB_CHECK_TIME_PERIOD);
		System.err.println("qqq3: Events to reminder: " +list.size());		
		populateScheduler();
	}
	
	public void populateScheduler() {
		scheduler.addBatchReminders(this.list);
	}

}
