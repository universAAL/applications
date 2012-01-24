/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.personal_safety.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.input.InputEvent;
import org.universAAL.middleware.input.InputSubscriber;
import org.universAAL.ontology.profile.User;

/**
 * @author <a href="mailto:alfiva@itaca.upv.es">Alvaro Fides Valero</a>
 *
 */
public class ISubscriber extends InputSubscriber{
	
	private final static Logger log=LoggerFactory.getLogger(ISubscriber.class);
	
	protected ISubscriber(ModuleContext context) {
		super(context);
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.persona.middleware.input.InputSubscriber#handleInputEvent(org.persona.middleware.input.InputEvent)
	 */
	public void handleInputEvent(InputEvent event) {
		User user=(User) event.getUser();
		log.info("Received an Input Event from user {}", user.getURI());
		String submit=event.getSubmissionID();

		try{
			if(submit.equals(RiskGUI.SUBMIT_HOME)){
				log.debug("Input received was go Home");
				//do nothing-> return to main menu
			}else if(submit.equals(RiskGUI.SUBMIT_CANCEL)){
				log.debug("Input received was Cancel (abort risk)");
				OPublisher.responseWatch.cancel();
			}
		}catch(Exception e){
			log.error("Error while processing the user input: {}",e);
		}

	}

	public void dialogAborted(String dialogID) {
		// TODO Auto-generated method stub
		
	}
	
	public void subscribe(String dialogID) {
		addNewRegParams(dialogID);
	}
	
}
