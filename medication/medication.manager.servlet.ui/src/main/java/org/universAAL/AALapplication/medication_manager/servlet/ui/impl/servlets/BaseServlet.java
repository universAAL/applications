package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author George Fournadjiev
 */
public abstract class BaseServlet extends HttpServlet {

  private SessionTracking sessionTracking;

  protected BaseServlet(SessionTracking sessionTracking) {
    this.sessionTracking = sessionTracking;
  }


  protected HttpSession getSession(HttpServletRequest req) {
    String id = req.getRequestedSessionId();
    HttpSession session = sessionTracking.getSession(id);
    if (session == null) {
      session = req.getSession(true);
      sessionTracking.addSession(session);
    }

    return session;
  }

  protected void invalidateSession(String id) {

    HttpSession session = sessionTracking.getSession(id);

    if (session == null) {
      return;
    }

    sessionTracking.removeSession(id);

  }

}
