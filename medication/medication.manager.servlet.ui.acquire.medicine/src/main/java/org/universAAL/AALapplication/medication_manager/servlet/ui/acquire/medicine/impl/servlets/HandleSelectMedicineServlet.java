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


/**
 * @author George Fournadjiev
 */
public final class HandleSelectMedicineServlet extends BaseServlet {


  private final Object lock = new Object();
  private SelectUserHtmlWriterServlet selectUserHtmlWriterServlet;
  private DisplayLoginHtmlWriterServlet displayLogin;

  public HandleSelectMedicineServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setSelectUserHtmlWriterServlet(SelectUserHtmlWriterServlet selectUserHtmlWriterServlet) {
    this.selectUserHtmlWriterServlet = selectUserHtmlWriterServlet;
  }

  public void setDisplayLogin(DisplayLoginHtmlWriterServlet displayLogin) {
    this.displayLogin = displayLogin;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(selectUserHtmlWriterServlet, "selectUserHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        debugSessions(session.getId(), "the servlet doGet/doPost method", getClass());
        Person caregiver = (Person) session.getAttribute(LOGGED_CAREGIVER);

        String back = req.getParameter(BACK);

        if (back != null && back.equalsIgnoreCase(TRUE)) {
          debugSessions(session.getId(), "cancel button (invalidate) the servlet doGet/doPost method", getClass());
          selectUserHtmlWriterServlet.doGet(req, resp);
          return;
        }

        if (caregiver != null) {
          debugSessions(session.getId(), "End of the servlet doGet/doPost method (caregiver is not null", getClass());
          resp.getWriter().println("uraaaaaaaaaaa");
        } else {
          debugSessions(session.getId(), "End of the servlet doGet/doPost method (caregiver is null)", getClass());
          invalidateSession(req, resp);
          displayLogin.doGet(req, resp);
        }
      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

}
