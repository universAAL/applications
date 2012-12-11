package org.universaal.ontology.ICD10Diseases.owl;

import org.universaal.ontology.disease.owl.Disease;
import org.universaal.ontology.disease.owl.DiseaseOntology;

public class CirculatorySystemDisease extends Disease{
	
	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "CirculatorySystemDisease";
	
	public CirculatorySystemDisease () {
	    super();
	  }
	  
	  public CirculatorySystemDisease (String uri) {
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
