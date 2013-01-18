package org.universAAL.AALapplication.medication_manager.persistence;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Medicine {

  private final int medicineId;
  private final String medicineName;
  private final String medicineInfo;
  private final String medicineSideEffects;
  private final String incompliances;
  private final MealRelation mealRelation;

  public Medicine(int medicineId, String medicineName, String medicineInfo,
                      String medicineSideEffects, String incompliances, MealRelation mealRelation) {

    validate(medicineId, medicineName, mealRelation);

    this.medicineId = medicineId;
    this.medicineName = medicineName;
    this.medicineInfo = medicineInfo;
    this.medicineSideEffects = medicineSideEffects;
    this.incompliances = incompliances;
    this.mealRelation = mealRelation;
  }

  private void validate(int medicineId, String medicineName, MealRelation mealRelation) {

    validateParameter(medicineId, "medicineId");
    validateParameter(medicineName, "medicineName");
    validateParameter(mealRelation, "mealRelation");

  }

  public int getMedicineId() {
    return medicineId;
  }

  public String getMedicineName() {
    return medicineName;
  }

  public String getMedicineInfo() {
    return medicineInfo;
  }

  public String getMedicineSideEffects() {
    return medicineSideEffects;
  }

  public String getIncompliances() {
    return incompliances;
  }

  public MealRelation getMealRelation() {
    return mealRelation;
  }

  @Override
  public String toString() {
    return "Medicine{" +
        "medicineId=" + medicineId +
        ", medicineName='" + medicineName + '\'' +
        ", medicineInfo='" + medicineInfo + '\'' +
        ", medicineSideEffects='" + medicineSideEffects + '\'' +
        ", incompliances='" + incompliances + '\'' +
        ", mealRelation=" + mealRelation +
        '}';
  }


}
