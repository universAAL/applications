/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


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
  private static final String DEBUG = "medication.manager.debug";
  private static final String TEST_MODE = "medication.manager.test.mode";
  private static final String ON = "true";
  private static final String MEDICATION_REMINDER_TIMEOUT = "medication.manager.reminder.timeout";
  private static final String MEDICATION_UPSIDE_DOWN_TIMEOUT = "medication.manager.upside.down.timeout";
  private static final String MEDICATION_INTAKE_INTERVAL = "medication.manager.intake.interval";
  private static final String MEDICATION_MANAGER_ISSUER_INTERVAL_MINUTES = "medication.manager.issuer.interval.minutes";
  private static final String HTTP_SESSION_EXPIRE_TIMEOUT_IN_MINUTES = "medication.manager.http.session.expire.timeout.in.minutes";
  private static final String LOAD_PRESCRIPTIONSDTOS = "medication.manager.load.prescriptionsdtos";
  private static final String HTTP_SESSION_TIMER_CHECKER_INTERVAL_IN_MINUTES =
      "medication.manager.http.session.timer.checker.interval.in.minutes";
  private final Map<String, PropertyInfo> propertyInfoMap;

  public ConfigurationPropertiesImpl() {
    try {
      InputStream inputStream =
          ConfigurationPropertiesImpl.class.getClassLoader().getResourceAsStream("medication.properties");
      medicationProperties.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      throw new MedicationManagerConfigurationException(e);
    }
    propertyInfoMap = createPropertyInfoMap();
  }

  public int getMedicationReminderTimeout() {
    return getInt(MEDICATION_REMINDER_TIMEOUT);

  }

  public int getMedicationUpsideDownTimeout() {
    return getInt(MEDICATION_UPSIDE_DOWN_TIMEOUT);

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

  public boolean isDebugOn() {

    String debug = System.getProperty(DEBUG);

    if (debug == null) {
      throw new MedicationManagerConfigurationException("Missing property: " + DEBUG);
    }

    return debug.equalsIgnoreCase(ON);

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

  public int getMedicationManagerIssuerIntervalInMinutes() {

    return getInt(MEDICATION_MANAGER_ISSUER_INTERVAL_MINUTES);
  }

  public boolean isTestMode() {
    String testMode = medicationProperties.getProperty(TEST_MODE);

    if (testMode == null) {
      throw new MedicationManagerConfigurationException("Missing property: " + TEST_MODE);
    }

    return testMode.equalsIgnoreCase(ON);
  }

  public Map<String, PropertyInfo> getPropertyInfoMap() {

    return propertyInfoMap;
  }

  private Map<String, PropertyInfo> createPropertyInfoMap() {
    Map<String, PropertyInfo> propertyInfoMap = new HashMap<String, PropertyInfo>();

    addReminderTimeout(propertyInfoMap);

    addUpsideDownTimeout(propertyInfoMap);

    addIntakeIntervalInMinutes(propertyInfoMap);

    addMedicationManagerIssuerIntervalInMinutes(propertyInfoMap);

    addHttpSessionExpireInMinutes(propertyInfoMap);

    addHttpSessionTimerCheckerIntervalInMinutes(propertyInfoMap);

    addIsDebugWriterOn(propertyInfoMap);

    addIsDebugOn(propertyInfoMap);

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
        "Timeout for user response by clicking a button on a reminder dialog"
    );

    propertyInfoMap.put(reminderInterval.getName(), reminderInterval);
    System.setProperty(MEDICATION_REMINDER_TIMEOUT, value);
  }

  private void addUpsideDownTimeout(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(MEDICATION_UPSIDE_DOWN_TIMEOUT);
    PropertyInfo upsideDownInterval = new PropertyInfo(
        MEDICATION_UPSIDE_DOWN_TIMEOUT,
        value,
        FormatEnum.SECONDS,
        TypeEnum.NUMBER,
        "Timeout for user response by clicking a button on a dispenser upside down dialog"
    );

    propertyInfoMap.put(upsideDownInterval.getName(), upsideDownInterval);
    System.setProperty(MEDICATION_UPSIDE_DOWN_TIMEOUT, value);
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
    System.setProperty(MEDICATION_INTAKE_INTERVAL, value);
  }

  private void addMedicationManagerIssuerIntervalInMinutes(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(MEDICATION_MANAGER_ISSUER_INTERVAL_MINUTES);
    PropertyInfo issuerInterval = new PropertyInfo(
        MEDICATION_MANAGER_ISSUER_INTERVAL_MINUTES,
        value,
        FormatEnum.MINUTES,
        TypeEnum.NUMBER,
        "Event issuer predefined interval in minutes in which it will check fo upcoming intakes and " +
            "will start a timer for every intake in that interval"
    );

    propertyInfoMap.put(issuerInterval.getName(), issuerInterval);
    System.setProperty(MEDICATION_MANAGER_ISSUER_INTERVAL_MINUTES, value);
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
    System.setProperty(HTTP_SESSION_EXPIRE_TIMEOUT_IN_MINUTES, value);
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
    System.setProperty(HTTP_SESSION_TIMER_CHECKER_INTERVAL_IN_MINUTES, value);
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
    System.setProperty(DEBUG_WRITE_FILE, value);
  }

  private void addIsDebugOn(Map<String, PropertyInfo> propertyInfoMap) {
    String value = medicationProperties.getProperty(DEBUG);
    PropertyInfo debugOn = new PropertyInfo(
        DEBUG,
        value,
        FormatEnum.BOOLEAN,
        TypeEnum.BOOLEAN,
        "This turn on/off the medication manager debugging"
    );

    propertyInfoMap.put(debugOn.getName(), debugOn);
    System.setProperty(DEBUG, value);
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
    System.setProperty(LOAD_PRESCRIPTIONSDTOS, value);
  }

  private void addInsertDummyUsersInTheCHE(Map<String, PropertyInfo> propertyInfoMap) {
    String value = "true";
    PropertyInfo insertDummyUsers = new PropertyInfo(
        MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE,
        value,
        FormatEnum.BOOLEAN,
        TypeEnum.BOOLEAN,
        "This property turn on/off loading inserting dummy users into CHE at startup"
    );

    propertyInfoMap.put(insertDummyUsers.getName(), insertDummyUsers);
    System.setProperty(MEDICATION_MANAGER_INSERT_DUMMY_USERS_INTO_CHE, value);
  }
}
