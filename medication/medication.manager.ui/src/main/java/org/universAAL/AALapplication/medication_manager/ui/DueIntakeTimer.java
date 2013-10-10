package org.universAAL.AALapplication.medication_manager.ui;

import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.ui.impl.Log;
import org.universAAL.ontology.medMgr.DueIntake;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.util.Timer;
import java.util.TimerTask;

import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DueIntakeTimer {

  private final ReminderDialog reminderDialog;
  private final DueIntake dueIntake;
  private final User user;
  private final Person patient;
  private final MissedIntakeNotifier missedIntakeNotifier;
  private Timer timer = new Timer();
  private boolean timeoutExpired = false;

  public DueIntakeTimer(ReminderDialog reminderDialog, DueIntake dueIntake, User user,
                        Person patient, MissedIntakeNotifier missedIntakeNotifier) {


    this.reminderDialog = reminderDialog;
    this.dueIntake = dueIntake;
    this.user = user;
    this.patient = patient;
    this.missedIntakeNotifier = missedIntakeNotifier;
  }

  public void setTimeOut() {

    ConfigurationProperties properties = getConfigurationProperties();

    final int timeoutSeconds = properties.getMedicationReminderTimeout();
    timeoutExpired = false;
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        try {
          Log.info("The reminder timeout interval expired: reminder_interval_in_seconds=%s", getClass(), timeoutSeconds);
          timeoutExpired = true;
          boolean userActed = reminderDialog.isUserActed();
          Log.info("Did the user responded in time?: %s", getClass(), userActed);
          if (!userActed) {
            publishMissedIntakeEvent(dueIntake, user, timeoutSeconds);
          }
          timer.cancel();
        } catch (Exception e) {
          Log.error(e, "Error while processing the timeout", getClass());
        }
      }

    }, timeoutSeconds * 1000);


  }

  public void cancel() {
    timer.cancel();
    timer = new Timer();
  }

  public boolean isTimeoutExpired() {
    return timeoutExpired;
  }

  private void publishMissedIntakeEvent(DueIntake dueIntake, User user, int timeoutSeconds) {
    Log.info("The user didn't respond in the required time: %s. " +
        "Publishing missed intake event", getClass(), timeoutSeconds);

    Time time = dueIntake.getTime();

    missedIntakeNotifier.publishMissedIntakeEvent(time, user);

  }
}
