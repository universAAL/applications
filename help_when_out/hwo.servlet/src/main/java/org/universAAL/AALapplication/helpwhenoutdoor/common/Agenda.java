package org.universAAL.AALapplication.helpwhenoutdoor.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.osgi.framework.BundleContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.owls.process.ProcessOutput;

/**
 * This class manages the service interaction with Agenda service
 * 
 * @author zzsm203
 * 
 */
public class Agenda implements Runnable {

	private ServiceCaller caller;
	private AgendaEvent[] agendaEvents;

	// private static final String OUTPUT_EVENT_LIST = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE+ "eventList";
	private Thread thread;
	private Object lock;
	private static final Logger log = LoggerFactory.getLogger(Agenda.class);

	/**
	 * Create an Agenda object and initialize current and next appointment
	 * 
	 * @param userURI
	 * @param context
	 */
	public Agenda(ModuleContext context) {
		caller = new DefaultServiceCaller(context);
		agendaEvents = new AgendaEvent[2];
		lock = new Object();
		thread = new Thread(this, "Agenda UPDATE SERVICE");
		thread.start();
	}

	/**
	 * Updates agendaEvents property if the current or the next appointment is
	 * expired
	 */
	private void updateAppointments() {
		XMLGregorianCalendar currentDT = TypeMapper.getCurrentDateTime();
		currentDT.setYear(1980);

		synchronized (lock) {
			if (agendaEvents[0] != null
					&& agendaEvents[0].endTime.compare(currentDT) == DatatypeConstants.LESSER)
				agendaEvents[0] = getCurrentAppointmentFromAgenda();

			if (agendaEvents[1] != null
					&& agendaEvents[1].endTime.compare(currentDT) == DatatypeConstants.LESSER)
				agendaEvents[1] = getNextAppointmentFromAgenda();
		}
	}

	/**
	 * Retrieves the current appointment from the Agenda service via service bus
	 * 
	 * @return an AgendaEvent object with the current appointment. This value
	 *         can be null in two cases: no Agenda service is available or no
	 *         appointment is taking place at the current datetime.
	 * 
	 */
	private AgendaEvent getCurrentAppointmentFromAgenda() {

		List calList = new ArrayList();
		XMLGregorianCalendar eventBegin = TypeMapper.getCurrentDateTime();
		//FilterParams filterParams = new FilterParams(FilterParams.MY_URI			+ "Current");

		// TODO :
		eventBegin.setYear(1980);
		//filterParams.setDTbegin(eventBegin);
		//filterParams.setTimeSearchType(TimeSearchType.startsBeforeAndEndsAfter);
		//ServiceResponse sr;
		//	sr = caller.call(requestFromCalendarLimitedEvents(filterParams,				calList, 1));
		//return manageServiceResponse(sr);
		return null;
	}

	/**
	 * Retrieves the next appointment from the Agenda service via service bus
	 * 
	 * @return an AgendaEvent object with the next appointment. This value can
	 *         be null in two cases: no Agenda service is available or there is
	 *         no future appointment in the user agenda.
	 * 
	 */
	private AgendaEvent getNextAppointmentFromAgenda() {

		ServiceResponse sr;
		List calList = new ArrayList();
		//sr = caller.call(requestFollowingEvents(calList, 1));
		//return manageServiceResponse(sr);
		return null;
	}

	/**
	 * Manages the Agenda service output received by service bus
	 * 
	 * @param outputs
	 * @param expectedOutput
	 * @return
	 */
	private Object getReturnValue(List outputs, String expectedOutput) {
		Object returnValue = null;

		if (outputs == null) {
			log.info("EventSelectionToolConsumer: No info found!");
		} else
			for (Iterator i = outputs.iterator(); i.hasNext();) {
				ProcessOutput output = (ProcessOutput) i.next();

				if (output.getURI().equals(expectedOutput))
					if (returnValue == null)
						returnValue = output.getParameterValue();
					else
						log
								.info("EventSelectionToolConsumer: redundant return value!");
				else
					log.info("EventSelectionToolConsumer - output ignored: "
							+ output.getURI());
			}

		return returnValue;
	}

	/*private ServiceRequest requestFromCalendarLimitedEvents(FilterParams fp,
			List calList, int maxEventNo) {
		ServiceRequest listOfRequestedEvents = new ServiceRequest(
				new EventSelectionToolService(null), null);
		PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
				CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
		PropertyPath ppCalendar = new PropertyPath(null, true, new String[] {
				EventSelectionToolService.PROP_CONTROLS,
				EventSelectionTool.PROP_HAS_CALENDARS });
		PropertyPath ppFilterParams = new PropertyPath(null, true,
				new String[] { EventSelectionToolService.PROP_CONTROLS,
						EventSelectionTool.PROP_HAS_FILTER_PARAMS });
		PropertyPath ppMaxEventNo = new PropertyPath(null, true, new String[] {
				EventSelectionToolService.PROP_CONTROLS,
				EventSelectionTool.PROP_MAX_EVENT_NO });
		listOfRequestedEvents.addChangeEffect(ppCalendar, calList);
		listOfRequestedEvents.addChangeEffect(ppFilterParams, fp);
		listOfRequestedEvents.addChangeEffect(ppMaxEventNo, new Integer(
				maxEventNo));
		listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
				OUTPUT_EVENT_LIST), ppEvent);
		return listOfRequestedEvents;
	}*/
	
	private ServiceRequest requestFromCalendarLimitedEvents(){
		return null;
	}

	/*private ServiceRequest requestFollowingEvents(List calList, int maxEventNo) {
		ServiceRequest listOfRequestedEvents = new ServiceRequest(
				new EventSelectionToolService(null), null);
		PropertyPath ppEvent = new PropertyPath(null, true, new String[] {
				CalendarAgenda.PROP_CONTROLS, Calendar.PROP_HAS_EVENT });
		PropertyPath ppCalendar = new PropertyPath(null, true, new String[] {
				EventSelectionToolService.PROP_CONTROLS,
				EventSelectionTool.PROP_HAS_CALENDARS });
		PropertyPath ppMaxEventNo = new PropertyPath(null, true, new String[] {
				EventSelectionToolService.PROP_CONTROLS,
				EventSelectionTool.PROP_MAX_EVENT_NO });

		listOfRequestedEvents.addChangeEffect(ppCalendar, calList);
		listOfRequestedEvents.addChangeEffect(ppMaxEventNo, new Integer(
				maxEventNo));
		listOfRequestedEvents.addSimpleOutputBinding(new ProcessOutput(
				OUTPUT_EVENT_LIST), ppEvent);

		return listOfRequestedEvents;
	}*/
	
	private ServiceRequest requestFollowingEvents(){
		return null;
	}
	

	/**
	 * 
	 * @return an AgendaEvent array with the current agenda event in the first
	 *         position and the next one in the second position. Both values can
	 *         be null.
	 */
	public AgendaEvent[] getAgendaEvents() {

		updateAppointments();
		AgendaEvent[] result = new AgendaEvent[2];
		synchronized (lock) {
			result[0] = agendaEvents[0];
			result[1] = agendaEvents[1];
		}

		return result;
	}
	private AgendaEvent manageServiceResponse(){
		return null;
	}

	/*private AgendaEvent manageServiceResponse(ServiceResponse sr) {
		List eventList = (List) getReturnValue(sr.getOutputs(),
				OUTPUT_EVENT_LIST);
		if (eventList == null || eventList.size() == 0)
			return null;
		Event ev = (Event) eventList.get(0);
		EventDetails eventDetails = ev.getEventDetails();

		AgendaEvent rsEvent = new AgendaEvent();
		rsEvent.beginTime = eventDetails.getTimeInterval().getStartTime();
		rsEvent.endTime = eventDetails.getTimeInterval().getEndTime();
		PhysicalAddress address = eventDetails.getAddress();

		String streetNo;
		try {
			streetNo = " " + address.getStreetAddress().buildingIdentifier
					+ " ";
		} catch (NullPointerException e) {
			streetNo = " ";
		}

		String district;
		try {
			district = " " + address.getRegion() + " ";
		} catch (NullPointerException e) {
			district = " ";
		}
		String streetName;

		try {
			streetName = " " + address.getStreetAddress().streetName + " ";
		} catch (NullPointerException e) {
			streetName = "";
		}

		String locality;

		try {
			locality = " " + address.getLocality() + " ";
		} catch (NullPointerException e) {
			locality = " ";
		}

		String country;

		try {
			country = " " + address.getCountryName()[0];
		} catch (NullPointerException e) {
			country = " ";
		}

		rsEvent.address = (streetName + streetNo + locality + district + country)
				.trim();

		rsEvent.eventID = ev.getEventID();
		double[] latLong = Geocoding.geoCoding(rsEvent.address);
		if (latLong == null) {
			rsEvent.latitude = AgendaEvent.UNKNOWN_POSITION;
			rsEvent.longitude = AgendaEvent.UNKNOWN_POSITION;
		} else {
			rsEvent.latitude = latLong[0];
			rsEvent.longitude = latLong[1];
		}

		rsEvent.description = eventDetails.getDescription();

		return rsEvent;

	}*/

	public void run() {/*
		Thread current = Thread.currentThread();
		while (current == thread) {
			try {

				//synchronized (lock) {
					agendaEvents[1] = getNextAppointmentFromAgenda();
					agendaEvents[0] = getCurrentAppointmentFromAgenda();
				//}
				Thread.sleep(300000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.notifyAll();

			}
		}*/
	}

}
