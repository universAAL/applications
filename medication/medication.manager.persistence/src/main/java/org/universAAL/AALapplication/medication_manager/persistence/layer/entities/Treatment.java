package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Treatment extends Entity {

  private final Prescription prescription;
  private final Medicine medicine;
  private final Date startDate;
  private final Date endDate;
  private final TreatmentStatus status;
  private final boolean missedIntakeAlert;
  private final boolean newDoseAlert;

  public Treatment(int id, Prescription prescription, Medicine medicine, boolean missedIntakeAlert,
                   boolean newDoseAlert, Date startDate, Date endDate, TreatmentStatus status) {

    super(id);

    validate(prescription, medicine, startDate,endDate, status);

    this.prescription = prescription;
    this.medicine = medicine;
    this.missedIntakeAlert = missedIntakeAlert;
    this.newDoseAlert = newDoseAlert;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public Treatment(Prescription prescription, Medicine medicine, boolean missedIntakeAlert,
                     boolean newDoseAlert, Date startDate, Date endDate, TreatmentStatus status) {

    this(0, prescription, medicine, missedIntakeAlert, newDoseAlert, startDate, endDate, status);
  }

  private void validate(Prescription prescription, Medicine medicine,
                        Date startDate, Date endDate, TreatmentStatus status) {

    validateParameter(prescription, "prescription");
    validateParameter(medicine, "medicine");
    validateParameter(startDate, "startDate");
    validateParameter(endDate, "endDate");
    validateParameter(status, "status");

  }

  public Prescription getPrescription() {
    return prescription;
  }

  public Medicine getMedicine() {
    return medicine;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public TreatmentStatus getStatus() {
    return status;
  }

  public boolean isMissedIntakeAlert() {
    return missedIntakeAlert;
  }

  public boolean isNewDoseAlert() {
    return newDoseAlert;
  }

  @Override
  public String toString() {
    return "Treatment{" +
        "prescription=" + prescription +
        ", medicine=" + medicine +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", status=" + status +
        ", missedIntakeAlert=" + missedIntakeAlert +
        ", newDoseAlert=" + newDoseAlert +
        '}';
  }
}


