package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.configuration.MedicationManagerConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author George Fournadjiev
 */
public final class ConfigurationPropertiesImpl implements ConfigurationProperties {

  private static final String MEDICATION_REMINDER_TIMEOUT = "medication.reminder.timeout";
  private static final String MEDICATION_INTEKA_INTERVAL = "medication.intake.interval";
  private final Properties medicationProperties = new Properties();

  ConfigurationPropertiesImpl() {
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
    String prop = medicationProperties.getProperty(propertyName);
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
    return getInt(MEDICATION_INTEKA_INTERVAL);
  }
}
