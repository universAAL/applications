package org.universAAL.ontology.agenda.service;

//import org.universAAL.pmd.ont.DummyInputDevice;
//import org.universAAL.pmd.ont.DummyOutputDevice;

import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;

/**
 * @author kagnantis
 * 
 */
public class MobileDevice extends ManagedIndividual { // implements
    // DummyInputDevice,
    // DummyOutputDevice {
    public static final String PMD_NAMESPACE = "http://ontology.universAAL.org/PMD.owl#";
    
    public static final String MY_URI = PMD_NAMESPACE + "MobileDevice";


     public MobileDevice() {
	super();
    }

    public MobileDevice(String uri) {
	super(uri);
    }

    // TODO: to be revised
    public int getPropSerializationType(String propURI) {
	// return PROP_HAS_CALENDAR_ENTRY.equals(propURI) ?
	// PROP_SERIALIZATION_REDUCED : PROP_SERIALIZATION_FULL;

	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true;
    }
    
    public String getClassURI() {
	return MY_URI;
    }

}
