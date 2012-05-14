package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;

import java.util.Hashtable;


/**
 * @author George Fournadjiev
 */
public class Medication extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Medication";

  public Medication() {
    super();
  }

  public Medication(String uri) {
    super(uri);
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public boolean isWellFormed() {
    return true;
  }

  protected Hashtable getClassLevelRestrictions() {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
