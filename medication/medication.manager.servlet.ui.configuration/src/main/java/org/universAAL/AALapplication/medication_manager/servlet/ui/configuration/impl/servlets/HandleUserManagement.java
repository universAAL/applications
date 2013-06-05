package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.util.UserManagementParameterHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class HandleUserManagement extends BaseServlet {

  private final Object lock = new Object();
  private DisplayUserManagementHtmlWriterServlet userManagementHtmlWriterServlet;

  private final static String SAVE_PATIENT = "save_patient";

  public HandleUserManagement(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setUserManagementHtmlWriterServlet(
      DisplayUserManagementHtmlWriterServlet userManagementHtmlWriterServlet) {

    this.userManagementHtmlWriterServlet = userManagementHtmlWriterServlet;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      try {
        isServletSet(displayLoginHtmlWriterServlet, "displayLoginHtmlWriterServlet");
        isServletSet(displayErrorPageWriterServlet, "displayErrorPageWriterServlet");
        isServletSet(userManagementHtmlWriterServlet, "userManagementHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        debugSessions(session.getId(), "the servlet doGet/doPost method", getClass());
        Person admin = (Person) session.getAttribute(LOGGED_ADMIN);

        if (admin == null) {
          debugSessions(session.getId(), "Servlet doGet/doPost method (admin is null)", getClass());
          displayLoginHtmlWriterServlet.doGet(req, resp);
          return;
        }

        debugSessions(session.getId(), "Servlet doGet/doPost method (admin is not null", getClass());

        String savePatient = req.getParameter(SAVE_PATIENT);

        if (savePatient == null) {
          throw new MedicationManagerServletUIConfigurationException("Missing expected parameter : " + SAVE_PATIENT);
        }

        UserManagementParameterHandler parameterHandler = new UserManagementParameterHandler(req);

        parameterHandler.validateParameters();


      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

}
