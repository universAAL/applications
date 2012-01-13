package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;

/**
 * @author George Fournadjiev
 */
public final class MissedIntakeEventSubscriber extends ContextSubscriber {


  public MissedIntakeEventSubscriber(ModuleContext context, ContextEventPattern[] initialSubscriptions) {
    super(context, initialSubscriptions);
  }

  public void communicationChannelBroken() {
    //"Not implemented yet"
  }

  public void handleContextEvent(ContextEvent event) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
