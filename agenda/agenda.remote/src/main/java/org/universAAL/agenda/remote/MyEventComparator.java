package org.universAAL.agenda.remote;

import java.util.Comparator;

import org.universAAL.agenda.ont.Event;

public class MyEventComparator implements Comparator<Event> {

	public int compare(Event e1, Event e2) {
		
		long first = e1.getEventDetails().getTimeInterval().getStartTime().toGregorianCalendar().getTimeInMillis();
		long second = e2.getEventDetails().getTimeInterval().getStartTime().toGregorianCalendar().getTimeInMillis();
		
		if(first==second)
			return 0;
		else if(first<second)
			return -1;
		else
			return 1;
	}

}
