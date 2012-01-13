package org.universAAL.agendaEventSelectionTool.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.agendaEventSelectionTool.ont.EventSelectionTool;
import org.universAAL.agendaEventSelectionTool.ont.FilterParams;
import org.universAAL.agendaEventSelectionTool.server.impl.EventSelectionListener;
import org.universAAL.agendaEventSelectionTool.server.impl.MyEventSelectionTool;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;

public class EventProvider extends ServiceCallee implements
		EventSelectionListener {

	private static final Logger mainLogger = LoggerFactory
			.getLogger(EventProvider.class);

	private static final ServiceResponse invalidInput = new ServiceResponse(
			CallStatus.serviceSpecificFailure);
	static {
		invalidInput.addOutput(new ProcessOutput(
				ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, Messages
						.getString("EventProvider.InvalidInput"))); //$NON-NLS-1$
	}

	private MyEventSelectionTool theServer;
	private ContextPublisher cp;

	EventProvider(ModuleContext mcontext) throws FileNotFoundException,
			IOException {

		super(mcontext, ProvidedESTService.profiles);

		// prepare for context publishing
		ContextProvider info = new ContextProvider(
				ProvidedESTService.EVENTSELECTIONTOOL_SERVER_NAMESPACE
						+ "EventSelectionToolContextProvider"); //$NON-NLS-1$
		info.setType(ContextProviderType.controller);
		cp = new DefaultContextPublisher(mcontext, info);

		mainLogger.info("Starting agendaEventSelectionTool.server");
		// start the server
		Properties prop = new Properties();
		prop.load(new FileInputStream("credentials.properties"));
		theServer = new MyEventSelectionTool(
				prop.getProperty("database"), prop.getProperty("username"), prop.getProperty("password")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		// theServer.addListener(this);
	}

	/**
	 * 
	 * @see org.universAAL.middleware.service.ServiceCallee#handleCall(org.persona.
	 * middleware.service.ServiceCall)
	 */
	public ServiceResponse handleCall(ServiceCall call) {
		if (call == null)
			return null;

		String operation = call.getProcessURI();

		if (operation == null)
			return null;

		mainLogger.info("EventSelectionTool Service requested");

		Object inCalendarList = call
				.getInputValue(ProvidedESTService.INPUT_CALENDAR_LIST);
		Object inFilterParams = call
				.getInputValue(ProvidedESTService.INPUT_FILTER_PARAMS);
		Object inMaxEventNo = call
				.getInputValue(ProvidedESTService.INPUT_MAX_EVENT_NO);

		if (operation.startsWith(ProvidedESTService.SERVICE_REQUEST_EVENTS)) {
			return requestEvents((FilterParams) inFilterParams);// /inFilterParamsURI);
		}

		if (operation
				.startsWith(ProvidedESTService.SERVICE_REQUEST_EVENTS_FROM_CALENDARS)) {
			if (inCalendarList instanceof List)
				return requestFromCalendarEvents((FilterParams) inFilterParams,
						(List) inCalendarList);
			// else is a lone object
			List temp = new ArrayList();
			temp.add(inCalendarList);

			mainLogger
					.info("\"get filter events from calendars\" service requested");
			return requestFromCalendarEvents((FilterParams) inFilterParams,
					temp);
		}

		if (operation
				.startsWith(ProvidedESTService.SERVICE_REQUEST_LIMITED_EVENTS)) {
			if (inCalendarList instanceof List)
				return requestLimitedEvents((FilterParams) inFilterParams,
						(List) inCalendarList, ((Integer) inMaxEventNo)
								.intValue());
			// else is a lone object
			List temp = new ArrayList();
			temp.add(inCalendarList);
			mainLogger
					.info("\"get limited filter events from calendars\" service requested");
			return requestLimitedEvents((FilterParams) inFilterParams, temp,
					((Integer) inMaxEventNo).intValue());
		}

		if (operation
				.startsWith(ProvidedESTService.SERVICE_REQUEST_FOLLOWING_EVENTS)) {
			if (inCalendarList instanceof List)
				return requestFollowingEvents((List) inCalendarList,
						((Integer) inMaxEventNo).intValue());
			// else is a lone object
			List temp = new ArrayList();
			temp.add(inCalendarList);

			mainLogger
					.info("\"get current filter events from calendars\" service requested");
			return requestFollowingEvents(temp, ((Integer) inMaxEventNo)
					.intValue());
		}

		return null;
	}

	/**
	 * Filters all saved events using <code>FilterParam</code> and retrieves a
	 * list with events that matched the criteria.
	 * 
	 * @param FilterParams
	 *            an event selection filter
	 * @return a service response to the specific service
	 */
	private ServiceResponse requestEvents(FilterParams filterParams) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		List eventsList = (List) theServer.requestEvents(filterParams);
		sr.addOutput(new ProcessOutput(ProvidedESTService.OUTPUT_EVENT_LIST,
				eventsList));

		return sr;
	}

	/**
	 * Filters only these events that are stored in any of the
	 * <code>Calendar</code>s of <code>List</code> <i>calendarList</i>, using
	 * <code>FilterParam</code> and retrieves a list of events that match the
	 * criteria.
	 * 
	 * @param FilterParams
	 *            an event selection filter
	 * @param List
	 *            a list with calendars to be checked
	 * @return a service response to the specific service
	 */
	private ServiceResponse requestFromCalendarEvents(
			FilterParams filterParams, List calendarList) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		List eventsList = (List) theServer.requestFromCalendarEvents(
				filterParams, calendarList);
		sr.addOutput(new ProcessOutput(ProvidedESTService.OUTPUT_EVENT_LIST,
				eventsList));
		return sr;
	}

	/**
	 * Filters only these events that are stored in any of the
	 * <code>Calendar</code>s of <code>List</code> <i>calendarList</i>, using
	 * <code>FilterParam</code> and retrieves at most <i>maxNo</i> events that
	 * match the criteria.
	 * 
	 * @param FilterParams
	 *            an event selection filter
	 * @param List
	 *            a list with calendars to be checked
	 * @param int max number of returned events
	 * @return a service response to the specific service
	 */
	private ServiceResponse requestLimitedEvents(FilterParams filterParams,
			List calendarList, int maxNo) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		List eventsList = (List) theServer.requestFromCalendarLimitedEvents(
				filterParams, calendarList, maxNo);
		sr.addOutput(new ProcessOutput(ProvidedESTService.OUTPUT_EVENT_LIST,
				eventsList));
		return sr;
	}

	/**
	 * Retrieves at most <i>maxNo</i> events that are stored in any of the
	 * <code>Calendar</code>s of <code>List</code> <i>calendarList</i>, with
	 * chronological order, starting with the most imminent.
	 * 
	 * @param List
	 *            a list with calendars to be checked
	 * @param int max number of returned events
	 * @return a service response to the specific service
	 */
	private ServiceResponse requestFollowingEvents(List calendarList, int maxNo) {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		List eventsList = (List) theServer.requestFollowingEvents(calendarList,
				maxNo);
		sr.addOutput(new ProcessOutput(ProvidedESTService.OUTPUT_EVENT_LIST,
				eventsList));
		return sr;
	}

	/**
	 * 
	 * @see org.universAAL.middleware.service.ServiceCallee#communicationChannelBroken()
	 */
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
	}

	public void eventSelectionChanged(EventSelectionTool evTool,
			FilterParams filterParams) {
		// TODO Auto-generated method stub
	}
}
