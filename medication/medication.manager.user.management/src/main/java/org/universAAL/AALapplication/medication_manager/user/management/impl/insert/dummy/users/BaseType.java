package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class BaseType {

  private final String value;

  protected BaseType(String value) {

    validateParameter(value, "validate");

    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public abstract String getType();
}
