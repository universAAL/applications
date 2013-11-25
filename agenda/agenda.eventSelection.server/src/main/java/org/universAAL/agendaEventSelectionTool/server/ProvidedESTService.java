package org.universAAL.agendaEventSelectionTool.server;

import java.util.Hashtable;

import org.universAAL.agendaEventSelectionTool.server.osgi.Activator;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.service.CalendarAgenda;
import org.universAAL.ontology.agendaEventSelection.EventSelectionTool;
import org.universAAL.ontology.agendaEventSelection.FilterParams;
import org.universAAL.ontology.agendaEventSelection.service.EventSelectionToolService;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class ProvidedESTService extends EventSelectionToolService {
    // define service namespace + class URI
    public static final String EVENTSELECTIONTOOL_SERVER_NAMESPACE = "http://ontology.universaal.org/EventSelectionToolServer.owl#";
    public static final String MY_URI = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "EventSelectionToolService";

    // define the URI for each service provided
    static final String SERVICE_REQUEST_EVENTS = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "requestEvents1";
    static final String SERVICE_REQUEST_EVENTS_FROM_CALENDARS = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "requestEventsFromCalendar2";
    static final String SERVICE_REQUEST_LIMITED_EVENTS = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "requestLimitedEvents3";
    static final String SERVICE_REQUEST_FOLLOWING_EVENTS = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "requestNextEvents4";

    // define the URI of every input, which is needed from the service
    static final String INPUT_FILTER_PARAMS = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "inFilterParams";
    static final String INPUT_CALENDAR_LIST = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "inCalendarList";
    static final String INPUT_MAX_EVENT_NO = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "inMaxEventNo";

    // define the uri of every output, which is needed from the service
    static final String OUTPUT_EVENT_LIST = EVENTSELECTIONTOOL_SERVER_NAMESPACE
	    + "eventList";

    private static final int PROVIDED_SERVICES = 4; // The number of provided
    // sub-services from main
    // service

    static final ServiceProfile[] profiles = new ServiceProfile[PROVIDED_SERVICES];
    private static Hashtable serverEventSelctionToolRestrictions = new Hashtable();

    static {
	OntologyManagement.getInstance().register(
		Activator.getMcontext(),
		new SimpleOntology(MY_URI, EventSelectionToolService.MY_URI,
			new ResourceFactory() {
			    @Override
			    public Resource createInstance(String classURI,
				    String instanceURI, int factoryIndex) {
				return new ProvidedESTService(instanceURI);
			    }
			}));

	// add restriction about what the service controls
	addRestriction((MergedRestriction) EventSelectionToolService
		.getClassRestrictionsOnProperty(
			EventSelectionToolService.MY_URI,
			EventSelectionToolService.PROP_CONTROLS).copy(),
		new String[] { EventSelectionToolService.PROP_CONTROLS },
		serverEventSelctionToolRestrictions);

	/**********************************************************************
	 * INPUT(S), OUTPUT(S), RESTRICTION(S), PROPERTY_PATH(S) DECLERATIONS *
	 **********************************************************************/
	ProcessInput inFilterParams = new ProcessInput(INPUT_FILTER_PARAMS);
	inFilterParams.setParameterType(FilterParams.MY_URI);
	inFilterParams.setCardinality(1, 1);

	ProcessInput inCalendarList = new ProcessInput(INPUT_CALENDAR_LIST);
	inCalendarList.setParameterType(Calendar.MY_URI);
	// inCalendarList.setCardinality(0, 1);

	ProcessInput inEventMaxNo = new ProcessInput(INPUT_MAX_EVENT_NO);
	inEventMaxNo.setParameterType(TypeMapper.getDatatypeURI(Integer.class));
	inEventMaxNo.setCardinality(1, 0);

	ProcessOutput outEventList = new ProcessOutput(OUTPUT_EVENT_LIST);
	outEventList.setParameterType(Event.MY_URI);

	String[] ppFilterParams = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_FILTER_PARAMS };
	String[] ppEvent = new String[] { CalendarAgenda.PROP_CONTROLS,
		Calendar.PROP_HAS_EVENT };
	String[] ppCalendar = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_HAS_CALENDARS };
	String[] ppMaxEvents = new String[] {
		EventSelectionToolService.PROP_CONTROLS,
		EventSelectionTool.PROP_MAX_EVENT_NO };

	MergedRestriction resFilterParams = MergedRestriction
		.getFixedValueRestriction(
			EventSelectionTool.PROP_HAS_FILTER_PARAMS,
			inFilterParams.asVariableReference());
	MergedRestriction resCalendar = MergedRestriction
		.getFixedValueRestriction(
			EventSelectionTool.PROP_HAS_CALENDARS, inCalendarList
				.asVariableReference());

	/*******************************************************************************
	 ** service 0: List<Event> requestEvent(FilterParams filterParams) **
	 * Returns a sorted list (ascending start time) of events comply with
	 * filter **
	 *******************************************************************************/
	ProvidedESTService requestEvents = new ProvidedESTService(
		SERVICE_REQUEST_EVENTS);
	requestEvents.addInstanceLevelRestriction(
		(MergedRestriction) resFilterParams, ppFilterParams);

	profiles[0] = requestEvents.getProfile();
	profiles[0].addInput(inFilterParams);
	profiles[0].addOutput(outEventList);
	profiles[0].addSimpleOutputBinding(outEventList, ppEvent);

	/**********************************************************************************************
	 ** service 1: List<Event> requestEventsFromCalendar(FilterParams
	 * filterParams, List<Calendar> calendar) ** Returns a sorted list
	 * (ascending start time) of events comply with filter **
	 **********************************************************************************************/
	ProvidedESTService requestEventsFromCalendar = new ProvidedESTService(
		SERVICE_REQUEST_EVENTS_FROM_CALENDARS);

	profiles[1] = requestEventsFromCalendar.getProfile();
	profiles[1].addInput(inFilterParams);
	profiles[1].addChangeEffect(ppFilterParams, inFilterParams
		.asVariableReference());
	profiles[1].addInput(inCalendarList);
	profiles[1].addChangeEffect(ppCalendar, inCalendarList
		.asVariableReference());
	profiles[1].addOutput(outEventList);
	profiles[1].addSimpleOutputBinding(outEventList, ppEvent);

	/**************************************************************************************************************
	 ** service 2: List<Event> requestLimitedEvents(FilterParams
	 * filterParams, List<Calendar> calendar, int maxEventNo) ** Returns an
	 * UNSORTED list of (at most maxEventNo) events comply with filter **
	 **************************************************************************************************************/
	ProvidedESTService requestLimitedEvents = new ProvidedESTService(
		SERVICE_REQUEST_LIMITED_EVENTS);

	profiles[2] = requestLimitedEvents.getProfile();
	profiles[2].addInput(inFilterParams);
	profiles[2].addChangeEffect(ppFilterParams, inFilterParams
		.asVariableReference());
	profiles[2].addInput(inCalendarList);
	profiles[2].addChangeEffect(ppCalendar, inCalendarList
		.asVariableReference());
	profiles[2].addInput(inEventMaxNo);
	profiles[2].addChangeEffect(ppMaxEvents, inEventMaxNo
		.asVariableReference());
	profiles[2].addOutput(outEventList);
	profiles[2].addSimpleOutputBinding(outEventList, ppEvent);

	/***************************************************************************************
	 ** service 3: List<Event> requestNextEvent(List<Calendar> calendar, int
	 * maxEventNo) ** Returns a sorted list (ascending start time) of (at
	 * most maxEventNo) events ** that their start time is greater than
	 * now() **
	 ***************************************************************************************/
	ProvidedESTService requestNextEvents = new ProvidedESTService(
		SERVICE_REQUEST_FOLLOWING_EVENTS);

	profiles[3] = requestNextEvents.getProfile();
	profiles[3].addInput(inCalendarList);
	profiles[3].addChangeEffect(ppCalendar, inCalendarList
		.asVariableReference());
	profiles[3].addInput(inEventMaxNo);
	profiles[3].addChangeEffect(ppMaxEvents, inEventMaxNo
		.asVariableReference());
	profiles[3].addOutput(outEventList);
	profiles[3].addSimpleOutputBinding(outEventList, ppEvent);

    }

    private ProvidedESTService(String uri) {
	super(uri);
    }
}
