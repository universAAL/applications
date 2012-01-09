package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.UserIDs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MyPrecautionDatabase {

  private static final Map<String, Precaution> PRECAUTION_MAP = new HashMap<String, Precaution>();

  static {
    fillPrecautionMap();
  }

  private static void fillPrecautionMap() {
    String[] ids = UserIDs.getAllIDs();

    for (int i = 0; i < ids.length; i++) {
      String id = ids[i];
      Precaution precaution = new Precaution();
      addSideeffect(precaution, id);
      addIncompliance(precaution, id);
      PRECAUTION_MAP.put(id, precaution);
    }
  }

  private static void addSideeffect(Precaution precaution, String id) {
    List sideeffectList = new ArrayList();
    for (int i = 1; i < 6; i++) {
      String sideeffect = "sideeffect No " + i + " for user with id = " + id;
      sideeffectList.add(sideeffect);
    }

    precaution.setSideeffect(sideeffectList);

  }

  private static void addIncompliance(Precaution precaution, String id) {
    List incomplianceList = new ArrayList();
    for (int i = 1; i < 6; i++) {
      String incompliance = "incompliance No " + i + " for user with id = " + id;
      incomplianceList.add(incompliance);
    }

    precaution.setIncompliance(incomplianceList);
  }
  
  public static Precaution getPrecaution(String userId) {
    return PRECAUTION_MAP.get(userId);
  }

}
