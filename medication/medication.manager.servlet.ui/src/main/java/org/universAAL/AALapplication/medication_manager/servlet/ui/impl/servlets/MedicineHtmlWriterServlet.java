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
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.forms.NewMedicineScriptForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class MedicineHtmlWriterServlet extends BaseHtmlWriterServlet {

  public static final String PRESCRIPTION_ID = "prescriptionId";
  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet;
  private NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet;
  private ListPrescriptionsHtmlWriterServlet listPrescriptionsHtmlWriterServlet;

  private static final String MEDICINE_HTML_FILE_NAME = "medicine.html";

  public MedicineHtmlWriterServlet(SessionTracking sessionTracking) {
    super(MEDICINE_HTML_FILE_NAME, sessionTracking);
  }

  public void setDisplayLoginHtmlWriterServlet(DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet) {
    this.displayLoginHtmlWriterServlet = displayLoginHtmlWriterServlet;
  }

  public void setNewPrescriptionHtmlWriterServlet(NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet) {
    this.newPrescriptionHtmlWriterServlet = newPrescriptionHtmlWriterServlet;
  }

  public void setListPrescriptionsHtmlWriterServlet(ListPrescriptionsHtmlWriterServlet
                                                        listPrescriptionsHtmlWriterServlet) {

    this.listPrescriptionsHtmlWriterServlet = listPrescriptionsHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
        isServletSet(newPrescriptionHtmlWriterServlet, "newPrescriptionHtmlWriterServlet");
        isServletSet(listPrescriptionsHtmlWriterServlet, "listPrescriptionsHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        Person doctor = (Person) session.getAttribute(LOGGED_DOCTOR);

        if (doctor == null) {
          debugSessions(session.getId(), "if(doctor is null) servlet doGet/doPost method", getClass());
          displayLoginHtmlWriterServlet.doGet(req, resp);
          return;
        }


        NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(PRESCRIPTION_VIEW);

        if (newPrescriptionView == null) {
          debugSessions(session.getId(), "if(newPrescriptionView is null) the servlet doGet/doPost method", getClass());
          newPrescriptionHtmlWriterServlet.doGet(req, resp);
          return;
        }

        PersistentService persistentService = getPersistentService();

        setDateAndNotes(req, newPrescriptionView);
        MedicineView medicineView = getMedicineView(newPrescriptionView, req, persistentService);

        debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());
        handleResponse(req, resp, persistentService, medicineView, newPrescriptionView.getPrescriptionId());
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  private MedicineView getMedicineView(NewPrescriptionView newPrescriptionView,
                                       HttpServletRequest req, PersistentService persistentService) {

    String medicineId = req.getParameter("id");

    MedicineView medicineView;
    if (medicineId != null) {
      medicineView = newPrescriptionView.getMedicineView(medicineId);
      medicineView.setNew(false);
    } else {
      medicineView = new MedicineView(persistentService.generateId());
      medicineView.setNew(true);
      newPrescriptionView.addMedicineView(medicineView);
    }

    int prescriptionId = getPrescriptionId(req, newPrescriptionView);

    int id = newPrescriptionView.getPrescriptionId();
    if (prescriptionId != id) {
      throw new MedicationManagerServletUIException("PrescriptionId parameter: " +
          prescriptionId + " is different from the NewPrescriptionView kept in the session: " + id);
    }

    return medicineView;
  }

  private int getPrescriptionId(HttpServletRequest req, NewPrescriptionView newPrescriptionView) {
    String param = req.getParameter(PRESCRIPTION_ID);
    int prescriptionId;
    if (param != null && !param.isEmpty()) {
      prescriptionId = getIntFromString(param, "param");
    } else {
      prescriptionId = newPrescriptionView.getPrescriptionId();
    }
    return prescriptionId;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletRequest req, HttpServletResponse resp, PersistentService persistentService,
                              MedicineView medicineView, int prescriptionId) throws IOException {
    try {
      ScriptForm scriptForm = new NewMedicineScriptForm(persistentService, medicineView, prescriptionId);
      sendResponse(req, resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
