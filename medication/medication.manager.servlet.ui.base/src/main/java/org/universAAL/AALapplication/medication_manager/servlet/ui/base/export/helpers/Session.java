package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class Session {

  private final String id;
  private final Map<String, Object> attributes = new HashMap<String, Object>();
  private Date lastAccessedTime;

  public Session(String id) {
    this.id = id;
    lastAccessedTime = new Date();
  }

  public String getId() {
    return id;
  }

  public Object getAttribute(String attributeName) {
    return attributes.get(attributeName);
  }

  public void setAttribute(String attributeName, Object value) {
    attributes.put(attributeName, value);
  }

  public void invalidate() {
    attributes.clear();
  }

  public void removeAttribute(String key) {
    attributes.remove(key);
  }

  public void setNowAsLastAccessedTime() {
    this.lastAccessedTime = new Date();
  }

  public Date getLastAccessedTime() {
    return lastAccessedTime;
  }

  public Map<String, Object> getAttributesMap() {
    return new HashMap<String, Object>(attributes);
  }

  public void printSession(DebugWriter debugWriter) {
    debugWriter.println("Printing attributes for session with id:" + id);
    printAttributes(debugWriter);
    debugWriter.println("End printing attributes for session with id:" + id);
  }

  private void printAttributes(DebugWriter debugWriter) {
    Collection<String> keys = attributes.keySet();
    for (String k : keys) {
      Object value = attributes.get(k);
      debugWriter.println("key = " + k + " | value = " + value);
    }
  }
}
