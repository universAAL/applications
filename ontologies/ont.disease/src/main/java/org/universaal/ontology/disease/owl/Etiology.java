package org.universaal.ontology.disease.owl;

import org.universAAL.middleware.owl.ManagedIndividual;

/*Causas de la enfermedad*/

public class Etiology extends ManagedIndividual{

	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "Etiology";
	
	public Etiology () {
	    super();
	  }
	  
	  public Etiology (String uri) {
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
