package org.universAAL.ontology.medMgr;

import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class UserIDs {

  private final static String PREFIX = "urn:org.universAAL.aal_space:test_env#";
  private final static User[] USERS = new User[5];

  static {
    USERS[0] = new User(PREFIX + "saied");
    USERS[1] = new User(PREFIX + "alejandro");
    USERS[2] = new User(PREFIX + "george");
    USERS[3] = new User(PREFIX + "hector");
    USERS[4] = new User(PREFIX + "venelin");
  }


  public static User[] getAllUsers() {
    User[] res = new User[USERS.length];
    System.arraycopy(USERS, 0, res, 0, USERS.length);
    return res;
  }

  public static User getSaiedUser() {
    return USERS[0];
  }
}
