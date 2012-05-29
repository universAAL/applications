package org.universAAL.ontology.medMgr;

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
  private static final String DEVICE_ID_FOR_SAIED_USER;

  static {
    DEVICE_ID_FOR_SAIED_USER = fillMapAndGetSaiedUserDeviceId();
  }

  private static String fillMapAndGetSaiedUserDeviceId() {

    User[] allUsers = UserIDs.getAllUsers();
    String[] deviceIDs = DeviceIDs.getAllIDs();

    if (allUsers.length != deviceIDs.length) {
      throw new MedicationException("usersIDs array length must match the deviceIDs array length");
    }

    for (int i = 0; i < allUsers.length; i++) {
      User user = allUsers[i];
      String deviceId = deviceIDs[i];
      USER_DEVICE_MAPPING_MAP.put(deviceId, user);
      if (user.equals(UserIDs.getSaiedUser())) {
        return deviceId;
      }
    }

    throw new MedicationException("The deviceId for the Saied user has not been found");

  }

  public static User getUser(String deviceId) {
    return USER_DEVICE_MAPPING_MAP.get(deviceId);
  }

  public static String getDeviceIdForSaiedUser() {
    return DEVICE_ID_FOR_SAIED_USER;
  }

}
