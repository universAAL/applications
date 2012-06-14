package org.universAAL.agendaEventSelectionTool.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agendaEventSelection.EventSelectionTool;
import org.universAAL.ontology.agendaEventSelection.FilterParams;
import org.universAAL.ontology.agendaEventSelection.TimeSearchType;

/**
 * Service caller towards agenda server. Service requests are for selecting
 * different Events.
 * 
 * @author kagnantis
 * @author eandgrg
 */
public class EventSelectionToolConsumer extends ContextSubscriber {

    /**  */
    public static final String EVENT_SELECTION_TOOL_SERVER_NAMESPACE = "http://ontology.universaal.org/EventSelectionToolConsumer.owl#";

    /**  */
    public static final String OUTPUT_EVENT_LIST = EVENT_SELECTION_TOOL_SERVER_NAMESPACE
	    + "eventList";

    /** {@link ServiceCaller} */
    private ServiceCaller caller;

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    /** {@link EventSelectionToolServiceRequestCreator} */
    private EventSelectionToolServiceRequestCreator estServiceRequestCreator = null;

    /**
     * Returns an array of context event patterns, to be used for context event
     * filtering.
     * 
     * @return ContextEventPattern
     */
    private static ContextEventPattern[] getContextSubscriptionParams() {
	// I am interested in all existing EventSelectionTool. Am I really? ->
	// To be re-considered
	ContextEventPattern cep = new ContextEventPattern();
	cep.addRestriction(MergedRestriction.getAllValuesRestriction(
		ContextEvent.PROP_RDF_SUBJECT, EventSelectionTool.MY_URI));

	return new ContextEventPattern[] { cep };
    }

    /**
     * Constructor.
     * 
     * @param context
     */
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

	estServiceRequestCreator = EventSelectionToolServiceRequestCreator
		.getInstance();
    }

    /**
     * Sends a service call for agenda server (@see AgendaProvider) and obtains
     * a list of events with {@link ServiceRequest} object created in
     * {@link EventSelectionToolServiceRequestCreator#getEventsRequest()} that
     * are filtered by {@link FilterParams}
     * 
     * @param fp
     *            filter parameters
     * @param calendarList
     *            list of calendars
     * @return list of events
     */
    public List getSelectedEventsService(FilterParams fp, List calendarList) {
	ServiceResponse sr;
	if (calendarList == null || calendarList.size() == 0) {
	    long startTime = System.currentTimeMillis();
	    sr = caller.call(estServiceRequestCreator.getEventsRequest(fp));
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
	    sr = caller.call(estServiceRequestCreator
		    .getEventsFromCalendarsRequest(fp, calendarList));
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

    /**
     * Sends a service call for agenda server (@see AgendaProvider) and obtains
     * a limited list of events with {@link ServiceRequest} object created in
     * {@link EventSelectionToolServiceRequestCreator#getLimitedEventsFromCalendarsRequest()}
     * that are filtered by {@link FilterParams}
     * 
     * @param fp
     *            filter parameters
     * @param calendarList
     *            list of calendars
     * @param maxEventNo
     *            max event number
     * @return list of events
     */
    public List getSelectedLimitedEventsService(FilterParams fp,
	    List calendarList, int maxEventNo) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(estServiceRequestCreator
		.getLimitedEventsFromCalendarsRequest((fp), calendarList,
			maxEventNo));
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

    /**
     * Sends a service call for agenda server (@see AgendaProvider) and obtains
     * a list of events with {@link ServiceRequest} object created in
     * {@link EventSelectionToolServiceRequestCreator#getFollowingEventsFromCalendars()}
     * 
     * @param calendarList
     *            list of calendars
     * @param maxEventNo
     *            max event number
     * @return list of events
     */
    public List getFollowingEventsService(List calendarList, int maxEventNo) {
	long startTime = System.currentTimeMillis();
	ServiceResponse sr = caller.call(estServiceRequestCreator
		.getFollowingEventsFromCalendars(calendarList, maxEventNo));
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
     * Check all outputs for output that is equal to expected output and if it
     * is found return it, otherwise return null Same as {@see
     * AgendaConsumer#getReturnValue()}
     * 
     * @param outputs
     *            list of outputs
     * @param expectedOutput
     *            expected output
     * @return output that is equal to expected output or null if that output is
     *         not found
     */
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
     * @see org.universAAL.middleware.context.ContextSubscriber#
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

}
