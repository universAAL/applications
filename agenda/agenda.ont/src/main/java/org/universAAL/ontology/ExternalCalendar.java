package org.universAAL.ontology;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class ExternalCalendar extends ManagedIndividual {

    /**  */
    public static final String MY_URI = AgendaOntology.NAMESPACE
	    + "externalCalendar";
    
    public ExternalCalendar(String uri) {
	super(uri);
	// setName(name);
    }
    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
     * (java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }
}
