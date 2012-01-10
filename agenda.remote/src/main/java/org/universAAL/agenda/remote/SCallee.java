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
package org.universAAL.agenda.remote;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.profile.User;

/**
 * 
 * @author alfiva
 */
public class SCallee extends ServiceCallee {

    private final static Logger log = LoggerFactory.getLogger(SCallee.class);

    private static final ServiceResponse failure = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    public SCallee(BundleContext context) {
	super(context, ProvidedService.profiles);
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    public ServiceResponse handleCall(ServiceCall call) {
	log.info("Received a Service Call addressed to the Agenda Web UI");

	if (call == null) {
	    failure
		    .addOutput(new ProcessOutput(
			    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			    "Null call!?!"));
	    log
		    .warn("Nutritional Advisor could not execute the requested service: Null call!?!");
	    return failure;
	}

	String operation = call.getProcessURI();
	if (operation == null) {
	    failure.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
		    "Null operation!?!"));
	    log
		    .warn("Nutritional Advisor could not execute the requested service: Null operation!?!");
	    return failure;
	}

	if (operation.startsWith(ProvidedService.SERVICE_START_UI)) {
	    Resource inputUser = call.getInvolvedUser();
	    User undefuser = null;
	    if (!(inputUser instanceof User)) {
		failure.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			"Invalid User Input!"));
		log
			.warn("Nutritional Advisor could not execute the requested service: Invalid User Input!");
		return failure;
	    } else {
		undefuser = (User) inputUser;
	    }
	    log.info("Addressed call was: {} ",
		    ProvidedService.SERVICE_START_UI);
	    return showMainDialog(undefuser);
	}
	log
		.warn("Nutritional Advisor could not execute the requested service: Unrecognized failure!");
	return failure;
    }

    public ServiceResponse showMainDialog(User user) {
	Activator.guioutput.showMainScreen(user);// TODO
	return new ServiceResponse(CallStatus.succeeded);
    }

}
