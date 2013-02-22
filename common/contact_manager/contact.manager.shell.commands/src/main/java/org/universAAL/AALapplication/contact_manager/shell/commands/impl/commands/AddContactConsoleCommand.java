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

import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Activator;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.ContactManagerShellException;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.Log;
import org.universAAL.AALapplication.contact_manager.shell.commands.impl.callees.AddContactConsumer;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
import org.universAAL.ontology.profile.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static java.io.File.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.Activator.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;

/**
 * @author George Fournadjiev
 */
public final class AddContactConsoleCommand extends ConsoleCommand {

  private static final String COMMAND = COMMAND_PREFIX + ':' + ADD_CONTACT + " command";

  private static final String VCARD_VERSION = "vCardVersion";
  private static final String NICKNAME = "nickname";
  private static final String DISPLAY_NAME = "displayName";
  private static final String EMAIL = "email";
  private static final String FN = "fn";
  private static final String N = "n";
  private static final String ORG = "org";
  private static final String TEL = "tel";

  public AddContactConsoleCommand(String name, String description) {
    super(name, description);
  }

  @Override
  public String getParametersInfo() {
    return NO_PARAMETERS;
  }

  @Override
  public void execute(String... parameters) {

    Log.info("Executing " + COMMAND, getClass());

    if (parameters == null || parameters.length != 1) {
      throw new ContactManagerShellException("The " + COMMAND +
          " expects exactly one parameter and it is the name of properties file containing properties of the new VCard");
    }

    PersonalInformationSubprofile personalInformationSubprofile = createPersonalInformationSubprofile(parameters[0]);


    User user = new User("urn:org.universAAL.aal_space:test_env#saied");
    AddContactConsumer addContactConsumer = new AddContactConsumer(Activator.mc);
    boolean res = addContactConsumer.sendAddContact(user, personalInformationSubprofile);
    System.out.println("res = " + res);
  }

  private PersonalInformationSubprofile createPersonalInformationSubprofile(String fileName) {

    Properties props = getProperties(fileName);

    PersonalInformationSubprofile personalInformationSubprofile =
        new PersonalInformationSubprofile(PersonalInformationSubprofile.MY_URI);

    populateTextProperty(VCARD_VERSION, props,
        personalInformationSubprofile, PROP_VCARD_VERSION);

    populateTextProperty(NICKNAME, props,
            personalInformationSubprofile, PROP_NICKNAME);

    populateTextProperty(DISPLAY_NAME, props,
                personalInformationSubprofile, PROP_DISPLAY_NAME);

    populateTextProperty(EMAIL, props,
                    personalInformationSubprofile, PROP_EMAIL);

    populateTextProperty(FN, props,
                    personalInformationSubprofile, PROP_FN);

    populateTextProperty(N, props,
                    personalInformationSubprofile, PROP_N);

    populateTextProperty(ORG, props,
                    personalInformationSubprofile, PROP_ORG);

    populateTextProperty(TEL, props,
                    personalInformationSubprofile, PROP_TEL);


    if (personalInformationSubprofile.numberOfProperties() == 0) {
      throw new ContactManagerShellException("There is not any property set in this VCard");
    }

    return personalInformationSubprofile;
  }

  private void populateTextProperty(String propNameSource, Properties props,
                                    PersonalInformationSubprofile personalInformationSubprofile,
                                    String propNameDestination) {

    String value = props.getProperty(propNameSource);

    if (value != null) {
      personalInformationSubprofile.setProperty(propNameDestination, value);
    }

  }

  private PersonalInformationSubprofile getPersonalInformationSubprofile() {
    return new PersonalInformationSubprofile(PersonalInformationSubprofile.MY_URI);
  }

  private Properties getProperties(String fileName) {
    Properties props = new Properties();
    try {
      String path = contactManagerConfigurationDirectory + separator + fileName;
      FileInputStream fis = new FileInputStream(path);
      props.load(fis);
      return props;
    } catch (FileNotFoundException e) {
      throw new ContactManagerShellException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public String getCommandText() {
    return COMMAND;
  }

}
