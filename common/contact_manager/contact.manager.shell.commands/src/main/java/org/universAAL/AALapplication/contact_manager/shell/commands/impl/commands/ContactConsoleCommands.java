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


package org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands;

/**
 * @author George Fournadjiev
 */
public final class ContactConsoleCommands {

  private static final ConsoleCommand HELP_CONSOLE_COMMAND;
  private static final ConsoleCommand ADD_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand GET_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand REMOVE_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand EDIT_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand LIST_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand IMPORT_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand EXPORT_CONTACT_CONSOLE_COMMAND;
  private static final ConsoleCommand PRINT_TABLES_CONSOLE_COMMAND;

  public static final String COMMAND_PREFIX = "contact";
  public static final String HELP_COMMAND = "help";
  public static final String ADD_CONTACT = "add";
  public static final String GET_CONTACT = "get";
  public static final String REMOVE_CONTACT = "remove";
  public static final String EDIT_CONTACT = "edit";
  public static final String LIST_CONTACTS = "list";
  public static final String IMPORT_CONTACT = "imports";
  public static final String EXPORT_CONTACT = "exports";
  public static final String PRINT_TABLES = "print";
  private static final ConsoleCommand[] CONSOLE_COMMANDS;

  static {
    CONSOLE_COMMANDS = new ConsoleCommand[9];
    HELP_CONSOLE_COMMAND = createHelpCommand();
    CONSOLE_COMMANDS[0] = HELP_CONSOLE_COMMAND;
    ADD_CONTACT_CONSOLE_COMMAND = createAddContactCommand();
    CONSOLE_COMMANDS[1] = ADD_CONTACT_CONSOLE_COMMAND;
    GET_CONTACT_CONSOLE_COMMAND = createGetContactCommand();
    CONSOLE_COMMANDS[2] = GET_CONTACT_CONSOLE_COMMAND;
    REMOVE_CONTACT_CONSOLE_COMMAND = createRemoveContactCommand();
    CONSOLE_COMMANDS[3] = REMOVE_CONTACT_CONSOLE_COMMAND;
    EDIT_CONTACT_CONSOLE_COMMAND = createEditContactCommand();
    CONSOLE_COMMANDS[4] = EDIT_CONTACT_CONSOLE_COMMAND;
    LIST_CONTACT_CONSOLE_COMMAND = createListContactCommand();
    CONSOLE_COMMANDS[5] = LIST_CONTACT_CONSOLE_COMMAND;
    IMPORT_CONTACT_CONSOLE_COMMAND = createImportContactCommand();
    CONSOLE_COMMANDS[6] = IMPORT_CONTACT_CONSOLE_COMMAND;
    EXPORT_CONTACT_CONSOLE_COMMAND = createExportContactCommand();
    CONSOLE_COMMANDS[7] = EXPORT_CONTACT_CONSOLE_COMMAND;
    PRINT_TABLES_CONSOLE_COMMAND = createPrintTablesCommand();
    CONSOLE_COMMANDS[8] = PRINT_TABLES_CONSOLE_COMMAND;
  }

  private static ConsoleCommand createHelpCommand() {
    String name = COMMAND_PREFIX + ':' + HELP_COMMAND;
    String description = "Listing available Contact Manager console commands";

    return new ContactManagerHelpCommand(name, description);
  }

  private static ConsoleCommand createAddContactCommand() {
    String name = COMMAND_PREFIX + ':' + ADD_CONTACT;
    String description = "Calling AddContactsService";

    return new AddContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createGetContactCommand() {
    String name = COMMAND_PREFIX + ':' + GET_CONTACT;
    String description = "Calling GetContactsService";

    return new GetContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createRemoveContactCommand() {
    String name = COMMAND_PREFIX + ':' + REMOVE_CONTACT;
    String description = "Calling RemoveContactsService";

    return new RemoveContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createEditContactCommand() {
    String name = COMMAND_PREFIX + ':' + EDIT_CONTACT;
    String description = "Calling EditContactsService";

    return new EditContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createListContactCommand() {
    String name = COMMAND_PREFIX + ':' + LIST_CONTACTS;
    String description = "Calling ListContactsService";

    return new ListContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createImportContactCommand() {
    String name = COMMAND_PREFIX + ':' + IMPORT_CONTACT;
    String description = "Calling ImportContactService";

    return new ImportContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createExportContactCommand() {
    String name = COMMAND_PREFIX + ':' + EXPORT_CONTACT;
    String description = "Calling ExportContactService";

    return new ExportContactConsoleCommand(name, description);
  }

  private static ConsoleCommand createPrintTablesCommand() {
    String name = COMMAND_PREFIX + ':' + PRINT_TABLES;
    String description = "Printing all the data in the database tables";

    return new PrintTablesConsoleCommand(name, description);
  }

  public static synchronized ConsoleCommand[] getConsoleCommands() {
    return CONSOLE_COMMANDS;
  }

  public static synchronized ConsoleCommand getHelpCommand() {
    return HELP_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getAddContactConsoleCommand() {
    return ADD_CONTACT_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getGetContactConsoleCommand() {
    return GET_CONTACT_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getRemoveContactConsoleCommand() {
    return REMOVE_CONTACT_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getEditContactConsoleCommand() {
    return EDIT_CONTACT_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getListContactConsoleCommand() {
    return LIST_CONTACT_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getImportContactConsoleCommand() {
    return IMPORT_CONTACT_CONSOLE_COMMAND;
  }

  public static synchronized ConsoleCommand getExportContactConsoleCommand() {
    return EXPORT_CONTACT_CONSOLE_COMMAND;
  }

  public static ConsoleCommand getPrintConsoleCommand() {
    return PRINT_TABLES_CONSOLE_COMMAND;
  }
}
