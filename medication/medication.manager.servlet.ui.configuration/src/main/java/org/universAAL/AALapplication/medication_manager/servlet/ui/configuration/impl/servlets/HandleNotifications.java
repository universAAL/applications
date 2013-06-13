package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class HandleNotifications extends BaseServlet {

  public static final String FALSE = "false";
  private final Object lock = new Object();
  private DisplayNotificationsHtmlWriterServlet displayNotificationsHtmlWriterServlet;

  private final static String SAVE = "save";

  public HandleNotifications(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setDisplayNotificationsHtmlWriterServlet(
      DisplayNotificationsHtmlWriterServlet displayNotificationsHtmlWriterServlet) {

    this.displayNotificationsHtmlWriterServlet = displayNotificationsHtmlWriterServlet;
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
        isServletSet(displayNotificationsHtmlWriterServlet, "displayNotificationHtmlWriterServlet");

        Session session = getSession(req, resp, getClass());
        debugSessions(session.getId(), "the servlet doGet/doPost method", getClass());
        Person admin = (Person) session.getAttribute(LOGGED_ADMIN);

        if (admin == null) {
          debugSessions(session.getId(), "Servlet doGet/doPost method (admin is null)", getClass());
          displayLoginHtmlWriterServlet.doGet(req, resp);
          return;
        }

        debugSessions(session.getId(), "Servlet doGet/doPost method (admin is not null", getClass());

        String save = req.getParameter(SAVE);

        if (save == null) {
          throw new MedicationManagerServletUIConfigurationException("Missing expected parameter : " + SAVE);
        }

        PersistentService persistentService = getPersistentService();

        Set<String> complexIds = (Set<String>) session.getAttribute(COMPLEX_IDS);

        if (complexIds == null) {
          throw new MedicationManagerServletUIConfigurationException("Missing the COMPLEX_IDS parameter");
        }

        for (String id : complexIds) {
          String param = getParameter(req, id);
          System.out.println("id = " + id + " | param = " + param);
        }

        displayNotificationsHtmlWriterServlet.doGet(req, resp);

      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

  private String getParameter(HttpServletRequest req, String id) {

    try {
      Enumeration en = req.getParameterNames();
      while (en.hasMoreElements()) {
        String paramName = (String) en.nextElement();
        paramName = paramName.trim();
        if (paramName.startsWith(id)) {
          return paramName;
        }
      }
    } catch (Exception e) {
      throw new MedicationManagerServletUIConfigurationException(e);
    }

    return null;

  }

  /*
  name = 1_12:missed | value = true
  name = save | value = Save
  name = 10_15:missed | value = true
  name = 1_12:shortage | value = true
  name = 10_15:shortage | value = true
  name = 10_15:dose | value = true
  name = 1_12:threshold | value = 5
  name = 10_15:threshold | value = 10
  name = 1_12:dose | value = true
   */

}
