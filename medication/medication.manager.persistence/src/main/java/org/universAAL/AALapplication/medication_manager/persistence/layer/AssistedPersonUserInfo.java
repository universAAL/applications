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

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;

/**
 * @author George Fournadjiev
 */
public final class AssistedPersonUserInfo extends UserInfo {

  private CaregiverUserInfo caregiverUserInfo;
  private CaregiverUserInfo doctor;
  private Dispenser dispenser;

  public AssistedPersonUserInfo(int id, String uri, String name) {
    super(id, uri, name);
  }

  public CaregiverUserInfo getCaregiverUserInfo() {
    return caregiverUserInfo;
  }

  public void setCaregiverUserInfo(CaregiverUserInfo caregiverUserInfo) {
    this.caregiverUserInfo = caregiverUserInfo;
  }

  public CaregiverUserInfo getDoctor() {
    return doctor;
  }

  public void setDoctor(CaregiverUserInfo doctor) {
    this.doctor = doctor;
  }

  public Dispenser getDispenser() {
    return dispenser;
  }

  public void setDispenser(Dispenser dispenser) {
    this.dispenser = dispenser;
  }

  @Override
  public String toString() {
    return "AssistedPerson{" +
        "caregiver=" + caregiverUserInfo +
        ", id=" + getId() +
        ", uri=" + getUri() +
        ", name=" + getName() +
        ", doctor=" + doctor +
        ", dispenser=" + dispenser +
        '}';
  }
}
