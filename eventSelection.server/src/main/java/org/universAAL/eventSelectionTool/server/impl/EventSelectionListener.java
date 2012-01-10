package org.universAAL.eventSelectionTool.server.impl;

import org.universAAL.eventSelectionTool.ont.EventSelectionTool;
import org.universAAL.eventSelectionTool.ont.FilterParams;


public interface EventSelectionListener {
		public void eventSelectionChanged(EventSelectionTool evTool, FilterParams filterParams);
}
