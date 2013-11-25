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
