package org.universAAL.AALapplication.medication_manager.persistence;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Intake {

  private final int id;
  private final Person patient;
  private final Medicine medicine;
  private final int quantity;
  private final UnitClass unitClass;
  private final Date timePlan;
  private final Date timeTaken;
  private final Dispenser dispenser;

  public Intake(int id, Person patient, Medicine medicine, int quantity, UnitClass unitClass,
                Date timePlan, Date timeTaken, Dispenser dispenser) {

    validate(id, patient, medicine, quantity, unitClass, timePlan);

    this.id = id;
    this.patient = patient;
    this.medicine = medicine;
    this.quantity = quantity;
    this.unitClass = unitClass;
    this.timePlan = timePlan;
    this.timeTaken = timeTaken;
    this.dispenser = dispenser;
  }

  private void validate(int id, Person patient, Medicine medicine, int quantity,
                        UnitClass unitClass, Date timePlan) {

    validateParameter(id, "id");
    validateParameter(patient, "patient");
    validateParameter(medicine, "medicine");
    validateParameter(quantity, "quantity");
    validateParameter(unitClass, "unitClass");
    validateParameter(timePlan, "timePlan");

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

  public int getQuantity() {
    return quantity;
  }

  public UnitClass getUnitClass() {
    return unitClass;
  }

  public Date getTimePlan() {
    return timePlan;
  }

  public Date getTimeTaken() {
    return timeTaken;
  }

  public Dispenser getDispenser() {
    return dispenser;
  }

  @Override
  public String toString() {
    return "Intake{" +
        "id=" + id +
        ", patient:" + patient +
        ", medicine=" + medicine +
        ", quantity=" + quantity +
        ", unitClass=" + unitClass +
        ", timePlan=" + timePlan +
        ", timeTaken=" + timeTaken +
        ", dispenser=" + dispenser +
        '}';
  }
}


