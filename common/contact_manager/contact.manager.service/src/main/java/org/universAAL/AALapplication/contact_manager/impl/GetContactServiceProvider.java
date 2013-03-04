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
import org.universAAL.AALapplication.contact_manager.persistence.layer.EmailEnum;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Mail;
import org.universAAL.AALapplication.contact_manager.persistence.layer.TelEnum;
import org.universAAL.AALapplication.contact_manager.persistence.layer.Telephone;
import org.universAAL.AALapplication.contact_manager.persistence.layer.VCard;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.vcard.Cell;
import org.universAAL.ontology.vcard.Email;
import org.universAAL.ontology.vcard.Fax;
import org.universAAL.ontology.vcard.Internet;
import org.universAAL.ontology.vcard.Msg;
import org.universAAL.ontology.vcard.Tel;
import org.universAAL.ontology.vcard.Video;
import org.universAAL.ontology.vcard.Voice;
import org.universAAL.ontology.vcard.X400;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.universAAL.AALapplication.contact_manager.impl.Activator.*;
import static org.universAAL.AALapplication.contact_manager.impl.GetContactService.*;
import static org.universAAL.AALapplication.contact_manager.persistence.layer.TelEnum.*;
import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;


/**
 * @author George Fournadjiev
 */
public final class GetContactServiceProvider extends ServiceCallee {


  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  public GetContactServiceProvider(ModuleContext context) {
    super(context, GetContactService.profiles);

  }

  @Override
  public void communicationChannelBroken() {
    //Not implemented yet
  }

  @Override
  public ServiceResponse handleCall(ServiceCall call) {
    String processURI = call.getProcessURI();

    Log.info("Received call %s", getClass(), processURI);

    User involvedUser = (User) call.getInvolvedUser();

    Log.info("involvedUser %s", getClass(), involvedUser);

    if (involvedUser == null) {
      return invalidInput;
    }

    if (!processURI.startsWith(GetContactService.GET_CONTACT)) {
      return invalidInput;
    }

    ContactManagerPersistentService persistentService = getContactManagerPersistentService();
    VCard vCard = persistentService.getVCard(involvedUser.getURI());

    if (vCard == null) {
      return invalidInput;
    }

    return getSuccessfulServiceResponse(involvedUser, vCard);

  }


  private ServiceResponse getSuccessfulServiceResponse(User involvedUser, VCard vCard) {
    String userId = involvedUser.getURI();
    Log.info("Remove contact Service Response for the user %s", getClass(), userId);
    return getPrecaution(involvedUser, vCard);
  }

  private ServiceResponse getPrecaution(User user, VCard vCard) {
    ServiceResponse response = new ServiceResponse(CallStatus.succeeded);

    PersonalInformationSubprofile subprofile = createPersonalInformationSubprofile(vCard);

    response.addOutput(new ProcessOutput(OUTPUT_GET_CONTACT, subprofile));

    return response;
  }

  private PersonalInformationSubprofile createPersonalInformationSubprofile(VCard vcard) {


    PersonalInformationSubprofile personalInformationSubprofile =
        new PersonalInformationSubprofile(PersonalInformationSubprofile.MY_URI);

    populateTextProperty(vcard.getVCardVersion(), personalInformationSubprofile, PROP_VCARD_VERSION);

    populateDateProperty(vcard.getLastRevision(), personalInformationSubprofile, PROP_LAST_REVISION);

    populateTextProperty(vcard.getNickname(), personalInformationSubprofile, PROP_NICKNAME);

    populateTextProperty(vcard.getDisplayName(), personalInformationSubprofile, PROP_DISPLAY_NAME);

    populateTextProperty(vcard.getUciLabel(), personalInformationSubprofile, PROP_UCI_LABEL);

    populateTextProperty(vcard.getUciAdditionalData(), personalInformationSubprofile, PROP_UCI_ADDITIONAL_DATA);

    populateTextProperty(vcard.getAboutMe(), personalInformationSubprofile, PROP_ABOUT_ME);

    populateDateProperty(vcard.getBday(), personalInformationSubprofile, PROP_BDAY);

    populateTextProperty(vcard.getFn(), personalInformationSubprofile, PROP_FN);

    populateTelProperty(vcard.getTelephones(), personalInformationSubprofile);

    populateEmailProperty(vcard.getEmails(), personalInformationSubprofile);


    return personalInformationSubprofile;
  }

  private void populateTextProperty(String value,
                                    PersonalInformationSubprofile personalInformationSubprofile,
                                    String propNameDestination) {

    if (value != null) {
      personalInformationSubprofile.setProperty(propNameDestination, value);
    }

  }

  private void populateDateProperty(Date value,
                                    PersonalInformationSubprofile personalInformationSubprofile,
                                    String propNameDestination) {

    if (value != null) {
      XMLGregorianCalendar calendar = getCalendar(value);
      personalInformationSubprofile.setProperty(propNameDestination, calendar);
    }

  }

  private void populateTelProperty(List<Telephone> telephones, PersonalInformationSubprofile personalInformationSubprofile) {

    List<Tel> tels = new ArrayList<Tel>();

    for (Telephone telephone : telephones) {
      Tel tel = createTel(telephone);
      tels.add(tel);
    }

    if (tels.isEmpty()) {
      throw new ContactManagerException("Missing tel property ; delimiter");
    }

    personalInformationSubprofile.setProperty(PROP_TEL, tels);
  }

  private Tel createTel(Telephone telephone) {

    TelEnum telEnum = telephone.getTelEnum();
    String number = telephone.getValue();
    Tel tel;

    if (CELL == telEnum) {
      tel = new Cell();
    } else if (MSG == telEnum) {
      tel = new Msg();
    } else if (VIDEO == telEnum) {
      tel = new Video();
    } else if (FAX == telEnum) {
      tel = new Fax();
    } else if (VOICE == telEnum) {
      tel = new Voice();
    } else {
      throw new ContactManagerException("Unknown TelEnum : " + telEnum);
    }

    tel.setProperty(Tel.PROP_VALUE, number);

    return tel;
  }

  private void populateEmailProperty(List<Mail> mails, PersonalInformationSubprofile personalInformationSubprofile) {


    List<Email> emails = new ArrayList<Email>();

    for (Mail mail : mails) {
      Email email = createEmail(mail);
      emails.add(email);
    }

    if (emails.isEmpty()) {
      throw new ContactManagerException("Missing email property ; delimiter");
    }

    personalInformationSubprofile.setProperty(PROP_EMAIL, emails);
  }

  private Email createEmail(Mail mail) {


    String value = mail.getValue();

    EmailEnum emailEnum = mail.getEmailEnum();
    Email email;

    if (EmailEnum.INTERNET == emailEnum) {
      email = new Internet();
    } else if (EmailEnum.X400 == emailEnum) {
      email = new X400();
    } else {
      throw new ContactManagerException("Unknown EmailEnum : " + emailEnum);
    }

    email.setProperty(Email.PROP_VALUE, value);

    return email;
  }

}
