package gr.anco.persona.eventSelectionTool.ont;

import gr.anco.persona.agenda.ont.Calendar;
import gr.anco.persona.agenda.ont.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.persona.middleware.TypeMapper;
import org.persona.ontology.ManagedIndividual;
import org.persona.ontology.expr.Restriction;

/**
 * @author kalogirou
 * 
 */
public class EventSelectionTool extends ManagedIndividual {
	public static final String EVENT_SELECTION_TOOL_NAMESPACE = "http://ontology.persona.anco.gr/EventSelectionTool.owl#";
	public static final String MY_URI;
	public static final String PROP_HAS_CALENDARS;
	public static final String PROP_HAS_FILTER_PARAMS;
	public static final String PROP_MAX_EVENT_NO;

	private List cachedFilteredEvents = new ArrayList();// list of events
	private boolean cacheIsValid = false;

	static {
		MY_URI = EVENT_SELECTION_TOOL_NAMESPACE + "EventSelectionTool";
		PROP_HAS_CALENDARS = EVENT_SELECTION_TOOL_NAMESPACE + "hasCalendar";
		PROP_HAS_FILTER_PARAMS = EVENT_SELECTION_TOOL_NAMESPACE
				+ "hasFilterParams";
		PROP_MAX_EVENT_NO = EVENT_SELECTION_TOOL_NAMESPACE + "maxEventNo";
		register(EventSelectionTool.class);
	}

	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (PROP_HAS_CALENDARS.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI,
					Calendar.MY_URI, 2, 2);
		if (PROP_HAS_FILTER_PARAMS.equals(propURI))
			return Restriction.getAllValuesRestriction(propURI,
					FilterParams.MY_URI);
		if (PROP_MAX_EVENT_NO.equals(propURI))
			return Restriction.getAllValuesRestrictionWithCardinality(propURI,
					TypeMapper.getDatatypeURI(Integer.class), 1, 0);

		return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
	}

	public static String[] getStandardPropertyURIs() {
		String[] inherited = ManagedIndividual.getStandardPropertyURIs();
		String[] toReturn = new String[inherited.length + 3];
		int i = 0;
		while (i < inherited.length) {
			toReturn[i] = inherited[i];
			i++;
		}
		toReturn[i++] = PROP_HAS_CALENDARS;
		toReturn[i++] = PROP_HAS_FILTER_PARAMS;
		toReturn[i++] = PROP_MAX_EVENT_NO;

		return toReturn;
	}

	public static String getRDFSComment() {
		return "The class of a EventSelectionTool.";
	}

	public static String getRDFSLabel() {
		return "EventSelectionTool";
	}

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
				for (Iterator eventIt = cal.getEventList().iterator(); eventIt.hasNext();) {
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
				for (Iterator eventIt = cal.getEventList().iterator(); eventIt.hasNext();) {
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
		XMLGregorianCalendar now = TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(new GregorianCalendar());
		fp.setTimeSearchType(TimeSearchType.allAfter);
		fp.setDTbegin(now);
		
		return fp;		
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.persona.ontology.ManagedIndividual#getPropSerializationType(java.
	 * lang.String)
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

}
