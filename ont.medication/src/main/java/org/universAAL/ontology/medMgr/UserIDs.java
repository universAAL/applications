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

/**
 * @author George Fournadjiev
 */
public final class UserIDs {

  private final static String PREFIX = "URN:ORG.UNIVERSAAL.AAL_SPACE:TEST_ENV#";
  private final static User[] USERS = new User[6];

  static {
    USERS[0] = new User(PREFIX + "SAIED");
    USERS[1] = new User(PREFIX + "ALEJANDRO");
    USERS[2] = new User(PREFIX + "GEORGE");
    USERS[3] = new User(PREFIX + "HECTOR");
    USERS[4] = new User(PREFIX + "VENELIN");
    USERS[5] = new User("SAIED");
  }


  public static User[] getAllUsers() {
    User[] res = new User[USERS.length];
    System.arraycopy(USERS, 0, res, 0, USERS.length);
    return res;
  }

  public static User getSaiedUser() {
    return USERS[0];
  }

  public static User getSaied() {
      return new User("SAIED");
  }
}
