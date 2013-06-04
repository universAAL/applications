package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.AALapplication.medication_manager.user.management.impl.UserInfo;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public final class CaregiverUserInfo extends UserInfo {

  private boolean isDoctor;
  private final String username;
  private final String password;
  private final String gsmNumber;

  public static final String PASS = "pass";

  public CaregiverUserInfo(int id, String uri, String name, String username, String gsmNumber) {
    super(id, uri, name);

    validateParameter(username, "username");
    validateParameter(gsmNumber, "gsmNumber");

    this.username = username;
    this.gsmNumber = gsmNumber;
    this.password = PASS; //TODO to be fixed when UniversAAL can provide username and password
  }

  public boolean isDoctor() {
    return isDoctor;
  }

  public void setDoctor(boolean doctor) {
    isDoctor = doctor;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getGsmNumber() {
    return gsmNumber;
  }

  @Override
  public String toString() {
    return "Caregiver{" +
        "isDoctor=" + isDoctor +
        ", id=" + getId() +
        ", uri=" + getUri() +
        ", name=" + getName() +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", gsmNumber='" + gsmNumber + '\'' +
        '}';
  }
}
