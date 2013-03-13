package org.universAAL.AALapplication.medication_manager.persistence.layer.entities;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Entity;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class Medicine extends Entity {

  private final String medicineName;
  private final String medicineInfo;
  private final String medicineSideEffects;
  private final String incompliances;
  private final MealRelation mealRelation;

  public Medicine(int id, String medicineName, String medicineInfo,
                  String medicineSideEffects, String incompliances, MealRelation mealRelation) {

    super(id);

    validate(medicineName, mealRelation);

    this.medicineName = medicineName;
    this.medicineInfo = medicineInfo;
    this.medicineSideEffects = medicineSideEffects;
    this.incompliances = incompliances;
    this.mealRelation = mealRelation;
  }

  public Medicine(String medicineName, String medicineInfo,
                  String medicineSideEffects, String incompliances, MealRelation mealRelation) {

    this(0, medicineName, medicineInfo, medicineSideEffects, incompliances, mealRelation);
  }

  private void validate(String medicineName, MealRelation mealRelation) {

    validateParameter(medicineName, "medicineName");
    validateParameter(mealRelation, "mealRelation");

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
        "medicineId=" + getId() +
        ", medicineName='" + medicineName + '\'' +
        ", medicineInfo='" + medicineInfo + '\'' +
        ", medicineSideEffects='" + medicineSideEffects + '\'' +
        ", incompliances='" + incompliances + '\'' +
        ", mealRelation=" + mealRelation +
        '}';
  }


}
