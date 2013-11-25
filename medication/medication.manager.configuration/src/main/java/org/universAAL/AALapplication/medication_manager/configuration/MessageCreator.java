package org.universAAL.AALapplication.medication_manager.configuration;

/**
 * @author George Fournadjiev
 */
public interface MessageCreator {

  Message createMessage(ClassLoader classLoader, String messagesFile);

}
