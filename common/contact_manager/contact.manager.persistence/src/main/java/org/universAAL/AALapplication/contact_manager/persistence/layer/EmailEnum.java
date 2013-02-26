package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;

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

  public static EmailEnum getEnumFromValue(String textValue) {

    for (EmailEnum telEnum : values()) {
      if (telEnum.value.equals(textValue)) {
        return telEnum;
      }
    }

    throw new ContactManagerPersistenceException("No EmailEnum for the following value : " + textValue);
  }
}
