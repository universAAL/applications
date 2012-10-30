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

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.usecases.Usecase;

/**
 * @author George Fournadjiev
 */
public final class UsecaseConsoleCommand extends ConsoleCommand {

  public static final String COMMAND_INFO =
      "The usecase command expects at least (minimum) one parameter, which is: usecase id." +
          "This must be the first parameter after the usecase command";

  public UsecaseConsoleCommand(String name, String description, ListidsConsoleCommand listidsCommand) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return COMMAND_INFO;
  }


  @Override
  public void execute(String... parameters) {
    if (parameters == null || parameters.length == 0) {
      throw new MedicationManagerShellException(COMMAND_INFO);
    }

    int usecaseId = getUsecaseId(parameters[0]);
    Usecase usecase = Usecase.getUsecase(usecaseId);
    String[] params = getParamsWithoutTheFirstElementUsecaseId(parameters);

    usecase.execute(params);
  }

  private String[] getParamsWithoutTheFirstElementUsecaseId(String[] parameters) {
    String[] params = new String[parameters.length - 1];

    System.arraycopy(parameters, 1, params, 0, parameters.length - 1);

    return params;
  }

  private int getUsecaseId(String parameter) {
    try {

      Integer usecaseId = Integer.valueOf(parameter);

      return usecaseId;

    } catch (NumberFormatException e) {

      throw new MedicationManagerShellException("The first command parameter must be number" +
          "and must represent usecaseId", e);

    }
  }
}
