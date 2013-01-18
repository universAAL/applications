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
import org.universAAL.AALapplication.medication_manager.simulation.MedicationConsumer;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UsecasePrecaution extends Usecase {


  private static final String USECASE_ID = "UC12";
  private static final String USECASE_TITLE = "UC12: Incompliancy identification";
  private static final String USECASE = USECASE_TITLE + " - The service provides warnings " +
      "about side effects and possible incompliancy with some food and drinks, " +
      "so that the Nutrition Adviser Service compose a health menu";

  public UsecasePrecaution() {
    super(USECASE_ID);
  }

  @Override
  public void execute(String... parameters) {

    if (parameters != null && parameters.length > 0) {
      throw new MedicationManagerShellException(NO_PARAMETERS_MESSAGE);
    }

    User saiedUser = UserIDs.getSaiedUser();
    Log.info("Executing the " + USECASE_TITLE + ". The mocked user is : " +
        saiedUser, getClass());


    Precaution[] precautions = MedicationConsumer.requestDetails(saiedUser);

    if (precautions == null || precautions.length != 2) {
      throw new MedicationManagerShellException("There is no precaution in our database for that user " +
          "or the returned Precaution array contains not 2 elements");
    }

    printInfo(precautions);

  }

  @Override
  public String getDescription() {
    return USECASE;
  }

  private void printInfo(Precaution[] precautions) {
    String sideeffect = precautions[0].getSideEffect();
    Log.info("Side effects:\n %s", getClass(), sideeffect);
    String incompliance = precautions[1].getIncompliance();
    Log.info("Incompliance:\n %s", getClass(), incompliance);
  }
}
