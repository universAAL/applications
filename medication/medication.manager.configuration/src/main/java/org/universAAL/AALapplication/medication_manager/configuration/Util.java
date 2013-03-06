package org.universAAL.AALapplication.medication_manager.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author George Fournadjiev
 */
public final class Util {

  private static final Properties MEDICATION_PROPERTIES = new Properties();

  static {
    try {
      InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("medication.properties");
      MEDICATION_PROPERTIES.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      throw new MedicationManagerConfigurationException(e);
    }
  }


  private Util() {
    // to prevent initialization, because this is util class
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerConfigurationException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerConfigurationException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static Properties getMedicationProperties() {
    return MEDICATION_PROPERTIES;
  }
}
