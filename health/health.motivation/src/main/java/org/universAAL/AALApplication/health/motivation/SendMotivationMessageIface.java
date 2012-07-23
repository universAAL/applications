package org.universAAL.AALApplication.health.motivation;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.owl.MotivationalMessage;

public interface SendMotivationMessageIface {
	
	public File getDBRoute(Locale language);
	public void sendMessageToAP (MotivationalMessage mm, Treatment t);
	public ArrayList <MotivationalMessage> getMMsentToAP(); 
	public void sendMessageToCaregiver (MotivationalMessage mm, Treatment t);
	public ArrayList <MotivationalMessage> getMMsentToCaregiver(); 

}
