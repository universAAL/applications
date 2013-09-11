package org.universAAL.agenda.server.unit_impl;

import org.universAAL.ontology.agenda.Event;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class SendEndEventTask extends SendContextEventTask {

    public SendEndEventTask(AgendaStateListener listener, Event event,
	    int repeatTime) {
	super(listener, event, repeatTime);
    }

    public SendEndEventTask(AgendaStateListener listener, Event event) {
	super(listener, event);
    }

    public void informListener() {
	this.listener.endEventTime(this.event);
	System.out.println("Event Ended: " + this.event.getEventID());
    }

}
