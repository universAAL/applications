package org.universAAL.AALapplication.medication_manager.configuration;

import java.util.Map;

/**
 * @author George Fournadjiev
 */
public interface ConfigurationProperties {

  int getMedicationReminderTimeout();

  int getMedicationUpsideDownTimeout();

  int getIntakeIntervalMinutes();

  long getHttpSessionExpireTimeoutInMinutes();

  int getHttpSessionTimerCheckerIntervalInMinutes();

  boolean isDebugWriterOn();

  boolean isHealthTreatmentServiceMocked();

  boolean isLoadPrescriptionDTOs();

  boolean isInsertDummyUsersIntoChe();

  int getMedicationManagerIssuerIntervalInMinutes();

  Map<String, PropertyInfo> getPropertyInfoMap();

}
