package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class SessionTracking {

  private final Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
  private final Object lock = new Object();


  public void addSession(HttpSession session) {
    String id = session.getId();
    synchronized (lock) {
      if (!hasSession(id)) {
         sessionMap.put(id, session);
      }
    }
  }

  public boolean hasSession(String id) {
    synchronized (lock) {
      return sessionMap.containsKey(id);
    }
  }

  public HttpSession getSession(String id) {
    synchronized (lock) {
      return sessionMap.get(id);
    }
  }

  public void removeSession(String id) {
    synchronized (lock) {
      HttpSession session = sessionMap.remove(id);
      if (session != null) {
        session.invalidate();
      }
    }
  }
}
