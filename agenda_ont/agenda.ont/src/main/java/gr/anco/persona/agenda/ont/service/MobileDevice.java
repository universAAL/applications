package gr.anco.persona.agenda.ont.service;

//import gr.anco.persona.pmd.ont.DummyInputDevice;
//import gr.anco.persona.pmd.ont.DummyOutputDevice;

import org.persona.ontology.ManagedIndividual;
import org.persona.ontology.expr.Restriction;

/**
* @author kagnantis
*
*/
public class MobileDevice extends ManagedIndividual { //implements DummyInputDevice, DummyOutputDevice {
	public static final String PMD_NAMESPACE = "http://ontology.persona.anco.gr/PMD.owl#";
	public static final String MY_URI;
	
	static {
		MY_URI = PMD_NAMESPACE + "MobileDevice";

		register(MobileDevice.class);
	}
	
	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		return ManagedIndividual.getClassRestrictionsOnProperty(propURI); 
	}
	
	public static String getRDFSComment() {
		return "The class of a Mobile device.";
	}
	
	public static String getRDFSLabel() {
		return "Mobile Device";
	}
	
	public MobileDevice() {
		super();
	}
	
	public MobileDevice(String uri) {
		super(uri);
	}
	
	//TODO: to be revised
	public int getPropSerializationType(String propURI) {
		//return PROP_HAS_CALENDAR_ENTRY.equals(propURI) ? PROP_SERIALIZATION_REDUCED : PROP_SERIALIZATION_FULL;
		
		return PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}

}
