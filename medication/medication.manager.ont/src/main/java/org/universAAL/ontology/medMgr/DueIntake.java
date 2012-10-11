package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;

/**
 * @author George Fournadjiev
 */
public class DueIntake extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "DueIntake";

  public static final String PROP_DEVICE_ID = MedicationOntology.NAMESPACE + "deviceId";
  public static final String PROP_TIME = MedicationOntology.NAMESPACE + "time";

  public DueIntake() {
    super();
  }

  public DueIntake(String uri) {
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

  public String getDeviceId() {
    return (String) props.get(PROP_DEVICE_ID);
  }

  public void setDeviceId(String deviceId) {
    props.put(PROP_DEVICE_ID, deviceId);
  }

  public Time getTime() {
    return (Time) props.get(PROP_TIME);
  }

  public void setTime(Time time) {
    props.put(PROP_TIME, time);
  }

}
