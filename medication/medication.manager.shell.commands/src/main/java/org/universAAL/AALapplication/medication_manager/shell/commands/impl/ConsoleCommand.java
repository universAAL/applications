package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

/**
 * @author George Fournadjiev
 */
public abstract class ConsoleCommand {

  private final String name;
  private final String description;

  public static final String NO_PARAMETERS = "no";

  public ConsoleCommand(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public abstract String getParametersInfo();

  public abstract void execute(String... parameters);

  public String getCommandDetails(int number) {
    StringBuilder infoBuilder = new StringBuilder();
    infoBuilder.append('\n');
    infoBuilder.append((number));
    infoBuilder.append(". ");
    infoBuilder.append(getName());
    infoBuilder.append(" | Description: ");
    infoBuilder.append(getDescription());
    infoBuilder.append(" | Parameters: ");
    infoBuilder.append(getParametersInfo());

    return infoBuilder.toString();
  }

}
