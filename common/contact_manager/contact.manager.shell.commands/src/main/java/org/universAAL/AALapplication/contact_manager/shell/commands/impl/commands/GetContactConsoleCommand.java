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

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.ContactManagerShellException;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log;
import org.universAAL.ontology.profile.User;

import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers.GetContactConsumer.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;

/**
 * @author George Fournadjiev
 */
public final class GetContactConsoleCommand extends ConsoleCommand {

  private static final String COMMAND = COMMAND_PREFIX + ':' + GET_CONTACT + " command";

  public GetContactConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return "This command requires one parameter : the user_uri";
  }

  @Override
  public void execute(String... parameters) {

    Log.info("Executing " + COMMAND, getClass());

    if (parameters != null && parameters.length != 1) {
      throw new ContactManagerShellException("The " + COMMAND + " expects one parameter (user_uri)");
    }

    User user = new User(parameters[0]);

    getContact(user);
  }


  @Override
  public String getCommandText() {
    return COMMAND;
  }

}
