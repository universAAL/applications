package org.universAAL.AALapplication.contact_manager.persistence.layer;


import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
final class Telephone {

  private final String value;
  private final TelEnum telEnum;

  Telephone(String value, TelEnum telEnum) {

    validateParameter(value, "validate");
    validateParameter(telEnum, "telEnum");

    this.value = value;
    this.telEnum = telEnum;
  }

  public String getValue() {
    return value;
  }

  public TelEnum getTelEnum() {
    return telEnum;
  }
}
