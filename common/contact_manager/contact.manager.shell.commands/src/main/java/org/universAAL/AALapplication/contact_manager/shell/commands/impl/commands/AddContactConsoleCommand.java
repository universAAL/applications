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

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import static java.io.File.*;
import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.Activator.*;
import static org.universAAL.AALapplication.contact_manager.shell.commands.impl.commands.ContactConsoleCommands.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;

/**
 * @author George Fournadjiev
 */
public final class AddContactConsoleCommand extends ConsoleCommand {

  private static final String COMMAND = COMMAND_PREFIX + ':' + ADD_CONTACT + " command";

  private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

  public static final String VCARD_VERSION = "vCardVersion";
  public static final String LAST_REVISION = "lastRevision";
  public static final String NICKNAME = "nicname";
  public static final String DISPLAY_NAME = "displayName";
  public static final String UCI_LABEL = "uciLable";
  public static final String UCI_ADDITIONAL_DATA = "uciAdditionalData";
  public static final String BIRTHPLACE = "birthplace";
  public static final String ABOUT_ME = "aboutMe";
  public static final String GENDER = "hasGender";
  public static final String BDAY = "bday";
  public static final String EMAIL = "email";
  public static final String FN = "fn";
  public static final String N = "n";
  public static final String ORG = "org";
  public static final String PHOTO = "photo";
  public static final String TEL = "tel";
  public static final String URL = "url";

  private static String[] UN_IMPLEMENTED_PROPERTIES;
  private static String[] PROPERTIES;

  static {
    PROPERTIES = new String[]{VCARD_VERSION, LAST_REVISION, NICKNAME, DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA,
        BIRTHPLACE, ABOUT_ME, GENDER, BDAY, EMAIL, FN, N, ORG, PHOTO, TEL, URL};

    UN_IMPLEMENTED_PROPERTIES = new String[]{BIRTHPLACE, GENDER, EMAIL, N, ORG, PHOTO, TEL, URL};

  }

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

    checkForUnknownProperties(props);
    checkForUnImplementedProperties(props);

    PersonalInformationSubprofile personalInformationSubprofile =
        new PersonalInformationSubprofile(PersonalInformationSubprofile.MY_URI);

    populateTextProperty(VCARD_VERSION, props,
        personalInformationSubprofile, PROP_VCARD_VERSION);

    populateDateProperty(LAST_REVISION, props,
        personalInformationSubprofile, PROP_LAST_REVISION);

    populateTextProperty(NICKNAME, props,
        personalInformationSubprofile, PROP_NICKNAME);

    populateTextProperty(DISPLAY_NAME, props,
        personalInformationSubprofile, PROP_DISPLAY_NAME);

    populateTextProperty(UCI_LABEL, props,
        personalInformationSubprofile, PROP_UCI_LABEL);

    populateTextProperty(UCI_ADDITIONAL_DATA, props,
        personalInformationSubprofile, PROP_UCI_ADDITIONAL_DATA);

    populateTextProperty(ABOUT_ME, props,
        personalInformationSubprofile, PROP_ABOUT_ME);

    populateDateProperty(BDAY, props,
        personalInformationSubprofile, PROP_BDAY);

    populateTextProperty(FN, props,
        personalInformationSubprofile, PROP_FN);


    if (personalInformationSubprofile.numberOfProperties() < 2) {
      throw new ContactManagerShellException("There is not any property set in this VCard");
    }

    return personalInformationSubprofile;
  }

  private void checkForUnknownProperties(Properties props) {

    Enumeration en = props.propertyNames();

    while (en.hasMoreElements()) {
      String name = (String) en.nextElement();
      boolean supported = false;
      for (String propName : PROPERTIES) {
        if (name.equals(propName)) {
          supported = true;
        }
      }

      if (!supported) {
        throw new ContactManagerShellException("The property : " + name + " is not supported");
      }
    }
  }

  private void checkForUnImplementedProperties(Properties props) {

    Enumeration en = props.propertyNames();

    while (en.hasMoreElements()) {
      String name = (String) en.nextElement();
      for (String propName : UN_IMPLEMENTED_PROPERTIES) {
        if (name.equals(propName)) {
          throw new ContactManagerShellException("The property : " + propName + " is not implemented");
        }
      }
    }
  }

  private void populateDateProperty(String propNameSource, Properties props,
                                    PersonalInformationSubprofile personalInformationSubprofile,
                                    String propNameDestination) {

    String value = props.getProperty(propNameSource);

    if (value != null) {
      XMLGregorianCalendar calendar = getXMLGregorianCalendar(value);
      personalInformationSubprofile.setProperty(propNameDestination, calendar);
    }

  }

  private XMLGregorianCalendar getXMLGregorianCalendar(String value) {
    try {
      Date date = DATE_FORMAT.parse(value);
      return getCalendar(date);
    } catch (ParseException e) {
      throw new ContactManagerShellException(e);
    }
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
