/*******************************************************************************
 * Copyright 2011 Universidad Politécnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.Application.personal_safety.sw.panic;


import java.util.Locale;

import org.universAAL.Application.personal_safety.sw.panic.osgi.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.risk.PanicButton;

public class OPublisher extends UICaller{

	public static String PANICACTION = "panic";
	static PanicButton panic = new PanicButton(PanicButton.MY_URI+"SWPanic");
	
	public OPublisher(ModuleContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
	
	public void confirmPanic(Resource user) {
		Form f = newComfimDialog();
		sendUIRequest(new UIRequest(user, f, LevelRating.high, Locale.getDefault(), PrivacyLevel.insensible));
	}
	
	protected Form newComfimDialog() {
		//Form f = Form.newMessage("Comfirm Panic", "Do you really whant to send a panic alert?");
		//f.setProperty(Form.LABEL_MESSAGE_KEEP, (Object) new Label("YES!",null));
		Form f = Form.newDialog("Comfirm Panic", "");
		new Submit(f.getSubmits(), new Label("YES!", (String) null), PANICACTION);
		new Submit(f.getSubmits(), new Label("NO", (String) null), "nopanic");
		new SimpleOutput(f.getIOControls(), new Label("Do you really whant to send a panic alert?", ""),
				null,"");
		return f;
	}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
		
	}

	public void handleUIResponse(UIResponse event) {
		if (event.getSubmissionID().startsWith(PANICACTION)) {
			panic.setPressedBy((User)event.getUser());
			panic.setActivated(true);
			ContextEvent panicCE = new ContextEvent(panic, PanicButton.PROP_ACTIVATED);
			Activator.cpublisher.publish(panicCE);
		}
		else {
			panic.setActivated(false);
		}
		
	}

}
