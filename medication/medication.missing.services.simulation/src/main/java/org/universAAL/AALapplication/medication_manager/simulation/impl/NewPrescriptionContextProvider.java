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


package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.NewPrescription;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionContextProvider {

  private ContextPublisher contextPublisher;

  private static final String NEW_PRESCRIPTION_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/NewPrescriptionServer.owl#";

  public static final String MY_URI = NEW_PRESCRIPTION_SERVER_NAMESPACE + "NewPrescriptionService";

  private static final String NEW_PRESCRIPTION_PROVIDER = NEW_PRESCRIPTION_SERVER_NAMESPACE +
      "NewPrescriptionContextProvider";

  public NewPrescriptionContextProvider(ModuleContext moduleContext) {

    ContextProvider info = new ContextProvider(NEW_PRESCRIPTION_PROVIDER);
    info.setType(ContextProviderType.controller);
    info.setProvidedEvents(getContextEventPatterns());

    contextPublisher = new DefaultContextPublisher(moduleContext, info);
  }

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    NewPrescription[] newPrescriptions = new NewPrescription[]{new NewPrescription()};

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, new Enumeration(newPrescriptions), 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }


  public void publishNewPrescriptionEvent(NewPrescription newPrescription) {
    ContextEvent contextEvent = new ContextEvent(newPrescription, NewPrescription.PROP_PRESCRIPTION_ID);
    contextPublisher.publish(contextEvent);
  }

  public String getClassURI() {
    return MY_URI;
  }

}
