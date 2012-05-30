package org.universAAL.ontology.medMgr;

import org.universAAL.ontology.profile.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MyIntakeInfosDatabase {

  private static final Map<User, MedicinesInfo> USER_INTAKE_INFO = new HashMap<User, MedicinesInfo>();

  static {
    addSaiedIntakeinfo();
  }

  private static void addSaiedIntakeinfo() {

    StringBuilder sb = new StringBuilder();

    sb.append("         Intake info           ");
    sb.append("\n\n");
    sb.append("1. Aspirin  -  2 pills");
    sb.append('\n');
    sb.append("2. Zokor    -  1 pill");
    sb.append('\n');

    String generalInfo = sb.toString();

    sb = new StringBuilder();

    sb.append("    Medication info          ");
    sb.append("\n\n");
    sb.append("1. Aspirin  ");
    sb.append('\n');
    sb.append("Aspirin is used to relieve many kinds of minor aches and pains—headaches, toothaches, muscle pain, menstrual cramps, the joint pain from arthritis, and aches associated with colds and flu. Some people take aspirin daily to reduce the risk of stroke, heart attack, or other heart problems.");
    sb.append('\n');
    sb.append('\n');
    sb.append("2. Zokor  ");
    sb.append('\n');
    sb.append("Coronary artery disease; hyperlipidemia");

    String detailedInfo = sb.toString();

    MedicinesInfo medicinesInfo = new MedicinesInfo(generalInfo, detailedInfo);


    USER_INTAKE_INFO.put(UserIDs.getSaied(), medicinesInfo);

  }

  public static MedicinesInfo getIntakeInfoForUser(User user) {
    return USER_INTAKE_INFO.get(user);
  }

}
