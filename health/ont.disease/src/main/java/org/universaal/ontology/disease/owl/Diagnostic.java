package org.universaal.ontology.disease.owl;

import org.universAAL.middleware.owl.ManagedIndividual;
/**
 * A diagnostic is the procedure trough which a a disease is identified.
 * @author Marina
 *
 */
public class Diagnostic extends ManagedIndividual{
	public static final String MY_URI = DiseaseOntology.NAMESPACE
	+ "Diagnostic";
	
	public Diagnostic () {
	    super();
	  }
	  
	  public Diagnostic (String uri) {
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
