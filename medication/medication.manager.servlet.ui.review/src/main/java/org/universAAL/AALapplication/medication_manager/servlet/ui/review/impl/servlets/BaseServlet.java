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

package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets;


import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriter;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;


/**
 * @author George Fournadjiev
 */
public abstract class BaseServlet extends HttpServlet {

  public static final String MISSING_MESSAGE = "Missing message!";
  private final SessionTracking sessionTracking;
  private final DebugWriter debugWriter;

  public static final int N = 1000000;
  public static final String COOKIE_NAME = "medication_manager_session_id";
  public static final Random RANDOM = new Random();
  private DisplayErrorPageWriterServlet displayErrorPageWriterServlet;

  private static String previousRequestedId;

  protected BaseServlet(SessionTracking sessionTracking) {
    this.sessionTracking = sessionTracking;
    this.debugWriter = sessionTracking.getDebugWriter();
  }

  public void setDisplayErrorPageWriterServlet(DisplayErrorPageWriterServlet displayErrorPageWriterServlet) {
    this.displayErrorPageWriterServlet = displayErrorPageWriterServlet;
  }


  protected Session getSession(HttpServletRequest req, HttpServletResponse resp, Class cl) {
    String id = getId(req, resp, cl);
    Session session = sessionTracking.getSession(id);
    if (session == null) {
      session = new Session(id);
      sessionTracking.addSession(session);
    }

    return session;
  }

  private String getId(HttpServletRequest req, HttpServletResponse resp, Class cl) {
    debugWriter.println("\n\n\n\n\n\n");
    debugWriter.println("Calling Servlet class: " + cl.getSimpleName());
    debugWriter.println("### HEADERS ###");
    Enumeration en = req.getHeaderNames();
    while (en.hasMoreElements()) {
      String name = (String) en.nextElement();
      String value = req.getHeader(name);
      String line = name + ": " + value;
      debugWriter.println(line);
    }
    debugWriter.println("### END HEADERS ###");

    String id = findIdFromCookie(req);

    debugSessions(id, "Beginning of the servlet doGet/doPost method", cl);

    if (id == null) {
      id = generateUniqueId();
      Cookie cookie = new Cookie(COOKIE_NAME, id);
      resp.addCookie(cookie);
    }
    previousRequestedId = id;
    return id;
  }

  public void debugSessions(String id, String servletPlace, Class cl) {
    debugWriter.println("\n\n");
    debugWriter.println("&&&&& Debug Sessions from servlet name: " +
        cl.getSimpleName() + "| place: " + servletPlace + " &&&&&");
    debugWriter.println("previousRequestedId = " + previousRequestedId);
    debugWriter.println("id = " + id);
    sessionTracking.printSessionsAndMarkTheActive(id);
    debugWriter.println("&&&&& END Debug Sessions &&&&&");
  }

  private String generateUniqueId() {
    String id;
    UUID uuid1 = UUID.randomUUID();
    UUID uuid2 = UUID.randomUUID();
    id = "sessionid:" + uuid1.getMostSignificantBits() + RANDOM.nextInt() +
        uuid2.getMostSignificantBits() + RANDOM.nextInt(N);
    return id;
  }

  private String findIdFromCookie(HttpServletRequest req) {

    Cookie cookie = getCookie(req);
    debugWriter.println("cookie = " + cookie);
    if (cookie != null) {
      debugWriter.println("cookie = " + cookie.getName() + "|" + cookie.getValue());
      return cookie.getValue();
    }
    return null;
  }

  private Cookie getCookie(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();

    debugWriter.println("cookies = " + cookies);

    if (cookies == null || cookies.length == 0) {
      debugWriter.println("cookies are null or empty");
      return null;
    }

    for (Cookie cookie : cookies) {
      String name = cookie.getName();
      debugWriter.println("name = " + name);
      if (name.equalsIgnoreCase(COOKIE_NAME)) {
        return cookie;
      }
    }
    return null;
  }

  protected void invalidateSession(HttpServletRequest req, HttpServletResponse resp) {

    Cookie cookie = getCookie(req);
    String id = cookie.getValue();

    if (id == null) {
      return;
    }

    Session session = sessionTracking.getSession(id);

    if (session == null) {
      return;
    }

    sessionTracking.removeSession(id);

  }

  public void sendErrorResponse(HttpServletRequest request, HttpServletResponse resp, Exception e) throws IOException {
    String message = e.getMessage();
    if (message == null) {
      message = MISSING_MESSAGE;
    }

    Session session = getSession(request, resp, getClass());

    session.setAttribute(ERROR, message);

    displayErrorPageWriterServlet.doGet(request, resp);
  }

}
