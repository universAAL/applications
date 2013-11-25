package org.universAAL.AALapplication.medication_manager.configuration.impl;

import org.universAAL.AALapplication.medication_manager.configuration.Message;
import org.universAAL.AALapplication.medication_manager.configuration.MessageCreator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author George Fournadjiev
 */
public final class MessageCreatorImpl implements MessageCreator {

  public Message createMessage(ClassLoader classLoader, String messagesFile) {
//    Locale.setDefault(Locale.GERMAN);

    Log.info("The current default Locale is: %s", getClass(), Locale.getDefault());

    ResourceBundle resourceBundle = null;
    try {
      resourceBundle = ResourceBundle.getBundle(messagesFile, Locale.getDefault(), classLoader);
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return new MessageImpl(resourceBundle);
  }
}
