package org.universAAL.AALApplication.health.motivation;

import org.universaal.ontology.owl.MotivationalMessage;

public interface SendMotivationMessageIface {
	
	public void sendMessageToAP (MotivationalMessage mm);
	public void sendMessageToCaregiver (MotivationalMessage mm);
	
}
