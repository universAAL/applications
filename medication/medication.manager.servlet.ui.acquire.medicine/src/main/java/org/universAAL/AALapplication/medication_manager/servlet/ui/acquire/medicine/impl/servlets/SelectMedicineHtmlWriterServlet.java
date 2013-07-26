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

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets.forms.MedicineSelectScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Util.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;


/**
 * @author George Fournadjiev
 */
public final class SelectMedicineHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;

  private static final String MEDICINE_HTML_FILE_NAME = "medicine.html";

  public SelectMedicineHtmlWriterServlet(SessionTracking sessionTracking) {
    super(MEDICINE_HTML_FILE_NAME, sessionTracking);
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
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        if (caregiver == null) {
          debugSessions(session.getId(), "if(caregiver is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        String cancel = req.getParameter(CANCEL);
        if (cancel != null && TRUE.equalsIgnoreCase(cancel)) {
          debugSessions(session.getId(), "cancel the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        Person patient = (Person) session.getAttribute(PATIENT);

        if (patient == null) {
          debugSessions(session.getId(), "if(patient attribute is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        handleResponse(req, resp, caregiver, patient);
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


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp,
                              Person caregiver, Person patient) throws IOException {

    PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new MedicineSelectScriptForm(patient, persistentService);
      sendResponse(req, resp, scriptForm);
  }


}
