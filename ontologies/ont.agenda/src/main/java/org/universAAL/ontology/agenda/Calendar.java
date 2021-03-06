package org.universAAL.ontology.agenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.profile.User;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class Calendar extends ManagedIndividual {
    public static final String MY_URI = AgendaOntology.NAMESPACE + "Calendar";
    public static final String PROP_HAS_EVENT = AgendaOntology.NAMESPACE
	    + "hasEvent";
    public static final String PROP_HAS_OWNER = AgendaOntology.NAMESPACE
	    + "hasOwner";
    public static final String PROP_NAME = AgendaOntology.NAMESPACE + "name";

    // eventcounter
    private int eventCounter = 0;

    // zakomentirano kod prijelaza na 1.1.0
    // public static String[] getStandardPropertyURIs() {
    // String[] inherited = ManagedIndividual.getStandardPropertyURIs();
    // String[] toReturn = new String[inherited.length + 2];
    // int i = 0;
    // while (i < inherited.length) {
    // toReturn[i] = inherited[i];
    // i++;
    // }
    // toReturn[i++] = PROP_HAS_EVENT;
    // toReturn[i++] = PROP_HAS_OWNER;
    //
    // return toReturn;
    // }

    public Calendar(String uri) {
	super(uri);
	// setName(name);
    }

    public Calendar() {
	super();
    }

    public Calendar(String uri, String name) {
	super(uri);
	setName(name);
    }

    // public Calendar(String uri, User owner){
    // super(uri);
    //		
    // if (owner == null)
    // throw new IllegalArgumentException();
    //		
    // props.put(PROP_HAS_OWNER, owner);
    // }

    // the owner of the calendar
    // just his/her uri, wrapped in a User object
    public User getOwner() {
	return (User) props.get(PROP_HAS_OWNER);
    }

    public void setOwner(User owner) {
	if (owner == null)
	    return;

	props.put(PROP_HAS_OWNER, owner);
    }

    // the name of the calendar
    public String getName() {
	return (String) props.get(PROP_NAME);
    }

    public void setName(String name) {
	if (name == null)
	    return;

	props.put(PROP_NAME, name);
    }

    public int getPropSerializationType(String propURI) {
	// return PROP_HAS_CALENDAR_ENTRY.equals(propURI) ?
	// PROP_SERIALIZATION_REDUCED : PROP_SERIALIZATION_FULL;

	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true; // props.containsKey(PROP_HAS_OWNER);
    }

    public int addEvent(Event i) {
	List l = (List) props.get(PROP_HAS_EVENT);
	int id = 0;
	if (l == null) {
	    l = new ArrayList();
	}
	if (i.getEventID() <= 0) {
	    id = generateUniqueEventId();
	    i.setEventID(id);
	}
	l.add(i);
	props.put(PROP_HAS_EVENT, l);
	// List l2 = (List) props.get(PROP_HAS_EVENT);
	return id;
    }

    public List getEventList() {
	List l = (List) props.get(PROP_HAS_EVENT);
	if (l == null) {
	    l = new ArrayList();
	    props.put(PROP_HAS_EVENT, l);
	}

	return new ArrayList(l);
    }

    public Event[] getEventArray() {
	List l = (List) props.get(PROP_HAS_EVENT);
	return (l == null) ? null : (Event[]) l.toArray(new Event[l.size()]);
    }

    public void removeEvent(Event i) {
	List l = (List) props.get(PROP_HAS_EVENT);
	if (l != null)
	    l.remove(i);
    }

    // return the event with id = eventID
    public Event getEvent(int eventID) {
	List l = (List) props.get(PROP_HAS_EVENT);
	if (l == null)
	    return null;
	for (Iterator it = l.iterator(); it.hasNext();) {
	    Event e = (Event) it.next();
	    if (e.getEventID() == eventID) {
		return e; // TODO: may need to return a copy of this
	    }
	}
	return null;
    }

    public void updateEvent(int eventID, Event event) {
	deleteEvent(eventID);
	addEvent(event);
    }

    public boolean deleteEvent(int eventID) {
	List l = (List) props.get(PROP_HAS_EVENT);
	if (l == null)

	    return false;

	for (Iterator it = l.iterator(); it.hasNext();) {
	    Event e = (Event) it.next();
	    if (e.getEventID() == eventID) {
		it.remove();
		return true; // TODO: may need to return a copy of this
	    }
	}
	return false;
    }

    public void setEventList(List items) {
	if (items != null) {
	    boolean valid = true;
	    for (Iterator i = items.iterator(); valid && i.hasNext();)
		if (i.next() instanceof Event) {
		    Event ev = (Event) i.next();
		    if (ev.getEventID() <= 0)
			ev.setEventID(generateUniqueEventId());
		} else {
		    valid = false;
		    break;
		}

	    if (valid)
		props.put(PROP_HAS_EVENT, items);
	}
    }

    public void setItems(Event[] items) {
	if (items != null && items.length > 0) {
	    for (int i = 0; i < items.length; ++i)
		if (items[i].getEventID() <= 0)
		    items[i].setEventID(generateUniqueEventId());

	    props.put(PROP_HAS_EVENT, Arrays.asList(items));
	}
    }

    // TODO: change this method with a new one, using a linked list to watch id
    // removals.
    /**
     * This method is used to generate a unique <i>id</i> for an event. It uses
     * a counter which is incremented in every method call. To avoid overflow
     * the counter is re-initialized when it's necessary.
     * 
     * @return a unique id for an event
     */
    private int generateUniqueEventId() {
	if (eventCounter == Integer.MAX_VALUE) {
	    eventCounter = 0;
	}
	++eventCounter;
	return eventCounter;
    }

    public String toString() {
	return this.getName();
    }

    // shallow copy, just the url and the name of the calendar
    public Calendar shallowCopy() {
	return new Calendar(this.getURI(), this.getName());
    }

    public boolean equals(Object that) {
	if (!(that instanceof Calendar))
	    return false;

	if (this.getURI().equals(((Calendar) that).getURI()))
	    return true;

	return false;
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
