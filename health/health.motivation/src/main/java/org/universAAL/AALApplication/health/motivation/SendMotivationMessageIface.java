package org.universAAL.AALApplication.health.motivation;

import java.util.ArrayList;

import org.universAAL.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;

/**
 * Implements the mechanism that sends the message to the platform.
 * Should be connected to the context bus and send messages there.
 * @author mdelafuente
 *
 */
public interface SendMotivationMessageIface {
	
	public void sendMessageToAP (MotivationalMessage mm, Treatment t);
	public ArrayList <MotivationalMessage> getMMsentToAP(); 
	public void sendMessageToCaregiver (MotivationalMessage mm, Treatment t);
	public ArrayList <MotivationalMessage> getMMsentToCaregiver(); 

}
