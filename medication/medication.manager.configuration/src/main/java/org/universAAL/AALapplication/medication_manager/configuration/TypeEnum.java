package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public enum TypeEnum {

  NUMBER("number"),
  BOOLEAN("boolean"),
  STRING("string");

  private final String value;

  private TypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static TypeEnum getEnumFromValue(String textValue) {
    textValue = textValue.toLowerCase();
    for (TypeEnum typeEnum : values()) {
      if (typeEnum.value.equals(textValue)) {
        return typeEnum;
      }
    }

    throw new MedicationManagerConfigurationException("No TypeEnum for the following value : " + textValue);
  }
}
