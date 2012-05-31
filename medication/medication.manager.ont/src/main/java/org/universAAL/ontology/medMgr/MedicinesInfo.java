package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class MedicinesInfo {

  private final String generalInfo;
  private final String detailsInfo;
  private final Time time;

  public MedicinesInfo(String generalInfo, String detailsInfo, Time time) {
    this.generalInfo = generalInfo;
    this.detailsInfo = detailsInfo;
    this.time = time;
  }

  public String getGeneralInfo() {
    return generalInfo;
  }

  public String getDetailsInfo() {
    return detailsInfo;
  }

  public Time getTime() {
    return time;
  }
}
