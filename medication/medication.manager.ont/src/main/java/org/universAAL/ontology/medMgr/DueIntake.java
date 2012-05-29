package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public class DueIntake extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "DueIntake";

  public static final String DEVICE_ID = MedicationOntology.NAMESPACE + "deviceId";

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
    return (String) props.get(DEVICE_ID);
  }

  public void setDeviceId(String deviceId) {
    props.put(DEVICE_ID, deviceId);
  }

}
