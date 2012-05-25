package org.universAAL.agenda.server.unit_impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.agenda.Calendar;
import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.Reminder;
import org.universAAL.ontology.agenda.ReminderType;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;

/**
 * Used to communicate with database.
 * 
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class AgendaDB implements AgendaDBInterface {
    // private static final String DEFAULT_INTERMIDIATE_CALENDAR_NAME =
    // "qwoeiptetfngbclvkjhsfdshf3284jhdf";
    // private static final boolean DEBUG_DB = false;//Set to true to refill the
    // DB every time (for debugging)
    /**
     * Database URL
     */
    private String DB_URL;
    /**
     * Database Username
     */
    private String DB_USER;
    /**
     * Database password
     */
    private String DB_PWD;

    private Connection conn;
    private Object theLock;

    public static final boolean COMMIT = true;
    public static final boolean DO_NOT_COMMIT = false;
    public static final int SUCCESS = -1;
    public static final int FAIL_EXITS = -2;
    public static final int FAIL_NOT_EXITS = -3;
    public static final int FAIL = -4;
    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    private Properties prop;

    public AgendaDB(ModuleContext mcontext, String url, String user, String pwd) {
	AgendaDB.mcontext = mcontext;
	File confHome = new File(new BundleConfigHome("agenda")
		.getAbsolutePath());
	this.DB_URL = url;
	this.DB_USER = user;
	this.DB_PWD = pwd;

	this.theLock = new Object();
	prop = new Properties();
	try {

	    prop.load(new FileInputStream(new File(confHome,
		    "DBquery.properties")));
	    connect();
	} catch (FileNotFoundException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "constructor",
			    new Object[] { "Exception trying to obtain SQL queries from DBquery.properties!" },
			    e);

	} catch (IOException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "constructor",
			    new Object[] { "Exception trying to obtain SQL queries from DBquery.properties!" },
			    e);
	}

    }

    public Object getLock() {
	return theLock;
    }

    /**
     * Connects to the database.
     */
    public void connect() {
	try {

	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
	    conn.setAutoCommit(false);
	    initDB();
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "connect",
			    new Object[] { "Exception trying to get connection to database." },
			    e);

	}
    }

    private void initDB() throws SQLException {

    }

    /**
     * Closes database connection.
     */
    public void disconnect() {
	try {
	    conn.close();
	} catch (SQLException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "disconnect",
			    new Object[] { "Exception trying to close connection to database!" },
			    e);

	}
    }

    /******************************
     * database queries
     ******************************/
    /**
     * Return the URI of <i>all</i> {@link Calendar}s that are controlled by the
     * server
     * 
     * @return a String array
     */
    // new version: using db
    // IGOR
    public String[] getCalURIs() {
	ResultSet result;
	try {
	    PreparedStatement getAllCalendars = conn.prepareStatement(prop
		    .getProperty("getAllCalendarURI"));
	    result = getAllCalendars.executeQuery();
	    List<String> calendars = new ArrayList<String>();
	    while (result.next()) {
		calendars.add(Calendar.MY_URI + result.getString(1));
	    }

	    String[] ids = new String[calendars.size()];
	    for (int i = 0; i < calendars.size(); i++)
		ids[i] = (String) calendars.get(i);
	    return ids;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return new String[0];
    }

    /**
     * Returns all calendars.
     * 
     * @return List of calendars
     */
    public List<Calendar> getCalendarsWithInfo() {
	ResultSet result;
	try {
	    PreparedStatement getAllCalendars = conn.prepareStatement(prop
		    .getProperty("getCalendarsWithUser"));
	    result = getAllCalendars.executeQuery();
	    List<Calendar> calendars = new ArrayList<Calendar>();
	    Calendar c;
	    while (result.next()) {
		int id = result.getInt(1);
		String name = result.getString(2);
		int ownerID = result.getInt(3);
		c = new Calendar(Calendar.MY_URI + id, name);
		c.setOwner(new User(User.MY_URI + ownerID));
		calendars.add(c);
	    }
	    return calendars;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return new ArrayList<Calendar>(0);
    }

    /**
     * Gets all calendars from certain user.
     * 
     * @param owner
     *            Owner of the calendar
     * @return List of calendars
     */
    public List<Calendar> getCalendarsWithInfo(User owner) {
	ResultSet result;
	try {
	    PreparedStatement getAllCalendars = conn.prepareStatement(prop
		    .getProperty("getCalendarsByUser"));
	    String[] split = owner.getURI().split("#");
	    getAllCalendars.setString(1, split[1]); // owner.getURI());
	    result = getAllCalendars.executeQuery();
	    List<Calendar> calendars = new ArrayList<Calendar>();
	    Calendar c;
	    while (result.next()) {
		int id = result.getInt("idCalendar");
		String name = result.getString(2);
		c = new Calendar(Calendar.MY_URI + id, name);
		c.setOwner(owner);
		calendars.add(c);
	    }

	    return calendars;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return new ArrayList<Calendar>(0);
    }

    /**
     * Gets all calendars
     * 
     * @return An array of calendars
     */
    public Calendar[] getCalendars() {
	String[] s = this.getCalURIs();
	Calendar[] c = new Calendar[s.length];

	for (int i = 0; i < s.length; ++i) {
	    c[i] = new Calendar(Calendar.MY_URI + s[i]);
	}
	return c;
    }

    /**
     * Returns the ID of <i>all</i> {@link Calendar}s that are controlled by the
     * server.
     * 
     * @return a String array
     */
    // new version: using db
    public String[] getCalIDs() {
	String[] s = this.getCalURIs();
	String[] ids = new String[s.length];
	for (int i = 0; i < s.length; i++)
	    ids[i] = s[i].substring(Calendar.MY_URI.length());
	return ids;
    }

    /**
     * Returns the name of the {@link Calendar} with the specific URI.
     * 
     * @param calendarURI
     *            a calendar URI
     * @return the name of the calendar
     */
    public String getCalType(String calendarURI) {
	return calendarURI.substring(Calendar.MY_URI.length());
    }

    /**
     * Inserts a new calendar into database
     * 
     * @param c
     *            An event's calendar
     * @param owner
     *            Owner of the calendar
     * @return A newly created calendar
     */
    public Calendar addCalendar(Calendar c, User owner, boolean commit)
	    throws InvalidParameterException {
	try {
	    PreparedStatement ps = conn.prepareStatement(prop
		    .getProperty("getUserID"));
	    if (owner == null)
		owner = c.getOwner();
	    ps.setString(1, owner.getURI().substring(
		    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX.length()));
	    ResultSet rs = ps.executeQuery();
	    int id;
	    if (rs.next()) {
		id = rs.getInt(1);
	    } else {
		System.out.println("No such user!");
		return null;
	    }
	    ps = conn.prepareStatement(prop.getProperty("addCalendar"));
	    ps.setString(1, c.getName());
	    ps.setInt(2, id);
	    ps.executeUpdate();
	    c.setOwner(owner);
	    if (commit)
		conn.commit();
	    return c;
	} catch (SQLException e) {
	    // e.printStackTrace();
	    return null;
	}
    }

    /**
     * Removes calendar from database. Note: Removing a calendar will also
     * remove ALL events and reminder which were in that calendar
     * 
     * @param c
     *            A calendar to remove
     * @param commit
     *            Boolean - <i>true</i> to commit to DB, otherwise <i>false</i>
     * @return <i>true</i> if successfully removed, otherwise <i>false</i>
     */
    public boolean removeCalendar(Calendar c, boolean commit) {

	try {
	    conn.setAutoCommit(false);
	    PreparedStatement ps = conn.prepareStatement(prop
		    .getProperty("deleteCalendar"));

	    int calID = extractIdFromURI(c.getURI());
	    ps.setInt(1, calID);
	    int rows = ps.executeUpdate();
	    if (commit) {
		conn.commit();
		conn.setAutoCommit(true);
	    }
	    if (rows == 0) {
		LogUtils.logInfo(mcontext, this.getClass(), "removeCalendar",
			new Object[] { "No such calendar" }, null);

		return false;
	    }
	} catch (SQLException e) {
	    // e.printStackTrace();
	    return false;
	}
	return true;
    }

    /**
     * Returns all types of categories from events.
     * 
     * @param commit
     * @return List of string representation of categories
     */
    public List<String> getAllEventCategories(boolean commit) {
	List<String> allCategories = new ArrayList<String>();
	try {
	    conn.setAutoCommit(false);
	    PreparedStatement ps = conn.prepareStatement(prop
		    .getProperty("getAllECategories"));
	    ResultSet result = ps.executeQuery();
	    while (result.next()) {
		String cat = result.getString(1);
		if (cat != null) {
		    allCategories.add(cat);
		}
		if (commit) {
		    conn.commit();
		    conn.setAutoCommit(true);
		}

	    }
	} catch (SQLException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getAllEventCategories",
			    new Object[] { "Exception trying to close connection to database!" },
			    e);
	    return new ArrayList<String>(0);
	}
	// Collections.sort(allCategories);
	return allCategories;
    }

    /**
     * Returns the name of the owner ({@link User}) of the {@link Calendar}
     * which is stored in the <code>position</code>-th position of the server's
     * calendar database.
     * 
     * @param position
     *            the position of the calendar in server's calendar database
     * @return the calendar URI
     */

    public String[] getCalNameAndOwner(String calendarURI) {
	String query = "SELECT u.Username, c.Description FROM users u, calendartype c WHERE"
		+ "c.idUser=u.idUser AND calendarID = ?";
	try {
	    PreparedStatement ps = conn.prepareStatement(query);
	    int id = AgendaDB.extractIdFromURI(calendarURI);
	    if (id == -1)
		throw new NumberFormatException();
	    ps.setInt(1, id);
	    ResultSet result = ps.executeQuery();

	    if (result.next()) {
		String name = result.getString("Description");
		String owner = result.getString("Username");
		return new String[] { name,
			Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX + owner };
	    }

	} catch (NumberFormatException nfe) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getCalNameAndOwner",
			    new Object[] { "Exception trying to get calendar name and owner!" },
			    nfe);

	} catch (SQLException e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getCalNameAndOwner",
			    new Object[] { "Exception trying to get calendar name and owner!" },
			    e);
	}

	return null;
    }

    /**
     * Extracts an ID from calendar's URI.
     * 
     * @param calendarURI
     *            A calendar's URI (string)
     * @return An integer ID of the calendar, -1 if URI is not in right format
     */
    public static int extractIdFromURI(String calendarURI) {
	int id = -1;
	if (calendarURI.startsWith(Calendar.MY_URI)) {
	    try {
		id = new Integer(calendarURI
			.substring(Calendar.MY_URI.length())).intValue();
	    } catch (NumberFormatException nfe) {
		return -1;
	    }
	}
	return id;
    }

    /**
     * Converts an XML Gregorian Calendar to String.
     * 
     * @param cal
     * @return A string representation of date/time.
     */
    public static String convertXMLGregCalendar2String(XMLGregorianCalendar cal) {
	if (cal == null)
	    return "";

	StringBuffer sb = new StringBuffer(19);
	sb.append(cal.getYear() + "-");
	sb.append(cal.getMonth() + "-");
	sb.append(cal.getDay() + " ");
	sb.append(cal.getHour() + ":");
	sb.append(cal.getMinute() + ":");
	sb.append(cal.getSecond());

	return sb.toString();
    }

    /**
     * Converts an SQLTimeStamp to XML Gregorian Calendar.
     * 
     * @param cal
     * @return XMLGregorianCalendar An XMLGregorianCalendar representation of
     *         SQLTimeStamp.
     */
    public static XMLGregorianCalendar convertSQLTimeStamp2XMLGregCalendar(
	    java.sql.Timestamp timeStamp) {
	if (timeStamp == null)
	    return null;
	java.util.Calendar c = java.util.Calendar.getInstance();
	c.setTimeInMillis(timeStamp.getTime());
	return TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(
		c.get(java.util.Calendar.YEAR),
		c.get(java.util.Calendar.MONTH),
		c.get(java.util.Calendar.DAY_OF_WEEK),
		c.get(java.util.Calendar.HOUR_OF_DAY),
		c.get(java.util.Calendar.MINUTE),
		c.get(java.util.Calendar.SECOND), 0, 0);
    }

    /**
     * Adds an event to database. If reminder exists, it is also added.
     * 
     * @return An Event ID, or -1 if adding fails
     */
    public int addEventToCalendar(String calendarURI, Event event,
	    boolean commit) {

	int eventId = 0; // foreign key: cal2event -> event
	int rID = -1;

	int calendarId = 0;
	try {
	    ResultSet rs;

	    calendarId = AgendaDB.extractIdFromURI(calendarURI);
	    if (calendarId == -1)
		throw new Exception("Illegal calendar URI format: "
			+ calendarURI);

	    // check for calendar existence
	    if (!calendarExists(calendarId)) {
		throw new Exception("No such calendar in database: "
			+ calendarURI);
	    }

	    PreparedStatement ps;
	    conn.setAutoCommit(false);

	    Reminder rem = event.getReminder();
	    ps = conn.prepareStatement(prop.getProperty("addReminder"),
		    Statement.RETURN_GENERATED_KEYS);
	    if (rem.getReminderTime() != null) {

		ps.setInt(1, event.getReminder().getReminderType().ord());

		XMLGregorianCalendar ccc = rem.getReminderTime();
		long i = 0;
		if (ccc != null) {
		    i = ccc.toGregorianCalendar().getTimeInMillis();
		    ps.setLong(2, i);
		} else {
		    ps.setNull(2, java.sql.Types.BIGINT);
		}

		ps.setInt(4, rem.getTimesToBeTriggered());
		ps.setString(5, rem.getMessage());

		// if (rem.getReminderType() == null) {
		// ps.setNull(4, java.sql.Types.INTEGER);
		// } else {
		// ps.setShort(4, (short) rem.getReminderType().ord());
		// }
		ps.setInt(3, rem.getRepeatInterval() * 60000);
		rID = ps.executeUpdate();
		rs = ps.getGeneratedKeys();
		rs.next();
		rID = rs.getInt(1);
		conn.commit();
	    } else {
		ps.setNull(1, java.sql.Types.INTEGER);
		ps.setNull(5, java.sql.Types.VARCHAR);
		ps.setNull(2, java.sql.Types.BIGINT);
		ps.setNull(3, java.sql.Types.INTEGER);
		ps.setNull(4, java.sql.Types.INTEGER);
	    }

	    ps = conn.prepareStatement(prop.getProperty("addEvent"),
		    Statement.RETURN_GENERATED_KEYS);

	    EventDetails ed = event.getEventDetails();
	    if (ed != null) {
		ps.setInt(1, calendarId);
		if (rID == -1)
		    ps.setNull(2, java.sql.Types.INTEGER);
		else
		    ps.setInt(2, rID);
		ps.setString(3, ed.getCategory());
		ps.setString(4, ed.getDescription());

		if (ed.getTimeInterval() != null) {
		    XMLGregorianCalendar start = ed.getTimeInterval()
			    .getStartTime();
		    if (start != null) {
			long i = start.toGregorianCalendar().getTimeInMillis();
			ps.setLong(5, i);
		    } else {
			ps.setNull(5, java.sql.Types.BIGINT);
		    }
		    XMLGregorianCalendar end = ed.getTimeInterval()
			    .getEndTime();
		    if (end != null) {
			long i = end.toGregorianCalendar().getTimeInMillis();
			ps.setLong(6, i);
		    } else {
			ps.setNull(6, java.sql.Types.BIGINT);
		    }
		} else {
		    ps.setNull(5, java.sql.Types.BIGINT);
		    ps.setNull(6, java.sql.Types.BIGINT);
		}

		ps.setString(7, ed.getPlaceName());
		ps.setBoolean(8, true);

	    } else {
		ps.setNull(1, java.sql.Types.INTEGER);
		ps.setNull(2, java.sql.Types.INTEGER);
		ps.setNull(3, java.sql.Types.VARCHAR);
		ps.setNull(4, java.sql.Types.VARCHAR);
		ps.setNull(5, java.sql.Types.BIGINT);
		ps.setNull(6, java.sql.Types.BIGINT);
		ps.setNull(7, java.sql.Types.VARCHAR);
		ps.setNull(8, java.sql.Types.TINYINT);
	    }
	    ps.executeUpdate();
	    ResultSet rse = ps.getGeneratedKeys();
	    rse.next();
	    eventId = rse.getInt(1);

	    conn.commit();
	} catch (NumberFormatException nfe) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "addEventToCalendar",
			    new Object[] { "Exception trying to add event to calendar!" },
			    nfe);
	    return -1;
	} catch (SQLException sqle) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "addEventToCalendar",
			    new Object[] { "Exception trying to add event to calendar!" },
			    sqle);
	    return -1;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "addEventToCalendar",
			    new Object[] { "Exception trying to add event to calendar!" },
			    e);
	    return -1;
	}
	return eventId;
    }

    /**
     * Gets all events from users calendar
     * 
     * @param calendarURI
     *            A Calendar's URI
     * @param eventID
     *            An ID of an event
     * @return A newly created Event
     */
    public Event getEventFromCalendar(String calendarURI, int eventID,
	    boolean commit) {
	List<Event> event = getEvents(calendarURI, eventID, commit);
	if (event.size() > 1) {
	    LogUtils.logWarn(mcontext, this.getClass(), "getEventFromCalendar",
		    new Object[] { "More than one events with the same id!",
			    eventID }, null);

	}
	if (event.size() == 0)
	    return null;
	return (Event) event.get(0);
    }

    /**
     * Gets all events from all calendars
     * 
     * @param calendarURI
     *            A Calendar's URI
     * @return List of events
     */
    public List<Event> getAllEvents(String calendarURI, boolean commit) {
	return getEvents(calendarURI, -1, commit);
    }

    private List<Event> getEvents(String calendarURI, int eventID,
	    boolean commit) {
	List<Event> allEvents = new ArrayList<Event>();
	String calendarCriteria = "";
	String eventCriteria = "";
	ResultSet rsr;
	ResultSet rs;
	PreparedStatement psr;
	PreparedStatement ps;
	String query = prop.getProperty("getEvent");
	boolean first = false;
	if (calendarURI != null) {
	    // get events from a specific calendars
	    calendarCriteria = " idCalendar = ? ";
	    first = true;
	    query = new String(query + calendarCriteria);

	}
	if (eventID > 0) {
	    // get Specific event
	    if (first)
		eventCriteria = " AND idEvent = ? ";
	    else
		eventCriteria = " idEvent = ? ";
	    query = new String(query + eventCriteria);
	}
	query = new String(query + ";");

	try {
	    ps = conn.prepareStatement(query);
	    if (calendarURI != null) {
		int calendarId = AgendaDB.extractIdFromURI(calendarURI);
		if (calendarId == -1)
		    throw new Exception("Illegal calendar URI format: "
			    + calendarURI);

		// check for calendar existence
		if (!calendarExists(calendarURI))
		    return new ArrayList<Event>(0);

		ps.setInt(1, calendarId);
	    }
	    if (eventID > 0) {
		if (calendarURI != null)
		    ps.setInt(2, eventID);
		else
		    ps.setInt(1, eventID);
	    }
	    conn.setAutoCommit(false);
	    rs = ps.executeQuery();

	    while (rs.next()) {

		GregorianCalendar gc = new GregorianCalendar();
		Event e = new Event();
		int eID = rs.getInt("idEvent");
		Calendar c = new Calendar(Calendar.MY_URI, rs
			.getString("CalendarType"));
		String title = rs.getString("Title");
		String desc = rs.getString("Description");
		long eStart = rs.getLong("StartTime");
		long eEnd = rs.getLong("EndTime");
		String location = rs.getString("Location");
		boolean isActive = rs.getBoolean("Active");

		psr = conn.prepareStatement(prop
			.getProperty("getReminderFromEvent"));
		psr.setInt(1, eID);
		rsr = psr.executeQuery();

		if (rsr.next()) {
		    int rID = rsr.getInt("idReminder");
		    long rStart = rsr.getLong("StartTime");
		    int rRepeatInterval = rsr.getInt("RepeatInterval");
		    int rRepeatTime = rsr.getInt("RepeatTime");
		    int rType = rsr.getInt("idReminderType");
		    String msg = rsr.getString("Message");
		    Reminder r = new Reminder(Reminder.MY_URI + rID);
		    r.setReminderType(ReminderType
			    .getReminderTypeByOrder(rType));
		    r.setRepeatInterval(rRepeatInterval);
		    r.setTimesToBeTriggered(rRepeatTime);
		    r.setMessage(msg);
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

	    return allEvents;

	} catch (NumberFormatException nfe) {
	    LogUtils.logError(mcontext, this.getClass(), "getEvents",
		    new Object[] { "Exception trying to get Events!" }, nfe);
	    return new ArrayList<Event>(0);
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "getEvents",
		    new Object[] { "Exception trying to get Events!" }, sqle);
	    return new ArrayList<Event>(0);
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "getEvents",
		    new Object[] { "Exception trying to get Events!" }, e);
	    return new ArrayList<Event>(0);
	}

    }

    /**
     * Removes an event from calendar
     * 
     * @param eventID
     *            An ID of an event
     * @return A calendars URI, null otherwise
     */
    public String removeEvent(int eventID, boolean commit) {
	// String checkEventInCalendarExistance = "SELECT * FROM cal_events ce "
	// + "WHERE ce.calendarID = ? AND ce.eventID = ?";
	try {
	    conn.setAutoCommit(false);
	    PreparedStatement ps;
	    String uri = null;
	    ps = conn
		    .prepareStatement(prop.getProperty("getCalendarFromEvent"));
	    ps.setInt(1, eventID);
	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
		uri = rs.getString(1);
	    } else
		return null;

	    ps = conn.prepareStatement(prop.getProperty("deleteEvent"));
	    ps.setInt(1, eventID);
	    ps.executeUpdate();

	    if (commit) {
		conn.commit();
		conn.setAutoCommit(true);
	    }
	    return uri;

	} catch (NumberFormatException nfe) {
	    LogUtils.logError(mcontext, this.getClass(), "removeEvent",
		    new Object[] { "Exception trying to remove Event!" }, nfe);
	    return null;
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "removeEvent",
		    new Object[] { "Exception trying to remove Event!" }, sqle);
	    return null;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "removeEvent",
		    new Object[] { "Exception trying to remove Event!" }, e);
	    return null;
	}
    }

    /**
     * Updates an event and, if exists, its reminder.
     * 
     * @return true if an event is updated, otherwise false
     */
    public boolean updateEvent(String calendarURI, Event event, boolean commit) {

	try {
	    conn.setAutoCommit(false);
	    PreparedStatement ps;
	    PreparedStatement psr;
	    int rID = 0;
	    ResultSet rs;
	    ps = conn.prepareStatement(prop.getProperty("updateEvent"));
	    if (event.getReminder().getReminderTime() != null) {
		// reminder postoji
		psr = conn
			.prepareStatement("SELECT idReminder FROM event WHERE idEvent=?");
		psr.setInt(1, event.getEventID());
		rs = psr.executeQuery();
		rs.next();
		rID = rs.getInt(1);

		if (rID > 0) {
		    psr = conn
			    .prepareStatement("UPDATE agendaUniversAALDB.Reminder SET idReminderType=?, StartTime=?, RepeatInterval=?"
				    + ", RepeatTime=?,  Message=? WHERE idReminder=?;");
		    psr.setInt(1, event.getReminder().getReminderType().ord());
		    psr.setLong(2, event.getReminder().getReminderTime()
			    .toGregorianCalendar().getTimeInMillis());
		    psr.setInt(3, event.getReminder().getRepeatInterval());
		    psr.setInt(4, event.getReminder().getTimesToBeTriggered());
		    psr.setString(5, event.getReminder().getMessage());
		    psr.setInt(6, rID);
		    psr.execute();
		    conn.commit();

		} else {
		    // adding
		    Reminder rem = event.getReminder();
		    psr = conn.prepareStatement(
			    prop.getProperty("addReminder"),
			    Statement.RETURN_GENERATED_KEYS);

		    psr.setInt(1, event.getReminder().getReminderType().ord());

		    XMLGregorianCalendar ccc = rem.getReminderTime();
		    long i = 0;
		    if (ccc != null) {
			i = ccc.toGregorianCalendar().getTimeInMillis();
			psr.setLong(2, i);
		    } else {
			psr.setNull(2, java.sql.Types.BIGINT);
		    }

		    psr.setInt(4, rem.getTimesToBeTriggered());
		    psr.setString(5, rem.getMessage());

		    psr.setInt(3, rem.getRepeatInterval() * 60000);
		    psr.executeUpdate();
		    conn.commit();
		    rs = psr.getGeneratedKeys();
		    rs.next();
		    rID = rs.getInt(1);

		}

		ps.setInt(1, rID);
	    }

	    else {
		ps.setNull(1, java.sql.Types.INTEGER);
		if (this.cancelReminder(calendarURI, event.getEventID(), true)) {
		    return false;
		}
	    }

	    ps.setString(2, event.getEventDetails().getCategory());
	    ps.setString(3, event.getEventDetails().getDescription());
	    ps.setLong(4, event.getEventDetails().getTimeInterval()
		    .getStartTime().toGregorianCalendar().getTimeInMillis());
	    if (event.getEventDetails().getTimeInterval().getEndTime() != null) {
		ps.setLong(5, event.getEventDetails().getTimeInterval()
			.getEndTime().toGregorianCalendar().getTimeInMillis());
	    } else {
		ps.setNull(5, java.sql.Types.BIGINT);
	    }

	    ps.setString(6, event.getEventDetails().getPlaceName());
	    ps.setInt(7, event.getEventID());

	    ps.executeUpdate();

	    conn.commit();

	    return true;
	} catch (NumberFormatException nfe) {
	    LogUtils.logError(mcontext, this.getClass(), "updateEvent",
		    new Object[] { "Exception trying to update Event!" }, nfe);
	    return false;
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "updateEvent",
		    new Object[] { "Exception trying to update Event!" }, sqle);
	    return false;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "updateEvent",
		    new Object[] { "Exception trying to update Event!" }, e);
	    return false;
	}
    }

    /**
     * Updates a reminder.
     * 
     * @return A reminders ID if succeeded, otherwise 0
     */
    public int updateReminder(String calendarURI, int eventId,
	    Reminder reminder, boolean commit) {

	try {
	    conn.setAutoCommit(false);

	    PreparedStatement psr;
	    psr = conn.prepareStatement(prop.getProperty("updateReminder"),
		    Statement.RETURN_GENERATED_KEYS);
	    psr.setInt(1, eventId);
	    psr.setInt(2, reminder.getReminderType().ord());
	    psr.setLong(3, reminder.getReminderTime().toGregorianCalendar()
		    .getTimeInMillis());
	    psr.setInt(4, reminder.getRepeatInterval());
	    psr.setInt(5, reminder.getTimesToBeTriggered());
	    psr.setString(6, reminder.getMessage());
	    psr.execute();
	    conn.commit();
	    ResultSet rs = psr.getResultSet();
	    rs.next();

	    return rs.getInt(1);

	} catch (NumberFormatException nfe) {
	    LogUtils.logError(mcontext, this.getClass(), "updateReminder",
		    new Object[] { "Exception trying to update reminder!" },
		    nfe);
	    return 0;
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "updateReminder",
		    new Object[] { "Exception trying to update reminder!" },
		    sqle);
	    return 0;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "updateReminder",
		    new Object[] { "Exception trying to update reminder!" }, e);
	    return 0;
	}
    }

    /**
     * Deletes a reminder.
     * 
     * @return true if a reminder is deleted, otherwise false
     */
    public boolean cancelReminder(String calendarURI, int eventId,
	    boolean commit) {
	try {
	    conn.setAutoCommit(false);
	    if (!calendarExists(calendarURI))
		return false;

	    PreparedStatement ps;
	    ps = conn.prepareStatement(prop.getProperty("cancelReminder"));
	    ps.setInt(1, eventId);
	    ps.executeUpdate();
	    if (commit) {
		conn.commit();
	    }
	    return true;

	} catch (NumberFormatException nfe) {
	    LogUtils.logError(mcontext, this.getClass(), "cancelReminder",
		    new Object[] { "Exception trying to cancel reminder!" },
		    nfe);
	    return false;
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "cancelReminder",
		    new Object[] { "Exception trying to cancel reminder!" },
		    sqle);
	    return false;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "cancelReminder",
		    new Object[] { "Exception trying to cancel reminder!" }, e);
	    return false;
	}

    }

    // INFO: sql checked ok, ok
    public boolean updateReminderType(String calendarURI, int eventId,
	    ReminderType reminderType, boolean commit) {
	String updateReminder = "UPDATE reminder SET rReminderType=? "
		+ " WHERE idReminder=?";

	try {
	    conn.setAutoCommit(false);
	    // if (!calendarExists(calendarURI))
	    // return false;

	    // = conn.prepareStatement(checkEventInCalendarExistance);
	    // ps.setInt(1, extractIdFromURI(calendarURI));
	    // ps.setInt(2, eventId);
	    //
	    // ResultSet rs = ps.executeQuery();
	    // if (!rs.next())
	    // throw new Exception("Event does not belong to this calendar");

	    PreparedStatement ps;
	    ps = conn.prepareStatement(updateReminder);
	    ps.setShort(1, (short) reminderType.ord());
	    ps.setInt(2, eventId);
	    ps.setInt(3, extractIdFromURI(calendarURI));
	    ps.executeUpdate();
	    if (commit) {
		conn.commit();
		conn.setAutoCommit(true);
	    }
	    return true;

	} catch (NumberFormatException nfe) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "updateReminderType",
			    new Object[] { "Exception trying to update reminder Type!" },
			    nfe);
	    return false;
	} catch (SQLException sqle) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "updateReminderType",
			    new Object[] { "Exception trying to update reminder Type!" },
			    sqle);
	    ;
	    return false;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "updateReminderType",
			    new Object[] { "Exception trying to update reminder Type!" },
			    e);
	    return false;
	}
    }

    /**
     * Checks if certain calendar exists in a DB.
     * 
     * @param calendarURI
     * @return true if a calendar exists, otherwise false
     */
    public boolean calendarExists(String calendarURI) {
	String calendarExists = "select * from calendartype where idCalendar = ?";

	try {
	    int calendarId = AgendaDB.extractIdFromURI(calendarURI);
	    if (calendarId == -1)
		throw new Exception("Illegal calendar URI format: "
			+ calendarURI);

	    // check for calendar existence
	    PreparedStatement ps = conn.prepareStatement(calendarExists,
		    Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, calendarId);
	    ResultSet result = ps.executeQuery();
	    if (!result.first()) {
		throw new Exception("No such calendar in database: "
			+ calendarURI);
	    }
	} catch (NumberFormatException nfe) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "calendarExists",
			    new Object[] { "Exception trying to check if calendar exists in DB!" },
			    nfe);
	    return false;
	} catch (SQLException sqle) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "calendarExists",
			    new Object[] { "Exception trying to check if calendar exists in DB!" },
			    sqle);
	    return false;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "calendarExists",
			    new Object[] { "Exception trying to check if calendar exists in DB!" },
			    e);
	    return false;
	}
	return true;
    }

    /**
     * Checks if certain calendar exists in a DB.
     * 
     * @param calendarId
     * @return true if a calendar exists, otherwise false
     */
    public boolean calendarExists(int calendarId) {
	String calendarExists = "select * from calendartype where idCalendar = ?";

	try {
	    // check for calendar existence
	    PreparedStatement ps = conn.prepareStatement(calendarExists,
		    Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, calendarId);
	    ResultSet result = ps.executeQuery();
	    if (!result.first()) {
		throw new Exception("No calendar with id = (" + calendarId
			+ ") in database");
	    }
	} catch (SQLException sqle) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "calendarExists",
			    new Object[] { "Exception trying to check if calendar exists in DB!" },
			    sqle);
	    ;
	    return false;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "calendarExists",
			    new Object[] { "Exception trying to check if calendar exists in DB!" },
			    e);
	    return false;
	}
	return true;
    }

    /**
     * Checks for reminders that are due to start in due time which is specified
     * by argument <i>time</i>.
     * 
     * @param time
     *            in seconds
     * @return A <code>List</code> with eventIds (int values)
     */
    public List<Integer> getCurrentReminders(int time) {
	List<Integer> eventList = new ArrayList<Integer>();

	java.util.Calendar c = java.util.Calendar.getInstance();
	long inMillis = c.getTimeInMillis();
	long interval = time;
	try {
	    PreparedStatement ps = conn.prepareStatement(prop
		    .getProperty("getCurrentReminders"));
	    ps.setLong(1, inMillis);
	    System.out.println(inMillis);
	    System.out.println(new Date(inMillis));
	    System.out.println(inMillis + interval);
	    System.out.println(new Date(inMillis + interval));
	    ps.setLong(2, inMillis + interval);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
		int i = rs.getInt(1);
		if (i != 0) {
		    eventList.add(new Integer(i));
		}
	    }
	    LogUtils.logInfo(mcontext, this.getClass(), "getCurrentReminders",
		    new Object[] {
			    "Number of event with forthcomming reminders: ",
			    eventList.size() }, null);

	    return eventList;
	} catch (SQLException sqle) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getCurrentReminders",
			    new Object[] { "Exception trying to get current reminders!" },
			    sqle);
	    return null;
	} catch (Exception e) {
	    LogUtils
		    .logError(
			    mcontext,
			    this.getClass(),
			    "getCurrentReminders",
			    new Object[] { "Exception trying to get current reminders!" },
			    e);
	    return null;
	}
    }

    /**
     * Checks for events that are due to end in due time which is specified by
     * argument <i>time</i>
     * 
     * @param time
     *            defined interval in seconds
     * @return A <code>List</code> with eventIds (int values)
     */
    public List<Integer> getStartingEvents(int time) {
	List<Integer> eventList = new ArrayList<Integer>();
	java.util.Calendar c = java.util.Calendar.getInstance();
	long inMillis = c.getTimeInMillis();

	long interval = time;
	try {
	    PreparedStatement ps = conn.prepareStatement(prop
		    .getProperty("getStartingEvents"));
	    ps.setLong(1, inMillis);
	    System.out.println(inMillis);
	    System.out.println(new Date(inMillis));
	    System.out.println(inMillis + interval);
	    System.out.println(new Date(inMillis + interval));
	    ps.setLong(2, inMillis + interval);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
		int i = rs.getInt(1);
		if (i != 0) {
		    eventList.add(new Integer(i));
		}
	    }
	    LogUtils.logInfo(mcontext, this.getClass(), "getStartingEvents",
		    new Object[] { "Number of forthcomming events: "
			    + eventList.size() }, null);

	    return eventList;
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "getStartingEvents",
		    new Object[] { "Exception!" }, sqle);
	    return null;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "getStartingEvents",
		    new Object[] { "Exception!" }, e);
	    return null;
	}
    }

    /**
     * Checks for events that are due to start in due time which is specified by
     * argument <i>time</i>
     * 
     * @param time
     *            in seconds
     * @return A <code>List</code> with eventIds (int values)
     */
    public List<Integer> getEndingEvents(int time) {
	List<Integer> eventList = new ArrayList<Integer>();

	java.util.Calendar c = java.util.Calendar.getInstance();
	long inMillis = c.getTimeInMillis();
	long interval = time;
	try {
	    PreparedStatement ps = conn.prepareStatement(prop
		    .getProperty("getEndingEvents"));
	    ps.setLong(1, inMillis);
	    System.out.println(inMillis);
	    System.out.println(new Date(inMillis));
	    System.out.println(inMillis + interval);
	    System.out.println(new Date(inMillis + interval));
	    ps.setLong(2, inMillis + interval);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
		int i = rs.getInt(1);
		if (i != 0) {
		    eventList.add(new Integer(i));
		}
	    }
	    LogUtils.logInfo(mcontext, this.getClass(), "getEndingEvents",
		    new Object[] { "Number of finishing events: "
			    + eventList.size() }, null);

	    return eventList;
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(), "getEndingEvents",
		    new Object[] { "Exception !" }, sqle);
	    return null;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(), "getEndingEvents",
		    new Object[] { "Exception !" }, e);
	    return null;
	}
    }

    /**
     * Gets a certain calendar with given name and owner.
     * 
     * @param calendarName
     * @param owner
     * @param commit
     * @return
     */
    public Calendar getCalendarByNameAndOwner(String calendarName, User owner,
	    boolean commit) {
	String calendarExists = "SELECT c.idCalendar FROM calendartype c,users u "
		+ "WHERE c.idUser=u.idUser AND description = ? AND username = ?";
	try {
	    // check for calendar existence
	    PreparedStatement ps = conn.prepareStatement(calendarExists);
	    ps.setString(1, calendarName);
	    ps.setString(2, owner.getURI().substring(owner.getURI().length()));
	    ResultSet result = ps.executeQuery();
	    if (result.first()) {
		int id = result.getInt(1);
		return new Calendar(Calendar.MY_URI + id);
	    } else {
		throw new Exception("No calendar with name = (" + calendarName
			+ ") in database");
	    }
	} catch (SQLException sqle) {
	    LogUtils.logError(mcontext, this.getClass(),
		    "getCalendarByNameAndOwner",
		    new Object[] { "Exception !" }, sqle);
	    return null;
	} catch (Exception e) {
	    LogUtils.logError(mcontext, this.getClass(),
		    "getCalendarByNameAndOwner",
		    new Object[] { "Exception !" }, e);
	    return null;
	}
    }

    // /*****************************
    // * MAIN *
    // *****************************/
    // public static void main(String[] str) {
    // AgendaDB db = new AgendaDB("jdbc:mysql://localhost/universaaldb",
    // "agendauser", "pass");
    // // String query = "select time from reminder";
    //
    // List<Integer> l = db.getCurrentReminders(60 * 60);
    // System.out.println(l);
    // String calendarURI = Calendar.MY_URI + 17;

    // int i = AgendaDB.extractIdFromURI(calendarURI);
    // // db.getAllEvents(calendarURI);
    // System.out.println(i);
    //
    // Address pa = new Address("Thessalia", "Kiprou 21", "b3");
    // pa.setCountryName(new String[] { "Hellas", "Greece" });
    // pa.setExtendedAddress("Neapoli");
    // pa.setPostalCode("41 500");
    // pa.setRegion("Nea politia");
    //
    // Reminder rm = new Reminder(null);
    // rm.setMessage("Hello worlds!");
    // rm.setReminderType(ReminderType.visualMessage);
    // rm.setRepeatTime(10); // after 10min
    // rm.setReminderTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009,
    // 3, 12, 15, 30, 0, 0, 2));
    //
    // TimeInterval ti = new TimeInterval(null);
    // ti.setStartTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009,
    // 3, 12, 18, 00, 0, 0, 2));
    // ti.setEndTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009,
    // 3, 12, 21, 00, 0, 0, 2));
    //
    // EventDetails ed = new EventDetails(null);
    // ed.setCategory("Sports");
    // ed.setPlaceName("Pale de spor");
    // ed.setSpokenLanguage("GR");
    // ed.setAddress(pa);
    // ed.setTimeInterval(ti);
    //
    // Event event = new Event(null);
    // event.setEventDetails(ed);
    // event.setReminder(rm);

    // System.out.println(db.addEventToCalendar(calendarURI, event,
    // AgendaDB.COMMIT));
    // Event e = db.getEventFromCalendar(calendarURI, 41, AgendaDB.COMMIT);
    // e.getEventDetails().setCategory("Aris magic");
    // db.cancelReminder(calendarURI, 71, true);
    // System.out.println(db.updateEvent(calendarURI, e, AgendaDB.COMMIT));
    // System.out.println(db.updateReminderType(calendarURI, 41,
    // ReminderType.blinkingLight, AgendaDB.COMMIT));
    // System.out.println(db.removeEvent(calendarURI, 37, AgendaDB.COMMIT));
    // System.out.println("Calendar: " + calendarURI);
    // List events = db.getAllEvents(calendarURI);
    // System.out.println("Number of events: " + events.size());
    // //for (int i = 0; i < events.size(); ++i)
    // //System.out.println(events.get(i));
    //		
    // System.out.println(db.getEventFromCalendar(calendarURI, 29));
    // System.out.println(newEventId);

    // }

}
