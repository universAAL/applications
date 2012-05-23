package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.ontology.medMgr.DeviceIDs;
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universAAL.ontology.profile.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MyDeviceUserMappingDatabase {

  private static final Map<String, User> USER_DEVICE_MAPPING_MAP = new HashMap<String, User>();

  static {
    fillMap();
  }

  private static void fillMap() {
    User[] allUsers = UserIDs.getAllUsers();
    String[] deviceIDs = DeviceIDs.getAllIDs();

    if (allUsers.length != deviceIDs.length) {
      throw new MedicationException("usersIDs array length must match the deviceIDs array length");
    }

    for (int i = 0; i < allUsers.length; i++) {
      User user = allUsers[i];
      String deviceId = deviceIDs[i];
      USER_DEVICE_MAPPING_MAP.put(deviceId, user);
    }
  }

  public static User getUserId(String deviceId) {
    return USER_DEVICE_MAPPING_MAP.get(deviceId);
  }

}
