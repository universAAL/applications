package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;

/**
 * @author George Fournadjiev
 */
public enum MealRelation {

  BEFORE("BEFORE"),
  WITH_MEAL("WITH_MEAL"),
  AFTER("AFTER"),
  ANY("ANY");

  private String mealRelation;

  private MealRelation(String mealRelation) {
    this.mealRelation = mealRelation;
  }

  public String getValue() {
    return mealRelation;
  }

  public static MealRelation getEnumValueFor(String mealRelationText) {

    for (MealRelation mealRelationEnum : values()) {
      if (mealRelationEnum.mealRelation.equalsIgnoreCase(mealRelationText)) {
        return mealRelationEnum;
      }
    }

    throw new MedicationManagerPersistenceException("Unknown MealRelation enum for value : " + mealRelationText);
  }
}
