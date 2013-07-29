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

import org.universAAL.AALapplication.medication_manager.configuration.Pair;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;


/**
 * @author George Fournadjiev
 */
public final class TimerScheduler {


  public List<Intake> findIntakesForTheTimePeriod(int intervalInMunites) {

    PersistentService persistentService = Activator.getPersistentService();

    IntakeDao intakeDao = persistentService.getIntakeDao();

    Pair<Timestamp, Timestamp> periodTimestampPair = createBeginEndTimestamps(intervalInMunites);

    Timestamp begin = periodTimestampPair.getFirst();
    Timestamp end = periodTimestampPair.getSecond();

    Log.info("Looking for the following period: begin = %s end= %s", getClass(), begin, end);

    return intakeDao.getActiveIntakesByPeriod(begin, end);
  }

  private Pair<Timestamp, Timestamp> createBeginEndTimestamps(int intervalInMunites) {

    Calendar begin = Calendar.getInstance();
    begin.setTime(new Date());

    Calendar end = Calendar.getInstance();
    end.setTime(new Date());
    end.add(Calendar.MINUTE, intervalInMunites);

    Timestamp beginTimestamp = new Timestamp(begin.getTime().getTime());
    Timestamp endTimestamp = new Timestamp(end.getTime().getTime());

    return new Pair<Timestamp, Timestamp>(beginTimestamp, endTimestamp);

  }

  public void startTimers(List<Intake> intakesForTheTimePeriod) {

    for (Intake intake : intakesForTheTimePeriod) {
      IntakeTimerTask intakeTimerTask = new IntakeTimerTask(intake);
      Timer timer = new Timer();
      timer.schedule(intakeTimerTask, intake.getTimePlan());
    }


  }
}
