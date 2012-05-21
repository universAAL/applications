package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class UserIDs {

  private final static String PREFIX = "urn:org.universAAL.aal_space:test_env#";
  private final static String[] USER_IDS = new String[5];

  static {
    USER_IDS[0] = PREFIX + "saied";
    USER_IDS[1] = PREFIX + "alejandro";
    USER_IDS[2] = PREFIX + "george";
    USER_IDS[3] = PREFIX + "hector";
    USER_IDS[4] = PREFIX + "venelin";
  }


  public static String[] getAllIDs() {
    String[] res = new String[USER_IDS.length];
    System.arraycopy(USER_IDS, 0, res, 0, USER_IDS.length);
    return res;
  }

  public static String getSaiedUser() {
    return PREFIX + "saied";
  }
}
