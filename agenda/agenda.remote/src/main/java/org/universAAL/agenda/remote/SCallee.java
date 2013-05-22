/**
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	2012 Ericsson Nikola Tesla d.d., www.ericsson.com/hr
	
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

import org.universAAL.agenda.remote.osgi.Activator;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
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
 * @author eandgrg
 */
public class SCallee extends ServiceCallee {

    /**
     * {@link ModuleContext}
     */
    private static ModuleContext mcontext;

    private static final ServiceResponse failure = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);

    public SCallee(ModuleContext mcontext) {
	super(mcontext, ProvidedService.profiles);
	SCallee.mcontext = mcontext;
    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    public ServiceResponse handleCall(ServiceCall call) {
	LogUtils
		.logInfo(
			mcontext,
			this.getClass(),
			"handleCall",
			new Object[] { "Received a Service Call addressed to the Agenda Web UI" },
			null);

	if (call == null) {
	    failure
		    .addOutput(new ProcessOutput(
			    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			    "Null call!?!"));
	    LogUtils
		    .logInfo(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Agenda remote could not execute the requested service: Null call!!" },
			    null);
	    return failure;
	}

	String operation = call.getProcessURI();
	if (operation == null) {
	    failure.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
		    "Null operation!?!"));
	    LogUtils
		    .logWarn(
			    mcontext,
			    this.getClass(),
			    "handleCall",
			    new Object[] { "Agenda remote could not execute the requested service: Null operation!?!" },
			    null);

	    return failure;
	}

	if (operation.startsWith(ProvidedService.SERVICE_START_UI)) {
	    Resource inputUser = call.getInvolvedUser();
	    User undefuser = null;
	    if (!(inputUser instanceof User)) {
		failure.addOutput(new ProcessOutput(
			ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
			"Invalid User Input!"));
		LogUtils
			.logWarn(
				mcontext,
				this.getClass(),
				"handleCall",
				new Object[] { "Agenda remote could not execute the requested service: Invalid User Input!" },
				null);

		return failure;
	    } else {
		undefuser = (User) inputUser;
	    }
	    LogUtils.logInfo(mcontext, this.getClass(), "handleCall",
		    new Object[] { "Addressed call was: {} ",
			    ProvidedService.SERVICE_START_UI }, null);

	    return showInitialDialog(undefuser);
	}
	LogUtils
		.logWarn(
			mcontext,
			this.getClass(),
			"handleCall",
			new Object[] { "Agenda remote could not execute the requested service: Unrecognized failure!" },
			null);

	return failure;
    }

    public ServiceResponse showInitialDialog(User user) {
	// before adding screen for selecting the User (Assisted Person) to edit
	// events for
	// Activator.uIProvider.showMainScreen(user);

	Activator.uIProvider.showInitialScreen(user);
	return new ServiceResponse(CallStatus.succeeded);
    }

}
