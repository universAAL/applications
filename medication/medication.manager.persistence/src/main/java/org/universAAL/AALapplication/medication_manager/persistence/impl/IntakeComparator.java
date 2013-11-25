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

package org.universAAL.AALapplication.medication_manager.persistence.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;

import java.util.Comparator;
import java.util.Date;

/**
 * @author George Fournadjiev
 */
public final class IntakeComparator implements Comparator<Intake> {

  public int compare(Intake int1, Intake int2) {

    Date int1TimePlan = int1.getTimePlan();
    Date int2TimePlan = int2.getTimePlan();

    return int1TimePlan.compareTo(int2TimePlan);
  }
}
