package org.universAAL.ontology.medMgr;

/**
 * @author George Fournadjiev
 */
public final class MedicinesInfo {

  private final String generalInfo;
  private final String detailsInfo;

  public MedicinesInfo(String generalInfo, String detailsInfo) {
    this.generalInfo = generalInfo;
    this.detailsInfo = detailsInfo;
  }

  public String getGeneralInfo() {
    return generalInfo;
  }

  public String getDetailsInfo() {
    return detailsInfo;
  }
}
