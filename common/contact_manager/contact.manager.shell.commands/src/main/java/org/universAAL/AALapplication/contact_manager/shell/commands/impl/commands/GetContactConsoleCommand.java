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


package org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands;

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.ContactManagerShellException;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.vcard.Email;
import org.universAAL.ontology.vcard.Tel;

import java.util.List;

import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.callers.GetContactConsumer.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;
import static org.universAAL.ontology.vcard.Tel.*;

/**
 * @author George Fournadjiev
 */
public final class GetContactConsoleCommand extends ConsoleCommand {

  private static final String COMMAND = COMMAND_PREFIX + ':' + GET_CONTACT + " command";

  public GetContactConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return "This command requires one parameter : the user_uri";
  }

  @Override
  public void execute(String... parameters) {

    info("Executing " + COMMAND, getClass());

    if (parameters != null && parameters.length != 1) {
      throw new ContactManagerShellException("The " + COMMAND + " expects one parameter (user_uri)");
    }

    User user = new User(parameters[0]);

    PersonalInformationSubprofile vcard = getContact(user);

    info("Received PersonalInformationSubprofile object: %s", getClass(), vcard);

    if (vcard == null || vcard.numberOfProperties() < 2) {
      throw new ContactManagerShellException("The received object os not correct null or missing properties");
    }

    printData(vcard);
  }

  @Override
  public String getCommandText() {
    return COMMAND;
  }

  private void printData(PersonalInformationSubprofile vcard) {

    System.out.println(PROP_VCARD_VERSION + " -> " + vcard.getProperty(PROP_VCARD_VERSION));
    System.out.println(PROP_LAST_REVISION + " -> " + vcard.getProperty(PROP_LAST_REVISION));
    System.out.println(PROP_ABOUT_ME + " -> " + vcard.getProperty(PROP_ABOUT_ME));
    System.out.println(PROP_UCI_LABEL + " -> " + vcard.getProperty(PROP_UCI_LABEL));
    System.out.println(PROP_NICKNAME + " -> " + vcard.getProperty(PROP_NICKNAME));
    System.out.println(PROP_BDAY + " -> " + vcard.getProperty(PROP_BDAY));
    System.out.println(PROP_DISPLAY_NAME + " -> " + vcard.getProperty(PROP_DISPLAY_NAME));
    System.out.println(PROP_FN + " -> " + vcard.getProperty(PROP_FN));
    System.out.println(PROP_UCI_ADDITIONAL_DATA + " -> " + vcard.getProperty(PROP_UCI_ADDITIONAL_DATA));
    List<Tel> tels = (List<Tel>) vcard.getProperty(PROP_TEL);

    if (tels != null && !tels.isEmpty()) {
      for (Tel t : tels) {
        System.out.println(" telephone : type -> " +
            t.getClass().getSimpleName() + " value -> " + t.getProperty(PROP_VALUE));
      }
    } else {
      System.out.println(PROP_TEL + " -> is null or empty");
    }


    List<Email> mails = (List<Email>) vcard.getProperty(PROP_EMAIL);

    if (mails != null && !mails.isEmpty()) {
      for (Email email : mails) {
        System.out.println(" Email : type -> " +
            email.getClass().getSimpleName() + " value -> " + email.getProperty(PROP_VALUE));
      }
    } else {
      System.out.println(PROP_EMAIL + " -> is null or empty");
    }
  }


}
