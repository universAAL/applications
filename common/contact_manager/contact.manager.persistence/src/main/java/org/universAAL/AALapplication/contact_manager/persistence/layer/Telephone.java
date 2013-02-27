package org.universAAL.AALapplication.contact_manager.persistence.layer;


import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Telephone extends Type {

  private final TelEnum telEnum;

  public Telephone(String value, TelEnum telEnum) {

    super(value);

    validateParameter(telEnum, "telEnum");

    this.telEnum = telEnum;
  }

  public TelEnum getTelEnum() {
    return telEnum;
  }
}
