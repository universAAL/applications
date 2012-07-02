package org.universAAL.drools;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.drools.Rule;

/**
 * Provider to for the services of the rules engine.
 * @author mllorente
 * TSB Technologies for Health and Well-being
 */
public class DroolsReasonerProvider extends ServiceCallee {
private ModuleContext mctx;
	protected DroolsReasonerProvider(ModuleContext context) {
		super(context, ProvidedDroolsReasonerService.profiles);
		mctx = context;
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceResponse handleCall(ServiceCall call) {
		LogUtils.logDebug(mctx , getClass(), "ServiceProvided", new String[]{"Handling service call..."}, null);
		if (call == null) {
			return null;
		} else {
			String operation = call.getProcessURI();
			if (operation == null) {
				return null;
			} else if (operation
					.startsWith(ProvidedDroolsReasonerService.SERVICE_ADD_RULE)) {
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"ADDING A RULE..."},null);
				Object input = call.getInputValue(ProvidedDroolsReasonerService.INPUT_RULE);//URI DEL INPUT DEL PROFILE
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"The body is:"+((Rule)input).getBody()},null);
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedDroolsReasonerService.SERVICE_REMOVE_RULE)) {
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"REMOVING A RULE..."},null);
				return new ServiceResponse(CallStatus.succeeded);	
			} else if (operation.startsWith(ProvidedDroolsReasonerService.SERVICE_MODIFY_RULE)){
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"MODIFYING RULE..."},null);
			}else if (operation.startsWith(ProvidedDroolsReasonerService.SERVICE_ADD_FACT)){
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"ADDING FACT..."},null);
			}else if (operation.startsWith(ProvidedDroolsReasonerService.SERVICE_MODIFY_FACT)){
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"MODIFYING FACT..."},null);
			}else if (operation.startsWith(ProvidedDroolsReasonerService.SERVICE_REMOVE_FACT)){
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"REMOVING FACT..."},null);
			}else if (operation.startsWith(ProvidedDroolsReasonerService.SERVICE_SWITCH_ON)){
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"SWITCHING ON..."},null);
			}else if (operation.startsWith(ProvidedDroolsReasonerService.SERVICE_SWITCH_OFF)){
				LogUtils.logTrace(mctx , getClass(), "ServiceProvided", new String[]{"SWITCHING OFF..."},null);
			}
		}
		return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}

}
