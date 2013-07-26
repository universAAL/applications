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

package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.Log;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.MedicationManagerServletUIBaseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author George Fournadjiev
 */
public final class SessionTracking {

  private final DebugWriter debugWriter;
  private final Map<String, Session> sessionMap = new HashMap<String, Session>();
  private final Object lock = new Object();
  private final long httpSessionExpireTimeoutInMinutes;

  public SessionTracking(final long httpSessionExpireTimeoutInMinutes, DebugWriter debugWriter,
                         int httpSessionTimerCheckerIntervalInMinutes) {

    this.debugWriter = debugWriter;
    this.httpSessionExpireTimeoutInMinutes = httpSessionExpireTimeoutInMinutes;
    Timer timerSessionCleaner = new Timer("Session Cleaner");

    int delay = httpSessionTimerCheckerIntervalInMinutes * 60 * 1000;
    timerSessionCleaner.schedule(new TimerTask() {
      @Override
      public void run() {
        cleanSessions();
      }
    }, delay, delay);

  }

  private void cleanSessions() {
    synchronized (lock) {
      debugWriter.println("\n\n\n&&&&&&&&& Start session cleaning. Printing sessions currently available &&&&&&&&&&&&");
      printSessions();
      List<Session> sessionCollection = new ArrayList<Session>(sessionMap.values());
      for (Session session : sessionCollection) {
        if (isExpired(session)) {
          String id = session.getId();
          Log.info("Session with id:%s is expired and will be removed", getClass(), id);
          debugWriter.println("Session with id:" + id + " is expired and will be removed");
          removeSession(id);
        }
      }
      debugWriter.println("\n\n\n&&&&&&&&& End session cleaning. Printing sessions currently available &&&&&&&&&&&&");
    }
  }

  private boolean isExpired(Session session) {
    Date now = new Date();

    Date lastAccessedTime = session.getLastAccessedTime();

    long minutesDifference = (now.getTime() - lastAccessedTime.getTime()) / (60 * 1000);

    debugWriter.println("\nChecking the minutes between last access time and now for session:");
    session.printSession(debugWriter);
    debugWriter.println("\n\tminutesDifference = " + minutesDifference);

    return minutesDifference - httpSessionExpireTimeoutInMinutes >= 0;

  }

  public void addSession(Session session) {
    String id = session.getId();

    synchronized (lock) {
      if (!hasSession(id)) {
        Log.info("Adding session with id:%s", getClass(), id);
        debugWriter.println("Adding session with id:" + id);
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
    Log.info("Getting the session with id:%s", getClass(), id);
    debugWriter.println("Getting the session with id:" + id);
    synchronized (lock) {
      if (id == null) {
        throw new MedicationManagerServletUIBaseException("session id is null");
      }
      Session session = sessionMap.get(id);
      if (session != null) {
        session.setNowAsLastAccessedTime();
        session.printSession(debugWriter);
      }
      return session;
    }
  }

  public void removeSession(String id) {
    synchronized (lock) {
      removeSessionFromMap(id);
    }
  }

  private void removeSessionFromMap(String id) {
    Log.info("Removing session with id:%s", getClass(), id);
    debugWriter.println("Removing session with id:" + id);
    Session session = sessionMap.remove(id);
    if (session != null) {
      session.printSession(debugWriter);
      session.invalidate();
    }
  }

  public void printSessionsAndMarkTheActive(String id) {

    synchronized (lock) {
      Collection<Session> sessions = sessionMap.values();
      debugWriter.println("sessions.size() = " + sessions.size());
      for (Session ses : sessions) {
        if (ses.getId().equalsIgnoreCase(id)) {
          debugWriter.println("^^^^^^^^^^^^ Start Active Session Debugging ^^^^^^^^^^^^^^^^^^^^^");
          debugWriter.println("Active session : " + id);
        }
        printSession(ses);
        if (ses.getId().equalsIgnoreCase(id)) {
          debugWriter.println("^^^^^^^^^^^^^^^ End Active Session Debugging^^^^^^^^^^^^^^^^^^^^^^");
        }
      }
    }

  }

  public void printSessions() {

    synchronized (lock) {
      Collection<Session> sessions = sessionMap.values();
      debugWriter.println("sessions.size() = " + sessions.size());
      for (Session ses : sessions) {
        printSession(ses);
      }
    }

  }

  private void printSession(Session ses) {
    debugWriter.println("Printing attributes for session with id: " + ses.getId());
    printAttributes(ses.getAttributesMap());
    debugWriter.println("End printing attributes for session with id: " + ses.getId());
  }

  private void printAttributes(Map<String, Object> attributesMap) {
    Collection<String> keys = attributesMap.keySet();
    for (String k : keys) {
      Object value = attributesMap.get(k);
      debugWriter.println("key = " + k + " | value = " + value);
    }
  }

  public DebugWriter getDebugWriter() {
    return debugWriter;
  }
}
