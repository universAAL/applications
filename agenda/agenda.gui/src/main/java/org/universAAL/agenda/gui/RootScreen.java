package org.universAAL.agenda.gui;

import org.universAAL.ontology.agenda.Event;

import java.util.List;

public interface RootScreen {
    public void changeHeaderPanel(String headerPanelName);

    public void changeNavigationPanel(String navPanelName);

    public void changeMainPanel(String mainPanelName, List<Event> events);

    public void updateCalendarsTitle();

    public void updateEventTitle();
}
