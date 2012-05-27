package org.universaal.ontology.health.owl;

import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.Caregiver;
import org.universaal.ontology.disease.owl.Disease;
import javax.xml.datatype.XMLGregorianCalendar;

public class Diet extends HealthyHabitsAdoption{

	public static final String MY_URI = HealthOntology.NAMESPACE
	+ "Diet";

	public Diet () {
		super();
	}

	public Diet (String uri) {
		super(uri);
	}

	public Diet( AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, String description, XMLGregorianCalendar stDt, Disease disease){
		super(assistedPerson, caregiver,tname, description, stDt, disease); 
	}

	public Diet( AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, TreatmentPlanning tp, String description, Disease disease){
		super(assistedPerson, caregiver, tname, tp, description, disease);
	}
	public Diet(String tname, String description, XMLGregorianCalendar stDt, Disease disease){
		super(tname, description, stDt, disease);
	}
	public Diet(String tname, String description, TreatmentPlanning tp, Disease disease){
		super(tname, description, tp, disease);  
	}




	public Diet (String name, String description, Disease disease){
		super(name, description, disease);
	}

	public String getClassURI() {
		return MY_URI;
	}
	public int getPropSerializationType(String arg0) {
		return PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true; 
	}


}
