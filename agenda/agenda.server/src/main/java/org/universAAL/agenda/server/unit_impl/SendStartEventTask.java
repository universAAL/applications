package org.universAAL.agenda.server.unit_impl;
import org.universAAL.ontology.agenda.Event;

public class SendStartEventTask extends SendContextEventTask {

	public SendStartEventTask(AgendaStateListener listener, Event event, int repeatTime) {
		super(listener, event, repeatTime);
	}
	
	public SendStartEventTask(AgendaStateListener listener, Event event) {
		super(listener, event);
	}
	

	public void informListener() {
		this.listener.startEventTime(this.event);
		System.out.println("Event Started: " + this.event.getEventID());
	}
}
