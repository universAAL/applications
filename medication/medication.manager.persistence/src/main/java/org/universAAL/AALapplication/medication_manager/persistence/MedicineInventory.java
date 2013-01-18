package org.universAAL.AALapplication.medication_manager.persistence;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class MedicineInventory {

  private final int id;
  private final Person patient;
  private final Medicine medicine;
  private final UnitClass unitClass;
  private final int quantity;
  private final int warningThreshold;

  public MedicineInventory(int id, Person patient, Medicine medicine,
                           UnitClass unitClass, int quantity, int warningThreshold) {

    validate(id, patient, medicine, unitClass, warningThreshold);

    this.id = id;
    this.patient = patient;
    this.medicine = medicine;
    this.unitClass = unitClass;
    this.quantity = quantity;
    this.warningThreshold = warningThreshold;
  }

  private void validate(int id, Person patient, Medicine medicine, UnitClass unitClass, int warningThreshold) {

    validateParameter(id, "id");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(unitClass, "unitClass");
    validateParameter(warningThreshold, "warningThreshold");
  }

  public int getId() {
    return id;
  }

  public Person getPatient() {
    return patient;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public UnitClass getUnitClass() {
    return unitClass;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getWarningThreshold() {
    return warningThreshold;
  }
}

