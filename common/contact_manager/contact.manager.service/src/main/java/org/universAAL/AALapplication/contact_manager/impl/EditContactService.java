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


package org.universAAL.AALapplication.contact_manager.impl;

import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.owl.SimpleOntology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.Profilable;
import org.universAAL.ontology.profile.Profile;
import org.universAAL.ontology.profile.service.ProfilingService;


/**
 * @author George Fournadjiev
 */
public final class EditContactService extends ProfilingService {

  public static final String EDIT_CONTACT_SERVICE_SERVER_NAMESPACE =
      "http://ontology.igd.fhg.de/EditContactServer.owl#";

  public static final String MY_URI = EDIT_CONTACT_SERVICE_SERVER_NAMESPACE + "EditContactService";

  public static final String EDIT_CONTACT = EDIT_CONTACT_SERVICE_SERVER_NAMESPACE + "editContact";

  static final String INPUT_EDIT_CONTACT = EDIT_CONTACT_SERVICE_SERVER_NAMESPACE + "PersonalInformationSubprofile";

  static final ServiceProfile[] profiles = new ServiceProfile[1];

  static {


    //Register

    OntologyManagement.getInstance().register(
        new SimpleOntology(MY_URI, ProfilingService.MY_URI,
            new ResourceFactoryImpl() {
              @Override
              public Resource createInstance(String classURI,
                                             String instanceURI, int factoryIndex) {
                return new EditContactService(instanceURI);
              }
            }));

    EditContactService editContactService =
        new EditContactService(EDIT_CONTACT);


    String[] ppInputAddContact = new String[]{ProfilingService.PROP_CONTROLS, Profilable.PROP_HAS_PROFILE, Profile.PROP_HAS_SUB_PROFILE};

    editContactService.addInputWithChangeEffect(INPUT_EDIT_CONTACT, PersonalInformationSubprofile.MY_URI, 1, 1, ppInputAddContact);

    profiles[0] = editContactService.myProfile;

  }

  private EditContactService(String uri) {
    super(uri);
  }

  public String getClassURI() {
    return MY_URI;
  }

}

