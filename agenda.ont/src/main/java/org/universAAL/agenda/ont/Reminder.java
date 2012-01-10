package org.universAAL.agenda.ont;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.profile.UserProfile;

/**
 * @author kagnantis
 * 
 */
public class Reminder extends ManagedIndividual {
    // Declare static vars for describing that class. It is required!
    public static final String MY_URI;
    public static final String PROP_HAS_TIME; // required
    public static final String PROP_HAS_TYPE; // optional
    public static final String PROP_HAS_USER_PROFILE; // optional
    public static final String PROP_MESSAGE; // optional
    public static final String PROP_TRIGGER_TIMES; // optional
    public static final String PROP_REPEAT_INTERVAL; // optional

    static {
	MY_URI = Calendar.CALENDAR_NAMESPACE + "Reminder";
	PROP_HAS_TYPE = Calendar.CALENDAR_NAMESPACE + "hasReminderType";
	PROP_HAS_TIME = Calendar.CALENDAR_NAMESPACE + "hasReminderTime";
	PROP_HAS_USER_PROFILE = Calendar.CALENDAR_NAMESPACE + "hasUserPofile";
	PROP_MESSAGE = Calendar.CALENDAR_NAMESPACE + "message";
	PROP_TRIGGER_TIMES = Calendar.CALENDAR_NAMESPACE + "triggerTimes";
	PROP_REPEAT_INTERVAL = Calendar.CALENDAR_NAMESPACE + "repeatInterval";
	register(Reminder.class); // Do registration now
    }

    // This function declares the data type & cardinality of each attribute
    public static Restriction getClassRestrictionsOnProperty(String propURI) {
	if (PROP_HAS_TYPE.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    ReminderType.MY_URI, 1, 0);
	if (PROP_HAS_TIME.equals(propURI))
	    return Restriction
		    .getAllValuesRestrictionWithCardinality(propURI, TypeMapper
			    .getDatatypeURI(XMLGregorianCalendar.class), 1, 1);
	if (PROP_HAS_USER_PROFILE.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    UserProfile.MY_URI, 1, 0);
	if (PROP_MESSAGE.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(String.class), 1, 0);
	if (PROP_TRIGGER_TIMES.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(Integer.class), 1, 0);
	if (PROP_REPEAT_INTERVAL.equals(propURI))
	    return Restriction.getAllValuesRestrictionWithCardinality(propURI,
		    TypeMapper.getDatatypeURI(Integer.class), 1, 0);
	return ManagedIndividual.getClassRestrictionsOnProperty(propURI);
    }

    public static String[] getStandardPropertyURIs() {
	String[] inherited = ManagedIndividual.getStandardPropertyURIs();
	String[] toReturn = new String[inherited.length + 8];
	int i = 0;
	while (i < inherited.length) {
	    toReturn[i] = inherited[i];
	    i++;
	}
	toReturn[i++] = PROP_HAS_TYPE;
	toReturn[i++] = PROP_HAS_TIME;
	toReturn[i++] = PROP_HAS_USER_PROFILE;
	toReturn[i++] = PROP_MESSAGE;
	toReturn[i++] = PROP_TRIGGER_TIMES;
	toReturn[i++] = PROP_REPEAT_INTERVAL;

	return toReturn;
    }

    /**
     * A short description of the class
     * 
     * @return the description
     */
    public static String getRDFSComment() {
	return "The class of a reminder.";
    }

    /**
     * a label for the class
     * 
     * @return a label
     */
    public static String getRDFSLabel() {
	return "Reminder";
    }

    /**
     * empty constructor
     */
    public Reminder() {
	super();
    }

    /**
     * 
     * @param uri
     */
    public Reminder(String uri) {
	super(uri);
    }

    public Reminder(String uri, XMLGregorianCalendar time) {
	this(uri);
	if (time == null)
	    throw new IllegalArgumentException();

	props.put(PROP_HAS_TIME, time);
    }

    public Reminder(String uri, XMLGregorianCalendar time, ReminderType type) {
	this(uri, time);

	props.put(PROP_HAS_TYPE, type);
    }

    public Reminder(String uri, String message) {
	this(uri);

	props.put(PROP_HAS_TIME, message);
    }

    /*
     * Reminder Type: how the user will be informed by a reminder
     */
    public ReminderType getReminderType() {
	return (ReminderType) props.get(PROP_HAS_TYPE);
    }

    /*
     * Reminder time: when to trigger the reminder
     */
    public XMLGregorianCalendar getReminderTime() {
	return (XMLGregorianCalendar) props.get(PROP_HAS_TIME);
    }

    /*
     * NEVER used
     */
    public UserProfile getUserProfile() {
	return (UserProfile) props.get(PROP_HAS_USER_PROFILE);
    }

    /*
     * The message of the reminder
     */
    public String getMessage() {
	return (String) props.get(PROP_MESSAGE);
    }

    /*
     * How many times will the reminder be re-triggered
     */
    public int getTimesToBeTriggered() {
	Object o = props.get(PROP_TRIGGER_TIMES);
	if (o != null) {
	    return ((Integer) o).intValue();
	}
	return 0;
    }

    /*
     * Return the time (in milliseconds) that will be passed before the reminder
     * will be re-triggered
     */
    public int getRepeatInterval() {
	Object o = props.get(PROP_REPEAT_INTERVAL);
	if (o != null) {
	    return ((Integer) o).intValue();
	}
	return 0;
    }

    public void setReminderTime(XMLGregorianCalendar time) {
	if (time != null)
	    props.put(PROP_HAS_TIME, time);
    }

    public void setReminderType(ReminderType type) {
	if (type == null) {
	    props.remove(PROP_HAS_TYPE);
	} else {
	    props.put(PROP_HAS_TYPE, type);
	}
    }

    public void setUserProfile(UserProfile userProfile) {
	if (userProfile != null)
	    props.put(PROP_HAS_USER_PROFILE, userProfile);
    }

    public void setMessage(String message) {
	if (message == null)
	    return;
	props.put(PROP_MESSAGE, message);
    }

    public void setTimesToBeTriggered(int timesToTrigger) {
	if (timesToTrigger > 0)
	    props.put(PROP_TRIGGER_TIMES, new Integer(timesToTrigger));
    }

    public void setRepeatInterval(int repeatInterval) {
	if (repeatInterval > 0)
	    props.put(PROP_REPEAT_INTERVAL, new Integer(repeatInterval));
    }

    public int getPropSerializationType(String propURI) {
	// return PROP_HAS_REMINDER_TYPE.equals(propURI) ?
	// PROP_SERIALIZATION_OPTIONAL : PROP_SERIALIZATION_FULL;
	return PROP_SERIALIZATION_FULL;
    }

    // These are the requirements for well-forming (mandatory keys)
    public boolean isWellFormed() {
	return props.containsKey(PROP_HAS_TIME);
    }

    public String toString(String fieldSeperator) {
	StringBuffer s = new StringBuffer(60);

	s.append("Reminder:\t" + fieldSeperator);
	s.append("\tMessage :\t" + this.getMessage() + fieldSeperator);
	if (this.getReminderTime() != null)
	    s.append("\tTime    :\t" + this.getReminderTime().toString()
		    + fieldSeperator);
	s.append("\tType    :\t" + this.getReminderType());

	return s.toString();
    }

    public String toString() {
	return this.toString("\n");
    }
}
