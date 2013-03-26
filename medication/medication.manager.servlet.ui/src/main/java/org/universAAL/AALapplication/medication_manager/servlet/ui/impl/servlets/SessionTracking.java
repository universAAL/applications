package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class SessionTracking {

  private final Map<String, Session> sessionMap = new HashMap<String, Session>();
  private final Object lock = new Object();


  public void addSession(Session session) {
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

  public Session getSession(String id) {
    synchronized (lock) {
      return sessionMap.get(id);
    }
  }

  public void removeSession(String id) {
    synchronized (lock) {
      Session session = sessionMap.remove(id);
      if (session != null) {
        session.invalidate();
      }
    }
  }
}
