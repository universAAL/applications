/*
	Copyright 2008-2014 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
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
package org.universAAL.ltba.service;

import org.universAAL.ltba.manager.ConsequenceListener;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;

public class LTBAProvider extends ServiceCallee {
	private ModuleContext mctx;
	private ConsequenceListener listener;

	public LTBAProvider(ModuleContext context) {
		super(context, ProvidedLTBAService.profiles);
		mctx = context;
	}

	public LTBAProvider(ModuleContext mc, ConsequenceListener listener) {
		super(mc, ProvidedLTBAService.profiles);
		this.listener = listener;
	}

	@Override
	public void communicationChannelBroken() {
	}

	@Override
	public ServiceResponse handleCall(ServiceCall call) {

		LogUtils.logDebug(mctx, getClass(), "ServiceProvided",
				new String[] { "Handling service call..." }, null);

		System.out.println("HANDLING SERVICE CALL:" + "\n"
				+ call.getProcessURI());

		if (call == null) {
			return null;
		} else {
			String operation = call.getProcessURI();
			if (operation == null) {
				return null;
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SWITCH_ON)) {
//				System.out.println("SWITCHING ON IN HANDLERe");
				LogUtils.logTrace(mctx, getClass(), "ServiceProvided",
						new String[] { "SWITCHING ON..." }, null);
				listener.setStatus(true);
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SWITCH_OFF)) {
//				System.out.println("SWITCHING OFF IN HANDLER");
				LogUtils.logTrace(mctx, getClass(), "ServiceProvided",
						new String[] { "SWITCHING OFF..." }, null);
				listener.setStatus(false);
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_PRINT_REPORT)) {
//				System.out.println("PRINTING REPORT!!");
				// Assuming it's created in the Activator
				ConsequenceListener.getInstance().printDayReport(
						call.getInvolvedUser());
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SHOW_WEEK)) {
				System.out.println("PRINTING WEEK!!");
				// Assuming it's created in the Activator
				/** Gonna print a week report for testing */
				ConsequenceListener.getInstance().printWeekReport(call.getInvolvedUser());
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SHOW_MONTH)) {
				System.out.println("PRINTING MONTH!!");
				// Assuming it's created in the Activator
				/** Gonna print a week report for testing */
				ConsequenceListener.getInstance().printMonthReport(call.getInvolvedUser());
				return new ServiceResponse(CallStatus.succeeded);
			}
		}
		return new ServiceResponse(CallStatus.serviceSpecificFailure);

	}

}
