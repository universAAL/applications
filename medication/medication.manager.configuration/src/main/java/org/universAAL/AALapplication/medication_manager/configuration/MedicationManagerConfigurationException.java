package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public final class MedicationManagerConfigurationException extends RuntimeException {

  public MedicationManagerConfigurationException() {
  }

  public MedicationManagerConfigurationException(String message) {
    super(message);
  }

  public MedicationManagerConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  public MedicationManagerConfigurationException(Throwable cause) {
    super(cause);
  }
}
