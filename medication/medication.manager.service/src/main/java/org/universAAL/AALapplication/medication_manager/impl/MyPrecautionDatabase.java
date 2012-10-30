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


package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MyPrecautionDatabase {

  private static final Map<User, Precaution[]> PRECAUTION_MAP = new HashMap<User, Precaution[]>();

  static {
    fillPrecautionMap();
  }

  private static void fillPrecautionMap() {
    User[] allUsers = UserIDs.getAllUsers();

    for (int i = 0; i < allUsers.length; i++) {
      User user = allUsers[i];
      Precaution precautionSideEffect = new Precaution(Precaution.MY_URI);
      addSideeffect(precautionSideEffect);
      Precaution precautionIncompliance = new Precaution(Precaution.MY_URI);
      precautionIncompliance.setIncompliance("These medications must not be used with alcohol");
      Precaution[] precautions = new Precaution[]{precautionSideEffect, precautionIncompliance};
      PRECAUTION_MAP.put(user, precautions);
    }
  }

  private static void addSideeffect(Precaution precaution) {
    StringBuilder sideeffectBulder = new StringBuilder();

    sideeffectBulder.append("1. Aspirin:\n\t\t No sideeffect");

    String sideeffect = "Severe allergic reactions (rash; hives; itching; difficulty breathing; tightness in the chest;\n" +
        "swelling of the mouth, face, lips, or tongue; unusual hoarseness); burning, numbness, or tingling;\n" +
        "change in the amount of urine produced; confusion; dark or red-colored urine; decreased sexual ability;\n" +
        "depression; dizziness; fast or irregular heartbeat; fever, chills, or persistent sore throat; joint pain;\n" +
        "loss of appetite; memory problems; muscle pain, tenderness, or weakness (with or without fever and fatigue);\n" +
        "pale stools; red, swollen, blistered, or peeling skin; severe or persistent nausea or stomach or back pain;\n" +
        "shortness of breath; trouble sleeping; unusual bruising or bleeding; unusual tiredness or weakness; vomiting;\n" +
        "yellowing of the skin or eyes.";

    sideeffectBulder.append("\n\n2. Zocor:\n\t\t " + sideeffect);

    precaution.setSideEffect(sideeffectBulder.toString());

  }

  public static Precaution[] getPrecaution(User user) {
    return PRECAUTION_MAP.get(user);
  }

}
