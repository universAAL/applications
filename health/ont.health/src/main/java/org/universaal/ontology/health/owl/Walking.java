package org.universaal.ontology.health.owl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.Caregiver;

public class Walking extends HealthyHabitsAdoption{

	public static final String MY_URI = HealthOntology.NAMESPACE
	+ "Walking";
  
	public Walking () {
		super();
	}

	public Walking (String uri) {
		super(uri);
	}

	public Walking( AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, String description, XMLGregorianCalendar stDt, String diseaseURI){
		super(assistedPerson, caregiver,tname, description, stDt, diseaseURI); 
	}

	public Walking( AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, TreatmentPlanning tp, String description, String diseaseURI){
		super(assistedPerson, caregiver, tname, tp, description, diseaseURI);
	}
	public Walking(String tname, String description, XMLGregorianCalendar stDt, String diseaseURI){
		super(tname, description, stDt, diseaseURI);
	}
	public Walking(String tname, String description, TreatmentPlanning tp, String diseaseURI){
		super(tname, description, tp, diseaseURI);  
	}

	public Walking (String name, String description, String diseaseURI){
		super(name, description, diseaseURI);
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


