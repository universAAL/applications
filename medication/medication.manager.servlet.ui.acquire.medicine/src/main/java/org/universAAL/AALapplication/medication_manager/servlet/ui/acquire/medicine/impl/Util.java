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

package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl;


import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;

/**
 * @author George Fournadjiev
 */
public final class Util {


  public static final String LOGGED_CAREGIVER = "Caregiver";
  public static final String LOGIN_HTML_SERVLET_ALIAS = "/acquire/login.html";
  public static final String LOGIN_SERVLET_ALIAS = "/acquire/login";
  public static final String SELECT_USER_SERVLET_ALIAS = "/acquire/select_user";
  public static final String HANDLE_USER_SERVLET_ALIAS = "/acquire/handle_user";
  public static final String SELECT_MED_SERVLET_ALIAS = "/acquire/select_med";
  public static final String HANDLE_MED_SERVLET_ALIAS = "/acquire/handle_med";
  public static final String ERROR_PAGE_SERVLET_ALIAS = "/acquire/error";
  public static final String SUCCESS_PAGE_SERVLET_ALIAS = "/acquire/success";
  public static final String HANDLE_SUCCESS_PAGE_SERVLET_ALIAS = "/acquire/handle_success";
  public static final String LOGIN_ERROR = "LOGIN_ERROR";
  public static final String LOGIN_FILE_NAME = "login.html";
  public static final String ERROR_FILE_NAME = "error.html";
  public static final String SUCCESS_FILE_NAME = "success.html";
  public static final String EMPTY = "";
  public static final String CANCEL = "cancel";
  public static final String BACK = "back";
  public static final String TRUE = "true";
  public static final String PATIENT = "PATIENT";
  public static final Pair<String> EMPTY_PAIR = new Pair<String>(null, null);

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final String ERROR = "ERROR";
  public static final String MSG = "MSG";
  public static final String USER = "user";

  private Util() {
  }

  public static String getHtml(String resourcePath) {
    InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(resourcePath);

    if (inputStream == null) {
      throw new MedicationManagerAcquireMedicineException("The resource: " + resourcePath + " cannot be found");
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

    return getHtmlText(br);
  }

  private static String getHtmlText(BufferedReader br) {
    StringBuffer sb = new StringBuffer();
    try {
      String line = br.readLine();
      while (line != null) {
        sb.append(line);
        sb.append('\n');
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new MedicationManagerAcquireMedicineException(e);
    }

    return sb.toString();
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerAcquireMedicineException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerAcquireMedicineException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static String escapeNewLinesAndSingleQuotes(String description) {
    StringBuffer sb = new StringBuffer();
    StringReader reader = new StringReader(description);
    LineNumberReader lineNumberReader = new LineNumberReader(reader);

    try {
      String line = lineNumberReader.readLine();
      while (line != null) {
        line = line.replace("'", "\\'");
        sb.append(line);
        line = lineNumberReader.readLine();
        if (line != null) {
          sb.append("\\n");
        }
      }

      return sb.toString();
    } catch (IOException e) {
      throw new MedicationManagerAcquireMedicineException(e);
    }

  }

}
