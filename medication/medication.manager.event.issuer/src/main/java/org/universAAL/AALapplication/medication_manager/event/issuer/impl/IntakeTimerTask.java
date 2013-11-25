package org.universAAL.AALapplication.medication_manager.event.issuer.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.simulation.export.MedicationReminderContextProvider;
import org.universAAL.ontology.medMgr.Time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimerTask;

/**
 * @author George Fournadjiev
 */
public final class IntakeTimerTask extends TimerTask {

  private final Intake intake;

  public IntakeTimerTask(Intake intake) {

    this.intake = intake;
  }

  @Override
  public void run() {
    MedicationReminderContextProvider provider = Activator.getMedicationReminderContextProvider();
    Time time = getTimeObject(intake.getTimePlan());
    Person patient = intake.getTreatment().getPrescription().getPatient();
    provider.dueIntakeReminderPersonUriEvent(patient.getPersonUri(), time);
  }

  private Time getTimeObject(Date date) {

    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(date);

    int year = gregorianCalendar.get(Calendar.YEAR);
    int month = gregorianCalendar.get(Calendar.MONTH);
    int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
    int minutes = gregorianCalendar.get(Calendar.MINUTE);

    return new Time(year, month, day, hour, minutes);

  }
}
