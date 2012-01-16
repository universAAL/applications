package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Time;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeEventSubscriber extends ContextSubscriber {


  private static ContextEventPattern[] getContextEventPatterns() {
      ContextEventPattern cep = new ContextEventPattern();

      MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
          ContextEvent.PROP_RDF_SUBJECT, MissedIntake.MY_URI, 1, 1);

      cep.addRestriction(mr);

      return new ContextEventPattern[]{cep};

    }

  public MissedIntakeEventSubscriber(ModuleContext context) {
    super(context, getContextEventPatterns());
  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    Log.info("Received event of type %s", getClass(), event.getType());

    MissedIntake missedIntake = (MissedIntake) event.getRDFSubject();

    Time time = missedIntake.getTime();

    Log.info("Time %s", getClass(), time);

  }
}
