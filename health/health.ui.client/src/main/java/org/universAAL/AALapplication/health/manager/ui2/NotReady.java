/**
 * 
 */
package org.universAAL.AALapplication.health.manager.ui2;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.profile.User;

/**
 * @author amedrano
 *
 */
public final class NotReady extends AbstractHealthForm {

	private static final String GO_HOME = "goHome";

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL.middleware.ui.UIResponse)
	 */
	public NotReady(ModuleContext context, User inputUser) {
		super(context, inputUser);
	}

	@Override
	public void handleUIResponse(UIResponse arg0) {
		new MainMenu(context,inputUser).show();
	}
	
	public void show(){
		Form f = Form.newDialog("Functionality not available", new Resource());
		
		new SimpleOutput(f.getIOControls(), null, null,
				"Sorry this functionality is not yet ready. Press \"Back\" to got to Main menu");
		new Submit(f.getSubmits(), new Label("Back", null), GO_HOME);
	}

}
