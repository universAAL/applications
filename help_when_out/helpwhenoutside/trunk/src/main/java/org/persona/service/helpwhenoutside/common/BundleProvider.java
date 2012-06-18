package org.persona.service.helpwhenoutside.common;

//import org.aal_persona.platform.ContextInitializer.ContextInitializer;
import org.persona.service.helpwhenoutside.AbnormalSituationDetector;
import org.persona.service.helpwhenoutside.OutdoorLocationContextPublisher;

public interface BundleProvider {

	public Agenda getAgenda();
	//public ContextInitializer getContextInitializer();
	public AbnormalSituationDetector getAbnormalSituationDetector();
	public OutdoorLocationContextPublisher getOutdoorLocationContextPublisher();

}
