package org.universAAL.AALapplication.health.manager.ui.measurements;

import org.universAAL.AALapplication.health.manager.ui.AbstractHealthForm;
import org.universAAL.AALapplication.health.manager.ui.MainMenu;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
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
public final class Motivation extends AbstractHealthForm {

	private static final String GO_HOME = "goHome";

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.ui.UICaller#handleUIResponse(org.universAAL.middleware.ui.UIResponse)
	 */
	public Motivation(ModuleContext context, User inputUser) {
		super(context, inputUser);
	}

	@Override
	public void handleUIResponse(UIResponse arg0) {
		new MainMenu(context,inputUser).show();
	}
	
	public void show(){
		LogUtils.logDebug(context, getClass(), "show", "SHOWING: WELL DONE");
		
		Form f = Form.newMessage("Motivation Message",  GO_HOME);
		new SimpleOutput(f.getIOControls(), null, null,
				"Make sure you are relaxed and comfortable and make sure your blood presure is correctly.");
		new Submit(f.getSubmits(), new Label("Back", null), GO_HOME);
		
		sendForm(f);
	}

}