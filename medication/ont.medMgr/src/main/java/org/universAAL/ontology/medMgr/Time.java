package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.service.owl.Service;

/**
 * @author George Fournadjiev
 */
public final class Time extends Service {

  public static final String MY_URI = MedicationOntology.NAMESPACE + "Time";

  public static final String YEAR = MedicationOntology.NAMESPACE + "year";
  public static final String MONTH = MedicationOntology.NAMESPACE + "month";
  public static final String DAY = MedicationOntology.NAMESPACE + "day";
  public static final String HOUR = MedicationOntology.NAMESPACE + "hour";
  public static final String MINUTES = MedicationOntology.NAMESPACE + "minutes";

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
    return ((Integer) props.get(YEAR)).intValue();
  }

  public int getMonth() {
      return ((Integer) props.get(MONTH)).intValue();
    }

  public int getDay() {
    return ((Integer) props.get(DAY)).intValue();
  }

  public int getHour() {
    return ((Integer) props.get(HOUR)).intValue();
  }

  public int getMinutes() {
    return ((Integer) props.get(MINUTES)).intValue();
  }

  public void setYear(int year) {
    props.put(YEAR, Integer.valueOf(year));
  }

  public void setMonth(int month) {
      props.put(MONTH, Integer.valueOf(month));
    }

  public void setDay(int day) {
    props.put(DAY, Integer.valueOf(day));
  }

  public void setHour(int hour) {
    props.put(HOUR, Integer.valueOf(hour));
  }

  public void setMinutes(int minutes) {
    props.put(MINUTES, Integer.valueOf(minutes));
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
}
