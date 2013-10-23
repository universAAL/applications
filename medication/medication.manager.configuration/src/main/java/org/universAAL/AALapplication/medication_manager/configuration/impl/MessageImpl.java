package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.universAAL.AALapplication.medication_manager.configuration.Message;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author George Fournadjiev
 */
public final class MessageImpl implements Message {

  private final ResourceBundle resourceBundle;

  public MessageImpl(ResourceBundle resourceBundle) {

    this.resourceBundle = resourceBundle;
  }

  public String getMessage(String key, Object... args) {
    return MessageFormat.format(resourceBundle.getString(key), args);
  }
}
