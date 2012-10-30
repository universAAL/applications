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

import org.universAAL.AALapplication.medication_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.medication_manager.shell.commands.impl.MedicationManagerShellException;
import org.universAAL.AALapplication.medication_manager.simulation.DispenserUpsideDownContextProvider;
import org.universAAL.ontology.medMgr.MyDeviceUserMappingDatabase;

/**
 * @author George Fournadjiev
 */
public final class UsecaseDispenserUpsideDown extends Usecase {

  private static final String USECASE_TITLE = "UC03: Dispenser upside-down notification  - ";
  private static final String USECASE = USECASE_TITLE +
      "The service notifies the AP that the pill dispenser is upside-down and cannot be used";

  public UsecaseDispenserUpsideDown(int usecaseId) {
    super(usecaseId);
  }

  @Override
  public void execute(String... parameters) {

    if (parameters != null && parameters.length > 0) {
      throw new MedicationManagerShellException(NO_PARAMETERS_MESSAGE);
    }

    String deviceId = MyDeviceUserMappingDatabase.getDeviceIdForSaiedUser();

    Log.info("Executing the " + USECASE_TITLE + ". The deviceId is : " +
        deviceId, getClass());

    DispenserUpsideDownContextProvider.dispenserUpsideDownDeviceIdEvent(deviceId);
  }

  @Override
  public String getDescription() {
    return USECASE;
  }
}
