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

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.usecases.Usecase;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author George Fournadjiev
 */
public final class ListidsConsoleCommand extends ConsoleCommand {

  public ListidsConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return NO_PARAMETERS;
  }


  @Override
  public void execute(String... parameters) {
    if (parameters != null && parameters.length > 0) {
      throw new MedicationManagerShellException(
          "The listids command doesn't expect any parameters");
    }

    Collection<Usecase> usecases = Usecase.getUsecaseMap();

    Iterator<Usecase> iterator = usecases.iterator();

    while (iterator.hasNext()) {
      Usecase uc = iterator.next();
      printImplementedUsecaseId(uc.getUsecaseId(), uc.getDescription());
    }

  }

  private void printImplementedUsecaseId(String id, String usecase) {
    StringBuilder usecaseBuilder = new StringBuilder();
    usecaseBuilder.append("The usecase with the id=");
    usecaseBuilder.append(id);
    usecaseBuilder.append(" is equivalent to the the following usecase described in the Medication Manager documentation:");
    usecaseBuilder.append("\n\t\t");
    usecaseBuilder.append(usecase);
    usecaseBuilder.append('\n');

    Log.info("Printing info for the following usecase id: %s\n" + usecaseBuilder.toString(), getClass(), id);
  }
}
