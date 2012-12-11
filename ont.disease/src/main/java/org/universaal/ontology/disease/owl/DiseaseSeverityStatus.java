package org.universaal.ontology.disease.owl;

/**
 * This enumeration allows to classify the disease
 * depending on the illness severity.  
 */

import org.universAAL.middleware.owl.ManagedIndividual;


public class DiseaseSeverityStatus extends ManagedIndividual{

	 public static final String MY_URI = DiseaseOntology.NAMESPACE
	    + "DiseaseSeverityStatus";

	  public static final int MILD = 0;
	  public static final int SEVERE = 1;
	  public static final int FATAL = 2;

	  private static final String[] names = {
	    "mild","severe","fatal"};

	  public static final DiseaseSeverityStatus mild = new DiseaseSeverityStatus(MILD);
	  public static final DiseaseSeverityStatus severe = new DiseaseSeverityStatus(SEVERE);
	  public static final DiseaseSeverityStatus fatal = new DiseaseSeverityStatus(FATAL);

	  private int order;

	  private DiseaseSeverityStatus(int order) {
	    super(DiseaseOntology.NAMESPACE + names[order]);
	    this.order = order;
	  }

	  public int getPropSerializationType(String propURI) {
	    return PROP_SERIALIZATION_OPTIONAL;
	  }

	  public boolean isWellFormed() {
	    return true;
	  }

	  public String name() {
	    return names[order];
	  }

	  public int ord() {
	    return order;
	  }

	  public String getClassURI() {
	    return MY_URI;
	  }

	  public static DiseaseSeverityStatus getSeverityStatusTypeByOrder(int order) {
	    switch (order) {
	      case MILD:
	        return mild;
	      case SEVERE:
	        return severe;
	      case FATAL:
	        return fatal;
	    default:
	      return null;    }
	  }

	  public static final DiseaseSeverityStatus valueOf(String name) {
		if (name == null)
		    return null;

		if (name.startsWith(DiseaseOntology.NAMESPACE))
		    name = name.substring(DiseaseOntology.NAMESPACE.length());

		for (int i = MILD; i <= FATAL; i++)
		    if (names[i].equals(name))
			return getSeverityStatusTypeByOrder(i);

		return null;
	  }


}
