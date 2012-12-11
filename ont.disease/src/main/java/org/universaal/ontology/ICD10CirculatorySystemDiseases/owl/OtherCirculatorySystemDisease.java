package org.universaal.ontology.ICD10CirculatorySystemDiseases.owl;

import org.universaal.ontology.ICD10Diseases.owl.CirculatorySystemDisease;
import org.universaal.ontology.disease.owl.DiseaseOntology;

public class OtherCirculatorySystemDisease extends CirculatorySystemDisease{

	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "OtherCirculatorySystemDisease";
	
	public OtherCirculatorySystemDisease () {
	    super();
	  }
	  
	  public OtherCirculatorySystemDisease (String uri) {
	    super(uri);
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
