package org.universAAL.ontology.agendaEventSelection.service;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.agendaEventSelection.AgendaEventSelectionOntology;

public class EventSelectionToolService extends Service {
    public static final String MY_URI = AgendaEventSelectionOntology.NAMESPACE
	    + "EventSelectionToolService";
    public static final String PROP_CONTROLS = AgendaEventSelectionOntology.NAMESPACE
	    + "controls";
    
    
    public EventSelectionToolService(String uri) {
	super(uri);
    }

    /* (non-Javadoc)
     * @see org.universAAL.middleware.service.owl.Service#getPropSerializationType(java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_CONTROLS.equals(propURI) ? PROP_SERIALIZATION_FULL
		: PROP_SERIALIZATION_OPTIONAL;
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
