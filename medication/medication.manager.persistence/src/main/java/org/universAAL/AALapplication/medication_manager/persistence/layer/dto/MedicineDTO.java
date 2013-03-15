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


package org.universAAL.AALapplication.medication_manager.persistence.layer.dto;

import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class MedicineDTO {

  private final int id;
  private final String name;
  private final int days;
  private final String description;
  private final MealRelationDTO mealRelationDTO;
  private final Set<IntakeDTO> intakeDTOSet;

  public MedicineDTO(int id, String name, int days, String description,
                     MealRelationDTO mealRelationDTO, Set<IntakeDTO> intakeDTOSet) {

    this.id = id;
    this.name = name;
    this.days = days;
    this.description = description;
    this.mealRelationDTO = mealRelationDTO;
    this.intakeDTOSet = intakeDTOSet;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getDays() {
    return days;
  }

  public String getDescription() {
    return description;
  }

  public MealRelationDTO getMealRelationDTO() {
    return mealRelationDTO;
  }

  public Set<IntakeDTO> getIntakeDTOSet() {
    return intakeDTOSet;
  }

  @Override
  public String toString() {
    return "Medicine{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", days=" + days +
        ", description='" + description + '\'' +
        ", mealRelationDTO=" + mealRelationDTO +
        ", intakeSet=" + intakeDTOSet +
        '}';
  }
}
