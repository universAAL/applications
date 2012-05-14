package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class UserIDs {

  private final static String PREFIX = "http://ontology.universAAL.org/uAAL.owl#theInvolvedHumanUser";
  private final static String[] USER_IDS = new String[5];

  static {
    USER_IDS[0] = PREFIX + "#id1";
    USER_IDS[1] = PREFIX + "#id2";
    USER_IDS[2] = PREFIX + "#id3";
    USER_IDS[3] = PREFIX + "#id4";
    USER_IDS[4] = PREFIX + "#id5";
  }


  public static String[] getAllIDs() {
    String[] res = new String[USER_IDS.length];
    System.arraycopy(USER_IDS, 0, res, 0, USER_IDS.length);
    return res;
  }


}
