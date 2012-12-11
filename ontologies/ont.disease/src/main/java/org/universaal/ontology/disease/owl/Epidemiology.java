package org.universaal.ontology.disease.owl;

import org.universAAL.middleware.owl.ManagedIndividual;
/*contexto más probable sobre el cual es posible que se desarrolle una enfermedad.*/
public class Epidemiology extends ManagedIndividual{

	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "Epidemiology";
	
	public Epidemiology () {
	    super();
	  }
	  
	  public Epidemiology (String uri) {
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
