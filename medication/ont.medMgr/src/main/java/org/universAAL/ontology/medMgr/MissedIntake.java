package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;

/**
 * @author George Fournadjiev
 */
public class MissedIntake extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "MissedIntake";

  public static final String TIME = MedicationOntology.NAMESPACE + "time";

  public MissedIntake() {
    super();
  }

  public MissedIntake(String uri) {
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

  public Time getTime() {
    return (Time) props.get(TIME);
  }

  public void setTime(Time time) {
    props.put(TIME, time);
  }


}
