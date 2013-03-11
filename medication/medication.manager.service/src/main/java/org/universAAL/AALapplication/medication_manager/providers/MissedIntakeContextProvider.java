/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package org.universAAL.AALapplication.medication_manager.providers;

import org.universAAL.AALapplication.medication_manager.impl.Log;
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
import org.universAAL.ontology.profile.User;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeContextProvider {

  private final ContextPublisher contextPublisher;

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


  public void missedIntakeTimeEvent(Time time, User user) {
    Log.info("Publishing event for user: %s and time: %s", MissedIntakeContextProvider.class, user, time);
    MissedIntake missedIntake = new MissedIntake();
    missedIntake.setTime(time);
    missedIntake.setUser(user);
    ContextEvent contextEvent = new ContextEvent(missedIntake, MissedIntake.PROP_TIME);
    contextPublisher.publish(contextEvent);
  }

  public String getClassURI() {
    return MY_URI;
  }

}
