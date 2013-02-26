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

/**
 * @author George Fournadjiev
 */
public class Intake extends ManagedIndividual {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Intake";

  public static final String PROP_TIME = MedicationOntology.NAMESPACE + "time";

  public static final String PROP_DOSE = MedicationOntology.NAMESPACE + "dose";

  public static final String PROP_UNIT = MedicationOntology.NAMESPACE + "unit";

  public Intake() {
    super();
  }

  public Intake(String uri) {
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

  public String getTime() {
    return (String) props.get(PROP_TIME);
  }

  public void setTime(String time) {
    props.put(PROP_TIME, time);
  }

  public int getDose() {
    return (Integer) props.get(PROP_DOSE);
  }

  public void setDose(int dose) {
    props.put(PROP_DOSE, dose);
  }

  public IntakeUnit getUnit() {
    return (IntakeUnit) props.get(PROP_UNIT);
  }

  public void setUnit(IntakeUnit intakeUnit) {
    props.put(PROP_UNIT, intakeUnit);
  }

}
