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

import org.universAAL.AALapplication.medication_manager.configuration.Pair;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicationPropertiesDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.MedicationManagerServletUIConfigurationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class HandleParameters extends BaseServlet {

  public static final String FALSE = "false";
  private final Object lock = new Object();
  private DisplayParametersHtmlWriterServlet parametersHtmlWriterServlet;

  private final static String SAVE = "save";

  public HandleParameters(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setDisplayParametersHtmlWriterServlet(
      DisplayParametersHtmlWriterServlet userManagementHtmlWriterServlet) {

    this.parametersHtmlWriterServlet = userManagementHtmlWriterServlet;
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
        isServletSet(parametersHtmlWriterServlet, "parametersHtmlWriterServlet");

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

        Set<Integer> ids = (Set<Integer>) session.getAttribute(IDS);

        if (ids == null) {
          throw new MedicationManagerServletUIConfigurationException("Missing IDS attribute");
        }

        Set<Pair<Integer, String>> propertyValues = new HashSet<Pair<Integer, String>>();

        for (int id : ids) {
          String propValue = req.getParameter(String.valueOf(id));
          if (propValue != null) {
            Pair<Integer, String> pair = new Pair<Integer, String>(id, propValue);
            propertyValues.add(pair);
          } else {
            Pair<Integer, String> pair = new Pair<Integer, String>(id, FALSE);
            propertyValues.add(pair);
          }
        }

        PersistentService persistentService = getPersistentService();

        MedicationPropertiesDao medicationPropertiesDao = persistentService.getMedicationPropertiesDao();

        medicationPropertiesDao.updatePropertiesValues(propertyValues);

        parametersHtmlWriterServlet.doGet(req, resp);

      } catch (Exception e) {
        Log.error(e.fillInStackTrace(), "Unexpected Error occurred", getClass());
        sendErrorResponse(req, resp, e);
      }

    }


  }

}
