package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


import org.universAAL.AALapplication.medication_manager.user.management.impl.MedicationManagerUserManagementException;

/**
 * @author George Fournadjiev
 */
public enum EmailEnum {
  INTERNET("internet"),
  X400("x400");


  private final String value;

  private EmailEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static EmailEnum getEnumFromValue(String textValue) {
    textValue = textValue.toLowerCase();
    for (EmailEnum telEnum : values()) {
      if (telEnum.value.equals(textValue)) {
        return telEnum;
      }
    }

    throw new MedicationManagerUserManagementException("No EmailEnum for the following value : " + textValue);
  }
}
