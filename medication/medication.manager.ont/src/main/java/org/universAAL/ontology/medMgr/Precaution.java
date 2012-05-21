package org.universAAL.ontology.medMgr;


import org.universAAL.middleware.service.owl.Service;

/**
 * @author George Fournadjiev
 */
public class Precaution extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Precaution";
  public static final String SIDEEFFECT = MedicationOntology.NAMESPACE + "sideeffect";
  public static final String INCOMPLIANCE = MedicationOntology.NAMESPACE + "incompliance";

  public Precaution() {
    super();
  }

  public Precaution(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }

  public String getSideEffect() {
    return (String) getProperty(SIDEEFFECT);
  }

  public void setSideEffect(String value) {
    setProperty(SIDEEFFECT, value);
  }

  public String getIncompliance() {
    return (String) getProperty(INCOMPLIANCE);
  }

  public void setIncompliance(String value) {
    setProperty(INCOMPLIANCE, value);
  }

  public boolean isWellFormed() {
    return true;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

}
