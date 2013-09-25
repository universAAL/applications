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

import org.universAAL.ontology.health.owl.Treatment;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author George Fournadjiev
 */
public class MedicationTreatment extends Treatment {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "MedicationTreatment";
  public static final String PROP_PRESCRIPTION_ID = MedicationOntology.NAMESPACE + "prescription_id";
  public static final String PROP_DOCTOR_NAME = MedicationOntology.NAMESPACE + "doctorName";
  public static final String PROP_MEDICATION_TREATMENT_START_DATE = MedicationOntology.NAMESPACE + "medicationTreatmentStartDate";
  public static final String PROP_MEDICINE = MedicationOntology.NAMESPACE + "medicine";

  public MedicationTreatment() {
    super();
  }

  public MedicationTreatment(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }

  public int getPrescriptionId() {
    return (Integer) getProperty(PROP_PRESCRIPTION_ID);
  }

  public void setPrescriptionId(int id) {
    setProperty(PROP_PRESCRIPTION_ID, id);
  }

  public String getDoctorName() {
    return (String) getProperty(PROP_DOCTOR_NAME);
  }

  public void setDoctorName(String doctorName) {
    setProperty(PROP_DOCTOR_NAME, doctorName);
  }

  public XMLGregorianCalendar getMedicationTreatmentStartDate() {
    return (XMLGregorianCalendar) getProperty(PROP_MEDICATION_TREATMENT_START_DATE);
  }

  public void setMedicationTreatmentStartDate(XMLGregorianCalendar startDate) {
    setProperty(PROP_MEDICATION_TREATMENT_START_DATE, startDate);
  }

  public Medicine getMedicine() {
    return (Medicine) getProperty(PROP_MEDICINE);
  }

  public void setMedicine(Medicine medicine) {
    setProperty(PROP_MEDICINE, medicine);
  }

  public boolean isWellFormed() {
    return props.containsKey(PROP_NAME) && props.containsKey(PROP_DESCRIPTION) &&
        props.containsKey(PROP_DOCTOR_NAME) && props.containsKey(PROP_MEDICATION_TREATMENT_START_DATE);
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

}
