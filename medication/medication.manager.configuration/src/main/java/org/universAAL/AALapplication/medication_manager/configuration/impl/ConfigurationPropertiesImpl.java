package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.configuration.FormatEnum;
import org.universAAL.AALapplication.medication_manager.configuration.MedicationManagerConfigurationException;
import org.universAAL.AALapplication.medication_manager.configuration.PropertyInfo;
import org.universAAL.AALapplication.medication_manager.configuration.TypeEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author George Fournadjiev
 */
public final class ConfigurationPropertiesImpl implements ConfigurationProperties {

  private final Properties medicationProperties = new Properties();

  private static final String DEBUG_WRITE_FILE = "medication.manager.debug.write.file";
  private static final String ON = "true";
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

  public Map<String, PropertyInfo> getPropertyInfoMap() {

    Map<String, PropertyInfo> propertyInfoMap = createPropertyInfoMap();


    return propertyInfoMap;
  }

  private Map<String, PropertyInfo> createPropertyInfoMap() {
    Map<String, PropertyInfo> propertyInfoMap = new HashMap<String, PropertyInfo>();

    addReminderTimeout(propertyInfoMap);

    addIntakeIntervalInMinutes(propertyInfoMap);

    addHttpSessionExpireInMinutes(propertyInfoMap);

    addHttpSessionTimerCheckerIntervalInMinutes(propertyInfoMap);

    addIsDebugWriterOn(propertyInfoMap);

    addTreatmentServiceMocked(propertyInfoMap);

    addLoadPrescriptionDtos(propertyInfoMap);

    addInsertDummyUsersInTheCHE(propertyInfoMap);

    return propertyInfoMap;
  }

  private void addReminderTimeout(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(MEDICATION_REMINDER_TIMEOUT);
    PropertyInfo reminderInterval = new PropertyInfo(
        MEDICATION_REMINDER_TIMEOUT,
         value,
        FormatEnum.SECONDS,
        TypeEnum.NUMBER,
        "Timeout for user response by clicking a button on a dialog"
    );

    propertyInfoMap.put(reminderInterval.getName(), reminderInterval);
  }

  private void addIntakeIntervalInMinutes(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(MEDICATION_INTAKE_INTERVAL);
    PropertyInfo intakeInterval = new PropertyInfo(
        MEDICATION_INTAKE_INTERVAL,
        value,
        FormatEnum.MINUTES,
        TypeEnum.NUMBER,
        "timestamp to closest intake in minutes tolerance (+/-)"
    );

    propertyInfoMap.put(intakeInterval.getName(), intakeInterval);
  }

  private void addHttpSessionExpireInMinutes(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(HTTP_SESSION_EXPIRE_TIMEOUT_IN_MINUTES);
    PropertyInfo httpSessionExpireInMinutes = new PropertyInfo(
        HTTP_SESSION_EXPIRE_TIMEOUT_IN_MINUTES,
        value,
        FormatEnum.MINUTES,
        TypeEnum.NUMBER,
        "After this period the http session expire"
    );

    propertyInfoMap.put(httpSessionExpireInMinutes.getName(), httpSessionExpireInMinutes);
  }

  private void addHttpSessionTimerCheckerIntervalInMinutes(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(HTTP_SESSION_TIMER_CHECKER_INTERVAL_IN_MINUTES);
    PropertyInfo timerCheckerIntervalInMinutes = new PropertyInfo(
        HTTP_SESSION_TIMER_CHECKER_INTERVAL_IN_MINUTES,
        value,
        FormatEnum.MINUTES,
        TypeEnum.NUMBER,
        "The timer will check all session for expiration at this interval"
    );

    propertyInfoMap.put(timerCheckerIntervalInMinutes.getName(), timerCheckerIntervalInMinutes);
  }

  private void addIsDebugWriterOn(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(DEBUG_WRITE_FILE);
    PropertyInfo debugerWriterOn = new PropertyInfo(
        DEBUG_WRITE_FILE,
        value,
        FormatEnum.BOOLEAN,
        TypeEnum.BOOLEAN,
        "This turn on/off the http session debugging"
    );

    propertyInfoMap.put(debugerWriterOn.getName(), debugerWriterOn);
  }

  private void addTreatmentServiceMocked(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(HEALTH_TREATMENT_SERVICE_MOCKED);
    PropertyInfo treatmentServiceMocked = new PropertyInfo(
        HEALTH_TREATMENT_SERVICE_MOCKED,
        value,
        FormatEnum.BOOLEAN,
        TypeEnum.BOOLEAN,
        "This property switch between real and mocked implementation of the Health Treatment Service"
    );

    propertyInfoMap.put(treatmentServiceMocked.getName(), treatmentServiceMocked);
  }

  private void addLoadPrescriptionDtos(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(LOAD_PRESCRIPTIONSDTOS);
    PropertyInfo loadPrescriptionDtos = new PropertyInfo(
        LOAD_PRESCRIPTIONSDTOS,
        value,
        FormatEnum.BOOLEAN,
        TypeEnum.BOOLEAN,
        "This property turn on/off loading all prescriptionDtos for performance at startup"
    );

    propertyInfoMap.put(loadPrescriptionDtos.getName(), loadPrescriptionDtos);
  }

  private void addInsertDummyUsersInTheCHE(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE);
    PropertyInfo insertDummyUsers = new PropertyInfo(
        MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE,
        value,
        FormatEnum.BOOLEAN,
        TypeEnum.BOOLEAN,
        "This property turn on/off loading inserting dummy users into CHE at startup"
    );

    propertyInfoMap.put(insertDummyUsers.getName(), insertDummyUsers);
  }
}