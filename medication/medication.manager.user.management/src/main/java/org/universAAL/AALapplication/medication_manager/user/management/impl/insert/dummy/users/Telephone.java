package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Telephone extends BaseType {

  private final TelEnum telEnum;

  public Telephone(String value, TelEnum telEnum) {

    super(value);

    validateParameter(telEnum, "telEnum");

    this.telEnum = telEnum;
  }

  public TelEnum getTelEnum() {
    return telEnum;
  }

  @Override
  public String getType() {
    return telEnum.getValue();
  }
}
