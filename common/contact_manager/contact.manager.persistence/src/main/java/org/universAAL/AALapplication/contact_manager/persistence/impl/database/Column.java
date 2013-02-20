package org.universAAL.AALapplication.contact_manager.persistence.impl.database;

import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Column {

  private final String name;
  private final Object value;

  public Column(String name, Object value) {

    validateParameter(name, "name");

    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Column{" +
        "name=" + name +
        ", value=" + value +
        '}';
  }
}
