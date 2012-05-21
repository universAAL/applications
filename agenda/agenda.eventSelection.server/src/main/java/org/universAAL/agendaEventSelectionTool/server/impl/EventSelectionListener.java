package org.universAAL.agendaEventSelectionTool.server.impl;

import org.universAAL.ontology.agendaEventSelection.EventSelectionTool;
import org.universAAL.ontology.agendaEventSelection.FilterParams;

/**
 * @author kagnantis
 * */
public interface EventSelectionListener {
    public void eventSelectionChanged(EventSelectionTool evTool,
	    FilterParams filterParams);
}
