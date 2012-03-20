package org.universAAL.agenda.server.unit_impl;

import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.agenda.server.database.Scheduler;

public class CheckEndingEventsTask extends CheckDatabaseTask {

    public CheckEndingEventsTask(AgendaDBInterface dbServer, Scheduler scheduler) {
	super(dbServer, scheduler);
    }

    public void run() {
	this.list = dbServer.getEndingEvents(DB_CHECK_TIME_PERIOD);
	System.out.println("Events to end: " + list.size());
	populateScheduler();
    }

    public void populateScheduler() {
	scheduler.addBatchEndEvents(this.list);
    }

}
