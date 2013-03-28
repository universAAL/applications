package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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

  public String getHours() {
    return hours;
  }

  public String getUnit() {
    return unit;
  }

  public Set<IntakeDTO> getIntakeDTOSet() {
    if (intakeDTOSet.isEmpty()) {
      throw new MedicationManagerServletUIException("No intakeDTO set");
    }

    return intakeDTOSet;
  }

  public int getDose() {
    return dose;
  }

  public void fillIntakeDTOSet(int dose, String hoursArray, String unit) {

    hoursArray = validateParameters(dose, hoursArray, unit);



    hours = hoursArray.substring(1, hoursArray.length() - 1);

    if (hours.length() <= 2) {
      throw new MedicationManagerServletUIException("There is no numbers inside the array");
    }

    StringTokenizer st = new StringTokenizer(hours, ",");
    List<Integer> hoursList = new ArrayList<Integer>();
    while (st.hasMoreElements()) {
      String token = st.nextToken().trim();
      int h = getIntFromString(token, "token");
      hoursList.add(h);
    }

    if (hoursList.isEmpty()) {
      throw new MedicationManagerServletUIException("There is no numbers inside the array");
    }

    fillIntakes(hoursList, unit, dose);

    this.dose = dose;
    this.unit = unit;
    this.hours = hoursArray;

  }

  private void fillIntakes(List<Integer> hoursList, String unit, int dose) {
    intakeDTOSet.clear();
    for (Integer h : hoursList) {
      TimeDTO timeDTO = createTimeDTO(h);
      IntakeDTO.Unit un = IntakeDTO.Unit.getEnumValueFor(unit);
      IntakeDTO intakeDTO = new IntakeDTO(timeDTO, un, dose);
      intakeDTOSet.add(intakeDTO);
    }
  }

  public TimeDTO createTimeDTO(int hour) {

    if (hour < 0 || hour > 23) {
      String message = "The hour:" + hour + " is not a valid hour. " +
          "The hour must be between 0 and 23";
      throw new MedicationManagerServletUIException(message);
    }

    String time;
    if (hour < 10) {
      time = "0" + hour + ":00";
    } else {
      time = hour + ":00";
    }

    return TimeDTO.createTimeDTO(time);
  }

  private String validateParameters(int dose, String hours, String unit) {
    if (dose <= 0) {
      throw new MedicationManagerServletUIException("The dose is not set correctly must be positive number: " + dose);
    }

    if (unit == null || unit.trim().isEmpty()) {
      throw new MedicationManagerServletUIException("unit is null or empty String");
    }

    if (hours == null || hours.trim().isEmpty()) {
      throw new MedicationManagerServletUIException("hours is null or empty String");
    }

    hours = hours.trim();

    if (!hours.startsWith("[") || !hours.endsWith("]")) {
      throw new MedicationManagerServletUIException("Hours text must be surrounded with []");
    }
    return hours;
  }


}
