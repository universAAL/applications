package org.universAAL.AALapplication.contact_manager.persistence.layer;


import static org.universAAL.AALapplication.contact_manager.persistence.layer.Util.*;

/**
 * @author George Fournadjiev
 */
final class Mail {

  private final String value;
  private final EmailEnum emailEnum;

  Mail(String value, EmailEnum emailEnum) {

    validateParameter(value, "validate");
    validateParameter(emailEnum, "emailEnum");

    this.value = value;
    this.emailEnum = emailEnum;
  }

  public String getValue() {
    return value;
  }

  public EmailEnum getEmailEnum() {
    return emailEnum;
  }
}
