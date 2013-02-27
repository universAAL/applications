package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;

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

  public static TelEnum getEnumFromValue(String textValue) {
    textValue = textValue.toLowerCase();
    for (TelEnum telEnum : values()) {
      if (telEnum.value.equals(textValue)) {
        return telEnum;
      }
    }

    throw new ContactManagerPersistenceException("No TelEnum for the following value : " + textValue);
  }
}
