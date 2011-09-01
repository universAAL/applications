package org.universAAL.agenda.server.unit_impl;


import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.agenda.server.database.Scheduler;

public abstract class CheckDatabaseTask extends TimerTask {
	public final static int DB_CHECK_TIME_PERIOD = 65 * 60 * 1000; // 1 hour
																	// and 5
																	// minutes
																	// (value
																	// in
																	// seconds)
	public final static int DB_CHECK_INTERVAL = 60*30*1000; // 1 hour (value
															// in
															// milliseconds)
	protected AgendaDBInterface dbServer;
	protected Scheduler scheduler;
	protected List list;

	public CheckDatabaseTask(AgendaDBInterface dbServer, Scheduler scheduler) {
		this.dbServer = dbServer;
		this.scheduler = scheduler;
		list = new ArrayList();
	}

	public abstract void run(); 

	public abstract void populateScheduler(); 
}