package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Type extends BaseType {

  private final String name;
  private final String type;

  public Type(String value, String name, String type) {
    super(value);

    validateParameter(name, "name");
    validateParameter(type, "type");

    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
