package org.universAAL.agendaEventSelectionTool.server.impl;

import org.universAAL.agendaEventSelectionTool.ont.EventSelectionTool;
import org.universAAL.agendaEventSelectionTool.ont.FilterParams;

public interface EventSelectionListener {
    public void eventSelectionChanged(EventSelectionTool evTool,
	    FilterParams filterParams);
}
