package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public interface ConfigurationProperties {

  int getMedicationReminderTimeout();

  int getIntakeIntervalMinutes();

  long getHttpSessionExpireTimeoutInMinutes();

  int getHttpSessionTimerCheckerIntervalInMinutes();

  boolean isDebugWriterOn();

  boolean isHealthTreatmentServiceMocked();

}
