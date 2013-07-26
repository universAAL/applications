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

package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class UsersJavaScriptArrayCreator {

  private final PersistentService persistentService;
  private final List<AssistedPersonUserInfo> patients;
  private final List<CaregiverUserInfo> caregivers;

  public UsersJavaScriptArrayCreator(PersistentService persistentService, List<AssistedPersonUserInfo> patients,
                                     List<CaregiverUserInfo> caregivers) {

    this.persistentService = persistentService;
    this.patients = patients;
    this.caregivers = caregivers;
  }

  public String createUsersArrayText() {
    StringBuffer sb = new StringBuffer();
    sb.append("users = [");
    sb.append("\n\t\t");
    int size = patients.size();
    for (int i = 0; i < size; i++) {
      AssistedPersonUserInfo patient = patients.get(i);
      createTableRowData(patient, sb, i, size);
      sb.append("\n\t\t");
    }

    sb.append("\n\t");
    sb.append("];");

    return sb.toString();

  }

  private void createTableRowData(AssistedPersonUserInfo patient, StringBuffer sb, int i, int size) {
    sb.append('{');

    addPatient(patient, sb);

    addPhysicianAndCaregiver(patient, sb);

    addDispenser(patient, sb);

    addAlerts(patient, sb);

    sb.append("\n\t\t");
    sb.append("}");
    if (i < size - 1) {
      sb.append(',');
    }

  }

  private void addAlerts(AssistedPersonUserInfo patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("\"alerts\":");

    Dispenser dispenser = patient.getDispenser();

    if (dispenser != null) {
      createAlertsArray(dispenser, sb);
    } else {
      sb.append("{\"due\":false, \"missed\":false, \"successful\":false, \"upside\":false}");
    }


//    "alerts":{"due":false, "missed":true, "successful":true, "upside":false}

  }

  private void createAlertsArray(Dispenser dispenser, StringBuffer sb) {
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<Boolean> pair1 = new Pair<Boolean>("due", dispenser.isDueIntakeAlert(), true);
    creator.addPair(pair1);
    Pair<Boolean> pair2 = new Pair<Boolean>("missed", dispenser.isMissedIntakeAlert(), true);
    creator.addPair(pair2);
    Pair<Boolean> pair3 = new Pair<Boolean>("successful", dispenser.isSuccessfulIntakeAlert(), true);
    creator.addPair(pair3);
    Pair<Boolean> pair4 = new Pair<Boolean>("upside", dispenser.isUpsideDownAlert(), true);
        creator.addPair(pair4);

    String javascriptObject = creator.createJavascriptObject();

    sb.append(javascriptObject);
  }

  private void addDispenser(AssistedPersonUserInfo patient, StringBuffer sb) {
    sb.append(",\n\t\t\t\t");
    sb.append("\"dispenser\":");

    Dispenser dispenser = patient.getDispenser();

    if (dispenser != null) {
      String dispenserId = getNumberQuoted(dispenser.getId());
      sb.append(dispenserId);
    } else {
      sb.append(getNumberQuoted(-1));
    }

  }

  private void addPatient(AssistedPersonUserInfo patient, StringBuffer sb) {
    sb.append("\n\t\t\t\t");
    sb.append("\"patient\":");
    String patientId = getNumberQuoted(patient.getId());
    sb.append(patientId);
  }

  private void addPhysicianAndCaregiver(AssistedPersonUserInfo patient, StringBuffer sb) {

    CaregiverUserInfo doctor = patient.getDoctor();

    if (doctor != null) {
      sb.append(",\n\t\t\t\t");
      sb.append("\"physician\":");

      String physicianId = getNumberQuoted(doctor.getId());
      sb.append(physicianId);
    }

    CaregiverUserInfo caregiverUserInfo = patient.getCaregiverUserInfo();

    if (caregiverUserInfo != null) {
      sb.append(",\n\t\t\t\t");
      sb.append("\"caregiver\":");

      String caregiverId = getNumberQuoted(caregiverUserInfo.getId());
      sb.append(caregiverId);
    }
  }

  private String getNumberQuoted(int number) {
    return "\"" + number + "\"";
  }
}
