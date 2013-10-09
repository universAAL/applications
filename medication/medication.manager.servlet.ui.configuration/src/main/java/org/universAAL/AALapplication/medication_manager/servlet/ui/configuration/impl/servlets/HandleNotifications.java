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

package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.NotificationInfoComplexId;
import org.universAAL.AALapplication.medication_manager.persistence.layer.NotificationsInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.Util;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.ComplexDao;
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
import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class HandleNotifications extends BaseServlet {

  public static final String FALSE = "false";
  public static final String MISSED = "missed";
  public static final String SHORTAGE = "shortage";
  public static final String DOSE = "dose";
  public static final String THRESHOLD = "threshold";
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

        ComplexDao complexDao = persistentService.getComplexDao();

        Set<String> complexIds = (Set<String>) session.getAttribute(COMPLEX_IDS);

        if (complexIds == null) {
          throw new MedicationManagerServletUIConfigurationException("Missing the COMPLEX_IDS parameter");
        }

        for (String id : complexIds) {
          NotificationsInfo info = getNotificationInfo(req, id);
          complexDao.updateNotifications(info);
        }

        displayNotificationsHtmlWriterServlet.doGet(req, resp);

      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

  private NotificationsInfo getNotificationInfo(HttpServletRequest req, String id) {
    try {
      Set<String> params = getAllParametersForThatComplexId(req, id);

      NotificationsInfo notificationsInfo = createNotificationsInfo(req, id, params);

      return notificationsInfo;
    } catch (Exception e) {
      throw new MedicationManagerServletUIConfigurationException(e);
    }
  }

  private NotificationsInfo createNotificationsInfo(HttpServletRequest req, String id, Set<String> params) {

    if (params.isEmpty()) {
      throw new MedicationManagerServletUIConfigurationException("Unable to create NotificationsInfo object for the" +
          "following complexId : " + id);
    }

    boolean missed = false;
    boolean shortage = false;
    boolean dose = false;
    int threshold = -1;

    for (String name : params) {
      String value = req.getParameter(name);
      if (name.contains(MISSED)) {
        missed = Boolean.valueOf(value);
      } else if (name.contains(SHORTAGE)) {
        shortage = Boolean.valueOf(value);
      } else if (name.contains(DOSE)) {
        dose = Boolean.valueOf(value);
      } else if (name.contains(THRESHOLD)) {
        threshold = getThreshold(value);
      } else {
        throw new MedicationManagerServletUIConfigurationException("Unexpected parameter name : " + name);
      }

    }

    NotificationInfoComplexId complexId = Util.decodeComplexId(id);

    return new NotificationsInfo(complexId, missed, threshold, shortage, dose);
  }

  private int getThreshold(String value) {
    int threshold = Integer.parseInt(value);
    if (threshold < 0) {
      throw new MedicationManagerServletUIConfigurationException("The threshold parameter must be positive number");
    }
    return threshold;
  }

  private Set<String> getAllParametersForThatComplexId(HttpServletRequest req, String id) {
    Set<String> params = new HashSet<String>();
    try {
      Enumeration en = req.getParameterNames();
      while (en.hasMoreElements()) {
        String paramName = (String) en.nextElement();
        paramName = paramName.trim();
        if (paramName.startsWith(id)) {
          params.add(paramName);
        }
      }
    } catch (Exception e) {
      throw new MedicationManagerServletUIConfigurationException(e);
    }

    return params;
  }

}
