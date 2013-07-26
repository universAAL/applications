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
public final class CaregiverUserInfo extends UserInfo {

  private boolean isDoctor;
  private final String username;
  private final String password;
  private final String gsmNumber;

  public static final String PASS = "pass";

  public CaregiverUserInfo(int id, String uri, String name, String username, String gsmNumber) {
    super(id, uri, name);

    validateParameter(username, "username");
    validateParameter(gsmNumber, "gsmNumber");

    this.username = username;
    this.gsmNumber = gsmNumber;
    this.password = PASS; //TODO to be fixed when UniversAAL can provide username and password
  }

  public boolean isDoctor() {
    return isDoctor;
  }

  public void setDoctor(boolean doctor) {
    isDoctor = doctor;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getGsmNumber() {
    return gsmNumber;
  }

  @Override
  public String toString() {
    return "Caregiver{" +
        "isDoctor=" + isDoctor +
        ", id=" + getId() +
        ", uri=" + getUri() +
        ", name=" + getName() +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", gsmNumber='" + gsmNumber + '\'' +
        '}';
  }
}
