package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class DeviceIDs {

  private final static String PREFIX = "http://ontology.universAAL.org/uAAL.owl#DeviceId";
  private final static String[] DEVICE_IDS = new String[5];

  static {
    DEVICE_IDS[0] = PREFIX + "#id1";
    DEVICE_IDS[1] = PREFIX + "#id2";
    DEVICE_IDS[2] = PREFIX + "#id3";
    DEVICE_IDS[3] = PREFIX + "#id4";
    DEVICE_IDS[4] = PREFIX + "#id5";
  }


  public static String[] getAllIDs() {
    String[] res = new String[DEVICE_IDS.length];
    System.arraycopy(DEVICE_IDS, 0, res, 0, DEVICE_IDS.length);
    return res;
  }


}
