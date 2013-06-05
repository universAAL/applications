package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.util;

import javax.servlet.ServletRequest;
import java.util.Enumeration;

/**
 * @author George Fournadjiev
 */
public final class UserManagementParameterHandler {

  private final ServletRequest request;
  /*private final int caregiverId;
  private final int physianId;*/

  private final static String CAREGIVER = "caregiver";
  private final static String PHYSICIAN = "physician";
  private final static String DISPENSER = "dispenser";
  private final static String DUE = "due";
  private final static String UPSIDE = "upside";
  private final static String SUCCESSFUL = "successful";
  private final static String MISSED = "missed";

  public UserManagementParameterHandler(ServletRequest request) {
    this.request = request;
  }

  public void validateParameters() {

    Enumeration en = request.getParameterNames();
    while (en.hasMoreElements()) {
      String key = (String) en.nextElement();
      String value = request.getParameter(key);
      System.out.println("key = " + key + " | value = " + value);
    }

  }

  /* key=save_patient| value=Save Patient
  key=caregiver| value=6
  key=due| value=on
  key=dispenser| value=1
  key=upside| value=on
  key=successful| value=on
  key=missed| value=on
  key=physician| value=4*/

}
