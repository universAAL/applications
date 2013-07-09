package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.MedicationManagerReviewException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;


/**
 * @author George Fournadjiev
 */
public final class HandleSelectUserServlet extends BaseServlet {


  private final Object lock = new Object();
  private DisplayLoginHtmlWriterServlet displayServlet;
  private DisplayIntakesHtmlWriterServlet displayIntakesHtmlWriterServlet;

  public HandleSelectUserServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  public void setDisplayIntakesHtmlWriterServlet(DisplayIntakesHtmlWriterServlet displayIntakesHtmlWriterServlet) {
      this.displayIntakesHtmlWriterServlet = displayIntakesHtmlWriterServlet;
    }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(displayServlet, "displayServlet");
        isServletSet(displayIntakesHtmlWriterServlet, "displayIntakesHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        debugSessions(session.getId(), "the servlet doGet/doPost method", getClass());
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        String cancel = req.getParameter(CANCEL);

        if (cancel != null && cancel.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "cancel button (invalidate) the servlet doGet/doPost method", getClass());
          invalidateSession(req, resp);
          displayServlet.doGet(req, resp);
          return;
        }

        if (caregiver != null) {
          setPatient(req, session);
          debugSessions(session.getId(), "End of the servlet doGet/doPost method (caregiver is not null", getClass());
          displayIntakesHtmlWriterServlet.doGet(req, resp);
          return;
        } else {
          debugSessions(session.getId(), "End of the servlet doGet/doPost method (caregiver is null)", getClass());
          displayServlet.doGet(req, resp);
        }
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

  private void setPatient(HttpServletRequest req, Session session) {
    String idText = req.getParameter(USER);
    if (idText == null) {
      throw new MedicationManagerReviewException("Missing expected parameter : " + USER);
    }

    try {
      int id = Integer.parseInt(idText);
      PersistentService persistentService = getPersistentService();
      PersonDao personDao = persistentService.getPersonDao();
      Person patient = personDao.getById(id);
      session.setAttribute(PATIENT, patient);
    } catch (NumberFormatException e) {
      throw new MedicationManagerReviewException(e);
    }
  }


}
