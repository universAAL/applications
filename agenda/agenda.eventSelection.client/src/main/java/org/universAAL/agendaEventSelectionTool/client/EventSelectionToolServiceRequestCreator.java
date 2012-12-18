package org.universAAL.agendaEventSelectionTool.client;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.agendaEventSelection.EventSelectionTool;
import org.universAAL.ontology.agendaEventSelection.FilterParams;
import org.universAAL.ontology.agendaEventSelection.service.EventSelectionToolService;

/**
 * Used for composing service requests towards agenda server. For selecting
 * events.
 * 
 * @author eandgrg
 * 
 */
public class EventSelectionToolServiceRequestCreator {
    protected static EventSelectionToolServiceRequestCreator instance = null;

    /**
     * Deny instantiation. Singleton.
     */
    protected EventSelectionToolServiceRequestCreator() {
    }

    /**
     * 
     * @return EventSelectionToolServiceRequestCreator singleton instance
     */
    public static EventSelectionToolServiceRequestCreator getInstance() {
	if (instance == null) {
	    instance = new EventSelectionToolServiceRequestCreator();
	}
	return instance;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use an
     * {@link EventSelectionToolService} service and retrieve <i>all</i>
     * {@link EventList} which are managed by he server.
     * 
     * @return a service request for the specific service
     */
    protected ServiceRequest getEventsRequest(FilterParams filterParams) {
	EventSelectionToolService estService = new EventSelectionToolService(
		null);
	MergedRestriction r1 = MergedRestriction.getFixedValueRestriction(
		EventSelectionTool.PROP_HAS_FILTER_PARAMS, filterParams);

	estService.addInstanceLevelRestriction(r1, new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_FILTER_PARAMS });

	ServiceRequest listOfRequestedEvents = new ServiceRequest(estService,
		null);

	String[] ppEvent = new String[] { CalendarAgenda.PROP_CONTROLS,
		Calendar.PROP_HAS_EVENT };
	listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
		EventSelectionToolConsumer.OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use an.
     * {@link EventSelectionToolService} service and retrieve <i>all</i> events
     * from provided calendar list
     * 
     * @param fp
     *            filter parameters
     * @param calList
     *            calendar list
     * @return a service request for the specific service
     */
    protected ServiceRequest getEventsFromCalendarsRequest(FilterParams fp,
	    List calList) {
	ServiceRequest listOfRequestedEvents = new ServiceRequest(
		new EventSelectionToolService(null), null);
	String[] ppEvent = new String[] { CalendarAgenda.PROP_CONTROLS,
		Calendar.PROP_HAS_EVENT };
	String[] ppCalendar = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_CALENDARS };
	String[] ppFilterParams = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_FILTER_PARAMS };
	if (calList == null) {
	    calList = new ArrayList(0);
	}
	listOfRequestedEvents.addChangeEffect(ppCalendar, calList);
	listOfRequestedEvents.addChangeEffect(ppFilterParams, fp);
	listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
		EventSelectionToolConsumer.OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use an
     * {@link EventSelectionToolService} service and retrieve specific number of
     * events from provided calendar list based on given filter parameters
     * 
     * @param fp
     *            filter parameters
     * @param calList
     *            calendar list
     * @param maxEventNo
     *            max number of events that are returned
     * @return a service request for the specific service
     */
    protected ServiceRequest getLimitedEventsFromCalendarsRequest(
	    FilterParams fp, List calList, int maxEventNo) {
	ServiceRequest listOfRequestedEvents = new ServiceRequest(
		new EventSelectionToolService(null), null);
	String[] ppEvent = new String[] { CalendarAgenda.PROP_CONTROLS,
		Calendar.PROP_HAS_EVENT };
	String[] ppCalendar = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_CALENDARS };
	String[] ppFilterParams = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_FILTER_PARAMS };
	String[] ppMaxEventNo = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_MAX_EVENT_NO };
	if (calList == null) {
	    calList = new ArrayList(0);
	}
	listOfRequestedEvents.addChangeEffect(ppCalendar, calList);
	listOfRequestedEvents.addChangeEffect(ppFilterParams, fp);
	listOfRequestedEvents.addChangeEffect(ppMaxEventNo, new Integer(
		maxEventNo));
	listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
		EventSelectionToolConsumer.OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use an
     * {@link EventSelectionToolService} service and retrieve specific number of
     * events from provided calendar list
     * 
     * @param calList
     *            calendar list
     * @param maxEventNo
     *            max number of events that are returned
     * @return a service request for the specific service
     */
    protected ServiceRequest getFollowingEventsFromCalendars(List calList,
	    int maxEventNo) {
	ServiceRequest listOfRequestedEvents = new ServiceRequest(
		new EventSelectionToolService(null), null);
	String[] ppEvent = new String[] { CalendarAgenda.PROP_CONTROLS,
		Calendar.PROP_HAS_EVENT };
	String[] ppCalendar = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_CALENDARS };
	String[] ppMaxEventNo = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_MAX_EVENT_NO };

	if (calList == null) {
	    calList = new ArrayList(0);
	}
	listOfRequestedEvents.addChangeEffect(ppCalendar, calList);
	listOfRequestedEvents.addChangeEffect(ppMaxEventNo, new Integer(
		maxEventNo));
	listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
		EventSelectionToolConsumer.OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }
}
