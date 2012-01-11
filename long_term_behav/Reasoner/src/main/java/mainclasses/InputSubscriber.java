package mainclasses;

import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.InputField;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.rdf.Resource;

public class InputSubscriber {
	static final String INPUT_NAMESPACE = SCalleeProvidedService.SERVICE_START_UI+"InputSubscriber#";
	
	
	
	public void handleInputEvent(InputEvent ie) {
		
		
		
	if (ie != null) {
		   if (mainclasses.OutputProvider.SUBMISSION_ON.equals(ie.getSubmissionID())){
			     
			   Activator.opublisher.initMainDialog();
			   return;
			   
		   } else if(mainclasses.OutputProvider.SUBMISSION_OFF.equals(ie.getSubmissionID())){
			     
			   Activator.opublisher.initMainDialog();
			   return;
			   
		   } else if (mainclasses.OutputProvider.CONFIGURATION.equals(ie.getSubmissionID())){
			   
			   System.out.println("input subscriber calling menu");
			   Activator.opublisher.showConfigurationMenu();
		   
		    return;
		   }
	}
		  
		   // Cancel Dialog, go back to main dialog
	
	}
}


