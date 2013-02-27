package org.universAAL.AALapplication.contact_manager.persistence.layer;

import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class Type {

  private final String value;

  protected Type(String value) {

    validateParameter(value, "validate");

    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
