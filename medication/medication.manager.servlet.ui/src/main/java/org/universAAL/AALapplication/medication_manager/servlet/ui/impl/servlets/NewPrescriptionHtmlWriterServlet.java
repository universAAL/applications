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

package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.forms.NewPrescriptionScriptForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet;
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;

  private static final String NEW_PRESCRIPTION_HTML_FILE_NAME = "new_prescription.html";

  public NewPrescriptionHtmlWriterServlet(SessionTracking sessionTracking) {
    super(NEW_PRESCRIPTION_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayLoginHtmlWriterServlet(DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet) {
    this.displayLoginHtmlWriterServlet = displayLoginHtmlWriterServlet;
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
        isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

        if (doctor == null) {
          debugSessions(session.getId(), "if(doctor is null) the servlet doGet/doPost method", getClass());
          displayLoginHtmlWriterServlet.doGet(req, resp);
          return;
        }

        Person patient = (Person) session.getAttribute(PATIENT);

        if (patient == null) {
          debugSessions(session.getId(), "if(patient is null) the servlet doGet/doPost method", getClass());
          selectUserHtmlWriterServlet.doGet(req, resp);
          return;
        }

        PersistentService persistentService = getPersistentService();

        NewPrescriptionView newPrescriptionView = getNewPrescriptionView(doctor, patient, session, persistentService);

        debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());

        handleResponse(req, resp, newPrescriptionView, persistentService);
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  private NewPrescriptionView getNewPrescriptionView(Person doctor, Person patient,
                                                     Session session, PersistentService persistentService) {

    NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(PRESCRIPTION_VIEW);
    if (newPrescriptionView != null) {
      Person per = newPrescriptionView.getPatient();
      if (per.getId() != patient.getId()) {
        newPrescriptionView = null;
      }
    }

    if (newPrescriptionView == null) {
      newPrescriptionView = new NewPrescriptionView(persistentService.generateId(), doctor, patient);
      session.setAttribute(PRESCRIPTION_VIEW, newPrescriptionView);
    }

    validateNewPrescription(newPrescriptionView);

    return newPrescriptionView;
  }

  private void validateNewPrescription(NewPrescriptionView newPrescriptionView) {
    Set<MedicineView> medViews = newPrescriptionView.getMedicineViewSet();

    for (MedicineView view : medViews) {
      int id = view.getMedicineId();
      if (notValidMedicineView(view)) {
        newPrescriptionView.removeMedicineView(String.valueOf(id));
      }
    }
  }

  private boolean notValidMedicineView(MedicineView view) {
    if (view.getName() == null) {
      return true;
    }

    if (view.getDays() == 0) {
      return true;
    }

    if (view.getDose() == 0) {
      return true;
    }

    if (view.getUnit() == null) {
      return true;
    }

    if (view.getMealRelationDTO() == null) {
      return true;
    }

    if (view.getIntakeDTOSet().isEmpty()) {
      return true;
    }

    return false;

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp,
                              NewPrescriptionView newPrescriptionView,
                              PersistentService persistentService) throws IOException {
    try {
      ScriptForm scriptForm = new NewPrescriptionScriptForm(persistentService, newPrescriptionView);
      sendResponse(req, resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
