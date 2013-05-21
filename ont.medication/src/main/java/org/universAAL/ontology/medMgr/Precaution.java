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
public class Precaution extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Precaution";
  public static final String PROP_SIDEEFFECT = MedicationOntology.NAMESPACE + "sideeffect";
  public static final String PROP_INCOMPLIANCE = MedicationOntology.NAMESPACE + "incompliance";

  public Precaution() {
    super();
  }

  public Precaution(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }

  public String getSideEffect() {
    return (String) getProperty(PROP_SIDEEFFECT);
  }

  public void setSideEffect(String value) {
    setProperty(PROP_SIDEEFFECT, value);
  }

  public String getIncompliance() {
    return (String) getProperty(PROP_INCOMPLIANCE);
  }

  public void setIncompliance(String value) {
    setProperty(PROP_INCOMPLIANCE, value);
  }

  public boolean isWellFormed() {
    return true;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

}
