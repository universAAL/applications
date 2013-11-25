package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.AALapplication.medication_manager.providers.MissedIntakeContextProvider;
import org.universAAL.AALapplication.medication_manager.ui.MissedIntakeNotifier;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeNotifierImpl implements MissedIntakeNotifier {

  private final MissedIntakeContextProvider missedIntakeEventSubscriber;

  public MissedIntakeNotifierImpl(MissedIntakeContextProvider missedIntakeEventSubscriber) {
    this.missedIntakeEventSubscriber = missedIntakeEventSubscriber;
  }

  public void publishMissedIntakeEvent(Time time, User user) {
    missedIntakeEventSubscriber.missedIntakeTimeEvent(time, user);
  }
}
