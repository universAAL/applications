package org.universAAL.agenda.server.unit_impl;

import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.agenda.server.database.Scheduler;

public class CheckEndingEventsTask extends CheckDatabaseTask {

	public CheckEndingEventsTask(AgendaDBInterface dbServer, Scheduler scheduler) {
		super(dbServer, scheduler);
	}
	
	public void run() {
		System.err.println("qqq1: Check DB for ending events");
		this.list = dbServer.getEndingEvents(DB_CHECK_TIME_PERIOD);
		System.err.println("qqq1: Events to end: " + list.size());
		populateScheduler();
	}
	
	public void populateScheduler() {
		scheduler.addBatchEndEvents(this.list);
	}

}
