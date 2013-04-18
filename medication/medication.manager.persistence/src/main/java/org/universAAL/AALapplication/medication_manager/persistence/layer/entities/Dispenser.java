package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Dispenser extends Entity {

  private final Person patient;
  private final String dispenserUri;
  private final String instructionsFileName;

  public Dispenser(int id, Person patient, String dispenserUri, String instructionsFileName) {
    super(id);

    validate(patient, dispenserUri, instructionsFileName);

    this.patient = patient;
    this.dispenserUri = dispenserUri;
    this.instructionsFileName = instructionsFileName;
  }

  public Dispenser(Person patient, String dispenserUri, String instructionsFileName) {
    this(0, patient, dispenserUri, instructionsFileName);
  }

  private void validate(Person patient, String dispenserUri, String instructionsFileName) {

    validateParameter(patient, "patient");
    validateParameter(dispenserUri, "dispenserUri");
    validateParameter(instructionsFileName, "instructionsFileName");
  }

  public Person getPatient() {
    return patient;
  }

  public String getDispenserUri() {
    return dispenserUri;
  }

  public String getInstructionsFileName() {
    return instructionsFileName;
  }

  @Override
  public String toString() {
    return "Dispenser{" +
        "patient=" + patient +
        ", dispenserUri='" + dispenserUri + '\'' +
        ", instructionsFileName='" + instructionsFileName + '\'' +
        '}';
  }
}


