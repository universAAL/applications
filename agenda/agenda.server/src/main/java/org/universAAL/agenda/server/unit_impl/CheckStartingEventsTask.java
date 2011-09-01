package org.universAAL.agenda.server.unit_impl;

import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.agenda.server.database.Scheduler;

public class CheckStartingEventsTask extends CheckDatabaseTask {

	public CheckStartingEventsTask(AgendaDBInterface dbServer, Scheduler scheduler) {
		super(dbServer, scheduler);
	}
	
	public void run() {
		System.err.println("qqq2: Check DB for starting events");
		this.list = dbServer.getStartingEvents(DB_CHECK_TIME_PERIOD);
		System.err.println("qqq2: Events to start: " +list.size());
		populateScheduler();
	}
	
	public void populateScheduler() {
		scheduler.addBatchStartEvents(this.list);
	}

}
