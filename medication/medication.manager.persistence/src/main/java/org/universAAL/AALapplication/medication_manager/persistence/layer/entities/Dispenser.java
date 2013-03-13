package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Dispenser extends Entity {

  private final Person patient;
  private final String dispenserUri;

  public Dispenser(int id, Person patient, String dispenserUri) {
    super(id);
    validate(patient, dispenserUri);

    this.patient = patient;
    this.dispenserUri = dispenserUri;
  }

  public Dispenser(Person patient, String dispenserUri) {
    this(0, patient, dispenserUri);
  }

  private void validate(Person patient, String dispenserUri) {

    validateParameter(patient, "patient");
    validateParameter(dispenserUri, "dispenserUri");
  }

  public Person getPatient() {
    return patient;
  }

  public String getDispenserUri() {
    return dispenserUri;
  }

  @Override
  public String toString() {
    return "Dispenser{" +
        "id=" + getId() +
        ", patient:" + patient +
        ", dispenserUri='" + dispenserUri + '\'' +
        '}';
  }
}


