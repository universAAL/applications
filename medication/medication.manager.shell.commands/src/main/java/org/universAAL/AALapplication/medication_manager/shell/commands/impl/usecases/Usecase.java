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


package org.universAAL.AALapplication.medication_manager.shell.commands.impl.usecases;

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.commands.MedicationConsoleCommands;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public abstract class Usecase {

  private final String usecaseId;

  public static final String NO_PARAMETERS_MESSAGE = "This usecase doesn't expect parameters";
  private static final Map<String, Usecase> USECASE_MAP = new LinkedHashMap<String, Usecase>();

  static {
    USECASE_MAP.put(new UsecaseMedicationReminder().getUsecaseId(), new UsecaseMedicationReminder());
    USECASE_MAP.put(new UsecaseDispenserUpsideDown().getUsecaseId(), new UsecaseDispenserUpsideDown());
    USECASE_MAP.put(new UsecaseMissedIntake().getUsecaseId(), new UsecaseMissedIntake());
    USECASE_MAP.put(new UsecaseNewPrescription().getUsecaseId(), new UsecaseNewPrescription());
    USECASE_MAP.put(new UsecasePrecaution().getUsecaseId(), new UsecasePrecaution());
  }

  protected Usecase(String usecaseId) {
    this.usecaseId = usecaseId;
  }

  public String getUsecaseId() {
    return usecaseId;
  }

  public abstract void execute(String... parameters);

  public abstract String getDescription();

  public static Usecase getUsecase(String usecaseId) {
    Usecase usecase = USECASE_MAP.get(usecaseId);

    if (usecase == null) {
      throw new MedicationManagerShellException(
          "Not found usecase implementation with the following id <" + usecaseId + ">." +
              "\n\t\t You can execute the " + MedicationConsoleCommands.COMMAND_PREFIX + ':' +
              MedicationConsoleCommands.LISTIDS_COMMMAND +
              " command to see the implemented usecases with the corresponding ids");
    }

    return usecase;
  }

  public static Collection<Usecase> getUsecaseMap() {
    return USECASE_MAP.values();
  }
}
