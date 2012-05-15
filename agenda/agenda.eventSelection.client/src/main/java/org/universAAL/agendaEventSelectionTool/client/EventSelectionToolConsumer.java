package org.universAAL.agendaEventSelectionTool.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.agendaEventSelectionTool.ont.EventSelectionTool;
import org.universAAL.agendaEventSelectionTool.ont.FilterParams;
import org.universAAL.agendaEventSelectionTool.ont.TimeSearchType;
import org.universAAL.agendaEventSelectionTool.ont.service.EventSelectionToolService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;

public class EventSelectionToolConsumer extends ContextSubscriber {
    private static final String EVENT_SELECTION_TOOL_SERVER_NAMESPACE = "http://ontology.persona.anco.gr/EventSelectionToolServer.owl#";

    private static final String OUTPUT_EVENT_LIST = EVENT_SELECTION_TOOL_SERVER_NAMESPACE
	    + "eventList";
    private static final Logger mainLogger = LoggerFactory
	    .getLogger(EventSelectionToolConsumer.class);
    private ServiceCaller caller;

    /**
     * Returns an array of context event patterns, to be used for context event
     * filtering.
     */
    private static ContextEventPattern[] getContextSubscriptionParams() {
	// I am interested in all existing EventSelectionTool. Am I really? ->
	// To be re-considered
	ContextEventPattern cep = new ContextEventPattern();
	cep.addRestriction(Restriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, EventSelectionTool.MY_URI));

	return new ContextEventPattern[] { cep };
    }

    // Construct

    public EventSelectionToolConsumer(ModuleContext context) {
	super(context, getContextSubscriptionParams());
	caller = new DefaultServiceCaller(context);

	FilterParams fp = new FilterParams(null);
	fp.setDTbegin(TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendarDate(2009, 1 + 1, 27, 2));
	fp.setDTend(TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendarDate(2009, 1 + 1, 27 + 1, 2));
	fp.setTimeSearchType(TimeSearchType.startsBetween);

	// get all filter events from specific calendars
	getSelectedEventsService(fp, null);
    }

    public List getSelectedEventsService(FilterParams fp, List calendarList) {
	ServiceResponse sr;
	if (calendarList == null || calendarList.size() == 0) {
	    long startTime = System.currentTimeMillis();
	    sr = caller.call(requestEvents(fp));
	    long endTime = System.currentTimeMillis();
	    mainLogger.info("EST\tService called: \'request events\' ("
		    + startTime + ")" + "\n"
		    + "EST\tService returned: \'request events\' (" + endTime
		    + ")" + "\n" + "EST\tTime delay: " + (endTime - startTime));

	} else {
	    long startTime = System.currentTimeMillis();
	    sr = caller.call(requestFromCalendarEvents(fp, calendarList));
	    long endTime = System.currentTimeMillis();
	    mainLogger
		    .info("EST\tService called: \'request events from calendars\' ("
			    + startTime
			    + ")"
			    + "\n"
			    + "EST\tService returned: \'request events from calendars\' ("
			    + endTime
			    + ")"
			    + "\n"
			    + "EST\tTime delay: "
			    + (endTime - startTime));

	}

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List getEventList = (List) getReturnValue(sr.getOutputs(),
		    OUTPUT_EVENT_LIST);
	    mainLogger.info("getSelectedEventsService",
		    "Filtered Events have been retreived");

	    if ((getEventList == null) || (getEventList.size() == 0)) {
		mainLogger
			.info("getSelectedEventsService",
				"Filtered Events have been retreived: No matching event.");
		return new ArrayList(0);
	    }
	    return getEventList;
	}

	mainLogger.debug(sr.getCallStatus().toString());
	return new ArrayList(0);
    }

    public List getSelectedLimitedEventsService(FilterParams fp,
	    List calendarList, int maxEventNo) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(requestFromCalendarLimitedEvents((fp),
		calendarList, maxEventNo));
	long endTime = System.currentTimeMillis();
	mainLogger.info("EST\tService called: \'get limited events\' ("
		+ startTime + ")" + "\n"
		+ "EST\tService returned: \'get limited events\' (" + endTime
		+ ")" + "\n" + "EST\tTime delay: " + (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List getEventList = (List) getReturnValue(sr.getOutputs(),
		    OUTPUT_EVENT_LIST);

	    mainLogger.info("getSelectedLimitedEventsService",
		    "Filtered Events have been retreived");

	    if ((getEventList == null) || (getEventList.size() == 0)) {

		mainLogger
			.info("getSelectedLimitedEventsService",
				"Filtered Events have been retreived: No matching event.");
		return new ArrayList(0);
	    }
	    return getEventList;
	}
	mainLogger.debug(sr.getCallStatus().toString());
	return new ArrayList(0);
    }

    public List getFollowingEventsService(List calendarList, int maxEventNo) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(requestFollowingEvents(calendarList,
		maxEventNo));
	long endTime = System.currentTimeMillis();
	mainLogger.info("EST\tService called: \'get present/future events\' ("
		+ startTime + ")" + "\n"
		+ "EST\tService returned: \'get present/future event\' ("
		+ endTime + ")" + "\n" + "EST\tTime delay: "
		+ (endTime - startTime));

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List getEventList = (List) getReturnValue(sr.getOutputs(),
		    OUTPUT_EVENT_LIST);
	    mainLogger.info("getFollowingEventsService",
		    "Filtered Events have been retreived");

	    if ((getEventList == null) || (getEventList.size() == 0)) {
		mainLogger
			.info("getFollowingEventsService",
				"Filtered Events have been retreived: No matching event.");
		return new ArrayList(0);
	    }
	    return getEventList;
	}
	mainLogger.debug(sr.getCallStatus().toString());
	return new ArrayList(0);
    }

    /**
     * Creates a {@link ServiceRequest} object in order to use an
     * {@link EventSelectionToolService} service and retrieve <i>all</i>
     * {@link EventList} which are managed by he server.
     * 
     * @return a service request for the specific service
     */
    private ServiceRequest requestEvents(FilterParams filterParams) {
	EventSelectionToolService estService = new EventSelectionToolService(
		null);
	Restriction r1 = Restriction.getFixedValueRestriction(
		EventSelectionTool.PROP_HAS_FILTER_PARAMS, filterParams);

	estService.addInstanceLevelRestriction(r1, new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_FILTER_PARAMS });

	ServiceRequest listOfRequestedEvents = new ServiceRequest(estService,
		null);

	String[] ppEvent = new String[] { CalendarAgenda.PROP_CONTROLS,
		Calendar.PROP_HAS_EVENT };
	listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
		OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    private ServiceRequest requestFromCalendarEvents(FilterParams fp,
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
		OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    private ServiceRequest requestFromCalendarLimitedEvents(FilterParams fp,
	    List calList, int maxEventNo) {
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
		OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    private ServiceRequest requestFollowingEvents(List calList, int maxEventNo) {
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
		OUTPUT_EVENT_LIST), ppEvent);

	return listOfRequestedEvents;
    }

    private Object getReturnValue(List outputs, String expectedOutput) {
	Object returnValue = null;
	int testCount = 0;

	if (outputs == null) {
	    mainLogger.info("EventSelectionToolConsumer: No info found!");
	} else
	    for (Iterator i = outputs.iterator(); i.hasNext();) {
		ProcessOutput output = (ProcessOutput) i.next();

		if (output.getURI().equals(expectedOutput))
		    if (returnValue == null) {
			returnValue = output.getParameterValue();
			if ((returnValue instanceof Resource)
				&& ((Resource) returnValue).getURI().equals(
					Resource.RDF_EMPTY_LIST))
			    returnValue = new ArrayList(0);
		    } else
			mainLogger
				.info("EventSelectionToolConsumer: redundant return value!");
		else
		    mainLogger
			    .info("EventSelectionToolConsumer - output ignored: "
				    + output.getURI());
	    }

	return returnValue;
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    public void handleContextEvent(ContextEvent event) {
	mainLogger.info("Received1 context event:\n" + "    Subject      = "
		+ event.getSubjectURI() + "\n" + "    Subject type = "
		+ event.getSubjectTypeURI() + "\n" + "    Predicate    = "
		+ event.getRDFPredicate() + "\n" + "    Object       = "
		+ event.getRDFObject());
    }

    private void printEvents(List events) {
	mainLogger.info("Events received& printed out!");

	for (Iterator it = events.listIterator(); it.hasNext();) {
	    Event e = (Event) it.next();
	    mainLogger.info(">>> Client received event = " + e);
	}
    }

}
