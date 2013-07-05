package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.Util.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;


/**
 * @author George Fournadjiev
 */
public final class HandleSuccessMedicineServlet extends BaseServlet {


  private final Object lock = new Object();
  private SelectMedicineHtmlWriterServlet selectMedicineHtmlWriterServlet;
  private DisplayLoginHtmlWriterServlet displayLogin;

  public HandleSuccessMedicineServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setDisplayLogin(DisplayLoginHtmlWriterServlet displayLogin) {
    this.displayLogin = displayLogin;
  }

  public void setSelectMedicineHtmlWriterServlet(SelectMedicineHtmlWriterServlet selectMedicineHtmlWriterServlet) {
    this.selectMedicineHtmlWriterServlet = selectMedicineHtmlWriterServlet;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(displayLogin, "displayLogin");
        isServletSet(selectMedicineHtmlWriterServlet, "selectMedicineHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        debugSessions(session.getId(), "the servlet doGet/doPost method", getClass());
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        if (caregiver == null) {
          debugSessions(session.getId(), "cancel button (invalidate) the servlet doGet/doPost method", getClass());
          invalidateSession(req, resp);
          displayLogin.doGet(req, resp);
          return;
        }

        String back = req.getParameter(BACK);

        if (back != null && back.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "back button (invalidate) the servlet doGet/doPost method", getClass());
          selectMedicineHtmlWriterServlet.doGet(req, resp);
          return;
        }

        String cancel = req.getParameter(CANCEL);

        if (cancel != null && cancel.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "cancel button (invalidate) the servlet doGet/doPost method", getClass());
          invalidateSession(req, resp);
          displayLogin.doGet(req, resp);
          return;
        }


      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

}
