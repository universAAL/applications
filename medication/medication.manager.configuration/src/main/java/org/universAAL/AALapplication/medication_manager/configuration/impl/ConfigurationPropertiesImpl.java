package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.configuration.MedicationManagerConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class ConfigurationPropertiesImpl implements ConfigurationProperties {

  private final Properties medicationProperties = new Properties();

  private static final String DEBUG_WRITE_FILE = "medication.manager.debug.write.file";
  private static final String ON = "ON";
  private static final String MEDICATION_REMINDER_TIMEOUT = "medication.manager.reminder.timeout";
  private static final String MEDICATION_INTAKE_INTERVAL = "medication.manager.intake.interval";
  private static final String HTTP_SESSION_EXPIRE_TIMEOUT_IN_MINUTES = "medication.manager.http.session.expire.timeout.in.minutes";
  private static final String HEALTH_TREATMENT_SERVICE_MOCKED = "medication.manager.health.treatment.service.mocked";
  private static final String LOAD_PRESCRIPTIONSDTOS = "medication.manager.load.prescriptionsdtos";
  private static final String MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE = "medication.manager.insert.dummy.users.into.che";
  private static final String HTTP_SESSION_TIMER_CHECKER_INTERVAL_IN_MINUTES =
      "medication.manager.http.session.timer.checker.interval.in.minutes";

  public ConfigurationPropertiesImpl() {
    try {
      loadProperties();
    } catch (IOException e) {
      throw new MedicationManagerConfigurationException(e);
    }
  }

  private void loadProperties() throws IOException {
    InputStream inputStream =
        ConfigurationPropertiesImpl.class.getClassLoader().getResourceAsStream("medication.properties");
    medicationProperties.load(inputStream);
    inputStream.close();
    setSystemProperties();
  }

  private void setSystemProperties() {
    Set<String> keys = medicationProperties.stringPropertyNames();
    for (String key : keys) {
      String value = medicationProperties.getProperty(key);
      System.setProperty(key, value);
    }
  }

  public int getMedicationReminderTimeout() {
    return getInt(MEDICATION_REMINDER_TIMEOUT);

  }

  private int getInt(String propertyName) {
    Log.info("Getting property: " + propertyName, getClass());
    String prop = System.getProperty(propertyName);
    if (prop == null) {
      throw new MedicationManagerConfigurationException("Missing property: " + propertyName);
    }

    try {
      return Integer.parseInt(prop);
    } catch (NumberFormatException e) {
      throw new MedicationManagerConfigurationException("the property value is not a number");
    }
  }

  public int getIntakeIntervalMinutes() {
    return getInt(MEDICATION_INTAKE_INTERVAL);
  }

  public long getHttpSessionExpireTimeoutInMinutes() {

    return getInt(HTTP_SESSION_EXPIRE_TIMEOUT_IN_MINUTES);
  }

  public int getHttpSessionTimerCheckerIntervalInMinutes() {
    return getInt(HTTP_SESSION_TIMER_CHECKER_INTERVAL_IN_MINUTES);
  }

  public boolean isDebugWriterOn() {
    String debug = System.getProperty(DEBUG_WRITE_FILE);

    if (debug == null) {
      throw new MedicationManagerConfigurationException("Missing property: " + DEBUG_WRITE_FILE);
    }

    return debug.equalsIgnoreCase(ON);
  }

  public boolean isHealthTreatmentServiceMocked() {
    String mocked = System.getProperty(HEALTH_TREATMENT_SERVICE_MOCKED);

    if (mocked == null) {
      throw new MedicationManagerConfigurationException("Missing property: " + HEALTH_TREATMENT_SERVICE_MOCKED);
    }

    return mocked.equalsIgnoreCase(ON);
  }

  public boolean isLoadPrescriptionDTOs() {
    String load = System.getProperty(LOAD_PRESCRIPTIONSDTOS);

    if (load == null) {
      throw new MedicationManagerConfigurationException("Missing property: " + LOAD_PRESCRIPTIONSDTOS);
    }

    return load.equalsIgnoreCase(ON);
  }

  public boolean isInsertDummyUsersIntoChe() {
    String insert = System.getProperty(MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE);

    if (insert == null) {
      throw new MedicationManagerConfigurationException("Missing property: " +
          MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE);
    }

    return insert.equalsIgnoreCase(ON);
  }

  public Properties getMedicationProperties() {
    return medicationProperties;
  }
}
