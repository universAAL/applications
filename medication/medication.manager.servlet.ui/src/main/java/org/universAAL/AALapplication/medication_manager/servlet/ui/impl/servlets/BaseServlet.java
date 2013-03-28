package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

/**
 * @author George Fournadjiev
 */
public abstract class BaseServlet extends HttpServlet {

  public static final int N = 1000000;
  public static final String COOKIE_NAME = "medication_manager_session_id";
  private SessionTracking sessionTracking;
  public static final Random RANDOM = new Random();

  protected BaseServlet(SessionTracking sessionTracking) {
    this.sessionTracking = sessionTracking;
  }


  protected Session getSession(HttpServletRequest req, HttpServletResponse resp) {
    String id = getId(req, resp);
    Session session = sessionTracking.getSession(id);
    if (session == null) {
      session = new Session(id);
      sessionTracking.addSession(session);
    }

    return session;
  }

  private String getId(HttpServletRequest req, HttpServletResponse resp) {

    String id = findIdFromCookie(req);

    if (id == null) {
      id = generateUniqueId();
      Cookie cookie = new Cookie(COOKIE_NAME, id);
      resp.addCookie(cookie);
    }

    return id;
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
    if (cookie != null) {
      return cookie.getValue();
    }
    return null;
  }

  private Cookie getCookie(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();

    if (cookies == null || cookies.length == 0) {
      return null;
    }

    for (Cookie cookie : cookies) {
      String name = cookie.getName();
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

}
