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

    callCommand(getHelpCommand());

  }

  @Descriptor("Triggers a call to AddContactService, which is part of the Contact Manager Service")
  public void add(@Descriptor("parameters") String... parameters) {

    callCommand(getAddContactConsoleCommand());
  }

  @Descriptor("Triggers a call to GetContactService, which is part of the Contact Manager Service")
  public void get(@Descriptor("parameters") String... parameters) {

    callCommand(getGetContactConsoleCommand());
  }

  @Descriptor("Triggers a call to RemoveContactService, which is part of the Contact Manager Service")
  public void remove(@Descriptor("parameters") String... parameters) {

    callCommand(getRemoveContactConsoleCommand());
  }

  @Descriptor("Triggers a call to EditContactService, which is part of the Contact Manager Service")
  public void edit(@Descriptor("parameters") String... parameters) {

    callCommand(getEditContactConsoleCommand());
  }


  @Descriptor("Triggers a call to ListContactService, which is part of the Contact Manager Service")
  public void list(@Descriptor("parameters") String... parameters) {

    callCommand(getListContactConsoleCommand());
  }

  @Descriptor("Triggers a call to ImportContactService, which is part of the Contact Manager Service")
  public void imports(@Descriptor("parameters") String... parameters) {

    callCommand(getImportContactConsoleCommand());
  }

  @Descriptor("Triggers a call to ExportContactService, which is part of the Contact Manager Service")
  public void exports(@Descriptor("parameters") String... parameters) {

    callCommand(getExportContactConsoleCommand());
  }

  @Descriptor("Triggers printing of the data in the database tables Contact Manager Service")
  public void print(@Descriptor("parameters") String... parameters) {

    callCommand(getPrintConsoleCommand());
  }

  private void callCommand(ConsoleCommand consoleCommand) {

    Log.info("Calling " + consoleCommand.getCommandText(), getClass());

    consoleCommand.execute();
  }

}

