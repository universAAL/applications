package org.universAAL.AALapplication.medication_manager.ui;

import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public interface MissedIntakeNotifier {

  void publishMissedIntakeEvent(Time time, User user);

}
