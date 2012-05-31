package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.AALapplication.medication_manager.ui.ReminderDialog;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.DueIntake;
import org.universAAL.ontology.medMgr.MyDeviceUserMappingDatabase;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class DueIntakeReminderEventSubscriber extends ContextSubscriber {

  private final ModuleContext moduleContext;

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, DueIntake.MY_URI, 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }

  public DueIntakeReminderEventSubscriber(ModuleContext context) {
    super(context, getContextEventPatterns());

    this.moduleContext = context;
  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    Log.info("Received event of type %s", getClass(), event.getType());

    DueIntake missedIntake = (DueIntake) event.getRDFSubject();

    Time time = missedIntake.getTime();

    Log.info("Time %s", getClass(), time);

    String deviceId = missedIntake.getDeviceId();

    Log.info("DeviceId %s", getClass(), deviceId);

    User user = MyDeviceUserMappingDatabase.getUser(deviceId);

    ReminderDialog reminderDialog =
        new ReminderDialog(moduleContext, time);

    reminderDialog.showDialog(user);

  }
}
