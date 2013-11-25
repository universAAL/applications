/**
 * 
 */
package org.universAAL.ontology.agenda.service;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.agenda.AgendaOntology;

/**
 * @author kagnantis
 * @author eandgrg
 *
 */
public class CalendarAgenda extends Service {

    public static final String MY_URI = AgendaOntology.NAMESPACE
	    + "CalendarService";
    public static final String PROP_CONTROLS = AgendaOntology.NAMESPACE
	    + "controls";
    public static final String PROP_CONNECTED_TO = AgendaOntology.NAMESPACE
	    + "connectedTo";
    public static int MAX_NUMBER_OF_EXTERNAL_CALENDARS = 10;
    
    /**
     * 
     * 
     * @param uri
     */
    public CalendarAgenda(String uri) {
	super(uri);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.service.owl.Service#getPropSerializationType
     * (java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_CONTROLS.equals(propURI) ? PROP_SERIALIZATION_FULL
		: PROP_SERIALIZATION_OPTIONAL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#isWellFormed()
     */
    public boolean isWellFormed() {
	return true;
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
