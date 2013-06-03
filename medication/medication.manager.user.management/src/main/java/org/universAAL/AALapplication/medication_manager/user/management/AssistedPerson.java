package org.universAAL.AALapplication.medication_manager.user.management;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.user.management.impl.UserInfo;

/**
 * @author George Fournadjiev
 */
public final class AssistedPerson extends UserInfo {

  private Caregiver caregiver;
  private Caregiver doctor;
  private Dispenser dispenser;

  public AssistedPerson(int id, String uri, String name) {
    super(id, uri, name);
  }

  public Caregiver getCaregiver() {
    return caregiver;
  }

  public void setCaregiver(Caregiver caregiver) {
    this.caregiver = caregiver;
  }

  public Caregiver getDoctor() {
    return doctor;
  }

  public void setDoctor(Caregiver doctor) {
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
        "caregiver=" + caregiver +
        ", id=" + getId() +
        ", uri=" + getUri() +
        ", name=" + getName() +
        ", doctor=" + doctor +
        ", dispenser=" + dispenser +
        '}';
  }
}
