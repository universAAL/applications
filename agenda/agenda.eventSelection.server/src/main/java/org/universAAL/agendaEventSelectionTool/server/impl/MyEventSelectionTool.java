package org.universAAL.agendaEventSelectionTool.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.agendaEventSelectionTool.database.EventSelectionToolDBInterface;
import org.universAAL.agendaEventSelectionTool.ont.EventComparator;
import org.universAAL.agendaEventSelectionTool.ont.EventSelectionTool;
import org.universAAL.agendaEventSelectionTool.ont.FilterParams;
import org.universAAL.agendaEventSelectionTool.ont.TimeSearchType;
import org.universAAL.middleware.rdf.TypeMapper;

public class MyEventSelectionTool implements EventSelectionToolDBInterface {
    private String DB_URL;
    private String DB_USER;
    private String DB_PWD;
    // private ArrayList listeners;
    private EventSelectionTool esTool;

    private Connection conn;
    private Object theLock;

    public MyEventSelectionTool(String url, String user, String pwd) {
	this.DB_URL = url;
	this.DB_USER = user;
	this.DB_PWD = pwd;
	this.theLock = new Object();
	this.esTool = new EventSelectionTool(EventSelectionTool.MY_URI + "1");
	connect();
    }

    public Object getLock() {
	return theLock;
    }

    public void connect() {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
	    initDB();
	} catch (Exception e) {
	    System.out
		    .println("Exception trying to get connection to database: "
			    + e);
	}
    }

    private void initDB() throws SQLException {

    }

    public void disconnect() {
	try {
	    conn.close();
	} catch (SQLException e) {
	    System.out
		    .println("Exception trying to close connection to database: "
			    + e);
	}
    }

    /******************************
     * database queries
     ******************************/

    // The following function is just for test; creates events
    // private Event createEvent(int id, String category, String spokenLanguage,
    // String place, String description,
    // XMLGregorianCalendar BeginxmlGregorianCalendar, XMLGregorianCalendar
    // EndxmlGregorianCalendar) {
    //
    // // start Event Details
    // EventDetails ed = new EventDetails(EventDetails.MY_URI + "Detail" + id);
    // ed.setCategory(category);
    // ed.setSpokenLanguage(spokenLanguage);
    // ed.setPlaceName(place);
    // ed.setDescription(description);
    //
    // TimeInterval timeInterval = new TimeInterval(TimeInterval.MY_URI + id);
    // timeInterval.setStartTime(BeginxmlGregorianCalendar);
    // timeInterval.setEndTime(EndxmlGregorianCalendar);
    // ed.setTimeInterval(timeInterval);
    //
    // Event event = new Event(Event.MY_URI + "DummyEvent" + id);
    // event.setEventDetails(ed);
    //
    // return event;
    // }

    /**
     * Returns a list of events which are stored in db, no matter in what
     * calendar may belong to. Events are returned in chronological order (older
     * to newer)
     */
    public List<Event> requestEvents(FilterParams filterParams) {
	esTool.clearPreviousData();
	esTool.setFilterParams(filterParams);
	esTool.setCalendars(new ArrayList<Event>(0));
	return genericEventRequest();
    }

    /**
     * Gets data from DB and puts events into a list.
     * 
     * @param query
     * @return List of events
     */
    private List<Event> sqlRequestEvents(String query) {
	List<Event> allEvents = new ArrayList<Event>();
	try {
	    Event e;
	    PreparedStatement ps;
	    ps = conn.prepareStatement(query);
	    // auto-commit enabled
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {

		ResultSet rsr;
		int eID = rs.getInt("idEvent");
		Calendar c = new Calendar(Calendar.MY_URI, rs
			.getString("CalendarType"));
		String title = rs.getString("Title");
		String desc = rs.getString("Description");
		long eStart = rs.getLong("StartTime");
		long eEnd = rs.getLong("EndTime");
		String location = rs.getString("Location");
		boolean isActive = rs.getBoolean("Active");
		int rID = rs.getInt("idReminder");
		ps = conn
			.prepareStatement("SELECT * FROM reminder WHERE idReminder=?");
		ps.setInt(1, rID);
		rsr = ps.executeQuery();
		Reminder r;
		e = new Event();
		GregorianCalendar gc = new GregorianCalendar();

		if (rsr.next()) {
		    long rStart = rsr.getLong("StartTime");
		    int rRepeatInterval = rsr.getInt("RepeatInterval");
		    int rRepeatTime = rsr.getInt("RepeatTime");
		    int rType = rsr.getInt("idReminderType");
		    r = new Reminder(Reminder.MY_URI + rID);
		    r.setReminderType(ReminderType
			    .getReminderTypeByOrder(rType));
		    r.setRepeatInterval(rRepeatInterval);
		    r.setTimesToBeTriggered(rRepeatTime);
		    r.setMessage(rsr.getString("Message"));
		    gc.setTimeInMillis(rStart);
		    r.setReminderTime(TypeMapper.getDataTypeFactory()
			    .newXMLGregorianCalendar(gc));
		    e.setReminder(r);
		} else
		    e.setReminder(new Reminder());

		EventDetails ed = new EventDetails();
		ed.setDescription(desc);
		ed.setPlaceName(location);
		ed.setCategory(title);

		TimeInterval ti = new TimeInterval();
		gc.setTimeInMillis(eStart);
		ti.setStartTime(TypeMapper.getDataTypeFactory()
			.newXMLGregorianCalendar(gc));
		if (eEnd != 0) {
		    gc.setTimeInMillis(eEnd);
		    ti.setEndTime(TypeMapper.getDataTypeFactory()
			    .newXMLGregorianCalendar(gc));
		} else {
		    ti.setEndTime(null);
		}

		ed.setTimeInterval(ti);

		e.setVisible(isActive);
		e.setParentCalendar(c);
		e.setEventDetails(ed);
		e.setEventID(eID);

		allEvents.add(e);
	    }
	    Collections.sort(allEvents, new EventComparator());
	    return allEvents;
	} catch (NumberFormatException nfe) {
	    // Activator.log.log(LogService.LOG_ERROR, nfe.getMessage());
	    return new ArrayList<Event>(0);
	} catch (SQLException sqle) {
	    // Activator.log.log(LogService.LOG_ERROR, sqle.getMessage());
	    return new ArrayList<Event>(0);
	} catch (Exception e) {
	    // Activator.log.log(LogService.LOG_ERROR, e.getMessage());
	    return new ArrayList<Event>(0);
	}
    }

    /**
     * Returns a list of events which are stored in db, belong to any of the
     * calendars in <code>calendarList</code> and their start time are after
     * now(). Maximum size of event list is <code>maxEventNo</code>. Additional
     * events are dropped Events are returned in chronological order (older to
     * newer)
     */
    public List<Event> requestFollowingEvents(List calendarList, int maxEventNo) {
	esTool.clearPreviousData();
	esTool.setFilterParams(EventSelectionTool.buildCurrentTimeFilter());
	esTool.setCalendars(calendarList);

	List l = genericEventRequest();
	if (l.size() > maxEventNo)
	    return l.subList(0, maxEventNo);

	return l;
    }

    /**
     * Creates a query using parameters from FilterParams and calls
     * <code>sqlRequestEvents</code> which returns a list of events.
     * 
     * @return List of events
     */
    private List<Event> genericEventRequest() {
	StringBuffer sb = new StringBuffer();
	sb.append("SELECT * FROM getevents e");
	List<Calendar> l = esTool.getCalendars();
	FilterParams fp = esTool.getFilterParams();
	boolean first = false;

	if (l == null || l.size() == 0) {
	    sb.append(";");
	    return sqlRequestEvents(sb.toString());
	} else {
	    sb.append(" WHERE (");

	    for (Calendar c : l) {
		if (first) {
		    sb.append(" OR ");
		}
		sb.append("idCalendar=" + extractIdFromURI(c.getURI()));
		first = true;
	    }
	    sb.append(")");
	}

	if (fp.getTimeSearchType() != null) {
	    if (first) {
		sb.append(" AND ");
	    } else {
		sb.append(" WHERE ");
	    }
	    long start = 0;
	    long end = 0;
	    if (fp.getDTbegin() != null)
		start = fp.getDTbegin().toGregorianCalendar().getTimeInMillis();
	    if (fp.getDTend() != null)
		end = fp.getDTend().toGregorianCalendar().getTimeInMillis();

	    sb.append(createDateClause(start, end,
		    fp.getTimeSearchType().ord(), "e"));
	}
	sb.append(";");
	return sqlRequestEvents(sb.toString());
    }

    /**
     * Extracts ID from calendar URI.
     * 
     * @param calendarURI
     *            A String representation of Calendar's URI
     * @return Calendar ID (int)
     */
    private static int extractIdFromURI(String calendarURI) {
	int id = -1;
	try {
	    id = new Integer(calendarURI.substring(Calendar.MY_URI.length()))
		    .intValue();
	} catch (NumberFormatException nfe) {
	    return -1;
	}
	return id;
    }

    /**
     * Returns a list of events which are stored in db and belong to any of the
     * calendars in <code>calendarList</code>. Events are returned in
     * chronological order (older to newer)
     * 
     * @param filterParams
     *            Filter parameters
     * @param calendarList
     *            A list of calendars
     * @return A list of events
     */
    public List requestFromCalendarEvents(FilterParams filterParams,
	    List calendarList) {
	esTool.clearPreviousData();
	esTool.setFilterParams(filterParams);
	esTool.setCalendars(calendarList);

	return genericEventRequest();
    }

    /**
     * Returns a list of events which are stored in db, belong to any of the
     * calendars in <code>calendarList</code>. Maximum size of event list is
     * <code>maxEventNo</code>. Additional events are dropped Events are
     * returned in chronological order (older to newer)
     */
    public List requestFromCalendarLimitedEvents(FilterParams filterParams,
	    List calendarList, int maxEventNo) {
	esTool.clearPreviousData();
	esTool.setFilterParams(filterParams);
	esTool.setCalendars(calendarList);

	List l = genericEventRequest();
	if (l.size() > maxEventNo)
	    return l.subList(0, maxEventNo);

	return l;
    }

    // private String createFilterClauseInWhere(String tableName) {
    // FilterParams fp = esTool.getFilterParams();
    // StringBuffer query = new StringBuffer(30);
    // // boolean hasWhereClause = false;
    // boolean hasPrevious = false;
    // // query.append("WHERE ");
    // if (fp.getCategory() != null) {
    // if (hasPrevious) {
    // query.append("AND ");
    // }
    // query.append(tableName + ".eCategory LIKE '%" + fp.getCategory() +
    // "%' ");
    // hasPrevious = true;
    // }
    //
    // if (fp.getSpokenLanguage() != null) {
    // if (hasPrevious) {
    // query.append("AND ");
    // }
    // query.append(tableName + ".eSpokenLang = '" + fp.getSpokenLanguage() +
    // "' ");
    // hasPrevious = true;
    // }
    //
    // if (fp.getDescription() != null) {
    // if (hasPrevious) {
    // query.append("AND ");
    // }
    // query.append(tableName + ".eDescription LIKE '%" + fp.getDescription() +
    // "%' ");
    // hasPrevious = true;
    // }
    //
    // if (fp.getTimeSearchType() != null) {
    // if (hasPrevious) {
    // query.append("AND ");
    // }
    // long start = 0;
    // long end = 0;
    // if(fp.getDTbegin() != null)
    // start = fp.getDTbegin().toGregorianCalendar().getTimeInMillis();
    // if (fp.getDTend() != null)
    // end = fp.getDTend().toGregorianCalendar().getTimeInMillis();
    //			
    // query.append(createDateClause(start, end, fp.getTimeSearchType().ord(),
    // tableName));
    // hasPrevious = true;
    // }
    // if (hasPrevious)
    // return query.toString();
    //
    // return "(true)";
    // }

    /**
     * Creates a part of a query that ensures that a certain data is from a
     * specific time period.
     */
    private static String createDateClause(long startEventSearch,
	    long endEventSearch, int timeSearchType, String tableName) {
	if ((startEventSearch == 0) && (endEventSearch != 0))
	    startEventSearch = endEventSearch;
	else if ((startEventSearch != 0) && (endEventSearch == 0))
	    endEventSearch = startEventSearch;
	else if ((startEventSearch == 0) && (endEventSearch == 0))
	    return "(true)";

	switch (timeSearchType) {
	case TimeSearchType.STARTS_BETWEEN:
	    /* Event starts inside a period */
	    return "(" + tableName + ".StartTime > " + startEventSearch
		    + " AND " + tableName + ".StartTime < " + +endEventSearch
		    + ")";
	    // return "(" + startEventSearch + " < " + tableName +
	    // ".eDbStartTime < " + endEventSearch + ")";
	case TimeSearchType.ENDS_BETWEEN:
	    /* Event ends inside a period */
	    // return "(" + startEventSearch + " < " + tableName +
	    // ".eDbEndTime < " + endEventSearch + ")";
	    return "(" + tableName + ".EndTime > " + startEventSearch + " AND "
		    + tableName + ".EndTime < " + endEventSearch + ")";
	case TimeSearchType.STARTS_AND_ENDS_BETWEEN:
	    /* Event starts - ends inside a period */
	    return "(" + tableName + ".StartTime > " + startEventSearch
		    + " AND " + tableName + ".EndTime < " + endEventSearch
		    + ")";
	case TimeSearchType.START_BEFORE_AND_ENDS_AFTER:
	    /* Event is running throughout a period */
	    return "(" + tableName + ".StartTime < " + startEventSearch
		    + " AND " + tableName + ".EndTime > " + endEventSearch
		    + ")";
	case TimeSearchType.ALL_BEFORE:
	    /* Event starts - ends before a period */
	    return "(" + tableName + ".EndTime < " + startEventSearch + ")";
	case TimeSearchType.ALL_AFTER:
	    /* Event starts - ends after a period */
	    return "(" + tableName + ".StartTime > " + endEventSearch + ")";
	case TimeSearchType.ALL_CASES:
	default:
	    return "(true)";
	}
    }

    public static void main(String[] str) {
	MyEventSelectionTool db = new MyEventSelectionTool(
		"jdbc:mysql://localhost/agenda_reminder", "root", "sc2011");
	FilterParams fp = new FilterParams(FilterParams.MY_URI + "test");
	List<Calendar> cals = new ArrayList<Calendar>();
	cals.add(new Calendar(Calendar.MY_URI + 6));
	List<Event> eList = db.requestFollowingEvents(cals, 10);
	for (Iterator<Event> it = eList.iterator(); it.hasNext();) {
	    System.out.println("Hello: " + it.next());
	}

    }
}
