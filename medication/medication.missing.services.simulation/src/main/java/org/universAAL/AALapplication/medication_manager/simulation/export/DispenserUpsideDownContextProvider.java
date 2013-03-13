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


package org.universAAL.AALapplication.medication_manager.simulation.export;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.Enumeration;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.medMgr.DispenserUpsideDown;

/**
 * @author George Fournadjiev
 */
public final class DispenserUpsideDownContextProvider {

  private final ContextPublisher contextPublisher;

  private static final String DISPENSER_UPSIDE_DOWN_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/DispenserUpsideDownServer.owl#";

  public static final String MY_URI = DISPENSER_UPSIDE_DOWN_SERVER_NAMESPACE + "DispenserUpsideDownService";

  private static final String DISPENSER_UPSIDE_DOWN_PROVIDER = DISPENSER_UPSIDE_DOWN_SERVER_NAMESPACE +
      "DispenserUpsideDownContextProvider";

  public DispenserUpsideDownContextProvider(ModuleContext moduleContext) {

    ContextProvider info = new ContextProvider(DISPENSER_UPSIDE_DOWN_PROVIDER);
    info.setType(ContextProviderType.controller);
    info.setProvidedEvents(getContextEventPatterns());

    contextPublisher = new DefaultContextPublisher(moduleContext, info);
  }

  private static ContextEventPattern[] getContextEventPatterns() {
    ContextEventPattern cep = new ContextEventPattern();

    DispenserUpsideDown[] dispenserUpsideDowns = new DispenserUpsideDown[]{new DispenserUpsideDown()};

    MergedRestriction mr = MergedRestriction.getAllValuesRestrictionWithCardinality(
        ContextEvent.PROP_RDF_SUBJECT, new Enumeration(dispenserUpsideDowns), 1, 1);

    cep.addRestriction(mr);

    return new ContextEventPattern[]{cep};

  }


  public void dispenserUpsideDownDeviceIdEvent(String deviceUri) {
    DispenserUpsideDown dispenserUpsideDown = new DispenserUpsideDown();
    dispenserUpsideDown.setDeviceId(deviceUri);
    ContextEvent contextEvent = new ContextEvent(dispenserUpsideDown, DispenserUpsideDown.PROP_DEVICE_ID);
    contextPublisher.publish(contextEvent);
  }

  public String getClassURI() {
    return MY_URI;
  }

}
