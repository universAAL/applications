package org.universAAL.AALapplication.medication_manager.simulation;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.DueIntake;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class MedicationReminderContextProvider {

  private static ContextPublisher contextPublisher;

  private static final String MEDICATION_REMINDER_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/MedicationReminderServer.owl#";

  public static final String MY_URI = MEDICATION_REMINDER_SERVER_NAMESPACE + "MedicationReminderService";

  private static final String MEDICATION_REMINDER_PROVIDER = MEDICATION_REMINDER_SERVER_NAMESPACE +
      "MissedIntakeContextProvider";

  public MedicationReminderContextProvider(ModuleContext moduleContext) {

    ContextProvider info = new ContextProvider(MEDICATION_REMINDER_PROVIDER);
    info.setType(ContextProviderType.controller);
    info.setProvidedEvents(getContextEventPatterns());

    contextPublisher = new DefaultContextPublisher(moduleContext, info);
  }

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    DueIntake[] dueIntakes = new DueIntake[]{new DueIntake()};

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, new Enumeration(dueIntakes), 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }


  public static void dueIntakeReminderDeviceIdEvent(String userId, String deviceId) {
    DueIntake dueIntake = new DueIntake();
    User user = new User(userId);
    dueIntake.setUser(user);
    dueIntake.setDeviceId(deviceId);
    ContextEvent contextEvent = new ContextEvent(dueIntake, DueIntake.DEVICE_ID);
    contextPublisher.publish(contextEvent);
  }

  public String getClassURI() {
    return MY_URI;
  }

}
