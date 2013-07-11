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
