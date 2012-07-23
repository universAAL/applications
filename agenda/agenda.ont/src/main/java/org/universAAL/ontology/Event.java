package org.universAAL.ontology;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * @author kagnantis
 * @author eandgrg
 *
 */
public class Event extends ManagedIndividual {
    public static final String MY_URI = AgendaOntology.NAMESPACE + "event";
    public static final String PROP_HAS_REMINDER= AgendaOntology.NAMESPACE + "hasReminder";
    public static final String PROP_HAS_EVENT_DETAILS= AgendaOntology.NAMESPACE
	+ "hasEventDetails";
    
 // in case event.id <= 0: illegal state change to a valid one
    public static final String PROP_ID= AgendaOntology.NAMESPACE + "id"; 
 // because events are published on context bus, we have to know for  what reason.
    public static final String PROP_CE_TYPE= AgendaOntology.NAMESPACE + "ceType"; 
    public static final String PROP_PERSISTENT= AgendaOntology.NAMESPACE + "isPersistent";;
    public static final String PROP_HAS_PARENT_CALENDAR= AgendaOntology.NAMESPACE + "hasBelongsTo";
    public static final String PROP_VISIBLE= AgendaOntology.NAMESPACE + "visible";

    
    //commented when transferring to new data rep (1.1.0)
//    public static String[] getStandardPropertyURIs() {
//	String[] inherited = ManagedIndividual.getStandardPropertyURIs();
//	String[] toReturn = new String[inherited.length + 7];
//	int i = 0;
//	while (i < inherited.length) {
//	    toReturn[i] = inherited[i];
//	    i++;
//	}
//	toReturn[i++] = PROP_HAS_REMINDER;
//	toReturn[i++] = PROP_HAS_EVENT_DETAILS;
//	toReturn[i++] = PROP_ID;
//	toReturn[i++] = PROP_CE_TYPE;
//	toReturn[i++] = PROP_PERSISTENT;
//	toReturn[i++] = PROP_VISIBLE;
//	toReturn[i++] = PROP_HAS_PARENT_CALENDAR;
//
//	return toReturn;
//    }

    public Event() {
	super();
    }

    public Event(String uri) {
	super(uri);
    }

    public Event(String uri, int eventID, EventDetails event) {
	super(uri);
	if (event == null)
	    throw new IllegalArgumentException();

	props.put(PROP_HAS_EVENT_DETAILS, event);
	// if (eventID > 1)
	props.put(PROP_ID, new Integer(eventID));
    }

    public Event(String uri, EventDetails event) {
	super(uri);
	if (event == null)
	    throw new IllegalArgumentException();

	props.put(PROP_HAS_EVENT_DETAILS, event);
    }

    /*
     * Event Details
     */
    public EventDetails getEventDetails() {
	return (EventDetails) props.get(PROP_HAS_EVENT_DETAILS);
    }

    public void setEventDetails(EventDetails event) {
	if (event == null)
	    return;

	props.put(PROP_HAS_EVENT_DETAILS, event);
    }

    /*
     * Context Event Type
     */
    public CEType getCEType() {
	return (CEType) props.get(PROP_CE_TYPE);
    }

    // A client does not need to define this property
    // it is just used by the server, to mark an event before it send it
    // to the context bus
    public void setCEType(CEType ceType) {
	if (ceType == null)
	    return;
	props.put(PROP_CE_TYPE, ceType);
    }

    /*
     * EventID
     */
    public int getEventID() {
	Integer i = (Integer) props.get(PROP_ID);
	if (i == null)
	    return -1;

	return i.intValue();
    }

    public void setEventID(int id) {
	props.put(PROP_ID, new Integer(id));
    }

    /*
     * Event Persistence
     */
    public boolean isPersistent() {
	Boolean b = (Boolean) props.get(PROP_PERSISTENT);
	if (b == null)
	    return false;

	return b.booleanValue();
    }

    // if we don't want the user to be able to delete this event
    // we just set this property to true
    public void setPersistent(boolean b) {
	props.put(PROP_PERSISTENT, new Boolean(b));
    }

    /*
     * Event Visibility
     */
    public boolean isVisible() {
	Boolean b = (Boolean) props.get(PROP_VISIBLE);
	if (b == null)
	    return true;

	return b.booleanValue();
    }

    // if this property is true, then the user will be able
    // to see the event in his/her calendar gui
    public void setVisible(boolean b) {
	props.put(PROP_VISIBLE, new Boolean(b));
    }

    /*
     * Belongs to Calendar
     */
    public Calendar getParentCalendar() {
	Calendar c = (Calendar) props.get(PROP_HAS_PARENT_CALENDAR);

	return c.shallowCopy();
    }

    public void setParentCalendar(Calendar c) {
	if (c == null)
	    return;
	props.put(PROP_HAS_PARENT_CALENDAR, c.shallowCopy());
    }

    /*
     * Reminder
     */
    public Reminder getReminder() {
	return (Reminder) props.get(PROP_HAS_REMINDER);
    }

    public void setReminder(Reminder reminder) {
	if (reminder == null)
	    return;

	props.put(PROP_HAS_REMINDER, reminder);
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return props.containsKey(PROP_HAS_EVENT_DETAILS)
		|| props.containsKey(PROP_HAS_REMINDER);
    }

    public String toString() {
	return this.toString("\n");
    }

    public String toString(String fieldSeperator) {
	StringBuffer s = new StringBuffer(200);

	s.append("Event: " + this.getLocalName() + fieldSeperator);
	s.append("\tID:\t" + this.getEventID() + fieldSeperator);
	s.append("\tBelongs to: " + this.getParentCalendar() + fieldSeperator);
	s.append("\tPersistent:\t" + (this.isPersistent() ? "yes" : "no")
		+ fieldSeperator);
	s.append("\tVisible:\t" + (this.isVisible() ? "yes" : "no")
		+ fieldSeperator);

	EventDetails ed = this.getEventDetails();
	s.append("\n\t");
	if (ed == null) {
	    s.append("EventDetails:\t Not specified" + fieldSeperator);
	} else {
	    s.append(ed.toString("\n\t"));
	}
	s.append("\n\t");
	Reminder rm = this.getReminder();
	if (rm == null) {
	    s.append("Reminder:\t Not specified" + fieldSeperator);
	} else {
	    s.append(rm.toString("\n\t"));
	}

	return s.toString();
    }
    
    /* (non-Javadoc)
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }

}
