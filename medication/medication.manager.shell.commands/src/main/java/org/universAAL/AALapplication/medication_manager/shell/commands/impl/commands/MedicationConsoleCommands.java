/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands;

/**
 * @author George Fournadjiev
 */
public final class MedicationConsoleCommands {

  public static final String COMMAND_PREFIX = "medication";
  public static final String USECASE_COMMMAND = "usecase";
  public static final String HELP_COMMMAND = "help";
  public static final String LISTIDS_COMMMAND = "listids";
  private static final ConsoleCommand[] CONSOLE_COMMANDS;

  static {
    CONSOLE_COMMANDS = new ConsoleCommand[3];
    ListidsConsoleCommand listidsCommand = createListidsCommand();
    CONSOLE_COMMANDS[0] = createUseCaseCommand(listidsCommand);
    CONSOLE_COMMANDS[1] = listidsCommand;
    CONSOLE_COMMANDS[2] = createHelpCommand();
  }

  private static ConsoleCommand createHelpCommand() {
    String name = COMMAND_PREFIX + ':' + HELP_COMMMAND;
    String description = "Listing available Medication Manager console commands";

    return new HelpConsoleCommand(name, description);
  }

  private static ListidsConsoleCommand createListidsCommand() {
    String name = COMMAND_PREFIX + ':' + LISTIDS_COMMMAND;
    String description = "This command prints all available usecases ids";

    return new ListidsConsoleCommand(name, description);
  }

  private static ConsoleCommand createUseCaseCommand(ListidsConsoleCommand listidsCommand) {
    String name = COMMAND_PREFIX + ':' + USECASE_COMMMAND;
    String description = "This command triggers the corresponding usecase wich id is provided as a parameter.\n\t" +
        "You can call " + COMMAND_PREFIX + ':' + LISTIDS_COMMMAND + " to see available usecases";

    return new UsecaseConsoleCommand(name, description, listidsCommand);
  }

  public static synchronized ConsoleCommand[] getConsoleCommands() {
    return CONSOLE_COMMANDS;
  }

  public static synchronized ConsoleCommand getUsecaseConsoleCommand() {
    return CONSOLE_COMMANDS[0];
  }

  public static synchronized ConsoleCommand getListidsConsoleCommand() {
    return CONSOLE_COMMANDS[1];
  }

  public static synchronized ConsoleCommand getHelpConsoleCommand() {
    return CONSOLE_COMMANDS[2];
  }

}
