package org.universAAL.AALapplication.medication_manager.configuration;

import java.util.Properties;

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

  boolean isLoadPrescriptionDTOs();

  boolean isInsertDummyUsersIntoChe();

  Properties getMedicationProperties();

}
