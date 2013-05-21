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


package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;

/**
 * @author George Fournadjiev
 */
public final class Time extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Time";

  public static final String PROP_YEAR = MedicationOntology.NAMESPACE + "year";
  public static final String PROP_MONTH = MedicationOntology.NAMESPACE + "month";
  public static final String PROP_DAY = MedicationOntology.NAMESPACE + "day";
  public static final String PROP_HOUR = MedicationOntology.NAMESPACE + "hour";
  public static final String PROP_MINUTES = MedicationOntology.NAMESPACE + "minutes";

  public Time(int year, int month, int day, int hour, int minutes) {
    setYear(year);
    setMonth(month);
    setDay(day);
    setHour(hour);
    setMinutes(minutes);
  }

  public Time(String uri) {
    super(uri);
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_FULL;
  }

  public String getClassURI() {
    return MY_URI;
  }

  public boolean isWellFormed() {
    return true;
  }

  public int getYear() {
    return ((Integer) props.get(PROP_YEAR)).intValue();
  }

  public int getMonth() {
    return ((Integer) props.get(PROP_MONTH)).intValue();
  }

  public int getDay() {
    return ((Integer) props.get(PROP_DAY)).intValue();
  }

  public int getHour() {
    return ((Integer) props.get(PROP_HOUR)).intValue();
  }

  public int getMinutes() {
    return ((Integer) props.get(PROP_MINUTES)).intValue();
  }

  public void setYear(int year) {
    props.put(PROP_YEAR, Integer.valueOf(year));
  }

  public void setMonth(int month) {
    props.put(PROP_MONTH, Integer.valueOf(month));
  }

  public void setDay(int day) {
    props.put(PROP_DAY, Integer.valueOf(day));
  }

  public void setHour(int hour) {
    props.put(PROP_HOUR, Integer.valueOf(hour));
  }

  public void setMinutes(int minutes) {
    props.put(PROP_MINUTES, Integer.valueOf(minutes));
  }

  @Override
  public String toString() {
    int year = getYear();
    int month = getMonth();
    int day = getDay();
    int hour = getHour();
    int minutes = getMinutes();
    return "Time{" +
        "year=" + year +
        ", month=" + month +
        ", day=" + day +
        ", hour=" + hour +
        ", minutes=" + minutes +
        '}';
  }

  public String getDailyTextFormat() {
    int minutes = getMinutes();
    String minutesText = String.valueOf(minutes);
    if (minutes == 0) {
       minutesText = minutesText + '0';
    } else if(minutes < 10) {
      minutesText = '0' + minutesText;
    }
    return getHour() + ":" + minutesText;
  }
}
