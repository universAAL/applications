package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class InventoryLog {

  private final int id;
  private final Date timeOfCreation;
  private final Person patient;
  private final Medicine medicine;
  private final int changedQuantity;
  private final UnitClass unitClass;
  private final Reference reference;

  public InventoryLog(int id, Date timeOfCreation, Person patient, Medicine medicine,
                      int changedQuantity, UnitClass unitClass, Reference reference) {

    validate(id, timeOfCreation, patient, medicine, changedQuantity, unitClass, reference);

    this.id = id;
    this.timeOfCreation = timeOfCreation;
    this.patient = patient;
    this.medicine = medicine;
    this.changedQuantity = changedQuantity;
    this.unitClass = unitClass;
    this.reference = reference;
  }

  private void validate(int id, Date timeOfCreation, Person patient, Medicine medicine,
                        int changedQuantity, UnitClass unitClass, Reference reference) {

    validateParameter(id, "id");
    validateParameter(timeOfCreation, "timeOfCreation");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(changedQuantity, "changedQuantity");
    validateParameter(unitClass, "unitClass");
    validateParameter(reference, "reference");

  }

  public int getId() {
    return id;
  }

  public Date getTimeOfCreation() {
    return timeOfCreation;
  }

  public Person getPatient() {
    return patient;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public int getChangedQuantity() {
    return changedQuantity;
  }

  public UnitClass getUnitClass() {
    return unitClass;
  }

  public Reference getReference() {
    return reference;
  }

  @Override
  public String toString() {
    return "InventoryLog{" +
        "id=" + id +
        ", timeOfCreation=" + timeOfCreation +
        ", patient:" + patient +
        ", " + medicine +
        ", changedQuantity=" + changedQuantity +
        ", unitClass=" + unitClass +
        ", reference=" + reference +
        '}';
  }
}


