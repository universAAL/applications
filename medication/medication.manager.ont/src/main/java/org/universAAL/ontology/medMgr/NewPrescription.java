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
public class NewPrescription extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "NewPrescription";

  public static final String PROP_MEDICATION_TREATMENT = MedicationOntology.NAMESPACE + "medicationTreatment";
  public static final String PROP_RECEIVED_MESSAGE = MedicationOntology.NAMESPACE + "receivedMessage";

  public NewPrescription() {
    super();
  }

  public NewPrescription(String uri) {
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

  public MedicationTreatment getMedicationTreatment() {
    return (MedicationTreatment) props.get(PROP_MEDICATION_TREATMENT);
  }

  public void setMedicationTreatment(MedicationTreatment medicationTreatment) {
    props.put(PROP_MEDICATION_TREATMENT, medicationTreatment);
  }

  public String getReceivedMessage() {
    return (String) props.get(PROP_RECEIVED_MESSAGE);
  }

  public void setReceivedMessage(String message) {
    props.put(PROP_RECEIVED_MESSAGE, message);
  }

}
