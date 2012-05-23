package org.universAAL.ontology.agendaEventSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.TypeMapper;

/**
 * @author kalogirou
 * 
 */
public class EventSelectionTool extends ManagedIndividual {
        public static final String MY_URI= AgendaEventSelectionOntology.NAMESPACE + "EventSelectionTool";
    public static final String PROP_HAS_CALENDARS= AgendaEventSelectionOntology.NAMESPACE + "hasCalendar";
    public static final String PROP_HAS_FILTER_PARAMS = AgendaEventSelectionOntology.NAMESPACE
	+ "hasFilterParams";
    public static final String PROP_MAX_EVENT_NO= AgendaEventSelectionOntology.NAMESPACE + "maxEventNo";

    private List cachedFilteredEvents = new ArrayList();// list of events
    private boolean cacheIsValid = false;

    public EventSelectionTool() {
	super();
    }

    public EventSelectionTool(String uri) {
	super(uri);
    }

    /*
     * Calendar
     */
    public List getCalendars() {
	List calendars = (List) props.get(PROP_HAS_CALENDARS);
	if (calendars == null) {
	    calendars = new ArrayList(1);
	}
	return new ArrayList(calendars);
    }

    public void setCalendars(List calendars) {
	if (calendars != null) {
	    clearCache();
	    props.put(PROP_HAS_CALENDARS, calendars);
	}
    }

    public void addCalendar(Calendar calendar) {
	List calendars = (List) props.get(PROP_HAS_CALENDARS);
	if (calendars == null) {
	    calendars = new ArrayList(1);
	}
	if (calendar != null) {
	    clearCache();
	    calendars.add(calendar);
	}
	props.put(PROP_HAS_CALENDARS, calendars);
    }

    /*
     * FilterParams
     */
    public void setFilterParams(FilterParams params) {
	if (params != null) {
	    clearCache();
	    props.put(PROP_HAS_FILTER_PARAMS, params);
	}
    }

    public FilterParams getFilterParams() {
	return (FilterParams) props.get(PROP_HAS_FILTER_PARAMS);
    }

    /*
     * Max number of returned events
     */
    public void setMaxEventNo(int max) {
	clearCache();
	props.put(PROP_MAX_EVENT_NO, new Integer(max));
    }

    public int getMaxEventNo() {
	return ((Integer) props.get(PROP_MAX_EVENT_NO)).intValue();
    }

    // returns a list of events
    public List getFilteredEvents() {
	if (cacheIsValid)
	    return cachedFilteredEvents;

	List eventsList = new ArrayList();
	List calendarList = getCalendars();
	FilterParams fp = getFilterParams();
	if (fp == null)
	    fp = new FilterParams(null); // an empty object -> matches every
	// event

	for (Iterator it = calendarList.iterator(); it.hasNext();) {
	    Calendar cal = (Calendar) it.next();

	    if (cal != null) {
		for (Iterator eventIt = cal.getEventList().iterator(); eventIt
			.hasNext();) {
		    Event event = (Event) eventIt.next();
		    if (fp.matches(event))
			eventsList.add(event);
		}
	    }
	}
	setCache(eventsList);
	return eventsList;
    }

    // returns an unsorted list of events
    public List getLimitedFilteredEvents(int maxEventNo) {
	if (cacheIsValid)
	    return cachedFilteredEvents;

	List eventsList = new ArrayList();
	List calendarList = getCalendars();
	FilterParams fp = getFilterParams();
	if (fp == null)
	    fp = new FilterParams(null); // an empty object -> matches every
	// event

	int counter = maxEventNo;
	for (Iterator it = calendarList.iterator(); it.hasNext();) {
	    if (counter == 0) {
		break;
	    }
	    Calendar cal = (Calendar) it.next();
	    if (cal != null) {
		for (Iterator eventIt = cal.getEventList().iterator(); eventIt
			.hasNext();) {
		    if (counter == 0) {
			break;
		    }
		    Event event = (Event) eventIt.next();
		    if (fp.matches(event)) {
			eventsList.add(event);
			--counter;
		    }
		}
	    }
	}
	setCache(eventsList);
	return eventsList;
    }

    // returns a list of events
    public List getFollowingEvents(int maxEventNo) {
	if (cacheIsValid)
	    return cachedFilteredEvents;

	List eventsList = new ArrayList();

	List calendarList = getCalendars();
	FilterParams fp = buildCurrentTimeFilter();

	for (Iterator it = calendarList.iterator(); it.hasNext();) {
	    Calendar cal = (Calendar) it.next();
	    if (cal != null) {
		for (Iterator eventIt = cal.getEventList().iterator(); eventIt
			.hasNext();) {
		    Event event = (Event) eventIt.next();
		    if (fp.matches(event)) {
			eventsList.add(event);
		    }
		}
	    }
	}
	setCache(eventsList);
	Collections.sort(eventsList, new EventComparator());
	if (maxEventNo < 0) {
	    return eventsList;
	}
	return eventsList.subList(0, maxEventNo);
    }

    public static FilterParams buildCurrentTimeFilter() {
	FilterParams fp = new FilterParams(null);
	XMLGregorianCalendar now = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(new GregorianCalendar());
	fp.setTimeSearchType(TimeSearchType.allAfter);
	fp.setDTbegin(now);

	return fp;
    }

    /**
     * @see org.universAAL.ontology.ManagedIndividual#getPropSerializationType(java.
     *      lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }

    /*
     * Return either PROP_HAS_CALENDAR or PROP_HAS_FILTER_PARAMS property.
     */
    public boolean isWellFormed() {

	return props.containsKey(PROP_HAS_CALENDARS)
		|| props.containsKey(PROP_HAS_FILTER_PARAMS);
    }

    private void clearCache() {
	cacheIsValid = false;
	cachedFilteredEvents.clear();
    }

    private void setCache(List cachedEvents) {
	cachedFilteredEvents = new ArrayList(cachedEvents);
	cacheIsValid = true;
    }

    public void clearPreviousData() {
	setCalendars(new ArrayList());
    }
    
    /* (non-Javadoc)
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }
}
