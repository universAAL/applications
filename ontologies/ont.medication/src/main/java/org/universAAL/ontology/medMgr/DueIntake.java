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


package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;

/**
 * @author George Fournadjiev
 */
public class DueIntake extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "DueIntake";

  public static final String PROP_DEVICE_URI = MedicationOntology.NAMESPACE + "deviceUri";
  public static final String PROP_TIME = MedicationOntology.NAMESPACE + "time";
  public static final String PROP_PERSON_URI = MedicationOntology.NAMESPACE + "personUri";

  public DueIntake() {
    super();
  }

  public DueIntake(String uri) {
    super(uri);
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public boolean isWellFormed() {
    return true;
  }

  public String getDeviceUri() {
    return (String) props.get(PROP_DEVICE_URI);
  }

  public void setDeviceUri(String deviceId) {
    props.put(PROP_DEVICE_URI, deviceId);
  }

  public Time getTime() {
    return (Time) props.get(PROP_TIME);
  }

  public void setTime(Time time) {
    props.put(PROP_TIME, time);
  }

  public String getPersonUri() {
    return (String) props.get(PROP_PERSON_URI);
  }

  public void setPersonUri(String personId) {
    props.put(PROP_PERSON_URI, personId);
  }

}
