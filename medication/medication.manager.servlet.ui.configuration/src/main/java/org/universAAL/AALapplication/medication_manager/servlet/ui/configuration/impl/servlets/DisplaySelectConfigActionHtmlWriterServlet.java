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

package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.forms.SelectConfigActionForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;


/**
 * @author George Fournadjiev
 */
public final class DisplaySelectConfigActionHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;

  private static final String SELECT_CONFIG_ACTION_FILE_NAME = "select_config_action.html";

  public DisplaySelectConfigActionHtmlWriterServlet(SessionTracking sessionTracking) {
    super(SELECT_CONFIG_ACTION_FILE_NAME, sessionTracking);
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        isServletSet(displayServlet, "displayServlet");

        Session session = getSession(req, resp, getClass());
        Person admin = (Person) session.getAttribute(LOGGED_ADMIN);

        if (admin == null) {
          debugSessions(session.getId(), "if(admin is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }


        handleResponse(req, resp);


      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      ScriptForm scriptForm = new SelectConfigActionForm();
      sendResponse(req, resp, scriptForm);

    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
