package org.universAAL.ontology.agendaEventSelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.agenda.Event;
import org.universAAL.ontology.agenda.EventDetails;
import org.universAAL.ontology.agenda.TimeInterval;
import org.universAAL.middleware.rdf.TypeMapper;

public class EventComparator implements Comparator {

    public int compare(Object event1, Object event2) {
	try {
	    XMLGregorianCalendar x1 = ((Event) event1).getEventDetails()
		    .getTimeInterval().getStartTime();
	    XMLGregorianCalendar x2 = ((Event) event2).getEventDetails()
		    .getTimeInterval().getStartTime();
	    return x1.compare(x2);
	} catch (NullPointerException npe) {
	    return 0;
	}

    }

    /**
     * For testing.
     * @param str main method arguments
     */
    public static void main(String[] str) {
	XMLGregorianCalendar event1 = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2008, 11, 3, 23, 59, 59, 0, +2);
	XMLGregorianCalendar event2 = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2008, 10, 1, 23, 59, 59, 0, +2);
	XMLGregorianCalendar event3 = TypeMapper.getDataTypeFactory()
		.newXMLGregorianCalendar(2008, 12, 3, 23, 59, 59, 0, +2);

	ArrayList list = new ArrayList(3);
	Event e1 = new Event();
	EventDetails ed1 = new EventDetails();
	TimeInterval ti1 = new TimeInterval();
	ti1.setStartTime(event1);
	ed1.setTimeInterval(ti1);
	e1.setEventDetails(ed1);

	Event e2 = new Event();
	EventDetails ed2 = new EventDetails();
	TimeInterval ti2 = new TimeInterval();
	ti2.setStartTime(event2);
	ed2.setTimeInterval(ti2);
	e2.setEventDetails(ed2);

	Event e3 = new Event();
	EventDetails ed3 = new EventDetails();
	TimeInterval ti3 = new TimeInterval();
	ti3.setStartTime(event3);
	ed3.setTimeInterval(ti3);
	e3.setEventDetails(ed3);

	list.add(e1);
	list.add(e2);
	list.add(e3);

	System.out.println("Before sort");
	for (int i = 0; i < 3; ++i) {
	    System.out.println(((Event) list.get(i)).getEventDetails()
		    .getTimeInterval().getStartTime());
	}

	System.out.println("After sort");
	Collections.sort(list, new EventComparator());
	for (int i = 0; i < 3; ++i) {
	    System.out.println(((Event) list.get(i)).getEventDetails()
		    .getTimeInterval().getStartTime());
	}

    }

}
