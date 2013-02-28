package org.universAAL.AALapplication.contact_manager.persistence.layer;

/**
 * @author George Fournadjiev
 */
public final class Type extends BaseType {

  private final String name;
  private final String type;

  public Type(String value, String name, String type) {
    super(value);

    Util.validateParameter(name, "name");
    Util.validateParameter(type, "type");

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
