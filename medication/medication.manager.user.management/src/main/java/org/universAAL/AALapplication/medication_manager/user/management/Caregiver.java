package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.AALapplication.medication_manager.user.management.impl.UserInfo;

/**
 * @author George Fournadjiev
 */
public final class Caregiver extends UserInfo {

  private boolean isDoctor;
  private final String username;
  private final String password;

  public Caregiver(int id, String uri, String name, String username, String password) {
    super(id, uri, name);
    this.username = username;
    this.password = password;
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

  @Override
  public String toString() {
    return "Caregiver{" +
        "isDoctor=" + isDoctor +
        ", id=" + getId() +
        ", uri=" + getUri() +
        ", name=" + getName() +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
