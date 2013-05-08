package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class Dispenser extends Entity {

  private final Person patient;
  private final String name;
  private final String dispenserUri;
  private final String instructionsFileName;

  public Dispenser(int id, Person patient, String name, String dispenserUri, String instructionsFileName) {
    super(id);

    validate(patient, name, dispenserUri, instructionsFileName);

    this.patient = patient;
    this.name = name;
    this.dispenserUri = dispenserUri;
    this.instructionsFileName = instructionsFileName;
  }

  public Dispenser(Person patient, String name, String dispenserUri, String instructionsFileName) {
    this(0, patient, name, dispenserUri, instructionsFileName);
  }

  private void validate(Person patient, String name, String dispenserUri, String instructionsFileName) {

    validateParameter(patient, "patient");
    validateParameter(name, "name");
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
        ", name='" + name + '\'' +
        ", dispenserUri='" + dispenserUri + '\'' +
        ", instructionsFileName='" + instructionsFileName + '\'' +
        '}';
  }
}


