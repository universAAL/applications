/**
 * 
 */
package org.universAAL.ontology.agenda.service;

import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.agenda.Calendar;

/**
 * @author kagnantis
 * 
 */
public class CalendarAgenda extends Service {
    public static final String MY_URI;
    public static final String PROP_CONTROLS;
    public static final String PROP_CONNECTED_TO;
    private static int MAX_NUMBER_OF_EXTERNAL_CALENDARS = 10;

    private static Hashtable calendarRestrictions = new Hashtable(1);
    static {
	MY_URI = Calendar.CALENDAR_NAMESPACE + "CalendarService";
	PROP_CONTROLS = Calendar.CALENDAR_NAMESPACE + "controls";
	PROP_CONNECTED_TO = Calendar.CALENDAR_NAMESPACE + "connectedTo";
	register(CalendarAgenda.class);

	addRestriction(Restriction.getAllValuesRestriction(PROP_CONTROLS,
		Calendar.MY_URI), new String[] { PROP_CONTROLS },
		calendarRestrictions);
	addRestriction(Restriction.getAllValuesRestrictionWithCardinality(
		PROP_CONNECTED_TO, ExternalCalendar.MY_URI,
		MAX_NUMBER_OF_EXTERNAL_CALENDARS, 1),
		new String[] { PROP_CONNECTED_TO }, calendarRestrictions);
    }

    public static Restriction getClassRestrictionsOnProperty(String propURI) {
	if (propURI == null)
	    return null;
	Object r = calendarRestrictions.get(propURI);
	if (r instanceof Restriction)
	    return (Restriction) r;
	return Service.getClassRestrictionsOnProperty(propURI);
    }

    public static String getRDFSComment() {
	return "The class of services controling the MobileDevice/Personal Agenda.";
    }

    public static String getRDFSLabel() {
	return "MobileDevice/Personal Agenda";
    }

    public CalendarAgenda(String uri) {
	super(uri);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.persona.ontology.Service#getClassLevelRestrictions()
     */
    protected Hashtable getClassLevelRestrictions() {
	return calendarRestrictions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.persona.ontology.ManagedIndividual#getPropSerializationType(java.
     * lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_CONTROLS.equals(propURI) ? PROP_SERIALIZATION_FULL
		: PROP_SERIALIZATION_OPTIONAL;
    }

    public boolean isWellFormed() {
	return true;
    }
}
