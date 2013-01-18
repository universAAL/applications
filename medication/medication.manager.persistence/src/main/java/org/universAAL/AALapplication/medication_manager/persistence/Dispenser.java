package org.universAAL.AALapplication.medication_manager.persistence;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Dispenser {

  private final int dispenserId;
  private final Person patient;
  private final String dispenserUri;

  public Dispenser(int dispenserId, Person patient, String dispenserUri) {

    validate(dispenserId, patient, dispenserUri);

    this.dispenserId = dispenserId;
    this.patient = patient;
    this.dispenserUri = dispenserUri;
  }

  private void validate(int dispenserId, Person patient, String dispenserUri) {

    validateParameter(dispenserId, "dispenserId");
    validateParameter(patient, "patient");
    validateParameter(dispenserUri, "dispenserUri");
  }

  public int getDispenserId() {
    return dispenserId;
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
        "dispenserId=" + dispenserId +
        ", patient:" + patient +
        ", dispenserUri='" + dispenserUri + '\'' +
        '}';
  }
}


