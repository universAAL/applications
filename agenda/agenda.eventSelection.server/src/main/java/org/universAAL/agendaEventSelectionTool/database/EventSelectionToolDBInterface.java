package org.universAAL.agendaEventSelectionTool.database;

import java.util.List;

import org.universAAL.agendaEventSelectionTool.ont.FilterParams;

public interface EventSelectionToolDBInterface {
    List requestEvents(FilterParams filterParams);

    List requestFromCalendarEvents(FilterParams filterParams, List calendarList);

    List requestFromCalendarLimitedEvents(FilterParams filterParams,
	    List calendarList, int maxEventNo);

    List requestFollowingEvents(List calendarList, int maxEventNo);

}
