package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.util.HashSet;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

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
  private final Set<IntakeDTO> intakeDTOSet = new HashSet<IntakeDTO>();
  private String hours;
  private boolean isNew;
  private int dose;
  private String unit;

  public MedicineView(int medicineId) {
    this.medicineId = medicineId;
  }

  public boolean isNew() {
    return isNew;
  }

  public void setNew(boolean aNew) {
    isNew = aNew;
  }

  public int getMedicineId() {
    return medicineId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    validateRequiredStringField(name, "name");
    this.name = name;
  }

  public int getDays() {
    return days;
  }

  public void setDays(String daysText) {
    int d = getPositiveNumber(daysText, "daysText");
    this.days = d;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    if (description != null && !description.trim().isEmpty()) {
      this.description = description;
    }
  }

  public String getSideeffects() {
    return sideeffects;
  }

  public void setSideeffects(String sideeffects) {
    if (sideeffects != null && !sideeffects.trim().isEmpty()) {
      this.sideeffects = sideeffects;
    }
  }

  public String getIncompliances() {
    return incompliances;
  }

  public void setIncompliances(String incompliances) {
    if (incompliances != null && !incompliances.trim().isEmpty()) {
      this.incompliances = incompliances;
    }
  }

  public MealRelationDTO getMealRelationDTO() {
    return mealRelationDTO;
  }

  public void setMealRelationDTO(String mealRelation) {
    this.mealRelationDTO = MealRelationDTO.getEnumValueFor(mealRelation);
  }

  public String getHours() {
    return hours;
  }

  public String getUnit() {
    return unit;
  }

  public Set<IntakeDTO> getIntakeDTOSet() {
    return intakeDTOSet;
  }

  public int getDose() {
    return dose;
  }

  public void fillIntakeDTOSet(int dose, String[] hoursArray, String unit) {

    validateParameters(dose, hoursArray, unit);

    int[] hours = new int[hoursArray.length];
    for (int i = 0; i < hoursArray.length; i++) {
      int h = getPositiveNumber(hoursArray[i], "hoursArray[" + i + "]");
      hours[i] = h;
    }

    fillIntakes(hours, unit, dose);

    this.dose = dose;
    this.unit = unit;
    this.hours = createJavaScriptArray(hoursArray);

  }

  private String createJavaScriptArray(String[] hoursArray) {
    StringBuffer sb = new StringBuffer();
    sb.append('[');
    int i = 0;
    int size = hoursArray.length;
    for (String hour : hoursArray) {
      sb.append(hour);
      i++;
      if (i < size) {
        sb.append(", ");
      }
    }

    sb.append("]");

    return sb.toString();
  }

  private void fillIntakes(int[] hours, String unit, int dose) {
    intakeDTOSet.clear();
    for (int h : hours) {
      TimeDTO timeDTO = createTimeDTO(h);
      IntakeDTO.Unit un = createUnit(unit);
      IntakeDTO intakeDTO = new IntakeDTO(timeDTO, un, dose);
      intakeDTOSet.add(intakeDTO);
    }
  }

  private IntakeDTO.Unit createUnit(String unit) {
    unit = unit.trim();
    if (unit.equalsIgnoreCase("PILLS") || unit.equalsIgnoreCase("PILL")) {
      return IntakeDTO.Unit.PILL;
    } else if (unit.equalsIgnoreCase("DROPS")) {
      return IntakeDTO.Unit.DROPS;
    }

    throw new MedicationManagerServletUIException("Uknown unit : " + unit);
  }

  public TimeDTO createTimeDTO(int hour) {

    boolean isProblemHour = false;

    if (hour == 24) {
      hour = 23;
      isProblemHour = true;
    }

    if (hour < 0 || hour > 23) {
      String message = "The hour:" + hour + " is not a valid hour. " +
          "The hour must be between 0 and 23";
      throw new MedicationManagerServletUIException(message);
    }

    String minutes = ":00";
    if (isProblemHour) {
      minutes = ":59";
    }

    String time;
    if (hour < 10) {
      time = "0" + hour + minutes;
    } else {
      time = hour + minutes;
    }

    return TimeDTO.createTimeDTO(time);
  }

  private void validateParameters(int dose, String[] hours, String unit) {
    if (dose <= 0) {
      throw new MedicationManagerServletUIException("The dose is not set correctly must be positive number: " + dose);
    }

    if (unit == null || unit.trim().isEmpty()) {
      throw new MedicationManagerServletUIException("unit is null or empty String");
    }

    if (hours == null || hours.length == 0) {
      throw new MedicationManagerServletUIException("hours is null or empty");
    }


  }

  @Override
  public String toString() {
    return "MedicineView{" +
        "medicineId=" + medicineId +
        ", name='" + name + '\'' +
        ", days=" + days +
        ", description='" + description + '\'' +
        ", sideeffects='" + sideeffects + '\'' +
        ", incompliances='" + incompliances + '\'' +
        ", mealRelationDTO=" + mealRelationDTO +
        ", intakeDTOSet=" + intakeDTOSet +
        ", hours='" + hours + '\'' +
        ", isNew=" + isNew +
        ", dose=" + dose +
        ", unit='" + unit + '\'' +
        '}';
  }
}
