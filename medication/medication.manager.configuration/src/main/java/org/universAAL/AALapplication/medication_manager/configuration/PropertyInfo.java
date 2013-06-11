package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public final class PropertyInfo {

  private final String name;
  private final String value;
  private final String format;
  private final String type;
  private final String description;

  public PropertyInfo(String name, String value, String format, String type, String description) {
    this.name = name;
    this.value = value;
    this.format = format;
    this.type = type;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getFormat() {
    return format;
  }

  public String getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "PropertyInfo{" +
        "name='" + name + '\'' +
        ", value=" + value +
        ", format='" + format + '\'' +
        ", type='" + type + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
