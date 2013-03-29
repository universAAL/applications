package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.DebugWriter;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public abstract class BaseServlet extends HttpServlet {

  public static final String MISSING_MESSAGE = "Missing message!";
  private final SessionTracking sessionTracking;

  public static final int N = 1000000;
  public static final String COOKIE_NAME = "medication_manager_session_id";
  public static final Random RANDOM = new Random();
  private static final DebugWriter DEBUG_WRITER = new DebugWriter();
  private DisplayErrorPageWriterServlet displayErrorPageWriterServlet;

  private static String previousRequestedId;

  protected BaseServlet(SessionTracking sessionTracking) {
    this.sessionTracking = sessionTracking;
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
    DEBUG_WRITER.println("\n\n\n\n\n\n");
    DEBUG_WRITER.println("Calling Servlet class: " + cl.getSimpleName());
    DEBUG_WRITER.println("### HEADERS ###");
    Enumeration en = req.getHeaderNames();
    while (en.hasMoreElements()) {
      String name = (String) en.nextElement();
      String value = req.getHeader(name);
      String line = name + ": " + value;
      DEBUG_WRITER.println(line);
    }
    DEBUG_WRITER.println("### END HEADERS ###");

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
    DEBUG_WRITER.println("\n\n");
    DEBUG_WRITER.println("&&&&& Debug Sessions from servlet name: " +
        cl.getSimpleName() + "| place: " + servletPlace + " &&&&&");
    DEBUG_WRITER.println("previousRequestedId = " + previousRequestedId);
    DEBUG_WRITER.println("id = " + id);
    sessionTracking.printSessions(DEBUG_WRITER, id);
    DEBUG_WRITER.println("&&&&& END Debug Sessions &&&&&");
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
    DEBUG_WRITER.println("cookie = " + cookie);
    if (cookie != null) {
      DEBUG_WRITER.println("cookie = " + cookie.getName() + "|" + cookie.getValue());
      return cookie.getValue();
    }
    return null;
  }

  private Cookie getCookie(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();

    DEBUG_WRITER.println("cookies = " + cookies);

    if (cookies == null || cookies.length == 0) {
      DEBUG_WRITER.println("cookies are null or empty");
      return null;
    }

    for (Cookie cookie : cookies) {
      String name = cookie.getName();
      DEBUG_WRITER.println("name = " + name);
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
