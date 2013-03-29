package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class Session {

  private final String id;
  private final Map<String, Object> attributes = new HashMap<String, Object>();

  public Session(String id) {
    this.id = id;
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

  public Map<String, Object> getAttributesMap() {
    return new HashMap<String, Object>(attributes);
  }
}
