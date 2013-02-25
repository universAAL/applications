package org.universAAL.AALapplication.contact_manager.persistence.layer;

import org.universAAL.AALapplication.contact_manager.persistence.impl.ContactManagerPersistenceException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author George Fournadjiev
 */
public final class Util {

  private Util() {
    // to prevent initialization, because this is util class
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new ContactManagerPersistenceException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new ContactManagerPersistenceException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static XMLGregorianCalendar getCalendar(Date date) {
    try {
      GregorianCalendar c = new GregorianCalendar();
      c.setTime(date);
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    } catch (DatatypeConfigurationException e) {
      throw new ContactManagerPersistenceException(e);
    }
  }

  public static Date getDateFromXMLGregorianCalendar(XMLGregorianCalendar calendar) {
      return calendar.toGregorianCalendar().getTime();

  }

}
