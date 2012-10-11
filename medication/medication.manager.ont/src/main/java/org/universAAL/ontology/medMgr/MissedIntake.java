package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public class MissedIntake extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "MissedIntake";

  public static final String PROP_TIME = MedicationOntology.NAMESPACE + "time";

  public static final String PROP_USER = MedicationOntology.NAMESPACE + "user";

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
    return (Time) props.get(PROP_TIME);
  }

  public void setTime(Time time) {
    props.put(PROP_TIME, time);
  }

  public User getUser() {
    return (User) props.get(PROP_USER);
  }

  public void setUser(User user) {
    props.put(PROP_USER, user);
  }

}
