package org.universAAL.ontology;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.profile.UserProfile;

/**
 * @author kagnantis
 * @author eandgrg
 *
 */
public class Reminder extends ManagedIndividual {

    public static final String MY_URI = AgendaOntology.NAMESPACE + "Reminder";
    public static final String PROP_HAS_TIME = AgendaOntology.NAMESPACE
	    + "hasReminderTime";
    public static final String PROP_HAS_TYPE = AgendaOntology.NAMESPACE
	    + "hasReminderType";
    public static final String PROP_HAS_USER_PROFILE = AgendaOntology.NAMESPACE
	    + "hasUserPofile";// optional
    public static final String PROP_MESSAGE = AgendaOntology.NAMESPACE
	    + "message"; // optional
    public static final String PROP_TRIGGER_TIMES = AgendaOntology.NAMESPACE
	    + "triggerTimes"; // optional
    public static final String PROP_REPEAT_INTERVAL = AgendaOntology.NAMESPACE
	    + "repeatInterval"; // optional

    // commented when transferring to new data rep (1.1.0)
    // public static String[] getStandardPropertyURIs() {
    // String[] inherited = ManagedIndividual.getStandardPropertyURIs();
    // String[] toReturn = new String[inherited.length + 8];
    // int i = 0;
    // while (i < inherited.length) {
    // toReturn[i] = inherited[i];
    // i++;
    // }
    // toReturn[i++] = PROP_HAS_TYPE;
    // toReturn[i++] = PROP_HAS_TIME;
    // toReturn[i++] = PROP_HAS_USER_PROFILE;
    // toReturn[i++] = PROP_MESSAGE;
    // toReturn[i++] = PROP_TRIGGER_TIMES;
    // toReturn[i++] = PROP_REPEAT_INTERVAL;
    //
    // return toReturn;
    // }

    /**
     * empty constructor.
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

    /**
     * 
     * 
     * @param uri
     * @param time
     */
    public Reminder(String uri, XMLGregorianCalendar time) {
	this(uri);
	if (time == null)
	    throw new IllegalArgumentException();

	props.put(PROP_HAS_TIME, time);
    }

    /**
     * 
     * 
     * @param uri
     * @param time
     * @param type
     */
    public Reminder(String uri, XMLGregorianCalendar time, ReminderType type) {
	this(uri, time);

	props.put(PROP_HAS_TYPE, type);
    }

    /**
     * 
     * 
     * @param uri
     * @param message
     */
    public Reminder(String uri, String message) {
	this(uri);

	props.put(PROP_HAS_TIME, message);
    }

    /*
     * Reminder Type: how the user will be informed by a reminder
     */
    /**
     * 
     * 
     * @return
     */
    public ReminderType getReminderType() {
	return (ReminderType) props.get(PROP_HAS_TYPE);
    }

    /*
     * Reminder time: when to trigger the reminder
     */
    /**
     * 
     * 
     * @return
     */
    public XMLGregorianCalendar getReminderTime() {
	return (XMLGregorianCalendar) props.get(PROP_HAS_TIME);
    }

    /*
     * NEVER used
     */
    /**
     * 
     * 
     * @return
     */
    public UserProfile getUserProfile() {
	return (UserProfile) props.get(PROP_HAS_USER_PROFILE);
    }

    /*
     * The message of the reminder
     */
    /**
     * 
     * 
     * @return
     */
    public String getMessage() {
	return (String) props.get(PROP_MESSAGE);
    }

    /*
     * How many times will the reminder be re-triggered
     */
    /**
     * 
     * 
     * @return
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
    /**
     * 
     * 
     * @return
     */
    public int getRepeatInterval() {
	Object o = props.get(PROP_REPEAT_INTERVAL);
	if (o != null) {
	    return ((Integer) o).intValue();
	}
	return 0;
    }

    /**
     * 
     * 
     * @param time
     */
    public void setReminderTime(XMLGregorianCalendar time) {
	if (time != null)
	    props.put(PROP_HAS_TIME, time);
    }

    /**
     * 
     * 
     * @param type
     */
    public void setReminderType(ReminderType type) {
	if (type == null) {
	    props.remove(PROP_HAS_TYPE);
	} else {
	    props.put(PROP_HAS_TYPE, type);
	}
    }

    /**
     * 
     * 
     * @param userProfile
     */
    public void setUserProfile(UserProfile userProfile) {
	if (userProfile != null)
	    props.put(PROP_HAS_USER_PROFILE, userProfile);
    }

    /**
     * 
     * 
     * @param message
     */
    public void setMessage(String message) {
	if (message == null)
	    return;
	props.put(PROP_MESSAGE, message);
    }

    /**
     * 
     * 
     * @param timesToTrigger
     */
    public void setTimesToBeTriggered(int timesToTrigger) {
	if (timesToTrigger > 0)
	    props.put(PROP_TRIGGER_TIMES, new Integer(timesToTrigger));
    }

    /**
     * 
     * 
     * @param repeatInterval
     */
    public void setRepeatInterval(int repeatInterval) {
	if (repeatInterval > 0)
	    props.put(PROP_REPEAT_INTERVAL, new Integer(repeatInterval));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
     * (java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	// return PROP_HAS_REMINDER_TYPE.equals(propURI) ?
	// PROP_SERIALIZATION_OPTIONAL : PROP_SERIALIZATION_FULL;
	return PROP_SERIALIZATION_FULL;
    }

    // These are the requirements for well-forming (mandatory keys)
    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#isWellFormed()
     */
    public boolean isWellFormed() {
	return props.containsKey(PROP_HAS_TIME);
    }

    /**
     * 
     * 
     * @param fieldSeperator
     * @return
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.rdf.Resource#toString()
     */
    public String toString() {
	return this.toString("\n");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }
}
