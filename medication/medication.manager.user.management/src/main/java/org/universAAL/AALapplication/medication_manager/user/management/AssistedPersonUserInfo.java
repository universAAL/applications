package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.user.management.impl.UserInfo;

/**
 * @author George Fournadjiev
 */
public final class AssistedPersonUserInfo extends UserInfo {

  private CaregiverUserInfo caregiverUserInfo;
  private CaregiverUserInfo doctor;
  private Dispenser dispenser;

  public AssistedPersonUserInfo(int id, String uri, String name) {
    super(id, uri, name);
  }

  public CaregiverUserInfo getCaregiverUserInfo() {
    return caregiverUserInfo;
  }

  public void setCaregiverUserInfo(CaregiverUserInfo caregiverUserInfo) {
    this.caregiverUserInfo = caregiverUserInfo;
  }

  public CaregiverUserInfo getDoctor() {
    return doctor;
  }

  public void setDoctor(CaregiverUserInfo doctor) {
    this.doctor = doctor;
  }

  public Dispenser getDispenser() {
    return dispenser;
  }

  public void setDispenser(Dispenser dispenser) {
    this.dispenser = dispenser;
  }

  @Override
  public String toString() {
    return "AssistedPerson{" +
        "caregiver=" + caregiverUserInfo +
        ", id=" + getId() +
        ", uri=" + getUri() +
        ", name=" + getName() +
        ", doctor=" + doctor +
        ", dispenser=" + dispenser +
        '}';
  }
}
