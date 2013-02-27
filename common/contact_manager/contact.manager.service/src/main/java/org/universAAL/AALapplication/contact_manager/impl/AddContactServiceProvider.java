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

import org.universAAL.AALapplication.contact_manager.persistence.layer.ContactManagerPersistentService;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCardBuilder;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.vcard.Email;
import org.universAAL.ontology.vcard.Tel;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

import static org.universAAL.AALapplication.contact_manager.impl.Activator.*;
import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;


/**
 * @author George Fournadjiev
 */
public final class AddContactServiceProvider extends ServiceCallee {


  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  private static String[] UN_IMPLEMENTED_PROPERTIES;
  private static String[] IMPLEMENTED_PROPERTIES;

  static {
    invalidInput.addOutput(new ProcessOutput(
        ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));

    IMPLEMENTED_PROPERTIES = new String[]{PROP_VCARD_VERSION, PROP_LAST_REVISION, PROP_NICKNAME, PROP_DISPLAY_NAME,
        PROP_UCI_LABEL, PROP_UCI_ADDITIONAL_DATA, PROP_ABOUT_ME, PROP_BDAY, PROP_FN, PROP_TEL, PROP_EMAIL};

    UN_IMPLEMENTED_PROPERTIES = new String[]{PROP_BIRTHPLACE, PROP_GENDER, PROP_N, PROP_ORG,
        PROP_PHOTO, PROP_URL};
  }

  public AddContactServiceProvider(ModuleContext context) {
    super(context, AddContactService.profiles);

  }

  public void communicationChannelBroken() {
    //Not implemented yet
  }

  public ServiceResponse handleCall(ServiceCall call) {
    String processURI = call.getProcessURI();

    Log.info("Received call %s", getClass(), processURI);

    User involvedUser = (User) call.getInvolvedUser();

    Log.info("involvedUser %s", getClass(), involvedUser);

    if (involvedUser == null) {
      return invalidInput;
    }

    if (!processURI.startsWith(AddContactService.ADD_CONTACT)) {
      return invalidInput;
    }

    PersonalInformationSubprofile personalInformationSubprofile =
        (PersonalInformationSubprofile) call.getInputValue(AddContactService.INPUT_ADD_CONTACT);

    if (personalInformationSubprofile != null && personalInformationSubprofile.isWellFormed() &&
        personalInformationSubprofile.numberOfProperties() > 1) {

      return persistContact(involvedUser, personalInformationSubprofile);
    }

    return invalidInput;
  }

  private ServiceResponse getSuccessfulServiceResponse(User involvedUser) {
    String userId = involvedUser.getURI();
    Log.info("Add contact Service Response for the user %s", getClass(), userId);

    return new ServiceResponse(CallStatus.succeeded);
  }

  private ServiceResponse persistContact(User involvedUser, PersonalInformationSubprofile personalInformationSubprofile) {
    printNotSupportedProperties();

    Log.info("Start processing the properties", getClass());
    VCardBuilder vCardBuilder = new VCardBuilder();
    vCardBuilder.buildPersonUri(involvedUser.getURI());

    for (String propName : IMPLEMENTED_PROPERTIES) {
      Log.info("Check for %s property", getClass(), propName);
      Object value = personalInformationSubprofile.getProperty(propName);
      processProperty(propName, value, vCardBuilder);
    }

    VCard vCard = vCardBuilder.buildVCard();
    ContactManagerPersistentService persistentService = getContactManagerPersistentService();
    boolean success = persistentService.saveVCard(vCard);

    temoMethod(persistentService);

    if (success) {
      return getSuccessfulServiceResponse(involvedUser);
    }

    return invalidInput;
  }

  private void temoMethod(ContactManagerPersistentService persistentService) {
    VCard vCard = persistentService.getVCard("urn:org.universAAL.aal_space:test_env#hary");
    System.out.println("vCard" + vCard);
  }

  private void processProperty(String propName, Object value, VCardBuilder vCardBuilder) {
    if (value == null) {
      Log.info("The %s property is not set and skipping processing for it", getClass(), propName);
      return;
    }

    if (PROP_VCARD_VERSION.equals(propName)) {
      vCardBuilder.buildVcardVersion((String) value);
    } else if (PROP_LAST_REVISION.equals(propName)) {
      XMLGregorianCalendar calendar = (XMLGregorianCalendar) value;
      vCardBuilder.buildLastRevision(getDateFromXMLGregorianCalendar(calendar));

    } else if (PROP_ABOUT_ME.equals(propName)) {
      vCardBuilder.buildAboutMe((String) value);

    } else if (PROP_UCI_LABEL.equals(propName)) {
      vCardBuilder.buildUciLabel((String) value);

    } else if (PROP_NICKNAME.equals(propName)) {
      vCardBuilder.buildNickname((String) value);

    } else if (PROP_BDAY.equals(propName)) {
      XMLGregorianCalendar calendar = (XMLGregorianCalendar) value;
      vCardBuilder.buildBday(getDateFromXMLGregorianCalendar(calendar));

    } else if (PROP_DISPLAY_NAME.equals(propName)) {
      vCardBuilder.buildDisplayName((String) value);

    } else if (PROP_FN.equals(propName)) {
      vCardBuilder.buildFn((String) value);

    } else if (PROP_UCI_ADDITIONAL_DATA.equals(propName)) {
      vCardBuilder.buildUciAdditional_data((String) value);

    } else if (PROP_TEL.equals(propName)) {
      vCardBuilder.buildTelephones((List<Tel>) value);

    } else if (PROP_EMAIL.equals(propName)) {
      vCardBuilder.buildEmails((List<Email>) value);

    } else {
      throw new ContactManagerException("Unexpected property:" + propName);
    }


  }

  private void printNotSupportedProperties() {

    Log.info("Not supported properties", getClass());

    for (String propName : UN_IMPLEMENTED_PROPERTIES) {
      Log.info("%s is not supported", getClass(), propName);

    }
  }


}
