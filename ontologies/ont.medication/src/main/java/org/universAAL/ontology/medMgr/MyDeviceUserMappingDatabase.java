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


package org.universAAL.ontology.medMgr;

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
