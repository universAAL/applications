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

import org.universAAL.middleware.owl.ManagedIndividual;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public class NewPrescription extends ManagedIndividual {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "NewPrescription";
  public static final String PROP_PRESCRIPTION_ID = MedicationOntology.NAMESPACE + "prescription_id";
  public static final String PROP_DESCRIPTION = MedicationOntology.NAMESPACE + "description";
  public static final String PROP_DATE = MedicationOntology.NAMESPACE + "NewPrescriptionDate";
  public static final String PROP_MEDICATION_TREATMENTS = MedicationOntology.NAMESPACE + "medicationTreatments";

  public NewPrescription() {
    super();
  }

  public NewPrescription(String uri) {
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

  public String getDescription() {
    return (String) props.get(PROP_DESCRIPTION);
  }

  public void setDescription(String newPrescriptionDescription) {
      props.put(PROP_DESCRIPTION, newPrescriptionDescription);
  }

  public XMLGregorianCalendar getDate() {
    return (XMLGregorianCalendar) getProperty(PROP_DATE);
  }

  public void setDate(XMLGregorianCalendar startDate) {
    setProperty(PROP_DATE, startDate);
  }

  public List<MedicationTreatment> getMedicationTreatments() {
    Object value = getProperty(PROP_MEDICATION_TREATMENTS);
    if (value instanceof MedicationTreatment) {
      List<MedicationTreatment> medicines = new ArrayList<MedicationTreatment>();
      medicines.add((MedicationTreatment) value);
      return medicines;
    }
    return (List<MedicationTreatment>) value;
  }

  public void setMedicationTreatments(List<MedicationTreatment> medicationTreatments) {
    if (medicationTreatments == null || medicationTreatments.isEmpty()) {
      return;
    }
    if (medicationTreatments.size() > 1) {
      setProperty(PROP_MEDICATION_TREATMENTS, medicationTreatments);
    } else {
      MedicationTreatment treatment = medicationTreatments.get(0);
      setProperty(PROP_MEDICATION_TREATMENTS, treatment);
    }
  }

  public boolean isWellFormed() {
    return props.containsKey(PROP_PRESCRIPTION_ID) && props.containsKey(PROP_DESCRIPTION) &&
        props.containsKey(PROP_MEDICATION_TREATMENTS) && props.containsKey(PROP_DATE);

  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

}
