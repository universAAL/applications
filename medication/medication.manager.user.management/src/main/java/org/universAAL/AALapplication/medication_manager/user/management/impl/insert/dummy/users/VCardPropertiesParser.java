package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;

import org.universAAL.AALapplication.medication_manager.user.management.impl.MedicationManagerUserManagementException;
import org.universAAL.ontology.profile.PersonalInformationSubprofile;
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
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.TelEnum.*;
import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;
import static org.universAAL.ontology.profile.PersonalInformationSubprofile.*;

/**
 * @author George Fournadjiev
 */
public final class VCardPropertiesParser {


  static {
    PROPERTIES = new String[]{USER_URI, VCARD_VERSION, LAST_REVISION, NICKNAME, DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA,
        BIRTHPLACE, ABOUT_ME, GENDER, BDAY, EMAIL, FN, N, ORG, PHOTO, TEL};

    UN_IMPLEMENTED_PROPERTIES = new String[]{BIRTHPLACE, GENDER, N, ORG, PHOTO};

  }

  private final Properties props = new Properties();

  public PersonalInformationSubprofile createSubprofile(String propertyFileName) {
    System.out.println("*********** Parsing the following VCard: " + propertyFileName + " ***************************");

    loadProperties(propertyFileName, props);

    PersonalInformationSubprofile subprofile = createPersonalInformationSubprofile(props);


    System.out.println("*********** End of parsing: " + propertyFileName + " ***************************");

    return subprofile;
  }

  public Properties getProps() {
    return props;
  }

  private void loadProperties(String propertyFileName, Properties props) {
    try {
      InputStream inputStream =
          VCardPropertiesParser.class.getClassLoader().getResourceAsStream(propertyFileName);
      props.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      throw new MedicationManagerUserManagementException(e);
    }
  }

  private PersonalInformationSubprofile createPersonalInformationSubprofile(Properties props) {

    checkForUnknownProperties(props);
    checkForUnImplementedProperties(props);

    String userUri = props.getProperty(USER_URI);
    PersonalInformationSubprofile personalInformationSubprofile =
        new PersonalInformationSubprofile(userUri + "InfoSubProf");

    populateTextProperty(DISPLAY_NAME, props,
            personalInformationSubprofile, PROP_DISPLAY_NAME);

    populateTextProperty(VCARD_VERSION, props,
        personalInformationSubprofile, PROP_VCARD_VERSION);

    populateDateProperty(LAST_REVISION, props,
        personalInformationSubprofile, PROP_LAST_REVISION);

    populateTextProperty(NICKNAME, props,
        personalInformationSubprofile, PROP_NICKNAME);



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

    populateTextProperty(USER_URI, props,
        personalInformationSubprofile, PROP_URL);

    populateTelProperty(props, personalInformationSubprofile);

    populateEmailProperty(props, personalInformationSubprofile);


    if (personalInformationSubprofile.numberOfProperties() < 2) {
      throw new MedicationManagerUserManagementException("There is not any property set in this VCard");
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
        throw new MedicationManagerUserManagementException("The property : " + name + " is not supported");
      }
    }
  }

  private void checkForUnImplementedProperties(Properties props) {

    Enumeration en = props.propertyNames();

    while (en.hasMoreElements()) {
      String name = (String) en.nextElement();
      for (String propName : UN_IMPLEMENTED_PROPERTIES) {
        if (name.equals(propName)) {
          throw new MedicationManagerUserManagementException("The property : " + propName + " is not implemented");
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

  private void populateTelProperty(Properties props, PersonalInformationSubprofile personalInformationSubprofile) {

    String value = props.getProperty(TEL);

    if (value == null) {
      return;
    }

    List<Tel> telephones = new ArrayList<Tel>();

    StringTokenizer st = new StringTokenizer(value, ";");

    while (st.hasMoreElements()) {
      String pair = st.nextToken();
      Tel tel = createTel(pair);
      telephones.add(tel);
    }

    if (telephones.isEmpty()) {
      throw new MedicationManagerUserManagementException("Missing tel property ; delimiter");
    }

    personalInformationSubprofile.setProperty(PROP_TEL, telephones);
  }

  private Tel createTel(String pair) {
    StringTokenizer st = new StringTokenizer(pair, ":");
    if (st.countTokens() != 2) {
      throw new MedicationManagerUserManagementException("Missing tel property : delimiter. Must have two values separated it by this delimiter");
    }

    String number = st.nextToken();
    String type = st.nextToken();

    TelEnum telEnum = getEnumFromValue(type);
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
      throw new MedicationManagerUserManagementException("Unknown TelEnum : " + telEnum);
    }

    tel.setProperty(Tel.PROP_VALUE, number);

    return tel;
  }

  private void populateEmailProperty(Properties props, PersonalInformationSubprofile personalInformationSubprofile) {

    String value = props.getProperty(EMAIL);

    if (value == null) {
      return;
    }

    List<Email> emails = new ArrayList<Email>();

    StringTokenizer st = new StringTokenizer(value, ";");

    while (st.hasMoreElements()) {
      String pair = st.nextToken();
      Email mail = createEmail(pair);
      emails.add(mail);
    }

    if (emails.isEmpty()) {
      throw new MedicationManagerUserManagementException("Missing email property ; delimiter");
    }

    personalInformationSubprofile.setProperty(PROP_EMAIL, emails);
  }

  private Email createEmail(String pair) {
    StringTokenizer st = new StringTokenizer(pair, ":");
    if (st.countTokens() != 2) {
      throw new MedicationManagerUserManagementException("Missing email property : delimiter. " +
          "Must have two values separated it by this delimiter");
    }

    String number = st.nextToken();
    String type = st.nextToken();

    EmailEnum emailEnum = EmailEnum.getEnumFromValue(type);
    Email mail;

    if (EmailEnum.INTERNET == emailEnum) {
      mail = new Internet();
    } else if (EmailEnum.X400 == emailEnum) {
      mail = new X400();
    } else {
      throw new MedicationManagerUserManagementException("Unknown EmailEnum : " + emailEnum);
    }

    mail.setProperty(Email.PROP_VALUE, number);

    return mail;
  }


  private XMLGregorianCalendar getXMLGregorianCalendar(String value) {
    try {
      Date date = DATE_FORMAT.parse(value);
      return getCalendar(date);
    } catch (ParseException e) {
      throw new MedicationManagerUserManagementException(e);
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


}
