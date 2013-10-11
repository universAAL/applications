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

package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.user.management.impl.MedicationManagerUserManagementException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author George Fournadjiev
 */
public final class Util {

  public static final String USER_URI = "userUri";
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
  public static final String TYPE = "type";
  public static final String NEW_VCARD_BILL_PROPERTIES = "newVCardBill.properties";
  public static final String NEW_VCARD_NIK_PROPERTIES = "newVCardNik.properties";
  public static final String NEW_VCARD_NIKOLA_PROPERTIES = "newVCardNikola.properties";
  public static final String NEW_VCARD_JOHN_PROPERTIES = "newVCardJohn.properties";
  public static final String NEW_VCARD_MARIA_PROPERTIES = "newVCardMaria.properties";
  public static final String NEW_VCARD_IREN_PROPERTIES = "newVCardIren.properties";
  public static final String NEW_VCARD_ASPARUH_PROPERTIES = "newVCardAsparuh.properties";
  public static final String NEW_VCARD_GEORGE_PROPERTIES = "newVCardGeorge.properties";
  public static final String NEW_VCARD_IVAILO_PROPERTIES = "newVCardIvailo.properties";
  public static final String NEW_VCARD_IVAN_PROPERTIES = "newVCardIvan.properties";
  public static final String NEW_VCARD_PENCHO_PROPERTIES = "newVCardPencho.properties";
  public static final String NEW_VCARD_SAIED_PROPERTIES = "newVCardSaied.properties";
  public static final String NEW_VCARD_SIMEON_PROPERTIES = "newVCardSimeon.properties";
  public static final String NEW_VCARD_VENELIN_PROPERTIES = "newVCardVenelin.properties";
  private static final String[] DUMMY_USERS_PROPERTIES = new String[]{
      NEW_VCARD_BILL_PROPERTIES, NEW_VCARD_NIK_PROPERTIES, NEW_VCARD_NIKOLA_PROPERTIES,
      NEW_VCARD_JOHN_PROPERTIES, NEW_VCARD_MARIA_PROPERTIES, NEW_VCARD_IREN_PROPERTIES,
      NEW_VCARD_ASPARUH_PROPERTIES, NEW_VCARD_GEORGE_PROPERTIES, NEW_VCARD_IVAILO_PROPERTIES,
      NEW_VCARD_IVAN_PROPERTIES, NEW_VCARD_PENCHO_PROPERTIES, NEW_VCARD_SAIED_PROPERTIES,
      NEW_VCARD_SIMEON_PROPERTIES, NEW_VCARD_VENELIN_PROPERTIES
  };

  public static final String ASSISTED_PERSON = "AssistedPerson";
  public static final String CAREGIVER = "Caregiver";
  static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
  static String[] UN_IMPLEMENTED_PROPERTIES;
  static String[] PROPERTIES;

  static {
    PROPERTIES = new String[]{USER_URI, VCARD_VERSION, LAST_REVISION, NICKNAME, DISPLAY_NAME, UCI_LABEL, UCI_ADDITIONAL_DATA,
        BIRTHPLACE, ABOUT_ME, GENDER, BDAY, EMAIL, FN, N, ORG, PHOTO, TEL, TYPE};

    UN_IMPLEMENTED_PROPERTIES = new String[]{BIRTHPLACE, GENDER, N, ORG, PHOTO};

  }

  private Util() {
    // to prevent initialization, because this is util class
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerUserManagementException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerUserManagementException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static XMLGregorianCalendar getCalendar(Date date) {
    try {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime(date);
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    } catch (DatatypeConfigurationException e) {
      throw new MedicationManagerUserManagementException(e);
    }
  }

  public static Date getDateFromXMLGregorianCalendar(XMLGregorianCalendar calendar) {
    return calendar.toGregorianCalendar().getTime();

  }

  public static String[] getDummyUsersProperties(ConfigurationProperties configurationProperties) {
    if (configurationProperties.isTestMode()) {
      return DUMMY_USERS_PROPERTIES;
    }

    return new String[]{NEW_VCARD_SAIED_PROPERTIES};
  }
}
