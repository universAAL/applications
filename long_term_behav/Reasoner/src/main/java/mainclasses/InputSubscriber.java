package mainclasses;

import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.io.rdf.Label;
import org.universAAL.middleware.io.rdf.Submit;
import org.universAAL.middleware.rdf.Resource;

public class InputSubscriber {

	public void handleInputEvent(InputEvent ie) {
		
		Form f = Form.newDialog("UI: LTBA_CONFIG", new Resource());
		
		  if (ie != null) {
		   if (mainclasses.OutputProvider.CONFIGURATION.equals(ie.getSubmissionID()))
			   
			   new Submit(f.getIOControls(), new Label("ConfigurationMenu", null),mainclasses.OutputProvider.CONFIGURATION_MENU);
			   
		    return;
		  }
		   // Cancel Dialog, go back to main dialog
	
    }
}

