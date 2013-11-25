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

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class HandleMedicineServlet extends BaseServlet {

  public static final String SAVE_MEDICINE = "save_medicine";
  public static final String ID = "id";
  public static final String DAYS = "days";
  public static final String DESCRIPTION = "description";
  public static final String NAME = "name";
  public static final String MEAL_RELATION = "meal_relation";
  public static final String INCOMPLIANCES = "incompliances";
  public static final String SIDE_EFFECTS = "side_effects";
  public static final String UNIT = "unit";
  public static final String HOURS = "hours";
  public static final String CANCEL = "cancel";
  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet;
  private NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet;
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;

  public HandleMedicineServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setDisplayLoginHtmlWriterServlet(DisplayLoginHtmlWriterServlet displayLoginHtmlWriterServlet) {
    this.displayLoginHtmlWriterServlet = displayLoginHtmlWriterServlet;
  }

  public void setNewPrescriptionHtmlWriterServlet(NewPrescriptionHtmlWriterServlet newPrescriptionHtmlWriterServlet) {
    this.newPrescriptionHtmlWriterServlet = newPrescriptionHtmlWriterServlet;
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {

      try {
        isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
        isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");
        isServletSet(newPrescriptionHtmlWriterServlet, "newPrescriptionHtmlWriterServlet");

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

        NewPrescriptionView newPrescriptionView = (NewPrescriptionView) session.getAttribute(PRESCRIPTION_VIEW);

        if (newPrescriptionView == null) {
          debugSessions(session.getId(), "if(newPrescriptionView is null) the servlet doGet/doPost method", getClass());
          newPrescriptionHtmlWriterServlet.doGet(req, resp);
          return;
        }

        String cancel = req.getParameter(CANCEL);
        if (cancel != null && cancel.trim().equalsIgnoreCase(TRUE)) {
          String id = getNotNullParameter(req, ID);
          debugSessions(session.getId(), "cancel medicine id=" + id + " - the servlet doGet/doPost method", getClass());
          cancel(req, newPrescriptionView, id);
          debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());
          newPrescriptionHtmlWriterServlet.doGet(req, resp);
          return;
        }

        String deletedId = req.getParameter(DELETE_MEDICINE_VIEW_ID);
        if (deletedId != null) {
          debugSessions(session.getId(), "delete medicine id=" + deletedId + " - the servlet doGet/doPost method", getClass());
          newPrescriptionView.removeMedicineView(deletedId);
          debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());
          newPrescriptionHtmlWriterServlet.doGet(req, resp);
          return;
        }

        String saveMedicine = req.getParameter(SAVE_MEDICINE);
        if (saveMedicine != null) {
          saveMedicine(req, newPrescriptionView);
          debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());
          newPrescriptionHtmlWriterServlet.doGet(req, resp);
          return;
        }
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }

  }

  private void cancel(HttpServletRequest req, NewPrescriptionView newPrescriptionView, String id) {

    MedicineView medicineView = newPrescriptionView.getMedicineView(id);

    if (medicineView.isNew()) {
      newPrescriptionView.removeMedicineView(id);
    }
  }

  private void saveMedicine(HttpServletRequest req, NewPrescriptionView newPrescriptionView) {

    String id = getNotNullParameter(req, ID);
    MedicineView medicineView = newPrescriptionView.getMedicineView(id);
    String daysText = getNotNullParameter(req, DAYS);
    medicineView.setDays(daysText);
    String description = req.getParameter(DESCRIPTION);
    description = escapeNewLinesAndSingleQuotes(description);
    medicineView.setDescription(description);
    String name = getNotNullParameter(req, NAME);
    medicineView.setName(name);
    String mealRelation = getNotNullParameter(req, MEAL_RELATION);
    medicineView.setMealRelationDTO(mealRelation);
    String incompliances = req.getParameter(INCOMPLIANCES);
    incompliances = escapeNewLinesAndSingleQuotes(incompliances);
    medicineView.setIncompliances(incompliances);
    String sideeffects = req.getParameter(SIDE_EFFECTS);
    sideeffects = escapeNewLinesAndSingleQuotes(sideeffects);
    medicineView.setSideeffects(sideeffects);
    String unitText = getNotNullParameter(req, UNIT);
    String doseText = getNotNullParameter(req, "dose");
    int dose = getPositiveNumber(doseText, "doseText");
    String missed = req.getParameter(MISSED);

    if (MISSED.equals(missed)) {
      medicineView.setMissedIntakeAlert(true);
    }

    String doseAlert = req.getParameter(NEW_DOSE);

    if (NEW_DOSE.equals(doseAlert)) {
      medicineView.setNewDoseAlert(true);
    }

    String shortage = req.getParameter(SHORTAGE);

    if (SHORTAGE.equals(shortage)) {
      medicineView.setShortageAlert(true);
    }


    String[] hours = req.getParameterValues(HOURS);
    if (hours == null) {
      throw new MedicationManagerServletUIException("Missing parameter with the following name: " + HOURS);
    }

    medicineView.fillIntakeDTOSet(dose, hours, unitText);

  }

  private String getNotNullParameter(HttpServletRequest req, String paramName) {
    String parameter = req.getParameter(paramName);
    if (parameter == null) {
      throw new MedicationManagerServletUIException("Missing parameter with the following name: " + paramName);
    }
    return parameter;
  }

}
