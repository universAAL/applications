package org.universAAL.AALApplication.health.motivation.schedulingTools;

import org.universAAL.ontology.health.owl.Treatment;

public class TreatmentWithPendingSessions {

	public Treatment treatmentWPS;
	
	public TreatmentWithPendingSessions(Treatment t){
		
	}
	
	public void setTWPS(Treatment t){
		treatmentWPS=t;
	}
	
	public Treatment getTWPS(){
		return treatmentWPS;
	}
}
