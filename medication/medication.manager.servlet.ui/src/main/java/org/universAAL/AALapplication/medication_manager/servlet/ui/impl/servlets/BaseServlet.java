package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author George Fournadjiev
 */
public abstract class BaseServlet extends HttpServlet {

  private SessionTracking sessionTracking;

  protected BaseServlet(SessionTracking sessionTracking) {
    this.sessionTracking = sessionTracking;
  }


  protected Session getSession(HttpServletRequest req) {
    String id = req.getRequestedSessionId();
    Session session = sessionTracking.getSession(id);
    if (session == null) {
      session = new Session(id);
      sessionTracking.addSession(session);
    }

    return session;
  }

  protected void invalidateSession(String id) {

    Session session = sessionTracking.getSession(id);

    if (session == null) {
      return;
    }

    sessionTracking.removeSession(id);

  }

}
