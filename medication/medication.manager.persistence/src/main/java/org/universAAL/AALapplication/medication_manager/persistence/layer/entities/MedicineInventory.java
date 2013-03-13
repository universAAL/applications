package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class MedicineInventory extends Entity {

  private final Person patient;
  private final Medicine medicine;
  private final UnitClass unitClass;
  private final int quantity;
  private final int warningThreshold;

  public MedicineInventory(int id, Person patient, Medicine medicine,
                           UnitClass unitClass, int quantity, int warningThreshold) {

    super(id);

    validate(patient, medicine, unitClass, warningThreshold);

    this.patient = patient;
    this.medicine = medicine;
    this.unitClass = unitClass;
    this.quantity = quantity;
    this.warningThreshold = warningThreshold;
  }

  public MedicineInventory(Person patient, Medicine medicine,
                           UnitClass unitClass, int quantity, int warningThreshold) {

    this(0, patient, medicine, unitClass, quantity, warningThreshold);
  }

  private void validate(Person patient, Medicine medicine, UnitClass unitClass, int warningThreshold) {

    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(unitClass, "unitClass");
    validateParameter(warningThreshold, "warningThreshold");
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

  @Override
  public String toString() {
    return "MedicineInventory{" +
        "id=" + getId() +
        ", patient=" + patient +
        ", medicine=" + medicine +
        ", unitClass=" + unitClass +
        ", quantity=" + quantity +
        ", warningThreshold=" + warningThreshold +
        '}';
  }
}

