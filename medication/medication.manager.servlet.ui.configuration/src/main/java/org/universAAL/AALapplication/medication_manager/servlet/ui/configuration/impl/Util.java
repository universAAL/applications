package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;

/**
 * @author George Fournadjiev
 */
public final class Util {


  public static final String LOGGED_ADMIN = "admin";
  public static final String LOGIN_HTML_SERVLET_ALIAS = "/configuration/login.html";
  public static final String LOGIN_SERVLET_ALIAS = "/configuration/login";
  public static final String CONFIG_ACTION_SELECTOR = "/configuration/config_action_selector";
  public static final String LOGIN_ERROR = "LOGIN_ERROR";
  public static final String LOGIN_FILE_NAME = "login.html";
  public static final String ERROR_FILE_NAME = "error.html";
  public static final String EMPTY = "";
  public static final String CANCEL = "cancel";
  public static final String TRUE = "true";
  public static final String PATIENT = "PATIENT";
  public static final String ERROR_PAGE_SERVLET_ALIAS = "/configuration/error";
  public static final Pair<String> EMPTY_PAIR = new Pair<String>(null, null);
  public static final String ERROR = "ERROR";
  static final String JS_ALIAS = "/configuration/js";
  static final String CSS_ALIAS = "/configuration/css";

  private Util() {
  }
}
