package org.universAAL.agenda.server.unit_impl;


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
import javax.xml.datatype.XMLGregorianCalendar;
import org.universAAL.ontology.location.address.Address;
import org.universAAL.ontology.location.outdoor.City;
import org.universAAL.ontology.location.outdoor.CityPlace;
import org.universAAL.ontology.location.outdoor.Country;
import org.universAAL.ontology.location.outdoor.Region;

import org.osgi.service.log.LogService;
import org.universAAL.agenda.ont.*;
import org.universAAL.agenda.server.Activator;
import org.universAAL.agenda.server.database.AgendaDBInterface;
import org.universAAL.middleware.rdf.TypeMapper;

import org.universAAL.ontology.profile.User;

public class MyAgenda implements AgendaDBInterface {
	private static final String DEFAULT_INTERMIDIATE_CALENDAR_NAME = "qwoeiptetfngbclvkjhsfdshf3284jhdf";
	// private static final boolean DEBUG_DB = false;//Set to true to refill the
	// DB every time (for debugging)
	private String DB_URL;
	private String DB_USER;
	private String DB_PWD;

	private Connection conn;
	private Object theLock;

	public static final boolean COMMIT = true;
	public static final boolean DO_NOT_COMMIT = false;

	public MyAgenda(String url, String user, String pwd) {
		this.DB_URL = url;
		this.DB_USER = user;
		this.DB_PWD = pwd;
		this.theLock = new Object();
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
			Activator.log.log(LogService.LOG_ERROR, "Exception trying to get connection to database: " + e);
		}
	}

	private void initDB() throws SQLException {

	}

	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			Activator.log.log(LogService.LOG_ERROR, "Exception trying to close connection to database: " + e);
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
	public String[] getCalURIs() {
		String query = "select calendarID " + "from calendar";
		ResultSet result;
		try {
			PreparedStatement getAllCalendars = conn.prepareStatement(query);
			result = getAllCalendars.executeQuery();
			List calendars = new ArrayList();
			while (result.next()) {
				calendars.add(Calendar.MY_URI + result.getString(1));
			}

			String[] ids = new String[calendars.size()];
			for (int i = 0; i < calendars.size(); i++)
				ids[i] = (String) calendars.get(i);
			return ids;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String[0];
	}
	
	// new version: using db
	public List/*<Calendar>*/ getCalendarsWithInfo() {
		String query = "SELECT calendarID, name, owner FROM calendar";
		ResultSet result;
		try {
			PreparedStatement getAllCalendars = conn.prepareStatement(query);
			result = getAllCalendars.executeQuery();
			List calendars = new ArrayList();
			Calendar c;
			while (result.next()) {
				String id = result.getString(1);
				String name = result.getString(2);
				String ownerURI = result.getString(3);
				c = new Calendar(Calendar.MY_URI+id, name);
				c.setOwner(new User(ownerURI));
				calendars.add(c);
			}
			return calendars;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList(0);
	}
	
	// new version: using db
	public List/*<Calendar>*/ getCalendarsWithInfo(User owner) {
		String query = "SELECT calendarID, name FROM calendar WHERE owner = ?";
		ResultSet result;
		try {
			PreparedStatement getAllCalendars = conn.prepareStatement(query);
			getAllCalendars.setString(1, owner.getURI()); //owner.getURI());
			System.out.println("QUERY: " + getAllCalendars.toString());
			result = getAllCalendars.executeQuery();
			List calendars = new ArrayList();
			Calendar c;
			while (result.next()) {
				String id = result.getString(1);
				String name = result.getString(2);
				c = new Calendar(Calendar.MY_URI+id, name);
				c.setOwner(owner);
				calendars.add(c);
			}
			return calendars;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList(0);
	}

	// new version: using db
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

	// sql ckeck: ok
	public Calendar addCalendar(Calendar c, User owner, boolean commit) throws InvalidParameterException {
		Calendar newCal = null;
		String query = "insert into calendar(name, owner) values (?, ?)";
		String updateName = "UPDATE calendar SET name = ? WHERE name = '" + DEFAULT_INTERMIDIATE_CALENDAR_NAME + "'";
		if (owner == null) {
			owner = c.getOwner();
			if (owner == null) {
				Activator.log.log(LogService.LOG_WARNING, "You must specify an owner for the calendar");
				throw new InvalidParameterException("You must specify an owner for the new calendar");
			}
		}
		boolean updateCalendarName = false;
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			String name = c.getName();
			if (name == null) {
				name = DEFAULT_INTERMIDIATE_CALENDAR_NAME;
				updateCalendarName = true;
			}
		
			ps.setString(1, name);
			ps.setString(2, owner.getURI());
			ps.executeUpdate();
			ResultSet key = ps.getGeneratedKeys();
			if (key.next()) {
				int id = key.getInt(1);
				if (updateCalendarName){
					ps = conn.prepareStatement(updateName);
					name = "calendar " + id;
					ps.setString(1, "calendar " + name);
					int rows = ps.executeUpdate();
					if (rows < 1) {
						System.out.println("DEBUG: Calendar name has not been updated");
						return null;
					}
				}
				newCal = new Calendar(Calendar.MY_URI + id);
				newCal.setName(name);
				newCal.setOwner(owner);
			} else {
				return null;
			}
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
		return newCal;
	}
	
	// sql ckeck: ok ,ok
	public boolean removeCalendar(Calendar c, boolean commit) {
//		String removeAssosiatedEvents = "DELETE FROM extendedEvent " +
//										"WHERE eventID IN " +
//										"	(SELECT ce.eventID " +
//										"		FROM calendar c, cal_events ce " +
//										"		WHERE c.calendarID=ce.calendarID AND c.name=? " +
//										"	) ";
		String removeCalendar = "DELETE FROM calendar WHERE (name = ? AND owner = ?) OR (calendarID = ?)";
		
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(removeCalendar);
			ps.setString(1, c.getName());
			User owner = c.getOwner();
			if (owner != null)
				ps.setString(2, owner.getURI());
			else
				ps.setNull(2,java.sql.Types.VARCHAR);

			int calID = extractIdFromURI(c.getURI());
			ps.setInt(3, calID);
			int rows = ps.executeUpdate();
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
			if (rows == 0) {
				Activator.log.log(LogService.LOG_INFO, "No such calendar");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// sql ckeck: ok
	public List getAllEventCategories(boolean commit) {
		List allCategories = new ArrayList();
		String query = "SELECT distinct eCategory FROM extendedevent";
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				String cat = result.getString(1);
				if (cat != null) {
					allCategories.add(cat);
				} else {
					System.err.println("WTF?");
				}
			}
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList(0);
		}
		//Collections.sort(allCategories);
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
		String query = "select owner from calendar where calendarID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			int id = MyAgenda.extractIdFromURI(calendarURI);
			if (id == -1)
				throw new NumberFormatException();
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			if (result.next()) {
				String name = result.getString("name");
				String owner = result.getString("owner");
				return new String[] { name, owner };
			}

		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_ERROR, nfe.getMessage());
		} catch (SQLException e) {
			Activator.log.log(LogService.LOG_ERROR, e.getMessage());
		}

		return null;
	}

	public static int extractIdFromURI(String calendarURI) {
		int id = -1;
		if (calendarURI.startsWith(Calendar.MY_URI)) {
			try {
				id = new Integer(calendarURI.substring(Calendar.MY_URI.length())).intValue();
			} catch (NumberFormatException nfe) {
				return -1;
			}
		}
		return id;
	}

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

	public static XMLGregorianCalendar convertSQLTimeStamp2XMLGregCalendar(java.sql.Timestamp timeStamp) {
		if (timeStamp == null)
			return null;

		return TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(timeStamp.getYear(), timeStamp.getMonth(),
				timeStamp.getDay(), timeStamp.getHours(), timeStamp.getMinutes(), timeStamp.getSeconds(), 0, 0);
	}

	// /INFO: check sql ok, ok
	public int addEventToCalendar(String calendarURI, Event event, boolean commit) {
		
		System.err.println("-> pocetak addEventToCalendar");
		boolean hasExistingEventID = false;
		int eventId = 0; // foreign key: cal2event -> event
		String addEvent;
		if (event.getEventID() > 0) {
			eventId = event.getEventID();
			hasExistingEventID = true;
			addEvent = "insert into extendedEvent(eCategory, ePlace, eSpokenLang, eStartTime, eDbStartTime, eEndTime, eDbEndTime, eDescription, "
				+ "rMessage, rTime, rDbTime, rRepeatTimes, rReminderType, "
				+ "aCountryName1, aExtAddress, aLocality, aPostalCode, aRegion, aStreetName, aBuilding, isPersistent, parentID, rInterval, isVisible, eventID) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		} else {
			addEvent = "insert into extendedEvent(eCategory, ePlace, eSpokenLang, eStartTime, eDbStartTime, eEndTime, eDbEndTime, eDescription, "
				+ "rMessage, rTime, rDbTime, rRepeatTimes, rReminderType, "
				+ "aCountryName1, aExtAddress, aLocality, aPostalCode, aRegion, aStreetName, aBuilding, isPersistent, parentID, rInterval, isVisible) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		//String addEvent2Calendar = "insert into cal_events(calendarID, eventID) values (?, ?)";

		
		int calendarId = 0;
		try {
			
			System.err.println("-> pocetak try bloka");
			
			calendarId = MyAgenda.extractIdFromURI(calendarURI);
			if (calendarId == -1)
				throw new Exception("Illegal calendar URI format: " + calendarURI);

			// check for calendar existence
			if (!calendarExists(calendarId)) {
				throw new Exception("No such calendar in database: " + calendarURI);
			}

			PreparedStatement ps;
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(addEvent, Statement.RETURN_GENERATED_KEYS);
			
			System.err.println("-> nakon prepare statement");
			// add event 1-8
			EventDetails ed = event.getEventDetails();
			if (ed != null) {
				ps.setString(1, ed.getCategory());
				ps.setString(2, ed.getPlaceName());
				ps.setString(3, ed.getSpokenLanguage());
				if (ed.getTimeInterval() != null) {
					XMLGregorianCalendar start = ed.getTimeInterval().getStartTime();
					if (start != null) {
						long i = start.toGregorianCalendar().getTimeInMillis();
						ps.setString(4, MyAgenda.convertXMLGregCalendar2String(start));
						ps.setLong(5, i);
					} else {
						ps.setNull(4, java.sql.Types.DATE);
						ps.setNull(5, java.sql.Types.BIGINT);
					}
					XMLGregorianCalendar end = ed.getTimeInterval().getEndTime();
					if (end != null) {
						long i = end.toGregorianCalendar().getTimeInMillis();
						ps.setString(6, MyAgenda.convertXMLGregCalendar2String(end));
						ps.setLong(7, i);
					} else {
						ps.setNull(6, java.sql.Types.DATE);
						ps.setNull(7, java.sql.Types.BIGINT);
					}
				} else {
					ps.setNull(4, java.sql.Types.DATE);
					ps.setNull(5, java.sql.Types.BIGINT);
					ps.setNull(6, java.sql.Types.DATE);
					ps.setNull(7, java.sql.Types.BIGINT);
				}
				
				ps.setString(8, ed.getDescription());
			} else {
				ps.setNull(1, java.sql.Types.VARCHAR);
				ps.setNull(2, java.sql.Types.VARCHAR);
				ps.setNull(3, java.sql.Types.VARCHAR);
				ps.setNull(4, java.sql.Types.DATE);
				ps.setNull(5, java.sql.Types.BIGINT);
				ps.setNull(6, java.sql.Types.DATE);
				ps.setNull(7, java.sql.Types.BIGINT);
				ps.setNull(8, java.sql.Types.VARCHAR);
			}

			System.err.println("-> nakon add event");
			// add reminder 9-13
			Reminder rem = event.getReminder();
			if (rem != null) {
				ps.setString(9, rem.getMessage());

				XMLGregorianCalendar ccc = rem.getReminderTime();
				long i = 0;
				if (ccc != null) {
					i = ccc.toGregorianCalendar().getTimeInMillis();
					ps.setString(10, MyAgenda.convertXMLGregCalendar2String(rem.getReminderTime()));
					ps.setLong(11, i);
				} else {
					ps.setNull(10, java.sql.Types.DATE);
					ps.setNull(11, java.sql.Types.BIGINT);
				}

				ps.setInt(12, rem.getTimesToBeTriggered());
				if (rem.getReminderType() == null) {
					ps.setNull(13, java.sql.Types.SMALLINT);
				} else {
					ps.setShort(13, (short) rem.getReminderType().ord());
				}
				ps.setInt(23, rem.getRepeatInterval());
			} else {
				ps.setNull(9, java.sql.Types.VARCHAR);
				ps.setNull(10, java.sql.Types.DATE);
				ps.setNull(11, java.sql.Types.BIGINT);
				ps.setNull(12, java.sql.Types.INTEGER);
				ps.setNull(13, java.sql.Types.SMALLINT);
				ps.setInt(23, 0);
			}

			System.err.println("-> nakon add remainder");
			// add address 14 - 20
			Address address = null; //= event.getEventDetails().getAddress();
//			if (address != null) {
//				String[] countryNames = address.getCountryName();
//				if (countryNames != null) {
//					ps.setString(14, address.getCountryName()[0]);
//				} else {
//					ps.setNull(14, java.sql.Types.VARCHAR);
//				}
//				ps.setString(15, address.getExtendedAddress());
//				ps.setString(16, address.getLocality());
//				ps.setString(17, address.getPostalCode());
//				ps.setString(18, address.getRegion());
//				if (address.getStreetAddress() == null) {
//					ps.setNull(19, java.sql.Types.VARCHAR);
//					ps.setNull(20, java.sql.Types.VARCHAR);
//				} else {
//					ps.setString(19, address.getStreetAddress().streetName);
//					ps.setString(20, address.getStreetAddress().buildingIdentifier);
//				}
			System.err.println("-> prije null alsjdlaksjl");
			if (address != null) {
				System.err.println("-> prije String[] countryNames ");
				String[] countryNames = new String[] {address.getCountry().toString()};
				System.err.println("-> nakon String[] countryNames ");
				if (countryNames != null) {
					System.err.println("-> prije 14a");
					ps.setString(14, address.getCountry().toString());
					System.err.println("-> nakon 14a");
				} else {
					ps.setNull(14, java.sql.Types.VARCHAR);
				}
				System.err.println("-> nakon 14b");
				ps.setString(15, address.getCity().toString());
				System.err.println("-> nakon 15");
				ps.setString(16, address.getLocalName());
				System.err.println("-> nakon 16");
				ps.setString(17, address.getCityRegion().toString());
				System.err.println("-> nakon 17");
				ps.setString(18, address.getState().toString());
				System.err.println("-> proba ");
				
				System.err.println("-> nakon 18 " + address.getCityPlace().toString());
				if (address.getCityPlace() == null) {
					System.err.println("-> prije 19a");
					ps.setNull(19, java.sql.Types.VARCHAR);
					System.err.println("-> nakon 19a");
					ps.setNull(20, java.sql.Types.VARCHAR);
					System.err.println("-> nakon 20a");
				} else {
					System.err.println("-> prije 19b");
					ps.setString(19, address.getCityPlace().toString());
					System.err.println("-> nakon 19b");
					ps.setString(20, "1");
					System.err.println("-> nakon 20b");
				}
			} else {
				ps.setNull(14, java.sql.Types.VARCHAR);
				ps.setNull(15, java.sql.Types.VARCHAR);
				ps.setNull(16, java.sql.Types.VARCHAR);
				ps.setNull(17, java.sql.Types.VARCHAR);
				ps.setNull(18, java.sql.Types.VARCHAR);
				ps.setNull(19, java.sql.Types.VARCHAR);
				ps.setNull(20, java.sql.Types.VARCHAR);
			}
			
			System.err.println("-> prije castanja");
			
			ps.setBoolean(21, event.isPersistent());
			//ps.setInt(21, event.isPersistent() ? 1 : 0);
			
			ps.setInt(22, calendarId);
			ps.setBoolean(24, event.isVisible());
			//ps.setInt(24, event.isVisible() ? 1 : 0);
			
			System.err.println("-> nakon castanja");
			
			if (hasExistingEventID) {
				ps.setInt(25, eventId);
				ps.executeUpdate();
			} else {
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) 
					eventId = rs.getInt(1);
				else
					throw new Exception("You cannot add the event to the calendar");
			}

			System.err.println("-> nakon add address");
			// add record to cal2event
//			ps = conn.prepareStatement(addEvent2Calendar);
//			ps.setInt(1, calendarId);
//			ps.setInt(2, eventId);
//			ps.executeUpdate();
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
		} catch (NumberFormatException nfe) {
			System.err.println("->NumberFormatException nfe");
			Activator.log.log(LogService.LOG_ERROR, nfe.getMessage());
			return -1;
		} catch (SQLException sqle) {
			System.err.println("->SQLException sqle");
			Activator.log.log(LogService.LOG_ERROR, sqle.getMessage());
			return -1;
		} catch (Exception e) {
			System.err.println("->Exception e");
			Activator.log.log(LogService.LOG_ERROR, e.getMessage());
			return -1;
		}

		System.err.println("-> kraj addEventToCalendar");
		return eventId;
	}


	// INFO: check sql ok
	public Event getEventFromCalendar(String calendarURI, int eventID, boolean commit) {
		List event = getEvents(calendarURI, eventID, commit);
		if (event.size() > 1) {
			Activator.log.log(LogService.LOG_WARNING, "More than one events with the same id: " + eventID);
		}
		if (event.size() == 0)
			return null;
		return (Event) event.get(0);
	}

	// INFO: check sql ok
	public List getAllEvents(String calendarURI, boolean commit) {
		return getEvents(calendarURI, -1, commit);
	}

	// INFO: check sql ok, ok
	private List getEvents(String calendarURI, int eventID, boolean commit) {
		List allEvents = new ArrayList();
		String calendarCriteria = "";
		String eventCriteria = "";
		if (calendarURI != null) {
			// get events from a specific calendars
			calendarCriteria = " AND e.parentID = ? ";
		}
		if (eventID > 0) {
			// get Specific event
			eventCriteria = " AND e.eventID = ? ";
		}

		String getEvents = "SELECT e.eventID, e.eCategory, e.ePlace, e.eSpokenLang, e.eDbStartTime, e.eDbEndTime, e.eDescription, "
				+ "e.rMessage, e.rDbTime, e.rRepeatTimes, e.rReminderType, "
				+ "e.aCountryName1, e.aExtAddress, e.aLocality, e.aPostalCode, e.aRegion, e.aStreetName, e.aBuilding, e.isPersistent, e.parentID, e.rInterval, e.isVisible "
				+ "FROM extendedEvent e " + "WHERE true " + calendarCriteria + eventCriteria;

		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(getEvents);
			if (calendarURI != null) {
				int calendarId = MyAgenda.extractIdFromURI(calendarURI);
				if (calendarId == -1)
					throw new Exception("Illegal calendar URI format: " + calendarURI);

				// check for calendar existence
				if (!calendarExists(calendarURI))
					return new ArrayList(0);

				ps.setInt(1, calendarId);
			}
			if (eventID > 0) {
				if (calendarURI != null)
					ps.setInt(2, eventID);
				else
					ps.setInt(1, eventID);
			}
			conn.setAutoCommit(false);
			ResultSet result = ps.executeQuery();
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}

			while (result.next()) {
				int eID = result.getInt(1);
				String eCategory = result.getString(2);
				String ePlace = result.getString(3);
				String eSL = result.getString(4);
				long eStartTime = result.getLong(5);
				long eEndTime = result.getLong(6);
				String eDescription = result.getString(7);
				String rMessage = result.getString(8);
				long rTime = result.getLong(9);
				int rRepeat = result.getInt(10);
				int rType = result.getInt(11);
				String aCountry = result.getString(12);
				String aExtAddress = result.getString(13);
				String aLocality = result.getString(14);
				String aPostalCode = result.getString(15);
				String aRegion = result.getString(16);
				String aStreet = result.getString(17);
				String aBuilding = result.getString(18);
				boolean isPersistent = result.getBoolean(19);
				Calendar parent = new Calendar(Calendar.MY_URI + result.getInt(20));
				int rInterval = result.getInt(21);
				boolean isVisible = result.getBoolean(22);
				Event e = new Event(Event.MY_URI + eID);
				e.setEventID(eID);
				e.setPersistent(isPersistent);
				e.setVisible(isVisible);
				//cached value
				//e.setParentCalendar(new Calendar(calendarURI));
				e.setParentCalendar(parent);
				

				EventDetails ed = new EventDetails(EventDetails.MY_URI + eID);
				boolean storeEDobject = false;
				if (eCategory != null) {
					ed.setCategory(eCategory);
					storeEDobject = true;
				}
				if (eDescription != null) {
					ed.setDescription(eDescription);
					storeEDobject = true;
				}
				if (ePlace != null) {
					ed.setPlaceName(ePlace);
					storeEDobject = true;
				}
				if (eSL != null) {
					ed.setSpokenLanguage(eSL);
					storeEDobject = true;
				}

				TimeInterval ti = new TimeInterval(TimeInterval.MY_URI + eID);
				boolean storeTIobject = false;
				if (eStartTime != 0) {
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTimeInMillis(eStartTime);
					ti.setStartTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(gc));
					storeTIobject = true;
				}
				if (eEndTime != 0) {
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTimeInMillis(eEndTime);
					ti.setEndTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(gc));
					storeTIobject = true;
				}
				if (storeTIobject) {
					ed.setTimeInterval(ti);
					storeEDobject = true;
				}

//				if (aLocality != null) {
//					Address address = new Address(Address.MY_URI + eID, aLocality, aStreet,
//							aBuilding);
//
//					address.setStreetAddress(aStreet, aBuilding);
//					address.setExtendedAddress(aExtAddress);
//					address.setPostalCode(aPostalCode);
//					address.setRegion(aRegion);
//					address.setCountryName(new String[] { aCountry });
//
//					ed.setAddress(address);
//				}
				if (aLocality != null) {
					Address address = new Address(Address.MY_URI);
					City city= new City(aLocality);
					address.setCity(city);
					CityPlace cityplace=new CityPlace(aStreet);
					address.setCityPlace(cityplace);
					address.setExtAddress(aExtAddress);
					//address.setPostalCode(aPostalCode);
					Region region= new Region(aRegion);
					address.setRegion(region);
					Country country= new Country(aCountry);
					address.setCountry(country);

					
					ed.setAddress(address);
				}
				

				boolean storeReminderObject = false;
				Reminder reminder = new Reminder(Reminder.MY_URI + eID);
				if (rTime != 0) {
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTimeInMillis(rTime);
					reminder.setReminderTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(gc));
					storeReminderObject = true;
					reminder.setTimesToBeTriggered(rRepeat);
					reminder.setReminderType(ReminderType.getReminderTypeByOrder(rType));
					reminder.setRepeatInterval(rInterval);
				}
				if (rMessage != null) {
					reminder.setMessage(rMessage);
					storeReminderObject = true;
				}

				if (storeEDobject) {
					e.setEventDetails(ed);
				}
				if (storeReminderObject) {
					e.setReminder(reminder);
				}
				allEvents.add(e);
			}

		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_ERROR, nfe.getMessage());
			return new ArrayList(0);
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_ERROR, sqle.getMessage());
			return new ArrayList(0);
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_ERROR, e.getMessage());
			return new ArrayList(0);
		}
		return allEvents;
	}

	// INFO: sql checked ok, ok
	public boolean removeEvent(String calendarURI, int eventID, boolean commit) {
//		String checkEventInCalendarExistance = "SELECT * FROM cal_events ce " + "WHERE ce.calendarID = ? AND ce.eventID = ?";
		String removeEvent = "DELETE FROM extendedEvent WHERE eventID = ?"/* AND parentID = ? */  + " AND isPersistent = 0";

		try {
			conn.setAutoCommit(false);
			if (!calendarExists(calendarURI))
				return false;

//			PreparedStatement ps = conn.prepareStatement(checkEventInCalendarExistance);
//			ps.setInt(1, extractIdFromURI(calendarURI));
//			ps.setInt(2, eventID);
//
//			ResultSet rs = ps.executeQuery();
//			if (!rs.next())
//				throw new Exception("Event does not belong to this calendar");

			PreparedStatement ps = conn.prepareStatement(removeEvent);
			ps.setInt(1, eventID);
			System.out.println("ddd: " + ps.toString());
//			int calendarID = extractIdFromURI(calendarURI);
//			ps.setInt(2, calendarID);
			int eventDeleted = ps.executeUpdate();
			if (eventDeleted == 0) {
				throw new Exception("You can't delete this event");
			}
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
			return true;

		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_ERROR, nfe.getMessage());
			return false;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_ERROR, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_ERROR, e.getMessage());
			return false;
		}
	}

	// INFO: sql checked ok, ok
	public boolean updateEvent(String calendarURI, Event event, boolean commit) {
		try {
			boolean isRemoved = removeEvent(calendarURI, event.getEventID(), MyAgenda.DO_NOT_COMMIT);
			int i = -1;
			if (isRemoved)
				i = addEventToCalendar(calendarURI, event, MyAgenda.DO_NOT_COMMIT);
			else
				return false;

			if (i <= 0) {
				return false;
			}
			if (commit)
				conn.commit();
			return true;

		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_WARNING, nfe.getMessage());
			return false;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return false;
		}
	}

	// INFO: sql checked ok, ok
	public boolean updateReminder(String calendarURI, int eventId, Reminder reminder, boolean commit) {
		//String checkEventInCalendarExistance = "SELECT * FROM cal_events ce " + "WHERE ce.calendarID = ? AND ce.eventID = ?";
		String updateReminder = "UPDATE extendedEvent SET rMessage=?, rTime=?, rDbtime=?, rRepeatTimes=?, rReminderType=?, rInterval=? "
				+ " WHERE eventId = ? AND parentID = ?" + eventId;

		try {
			conn.setAutoCommit(false);
			if (!calendarExists(calendarURI))
				return false;

//			= conn.prepareStatement(checkEventInCalendarExistance);
//			ps.setInt(1, extractIdFromURI(calendarURI));
//			ps.setInt(2, eventId);
//
//			ResultSet rs = ps.executeQuery();
//			if (!rs.next())
//				throw new Exception("Event does not belong to this calendar");

			PreparedStatement ps;
			ps = conn.prepareStatement(updateReminder);
			ps.setString(1, reminder.getMessage());
			ps.setString(2, convertXMLGregCalendar2String(reminder.getReminderTime()));
			ps.setLong(3, reminder.getReminderTime().toGregorianCalendar().getTimeInMillis());
			ps.setInt(4, reminder.getTimesToBeTriggered());
			ps.setShort(5, (short) reminder.getReminderType().ord());
			ps.setInt(6, reminder.getRepeatInterval());
			ps.setInt(7, eventId);
			ps.setInt(8, extractIdFromURI(calendarURI));
			ps.executeUpdate();
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
			return true;

		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_WARNING, nfe.getMessage());
			return false;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return false;
		}
	}

	// INFO: sql checked ok, ok
	public boolean cancelReminder(String calendarURI, int eventId, boolean commit) {
		//String checkEventInCalendarExistance = "SELECT * FROM cal_events ce " + "WHERE ce.calendarID = ? AND ce.eventID = ?";
		String cancelReminder = "UPDATE extendedEvent SET rTime=?, rDbtime=? " + " WHERE eventId = ? AND parentID = ?";

		try {
			conn.setAutoCommit(false);
			if (!calendarExists(calendarURI))
				return false;

//			= conn.prepareStatement(checkEventInCalendarExistance);
//			ps.setInt(1, extractIdFromURI(calendarURI));
//			ps.setInt(2, eventId);
//
//			ResultSet rs = ps.executeQuery();
//			if (!rs.next())
//				throw new Exception("Event does not belong to this calendar");

			PreparedStatement ps;
			ps = conn.prepareStatement(cancelReminder);
			ps.setNull(1, java.sql.Types.TIMESTAMP);
			ps.setNull(2, java.sql.Types.BIGINT);
			ps.setInt(3, eventId);
			ps.setInt(4, extractIdFromURI(calendarURI));
			ps.executeUpdate();
			if (commit) {
				conn.commit();
				conn.setAutoCommit(true);
			}
			return true;

		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_WARNING, nfe.getMessage());
			return false;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return false;
		}

	}
	
	// INFO: sql checked ok, ok
	public boolean updateReminderType(String calendarURI, int eventId, ReminderType reminderType, boolean commit) {
		//String checkEventInCalendarExistance = "SELECT * FROM cal_events ce " + " WHERE ce.calendarID = ? AND ce.eventID = ?";
		String updateReminder = "UPDATE extendedEvent SET rReminderType=? " + " WHERE eventId = ? AND parentID = ?";

		try {
			conn.setAutoCommit(false);
			if (!calendarExists(calendarURI))
				return false;

//			= conn.prepareStatement(checkEventInCalendarExistance);
//			ps.setInt(1, extractIdFromURI(calendarURI));
//			ps.setInt(2, eventId);
//
//			ResultSet rs = ps.executeQuery();
//			if (!rs.next())
//				throw new Exception("Event does not belong to this calendar");

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
			Activator.log.log(LogService.LOG_WARNING, nfe.getMessage());
			return false;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return false;
		}
	}

	// INFO: sql checked ok, ok
	public boolean calendarExists(String calendarURI) {
		String calendarExists = "select * from calendar where calendarID = ?";

		try {
			int calendarId = MyAgenda.extractIdFromURI(calendarURI);
			if (calendarId == -1)
				throw new Exception("Illegal calendar URI format: " + calendarURI);

			// check for calendar existence
			PreparedStatement ps = conn.prepareStatement(calendarExists, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, calendarId);
			ResultSet result = ps.executeQuery();
			if (!result.first()) {
				throw new Exception("No such calendar in database: " + calendarURI);
			}
		} catch (NumberFormatException nfe) {
			Activator.log.log(LogService.LOG_WARNING, nfe.getMessage());
			return false;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return false;
		}
		return true;
	}

	public boolean calendarExists(int calendarId) {
		String calendarExists = "select * from calendar where calendarID = ?";

		try {
			// check for calendar existence
			PreparedStatement ps = conn.prepareStatement(calendarExists, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, calendarId);
			ResultSet result = ps.executeQuery();
			if (!result.first()) {
				throw new Exception("No calendar with id = (" + calendarId + ") in database");
			}
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return false;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param time
	 *            in seconds
	 * @return A <code>List</code> with eventIds (int values)
	 */
	public List getCurrentReminders(int time) {
		List eventList = new ArrayList();
		String query = "SELECT e.eventID " + " FROM extendedEvent e " + " WHERE e.rDbTime >= ? AND e.rDbTime < ?";

		java.util.Calendar c = java.util.Calendar.getInstance();
		long inMillis = c.getTimeInMillis();
		long interval = time;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, inMillis);
			System.out.println(inMillis);
			System.out.println(new Date(inMillis));
			System.out.println(inMillis + interval);
			System.out.println(new Date(inMillis+interval));
			ps.setLong(2, inMillis + interval);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = rs.getInt(1);
				if (i != 0)  { eventList.add(new Integer(i)); }
			}
			
			Activator.log.log(LogService.LOG_INFO, "Number of event with forthcomming reminders: " + eventList.size());
			return eventList;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return null;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Vraæa listu eventID remindera koji poèinju u odreðenom intervalu (sad+time).
	 * @param time unaprijed definirani interval
	 *            in seconds 
	 * @return A <code>List</code> with eventIds (int values)
	 */
	public List getStartingEvents(int time) {
		List eventList = new ArrayList();
		String query = "SELECT e.eventID " + " FROM extendedEvent e " + " WHERE e.eDbStartTime >= ? AND e.eDbStartTime < ?";

		java.util.Calendar c = java.util.Calendar.getInstance();
		long inMillis = c.getTimeInMillis();
		
		System.err.println("Trenutno vrijeme: " + inMillis);
		long interval = time;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, inMillis);
			System.out.println(inMillis);
			System.out.println(new Date(inMillis));
			System.out.println(inMillis + interval);
			System.out.println(new Date(inMillis+interval));
			ps.setLong(2, inMillis + interval);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = rs.getInt(1);
				if (i != 0)  { eventList.add(new Integer(i)); }
			}
			
			Activator.log.log(LogService.LOG_INFO, "Number of forthcomming events: " + eventList.size());
			return eventList;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return null;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param time
	 *            in seconds
	 * @return A <code>List</code> with eventIds (int values)
	 */
	public List getEndingEvents(int time) {
		List eventList = new ArrayList();
		String query = "SELECT e.eventID " + " FROM extendedEvent e " + " WHERE e.eDbEndTime >= ? AND e.eDbEndTime < ?";

		java.util.Calendar c = java.util.Calendar.getInstance();
		long inMillis = c.getTimeInMillis();
		long interval = time;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setLong(1, inMillis);
			System.out.println(inMillis);
			System.out.println(new Date(inMillis));
			System.out.println(inMillis + interval);
			System.out.println(new Date(inMillis+interval));
			ps.setLong(2, inMillis + interval);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = rs.getInt(1);
				if (i != 0)  { eventList.add(new Integer(i)); }
			}
			
			Activator.log.log(LogService.LOG_INFO, "Number of finishing events: " + eventList.size());
			return eventList;
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return null;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return null;
		}
	}

	public Calendar getCalendarByNameAndOwner(String calendarName, User owner, boolean commit) {
		String calendarExists = "select calendarID from calendar where name = ? AND owner = ?";

		try {
			// check for calendar existence
			PreparedStatement ps = conn.prepareStatement(calendarExists);
			ps.setString(1, calendarName);
			ps.setString(2, owner.getURI());
			ResultSet result = ps.executeQuery();
			if (result.first()) {
				int id = result.getInt(1);
				return new Calendar(Calendar.MY_URI + id);
			} else {
				throw new Exception("No calendar with name = (" + calendarName + ") in database");
			}
		} catch (SQLException sqle) {
			Activator.log.log(LogService.LOG_WARNING, sqle.getMessage());
			return null;
		} catch (Exception e) {
			Activator.log.log(LogService.LOG_WARNING, e.getMessage());
			return null;
		}
	}

	
	/*****************************
	 * MAIN *
	 *****************************/
	public static void main(String[] str) {
		MyAgenda db = new MyAgenda("jdbc:mysql://localhost/agenda_reminder", "root", "sc2011");
		// String query = "select time from reminder";

		List l = db.getCurrentReminders(60*60);
		System.out.println(l);
		String calendarURI = Calendar.MY_URI + 17;
//		int i = MyAgenda.extractIdFromURI(calendarURI);
//		// db.getAllEvents(calendarURI);
//		System.out.println(i);
//
//		Address pa = new Address("Thessalia", "Kiprou 21", "b3");
//		pa.setCountryName(new String[] { "Hellas", "Greece" });
//		pa.setExtendedAddress("Neapoli");
//		pa.setPostalCode("41 500");
//		pa.setRegion("Nea politia");
//
//		Reminder rm = new Reminder(null);
//		rm.setMessage("Hello worlds!");
//		rm.setReminderType(ReminderType.visualMessage);
//		rm.setRepeatTime(10); // after 10min
//		rm.setReminderTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009, 3, 12, 15, 30, 0, 0, 2));
//
//		TimeInterval ti = new TimeInterval(null);
//		ti.setStartTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009, 3, 12, 18, 00, 0, 0, 2));
//		ti.setEndTime(TypeMapper.getDataTypeFactory().newXMLGregorianCalendar(2009, 3, 12, 21, 00, 0, 0, 2));
//
//		EventDetails ed = new EventDetails(null);
//		ed.setCategory("Sports");
//		ed.setPlaceName("Pale de spor");
//		ed.setSpokenLanguage("GR");
//		ed.setAddress(pa);
//		ed.setTimeInterval(ti);
//
//		Event event = new Event(null);
//		event.setEventDetails(ed);
		// event.setReminder(rm);

		// System.out.println(db.addEventToCalendar(calendarURI, event,
		// MyAgenda.COMMIT));
		// Event e = db.getEventFromCalendar(calendarURI, 41, MyAgenda.COMMIT);
		// e.getEventDetails().setCategory("Aris magic");
		 db.cancelReminder(calendarURI, 71, true);
		// System.out.println(db.updateEvent(calendarURI, e, MyAgenda.COMMIT));
//		System.out.println(db.updateReminderType(calendarURI, 41, ReminderType.blinkingLight, MyAgenda.COMMIT));
		// System.out.println(db.removeEvent(calendarURI, 37, MyAgenda.COMMIT));
		// System.out.println("Calendar: " + calendarURI);
		// List events = db.getAllEvents(calendarURI);
		// System.out.println("Number of events: " + events.size());
		// //for (int i = 0; i < events.size(); ++i)
		// //System.out.println(events.get(i));
		//		
		// System.out.println(db.getEventFromCalendar(calendarURI, 29));
		// System.out.println(newEventId);

	}


}
