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

package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Util;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.DisplayErrorScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Util.*;


/**
 * @author George Fournadjiev
 */
public final class DisplayErrorPageWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();

  public DisplayErrorPageWriterServlet(SessionTracking sessionTracking) {
    super(Util.ERROR_FILE_NAME, sessionTracking);
  }


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        Session session = getSession(req, resp, getClass());

        String message = (String) session.getAttribute(ERROR);
        if (message == null) {
          message = "Internal error";
        }

        message = escapeNewLinesAndSingleQuotes(message);

        handleResponse(resp, message);
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendPlainErrorResponse(resp, e);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp, String message) throws IOException {
    try {
      ScriptForm scriptForm = new DisplayErrorScriptForm(message);
      String scriptText = scriptForm.createScriptText();
      String htmlOutput = htmlParser.addScriptElement(scriptText);
      PrintWriter writer = resp.getWriter();
      writer.println(htmlOutput);
    } catch (Exception e) {
      Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
      sendPlainErrorResponse(resp, e);
    }
  }

  private void sendPlainErrorResponse(HttpServletResponse resp, Exception e) throws IOException {
    StringBuffer sb = new StringBuffer();

    sb.append("Internal error - an exception occurred: ");
    sb.append('\n');
    sb.append("class: ");
    sb.append(e.getClass().getName());
    sb.append('\n');
    sb.append("message: ");
    String message = e.getMessage();
    if (message == null || message.trim().isEmpty()) {
      sb.append("Missing message");
    } else {
      sb.append(message);
    }

    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, sb.toString());
  }


}
