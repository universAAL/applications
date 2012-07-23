/**
 * 
 */
package org.universAAL.ontology.service;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.AgendaOntology;

/**
 * @author kagnantis
 * @author eandgrg
 *
 */
public class CalendarUIService extends Service {
    
    /**  */
    public static final String MY_URI= AgendaOntology.NAMESPACE + "CalendarUIService";
    
    /**  */
    public static final String PROP_CONTROLS= AgendaOntology.NAMESPACE + "controls";

    /**
     * 
     *
     * @param uri 
     */
    public CalendarUIService(String uri) {
	super(uri);
    }


    /* (non-Javadoc)
     * @see org.universAAL.middleware.service.owl.Service#getPropSerializationType(java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
    }

    /* (non-Javadoc)
     * @see org.universAAL.middleware.owl.ManagedIndividual#isWellFormed()
     */
    public boolean isWellFormed() {
	return true;
    }
    
    /* (non-Javadoc)
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }
}
