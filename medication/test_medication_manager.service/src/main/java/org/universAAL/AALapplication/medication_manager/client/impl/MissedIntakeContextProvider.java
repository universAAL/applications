package org.universAAL.AALapplication.medication_manager.client.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Time;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeContextProvider {

  private static ContextPublisher contextPublisher;

  private static final String MISSED_INTAKE_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/MissedIntakeServer.owl#";

  public static final String MY_URI = MISSED_INTAKE_SERVER_NAMESPACE + "MissedIntakeService";

  private static final String MISSED_INTAKE_PROVIDER = MISSED_INTAKE_SERVER_NAMESPACE +
      "MissedIntakeContextProvider";

  public MissedIntakeContextProvider(ModuleContext moduleContext) {

    ContextProvider info = new ContextProvider(MISSED_INTAKE_PROVIDER);
    info.setType(ContextProviderType.controller);
    info.setProvidedEvents(getContextEventPatterns());

    contextPublisher = new DefaultContextPublisher(moduleContext, info);
  }

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    MissedIntake[] missedIntakes = new MissedIntake[]{new MissedIntake()};

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, new Enumeration(missedIntakes), 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }


  public static void publishEvent(Time time, String userId) {
    MissedIntake missedIntake = new MissedIntake();
    missedIntake.setTime(time);
    missedIntake.setUserId(userId);
    ContextEvent contextEvent = new ContextEvent(missedIntake, MissedIntake.TIME);
    contextPublisher.publish(contextEvent);
  }

  public String getClassURI() {
    return MY_URI;
  }

}
