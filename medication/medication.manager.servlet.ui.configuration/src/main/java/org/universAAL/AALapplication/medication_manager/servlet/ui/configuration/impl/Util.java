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
  public static final String USER_MANAGEMENT = "/configuration/user_management";
  public static final String PARAMETERS_HANDLER = "/configuration/parameters_handler";
  public static final String NOTIFICATIONS_HANDLER = "/configuration/notification_rules_handler";
  public static final String HANDLE_NOTIFICATIONS = "/configuration/handle_notification_rules";
  public static final String HANDLE_PARAMETERS = "/configuration/handle_parameters";
  public static final String HANDLE_USER = "/configuration/handle_user";
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
  public static final String PATIENTS = "patients";
  public static final String CAREGIVERS = "caregivers";
  public static final String IDS = "IDS";
  public static final String COMPLEX_IDS = "COMPLEX_IDS";

  private Util() {
  }
}
