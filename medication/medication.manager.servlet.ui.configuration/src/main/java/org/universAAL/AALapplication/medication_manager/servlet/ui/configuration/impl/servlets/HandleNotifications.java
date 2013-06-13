package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


        PersistentService persistentService = getPersistentService();

        //TODO

        displayNotificationsHtmlWriterServlet.doGet(req, resp);

      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


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
