package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public enum FormatEnum {

  MINUTES("minutes"),
  SECONDS("seconds"),
  BOOLEAN("boolean");

  private final String value;

  private FormatEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static FormatEnum getEnumFromValue(String textValue) {
    textValue = textValue.toLowerCase();
    for (FormatEnum formatEnum : values()) {
      if (formatEnum.value.equals(textValue)) {
        return formatEnum;
      }
    }

    throw new MedicationManagerConfigurationException("No FormatEnum for the following value : " + textValue);
  }
}
