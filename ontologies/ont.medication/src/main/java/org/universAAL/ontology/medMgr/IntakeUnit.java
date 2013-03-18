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
public class IntakeUnit extends ManagedIndividual {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Unit";

  public static final int PILL = 0;
  public static final int DROPS = 1;

  private static final String[] names = {"pill", "drops"};

  public static final IntakeUnit INTAKE_UNIT_PILL = new IntakeUnit(PILL);
  public static final IntakeUnit INTAKE_UNIT_DROPS = new IntakeUnit(DROPS);


  public String getClassURI() {
    return MY_URI;
  }

  public static IntakeUnit getIntakeUnitByOrder(int order) {
    switch (order) {
      case PILL:
        return INTAKE_UNIT_PILL;
      case DROPS:
        return INTAKE_UNIT_DROPS;
      default:
        return null;
    }
  }

  public static final IntakeUnit valueOf(String name) {
    if (name == null) {
      return null;
    }

    if (name.startsWith(MedicationOntology.NAMESPACE)) {
      name = name.substring(MedicationOntology.NAMESPACE.length());
    }

    for (int i = 0; i < names.length; i++) {
      if (names[i].equalsIgnoreCase(name)) {
        return getIntakeUnitByOrder(i);
      }
    }

    return null;
  }

  private int order = 0;

  private IntakeUnit(int order) {
    super(MedicationOntology.NAMESPACE + names[order]);
    this.order = order;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_OPTIONAL;
  }

  public boolean isWellFormed() {
    return true;
  }

  public String name() {
    return names[order];
  }

  public int ord() {
    return order;
  }


}
