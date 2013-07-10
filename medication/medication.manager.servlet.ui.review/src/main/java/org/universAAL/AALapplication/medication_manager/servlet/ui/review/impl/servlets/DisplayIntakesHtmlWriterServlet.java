package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.DisplayIntakesScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.MedicationManagerReviewException;
import org.universAAL.AALapplication.medication_manager.persistence.layer.Week;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;


/**
 * @author George Fournadjiev
 */
public final class DisplayIntakesHtmlWriterServlet extends BaseHtmlWriterServlet {

  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;
  private static Calendar currentWeekStartDate;

  public DisplayIntakesHtmlWriterServlet(SessionTracking sessionTracking) {
    super(INTAKES_FILE_NAME, sessionTracking);
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    synchronized (lock) {
      try {
        isServletSet(displayServlet, "displayServlet");
        isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        if (caregiver == null) {
          debugSessions(session.getId(), "if(caregiver is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        Person patient = (Person) session.getAttribute(PATIENT);

        if (patient == null) {
          debugSessions(session.getId(), "if(caregiver is null) the servlet doGet/doPost method", getClass());
          displayServlet.doGet(req, resp);
          return;
        }

        String cancel = req.getParameter(CANCEL);

        if (cancel != null && cancel.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "cancel button (invalidate) the servlet doGet/doPost method", getClass());
          invalidateSession(req, resp);
          displayServlet.doGet(req, resp);
          return;
        }

        String back = req.getParameter(BACK);

        if (back != null && back.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "back button (invalidate) the servlet doGet/doPost method", getClass());
          selectUserHtmlWriterServlet.doGet(req, resp);
          return;
        }

        debugSessions(session.getId(), "End of the servlet doGet/doPost method", getClass());

        Week selectedWeek = findButtonAction(req);

        handleResponse(req, patient, resp, selectedWeek);

      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doGet(req, resp);
  }

  private Week findButtonAction(HttpServletRequest req) {
    String prev = req.getParameter(PREV);
    String next = req.getParameter(NEXT);

    if (prev == null && next == null) {
      currentWeekStartDate = getMondayOfTheCurrentWeek();
      return Week.createWeek(currentWeekStartDate);
    }

    boolean isNext = TRUE.equals(next);
    boolean isPrev = TRUE.equals(prev);

    if (isNext && isPrev) {
      throw new MedicationManagerReviewException("The prev and next parameters " +
          "cannot be true at the same time");
    }

    if (isNext) {
      currentWeekStartDate.add(Calendar.DAY_OF_YEAR, 7);
      return Week.createWeek(currentWeekStartDate);
    } else if (isPrev) {

      currentWeekStartDate.add(Calendar.DAY_OF_YEAR, -7);
      return Week.createWeek(currentWeekStartDate);
    }

    throw new MedicationManagerReviewException("Unexpected error. Something wrong with the selected action");
  }

  private Calendar getMondayOfTheCurrentWeek() {
    Calendar now = Calendar.getInstance();
    now.setFirstDayOfWeek(Calendar.MONDAY);
    now.setTime(new Date());
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.MONDAY) {
      now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }
    return now;
  }

  private void handleResponse(HttpServletRequest req, Person patient,
                              HttpServletResponse resp, Week selectedWeek) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new DisplayIntakesScriptForm(persistentService, patient, selectedWeek);
      sendResponse(req, resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(req, resp, e);
    }
  }


}
