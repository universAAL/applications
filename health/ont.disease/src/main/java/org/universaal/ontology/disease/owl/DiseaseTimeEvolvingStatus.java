package org.universaal.ontology.disease.owl;

/**
 * This disease classification depends on the velocity with which the disease evolves
 * or its duration.
 */

import org.universAAL.middleware.owl.ManagedIndividual;

public class DiseaseTimeEvolvingStatus extends ManagedIndividual{

	public static final String MY_URI = DiseaseOntology.NAMESPACE
    + "DiseaseTimeEvolvingStatus";

  public static final int ACUTE = 0;
  public static final int CHRONIC = 1;

  private static final String[] names = {
    "acute","chronic"};

  public static final DiseaseTimeEvolvingStatus acute = new DiseaseTimeEvolvingStatus(ACUTE);
  public static final DiseaseTimeEvolvingStatus chronic = new DiseaseTimeEvolvingStatus(CHRONIC);

  private int order;

  private DiseaseTimeEvolvingStatus(int order) {
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

  public static DiseaseTimeEvolvingStatus getTimeEvolvingStatusTypeByOrder(int order) {
    switch (order) {
      case ACUTE:
        return acute;
      case CHRONIC:
        return chronic;
    default:
      return null;    }
  }

  public static final DiseaseTimeEvolvingStatus valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(DiseaseOntology.NAMESPACE))
	    name = name.substring(DiseaseOntology.NAMESPACE.length());

	for (int i = ACUTE; i <= CHRONIC; i++)
	    if (names[i].equals(name))
		return getTimeEvolvingStatusTypeByOrder(i);

	return null;
  }
	
	
}
