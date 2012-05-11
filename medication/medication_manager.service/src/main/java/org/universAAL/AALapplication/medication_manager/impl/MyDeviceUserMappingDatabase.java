package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.ontology.medMgr.DeviceIDs;
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.UserIDs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MyDeviceUserMappingDatabase {

  private static final Map<String, String> USER_DEVICE_MAPPING_MAP = new HashMap<String, String>();

  static {
    fillMap();
  }

  private static void fillMap() {
    String[] userIDs = UserIDs.getAllIDs();
    String[] deviceIDs = DeviceIDs.getAllIDs();

    if (userIDs.length != deviceIDs.length) {
      throw new MedicationException("usersIDs array length must match the deviceIDs array length");
    }

    for (int i = 0; i < userIDs.length; i++) {
      String userId = userIDs[i];
      String deviceId = deviceIDs[i];
      USER_DEVICE_MAPPING_MAP.put(deviceId, userId);
    }
  }

  public static String getUserId(String deviceId) {
    return USER_DEVICE_MAPPING_MAP.get(deviceId);
  }

}
