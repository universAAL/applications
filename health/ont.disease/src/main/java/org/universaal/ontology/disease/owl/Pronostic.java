package org.universaal.ontology.disease.owl;
/**
 * Pronostic defines the probability of specific situations to occur,
 * based on historic facts from Medicine History.
 */
import org.universAAL.middleware.owl.ManagedIndividual;

public class Pronostic extends ManagedIndividual{
	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "Pronostic";
	
	public Pronostic () {
	    super();
	  }
	  
	  public Pronostic (String uri) {
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
