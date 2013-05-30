package org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users;


import static org.universAAL.AALapplication.medication_manager.user.management.impl.insert.dummy.users.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Mail extends BaseType {

  private final EmailEnum emailEnum;

  public Mail(String value, EmailEnum emailEnum) {
    super(value);

    validateParameter(emailEnum, "emailEnum");

    this.emailEnum = emailEnum;
  }

  public EmailEnum getEmailEnum() {
    return emailEnum;
  }

  @Override
  public String getType() {
    return emailEnum.getValue();
  }
}
