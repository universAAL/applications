package org.universAAL.agenda.server.unit_impl;
import java.util.TimerTask;

import org.universAAL.agenda.ont.*;

public abstract class SendContextEventTask extends TimerTask {
	public final Event event;
	protected AgendaStateListener listener;
	protected int repeatTime;

	public SendContextEventTask(AgendaStateListener listener, Event event) {
		this(listener, event, 0);
	}

	public SendContextEventTask(AgendaStateListener listener, Event event, int repeatTime) {
		this.listener = listener;
		this.event = event;
		this.repeatTime = repeatTime;
	}

	public void run() {
		if (repeatTime < 0) {
			this.cancel();
		} else {
			informListener();
			--repeatTime;
		}
	}
	
	public abstract void informListener();

	public int getRemainingRepeatTimes() {
		return this.repeatTime;
	}
}
