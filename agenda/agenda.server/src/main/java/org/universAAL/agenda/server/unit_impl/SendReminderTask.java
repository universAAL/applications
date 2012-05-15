package org.universAAL.agenda.server.unit_impl;

import org.universAAL.ontology.agenda.Event;

public class SendReminderTask extends SendContextEventTask {
    public SendReminderTask(AgendaStateListener listener, Event event,
	    int repeatTime) {
	super(listener, event, repeatTime);
    }

    public SendReminderTask(AgendaStateListener listener, Event event) {
	super(listener, event);
    }

    public void informListener() {
	this.listener.reminderTime(this.event);
	System.out.println("Reminder for Event: " + this.event.getEventID());
	System.out.println(" Message: " + event.getReminder().getMessage());
	System.out.println("\tAnother " + repeatTime + " times.");
    }

}
