package org.universAAL.agenda.server.unit_impl;

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;

/**
 * @author kagnantis
 * 
 */
public interface AgendaStateListener {
    public void eventDeleted(Calendar calendar, int eventID);

    public void eventAdded(Calendar calendar, Event e);

    public void eventUpdated(Calendar calendar, Event e);

    public void reminderTime(Event e);

    public void startEventTime(Event e);

    public void endEventTime(Event e);
}
