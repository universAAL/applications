package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


import org.universAAL.AALapplication.medication_manager.user.management.impl.MedicationManagerUserManagementException;

/**
 * @author George Fournadjiev
 */
public enum TelEnum {
  CELL("cell"),
  MSG("msg"),
  VIDEO("video"),
  FAX("fax"),
  VOICE("voice");


  private final String value;

  private TelEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value.toUpperCase();
  }

  public static TelEnum getEnumFromValue(String textValue) {
    textValue = textValue.toLowerCase();
    for (TelEnum telEnum : values()) {
      if (telEnum.value.equals(textValue)) {
        return telEnum;
      }
    }

    throw new MedicationManagerUserManagementException("No TelEnum for the following value : " + textValue);
  }
}
