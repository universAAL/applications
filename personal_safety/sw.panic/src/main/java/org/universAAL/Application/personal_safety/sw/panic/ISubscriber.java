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


import org.universAAL.Application.personal_safety.sw.panic.osgi.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;
import org.universAAL.ontology.profile.User;
import org.universAAL.ontology.risk.PanicButton;

public class ISubscriber extends InputSubscriber{
	
	public static String PANICACTION = "panic";
	static PanicButton panic = new PanicButton(PanicButton.MY_URI+"SWPanic");
	public ISubscriber(ModuleContext context) {
		super(context);
		panic.setActivated(false);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
		
	}

	public void handleInputEvent(InputEvent event) {
		// TODO Auto-generated method stub
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
	public void subscribe(String dialogID) {
		addNewRegParams(dialogID);
	}
}
