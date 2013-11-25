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

package org.universAAL.AALapplication.medication_manager.persistence.layer;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public abstract class UserInfo {

  private int id;
  private final String uri;
  private final String name;
  private boolean presentInDatabase;


  protected UserInfo(int id, String uri, String name) {
    validate(id, uri, name);

    this.id = id;
    this.uri = uri;
    this.name = name;
  }

  private void validate(int id, String uri, String name) {
    validateParameter(id, "id");
    validateParameter(uri, "id");
    validateParameter(name, "id");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isPresentInDatabase() {
    return presentInDatabase;
  }

  public void setPresentInDatabase(boolean presentInDatabase) {
    this.presentInDatabase = presentInDatabase;
  }

  public String getUri() {
    return uri;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
        "id=" + id +
        ", uri='" + uri + '\'' +
        ", name='" + name + '\'' +
        ", presentInDatabase=" + presentInDatabase +
        '}';
  }
}
