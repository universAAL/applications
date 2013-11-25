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

package org.universAAL.AALapplication.medication_manager.event.issuer.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author George Fournadjiev
 */
public final class EventIssuer {

  private final Timer timer;
  private int intervalInMinutes;
  private final TimerScheduler timerScheduler;

  private static final int DELAY = 5000;

  public EventIssuer(Timer timer) {

    this.timer = timer;
    timerScheduler = new TimerScheduler();

    intervalInMinutes = Activator.getEventInssuerInterval();

  }

  public void start() {
    Log.info("started Event issuer | interval : %s", getClass(), intervalInMinutes);

    timer.schedule(new TimerTask() {
      @Override
      public void run() {

        runIntervalTimer();

      }

    }, DELAY, intervalInMinutes * 60 * 1000);
  }

  private void runIntervalTimer() {
    Log.info("Executing intake checker, if intakes are available in this future period: %s minutes, " +
        "The event issuer will start timer for every intake", getClass(), intervalInMinutes);

    List<Intake> intakesForTheTimePeriod = timerScheduler.findIntakesForTheTimePeriod(intervalInMinutes);

    debugIntakes(intakesForTheTimePeriod);

    if (!intakesForTheTimePeriod.isEmpty()) {
      timerScheduler.startTimers(intakesForTheTimePeriod);
    }
  }

  private void debugIntakes(List<Intake> intakesForTheTimePeriod) {
    Log.info("The database query found for the current time period %s intakes",
        getClass(), intakesForTheTimePeriod.size());


    for (Intake intake : intakesForTheTimePeriod) {
      Log.info("\n", getClass(), intake);
    }

    Log.info("End printing the intakes for current period", getClass());

  }

}
