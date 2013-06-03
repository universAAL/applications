package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


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

}
