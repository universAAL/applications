package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeEventSubscriber extends ContextSubscriber {

  private final ModuleContext moduleContext;

  private static ServiceCaller serviceCaller;

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, MissedIntake.MY_URI, 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }

  public MissedIntakeEventSubscriber(ModuleContext context) {
    super(context, getContextEventPatterns());

    this.moduleContext = context;
    serviceCaller = new DefaultServiceCaller(context);
  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    Log.info("Received event of type %s", getClass(), event.getType());

    MissedIntake missedIntake = (MissedIntake) event.getRDFSubject();

    Time time = missedIntake.getTime();

    Log.info("Time %s", getClass(), time);

    User user = missedIntake.getUser();

    Log.info("Calling the Caregiver Notification Service for the userId %s", getClass(), user);

    ServiceRequest serviceRequest = new ServiceRequest(new MissedIntake(), null);

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();

    Log.info("Caregiver Notification callStatus %s", getClass(), callStatus);

  }
}
