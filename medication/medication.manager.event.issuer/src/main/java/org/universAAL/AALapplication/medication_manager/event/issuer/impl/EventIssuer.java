package org.universAAL.AALapplication.medication_manager.event.issuer.impl;

import java.util.Timer;
import java.util.TimerTask;

import static org.universAAL.AALapplication.medication_manager.event.issuer.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class EventIssuer {

  private final Timer timer;
  private int intervalInMunites;

  public EventIssuer(Timer timer) {

    this.timer = timer;

    intervalInMunites = getEventInssuerInterval();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        Log.info("Executing intake checker, if intakes are available in this future period: %s minutes, " +
            "The event issuer will start timer for every intake", getClass(), intervalInMunites);


      }
    }, 1000, intervalInMunites * 60 * 1000);
  }

  public void start() {
    System.out.println("started Event issuer | interval : " + intervalInMunites);
  }

}
