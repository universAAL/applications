package org.universAAL.ontology.medMgr;

import org.universAAL.ontology.profile.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MyIntakeInfosDatabase {

  public static Time MORNING_INTAKE = new Time(2012, 5, 30, 6, 30);
  public static Time LUNCH_INTAKE = new Time(2012, 5, 30, 12, 30);
  public static Time EVENING_INTAKE = new Time(2012, 5, 30, 20, 0);
  private static final Map<User, Map<Time, MedicinesInfo>> USER_INTAKE_INFO = new HashMap<User, Map<Time, MedicinesInfo>>();

  static {
    addSaiedIntakeinfo();
  }

  private static void addSaiedIntakeinfo() {

    StringBuilder sb = new StringBuilder();

    sb.append("    Medication info          ");
    sb.append("\n\n");
    sb.append("1. Aspirin  ");
    sb.append('\n');
    sb.append("Aspirin is used to relieve many kinds of minor aches and pains—headaches, toothaches, muscle pain, " +
        "menstrual cramps, the joint pain from arthritis, and aches associated with colds and flu. " +
        "Some people take aspirin daily to reduce the risk of stroke, heart attack, or other heart problems.");
    sb.append('\n');
    sb.append('\n');
    sb.append("2. Zokor  ");
    sb.append('\n');
    sb.append("Coronary artery disease; hyperlipidemia");

    String detailedInfo = sb.toString();

    Map<Time, MedicinesInfo> timeMedicinesInfoMap = getTimeMedicinesInfoMap(detailedInfo);

    USER_INTAKE_INFO.put(UserIDs.getSaied(), timeMedicinesInfoMap);

  }

  private static Map<Time, MedicinesInfo> getTimeMedicinesInfoMap(String detailedInfo) {
    Map<Time, MedicinesInfo> timeMedicinesInfoMap = new HashMap<Time, MedicinesInfo>();

    String morningDoseGeneralInfo = getGeneralInfo(1, 1, MORNING_INTAKE);
    MedicinesInfo morningMedicinesInfo = new MedicinesInfo(morningDoseGeneralInfo, detailedInfo, MORNING_INTAKE);
    timeMedicinesInfoMap.put(morningMedicinesInfo.getTime(), morningMedicinesInfo);

    String lunchDoseGeneralInfo = getGeneralInfo(1, 0, LUNCH_INTAKE);
    MedicinesInfo lunchMedicinesInfo = new MedicinesInfo(lunchDoseGeneralInfo, detailedInfo, LUNCH_INTAKE);
    timeMedicinesInfoMap.put(lunchMedicinesInfo.getTime(), lunchMedicinesInfo);

    String eveningDoseGeneralInfo = getGeneralInfo(2, 2, EVENING_INTAKE);
    MedicinesInfo eveningMedicinesInfo = new MedicinesInfo(eveningDoseGeneralInfo, detailedInfo, EVENING_INTAKE);
    timeMedicinesInfoMap.put(eveningMedicinesInfo.getTime(), eveningMedicinesInfo);
    return timeMedicinesInfoMap;
  }

  private static String getGeneralInfo(int aspirins, int zokors, Time time) {
    StringBuilder sb = new StringBuilder();

    sb.append("   Intake info for          ");
    sb.append(time.getDailyTextFormat());
    sb.append("  o'clock   ");
    sb.append("\n\n");
    sb.append("1. Aspirin  -  ");
    sb.append(aspirins);
    sb.append(" pills");
    sb.append('\n');
    if (zokors > 0) {
      sb.append("2. Zokor    -  ");
      sb.append(zokors);
      sb.append(" pill");
      sb.append('\n');
    }

    return sb.toString();
  }

  public static MedicinesInfo getIntakeInfoForUser(User user, Time time) {
    Map<Time, MedicinesInfo> timeMedicinesInfoMap = USER_INTAKE_INFO.get(user);

    return timeMedicinesInfoMap.get(time);
  }

}
