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
