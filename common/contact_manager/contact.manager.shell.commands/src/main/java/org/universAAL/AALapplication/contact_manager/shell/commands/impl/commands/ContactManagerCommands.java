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

import org.apache.felix.service.command.Descriptor;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log;

import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;

/**
 * @author George Fournadjiev
 */
public class ContactManagerCommands {

  @Descriptor("Displays Contact Manager commands")
  public void help() {

    ConsoleCommand consoleCommand = getHelpCommand();

    Log.info("Calling " + consoleCommand.getCommandText(), getClass());

    consoleCommand.execute();

  }

  @Descriptor("Triggers a call to ListContactService, which is part of the Contact Manager Service")
  public void list(@Descriptor("parameters") String... parameters) {

    ConsoleCommand consoleCommand = getListContactConsoleCommand();

    Log.info("Calling " + consoleCommand.getCommandText(), getClass());

    consoleCommand.execute(parameters);
  }

  @Descriptor("Triggers a call to ImportContactService, which is part of the Contact Manager Service")
  public void imports(@Descriptor("parameters") String... parameters) {

    ConsoleCommand consoleCommand = getImportContactConsoleCommand();

    Log.info("Calling " + consoleCommand.getCommandText(), getClass());

    consoleCommand.execute(parameters);
  }

  @Descriptor("Triggers a call to ExportContactService, which is part of the Contact Manager Service")
  public void exports(@Descriptor("parameters") String... parameters) {

    ConsoleCommand consoleCommand = getExportContactConsoleCommand();

    Log.info("Calling " + consoleCommand.getCommandText(), getClass());

    consoleCommand.execute(parameters);
  }

  @Descriptor("Triggers printing of the data in the database tables Contact Manager Service")
  public void print(@Descriptor("parameters") String... parameters) {

    ConsoleCommand consoleCommand = getPrintConsoleCommand();

    Log.info("Calling " + consoleCommand.getCommandText(), getClass());

    consoleCommand.execute(parameters);
  }


}

