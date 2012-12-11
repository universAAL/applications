package org.universaal.ontology.health.owl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.Caregiver;

public class WeightMeasurementTreatment extends TakeMeasurementActivity{

	public static final String MY_URI = HealthOntology.NAMESPACE
    + "WeightMeasurementTreatment";

  public WeightMeasurementTreatment (){
	  super();
  } 
  
  public WeightMeasurementTreatment (String uri) {
    super(uri);
  }

  public WeightMeasurementTreatment (AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, String description, XMLGregorianCalendar stDt, String diseaseURI, WeightRequirement wr) {
	    super(assistedPerson, caregiver, tname, description, stDt, diseaseURI);
	    this.setHasMeasurementRequirements(wr);
  }
  
  public WeightMeasurementTreatment (AssistedPersonProfile assistedPerson, Caregiver caregiver, String tname, TreatmentPlanning tp, String description, String diseaseURI, WeightRequirement wr){
	  super(assistedPerson, caregiver, tname, tp, description, diseaseURI);
	  this.setHasMeasurementRequirements(wr);
  }
  public WeightMeasurementTreatment (String tname, String description, String diseaseURI, WeightRequirement wr){
	  super(tname, description, diseaseURI);
	  this.setHasMeasurementRequirements(wr);
  } 

  public WeightMeasurementTreatment (String tname, String description, XMLGregorianCalendar stDt, String diseaseURI, WeightRequirement wr){
	  super(tname, description, stDt, diseaseURI);
	  this.setHasMeasurementRequirements(wr);
  }
  
  public WeightMeasurementTreatment (String tname, String description, TreatmentPlanning tp, String diseaseURI, WeightRequirement wr){
	  super(tname, description, tp, diseaseURI);
	  this.setHasMeasurementRequirements(wr);
  }

  
  public String getClassURI() {
    return MY_URI;
  }
  public int getPropSerializationType(String arg0) {
	  return PROP_SERIALIZATION_FULL;
  }

  public boolean isWellFormed() {
	return true ;
  }



}
