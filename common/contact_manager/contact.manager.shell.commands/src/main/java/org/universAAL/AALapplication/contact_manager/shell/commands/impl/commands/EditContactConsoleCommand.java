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

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log;

import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;

/**
 * @author George Fournadjiev
 */
public final class EditContactConsoleCommand extends SendVCardConsoleCommand {

  private static final String COMMAND = COMMAND_PREFIX + ':' + EDIT_CONTACT + " command";

  public EditContactConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public void execute(String... parameters) {

    Log.info("Executing " + COMMAND, getClass());

    executeCommand(COMMAND, EDIT_CONTACT_TYPE, parameters);

  }


  @Override
  public String getCommandText() {
    return COMMAND;
  }

}
