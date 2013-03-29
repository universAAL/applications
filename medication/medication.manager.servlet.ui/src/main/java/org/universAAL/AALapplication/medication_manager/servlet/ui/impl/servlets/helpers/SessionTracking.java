package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.util.Collection;
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
      if (id == null) {
        throw new MedicationManagerServletUIException("session id is null");
      }
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

  public void printSessions(DebugWriter debugWriter, String id) {

    synchronized (lock) {
      Collection<Session> sessions = sessionMap.values();
      debugWriter.println("sessions.size() = " + sessions.size());
      for (Session ses : sessions) {
        if (ses.getId().equalsIgnoreCase(id)) {
          debugWriter.println("^^^^^^^^^^^^ Start Active Session Debugging ^^^^^^^^^^^^^^^^^^^^^");
          debugWriter.println("Active session : " + id);
        }
        printSession(debugWriter, ses);
        if (ses.getId().equalsIgnoreCase(id)) {
          debugWriter.println("^^^^^^^^^^^^^^^ End Active Session Debugging^^^^^^^^^^^^^^^^^^^^^^");
        }
      }
    }

  }

  private void printSession(DebugWriter debugWriter, Session ses) {
    debugWriter.println("Printing attributes for session with id: " + ses.getId());
    printAttributes(ses.getAttributesMap(), debugWriter);
    debugWriter.println("End printing attributes for session with id: " + ses.getId());
  }

  private void printAttributes(Map<String, Object> attributesMap, DebugWriter debugWriter) {
    Collection<String> keys = attributesMap.keySet();
    for (String k : keys) {
      Object value = attributesMap.get(k);
      debugWriter.println("key = " + k + " | value = " + value);
    }
  }
}
