package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class MedicineView {

  private final int medicineId;
  private String name;
  private int days;
  private String description;
  private String sideeffects;
  private String incompliances;
  private MealRelationDTO mealRelationDTO;
  private Set<IntakeDTO> intakeDTOSet = new HashSet<IntakeDTO>();

  public MedicineView(int medicineId) {
    this.medicineId = medicineId;
  }

  public int getMedicineId() {
    return medicineId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDays() {
    return days;
  }

  public void setDays(int days) {
    this.days = days;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSideeffects() {
    return sideeffects;
  }

  public void setSideeffects(String sideeffects) {
    this.sideeffects = sideeffects;
  }

  public String getIncompliances() {
    return incompliances;
  }

  public void setIncompliances(String incompliances) {
    this.incompliances = incompliances;
  }

  public MealRelationDTO getMealRelationDTO() {
    return mealRelationDTO;
  }

  public void setMealRelationDTO(MealRelationDTO mealRelationDTO) {
    this.mealRelationDTO = mealRelationDTO;
  }

  public Set<IntakeDTO> getIntakeDTOSet() {
    return intakeDTOSet;
  }

  public void setIntakeDTOSet(Set<IntakeDTO> intakeDTOSet) {
    this.intakeDTOSet = intakeDTOSet;
  }
}
