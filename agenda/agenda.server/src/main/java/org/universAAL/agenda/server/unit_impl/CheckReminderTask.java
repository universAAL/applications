package org.universAAL.agenda.server.unit_impl;

import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.agenda.server.database.Scheduler;

public class CheckReminderTask extends CheckDatabaseTask {

    public CheckReminderTask(AgendaDBInterface dbServer, Scheduler scheduler) {
	super(dbServer, scheduler);
    }

    public void run() {
	this.list = dbServer.getCurrentReminders(DB_CHECK_TIME_PERIOD);
	System.out.println("Events to reminder: " + list.size());
	populateScheduler();
    }

    public void populateScheduler() {
	scheduler.addBatchReminders(this.list);
    }

}
