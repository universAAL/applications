package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;

/**
 * @author George Fournadjiev
 */
public final class DoctorPatient extends AbstractDao {

  private final Person doctor;
  private final Person patient;

  public DoctorPatient(Database database, String tableName, Person doctor, Person patient) {
    super(database, tableName);
    this.doctor = doctor;
    this.patient = patient;
  }

  public Person getDoctor() {
    return doctor;
  }

  public Person getPatient() {
    return patient;
  }

  @Override
  public <T> T getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public String toString() {
    return "DoctorPatient{" +
        "doctor=" + doctor +
        ", patient=" + patient +
        '}';
  }
}
