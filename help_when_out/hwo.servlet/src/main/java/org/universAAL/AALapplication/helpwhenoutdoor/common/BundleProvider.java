package org.universAAL.AALapplication.helpwhenoutdoor.common;

import org.universAAL.AALapplication.helpwhenoutdoor.AbnormalSituationDetector;
import org.universAAL.AALapplication.helpwhenoutdoor.OutdoorLocationContextPublisher;

public interface BundleProvider {

	public Agenda getAgenda();
	public AbnormalSituationDetector getAbnormalSituationDetector();
	public OutdoorLocationContextPublisher getOutdoorLocationContextPublisher();

}
