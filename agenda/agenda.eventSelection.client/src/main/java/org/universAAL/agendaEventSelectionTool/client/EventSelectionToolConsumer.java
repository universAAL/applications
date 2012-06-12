package org.universAAL.agendaEventSelectionTool.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.agendaEventSelection.EventSelectionTool;
import org.universAAL.ontology.agendaEventSelection.FilterParams;
import org.universAAL.ontology.agendaEventSelection.TimeSearchType;
import org.universAAL.ontology.agendaEventSelection.service.EventSelectionToolService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 * 
 */
public class EventSelectionToolConsumer extends ContextSubscriber {
    private static final String EVENT_SELECTION_TOOL_SERVER_NAMESPACE = "http://ontology.universaal.org/EventSelectionToolConsumer.owl#";

    private static final String OUTPUT_EVENT_LIST = EVENT_SELECTION_TOOL_SERVER_NAMESPACE
	    + "eventList";
    private ServiceCaller caller;

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /**
     * Returns an array of context event patterns, to be used for context event
     * filtering.
     */
    private static ContextEventPattern[] getContextSubscriptionParams() {
	// I am interested in all existing EventSelectionTool. Am I really? ->
	// To be re-considered
	ContextEventPattern cep = new ContextEventPattern();
	cep.addRestriction(MergedRestriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, EventSelectionTool.MY_URI));

	return new ContextEventPattern[] { cep };
    }

    // Construct

    public EventSelectionToolConsumer(ModuleContext context) {
	super(context, getContextSubscriptionParams());
	mcontext = context;
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
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getSelectedEventsService",
			    new Object[] {
				    "EventSelectionTool Service called: \'request events\' at startTime: ",
				    startTime }, null);
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getSelectedEventsService",
			    new Object[] {
				    "EventSelectionTool Service returned: \'request events\' at endTime: ",
				    endTime }, null);
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getSelectedEventsService",
			    new Object[] {
				    "EventSelectionTool Service \'request events\' time delay: ",
				    endTime - startTime }, null);

	} else {
	    long startTime = System.currentTimeMillis();
	    sr = caller.call(requestFromCalendarEvents(fp, calendarList));
	    long endTime = System.currentTimeMillis();
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getSelectedEventsService",
			    new Object[] {
				    "EventSelectionTool Service called: \'request events from calendars\' at startTime: ",
				    startTime }, null);
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getSelectedEventsService",
			    new Object[] {
				    "EventSelectionTool Service returned: \'request events from calendars\' at endTime: ",
				    endTime }, null);
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "getSelectedEventsService",
			    new Object[] {
				    "EventSelectionTool Service \'request events from calendars\' time delay: ",
				    endTime - startTime }, null);

	}

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List getEventList = (List) getReturnValue(sr.getOutputs(),
		    OUTPUT_EVENT_LIST);
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getSelectedEventsService",
		    new Object[] { "Filtered Events have been retreived!" },
		    null);

	    if ((getEventList == null) || (getEventList.size() == 0)) {
		LogUtils
			.logInfo(
				mcontext,
				this.getClass(),
				"getSelectedEventsService",
				new Object[] { "Filtered Events have been retreived: No matching event!" },
				null);

		return new ArrayList(0);
	    }
	    return getEventList;
	}

	LogUtils.logInfo(mcontext, this.getClass(), "getSelectedEventsService",
		new Object[] { "Service call status: ",
			sr.getCallStatus().toString() }, null);
	return new ArrayList(0);
    }

    public List getSelectedLimitedEventsService(FilterParams fp,
	    List calendarList, int maxEventNo) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(requestFromCalendarLimitedEvents((fp),
		calendarList, maxEventNo));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getSelectedLimitedEventsService",
			new Object[] {
				"EventSelectionTool Service called: \'get limited events\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getSelectedLimitedEventsService",
			new Object[] {
				"EventSelectionTool Service returned: \'get limited events\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getSelectedLimitedEventsService",
			new Object[] {
				"EventSelectionTool Service \'get limited events\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List getEventList = (List) getReturnValue(sr.getOutputs(),
		    OUTPUT_EVENT_LIST);
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getSelectedLimitedEventsService",
		    new Object[] { "Filtered Events have been retreived " },
		    null);

	    if ((getEventList == null) || (getEventList.size() == 0)) {
		LogUtils
			.logInfo(
				mcontext,
				this.getClass(),
				"getSelectedLimitedEventsService",
				new Object[] { "Filtered Events have been retreived: No matching event " },
				null);

		return new ArrayList(0);
	    }
	    return getEventList;
	}

	LogUtils.logInfo(mcontext, this.getClass(),
		"getSelectedLimitedEventsService",
		new Object[] { "Service call status: ",
			sr.getCallStatus().toString() }, null);
	return new ArrayList(0);
    }

    public List getFollowingEventsService(List calendarList, int maxEventNo) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(requestFollowingEvents(calendarList,
		maxEventNo));
	long endTime = System.currentTimeMillis();
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getFollowingEventsService",
			new Object[] {
				"EventSelectionTool Service called: \'get present/future events\' at startTime: ",
				startTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getFollowingEventsService",
			new Object[] {
				"EventSelectionTool Service returned: \'get present/future events\' at endTime: ",
				endTime }, null);
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"getFollowingEventsService",
			new Object[] {
				"EventSelectionTool Service \'get present/future events\' time delay: ",
				endTime - startTime }, null);

	if (sr.getCallStatus() == CallStatus.succeeded) {
	    List getEventList = (List) getReturnValue(sr.getOutputs(),
		    OUTPUT_EVENT_LIST);
	    LogUtils.logInfo(mcontext, this.getClass(),
		    "getFollowingEventsService",
		    new Object[] { "Filtered Events have been retreived " },
		    null);

	    if ((getEventList == null) || (getEventList.size() == 0)) {
		LogUtils
			.logInfo(
				mcontext,
				this.getClass(),
				"getFollowingEventsService",
				new Object[] { "Filtered Events have been retreived: No matching event " },
				null);

		return new ArrayList(0);
	    }
	    return getEventList;
	}
	LogUtils.logInfo(mcontext, this.getClass(),
		"getFollowingEventsService",
		new Object[] { "Service call status: ",
			sr.getCallStatus().toString() }, null);
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

	    LogUtils.logInfo(mcontext, this.getClass(), "getReturnValue",
		    new Object[] { "No info found" }, null);
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
			LogUtils.logInfo(mcontext, this.getClass(),
				"getReturnValue",
				new Object[] { "Redundant return value!" },
				null);

		else
		    LogUtils.logInfo(mcontext, this.getClass(),
			    "getReturnValue", new Object[] { "Output ignored: "
				    + output.getURI() }, null);

	    }

	return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.universAAL.middleware.context.ContextSubscriber#
     * communicationChannelBroken()
     */
    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.context.ContextSubscriber#handleContextEvent
     * (org.universAAL.middleware.context.ContextEvent)
     */
    public void handleContextEvent(ContextEvent event) {
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Received context event. Subject = ",
			event.getSubjectURI() }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Subject type = ", event.getSubjectTypeURI() },
		null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Predicate = ", event.getRDFPredicate() }, null);
	LogUtils.logInfo(mcontext, this.getClass(), "handleContextEvent",
		new Object[] { "Object= ", event.getRDFObject() }, null);

    }

    private void printEvents(List events) {
	LogUtils.logInfo(mcontext, this.getClass(), "printEvents",
		new Object[] { "Following events received: " }, null);

	for (Iterator it = events.listIterator(); it.hasNext();) {
	    Event e = (Event) it.next();
	    LogUtils.logInfo(mcontext, this.getClass(), "printEvents",
		    new Object[] { "received event = " + e }, null);

	}
    }

}
