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
		// TODO Auto-generated method stub

	}

	@Override
	public ServiceResponse handleCall(ServiceCall call) {

		LogUtils.logDebug(mctx, getClass(), "ServiceProvided",
				new String[] { "Handling service call..." }, null);
		
		System.out.println("HANDLING SERVICE CALL:" +
				"\n" +
				call.getProcessURI());
		
		if (call == null) {
			return null;
		} else {
			String operation = call.getProcessURI();
			if (operation == null) {
				return null;
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SWITCH_ON)) {
				System.out.println("SWITCHING ON IN HANDLERe");
				LogUtils.logTrace(mctx, getClass(), "ServiceProvided",
						new String[] { "SWITCHING ON..." }, null);
				listener.setStatus(true);
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SWITCH_OFF)) {
				System.out.println("SWITCHING OFF IN HANDLER");
				LogUtils.logTrace(mctx, getClass(), "ServiceProvided",
						new String[] { "SWITCHING OFF..." }, null);
				listener.setStatus(false);
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_PRINT_REPORT)) {
				System.out.println("PRINTING REPORT!!");
				// Assuming it's created in the Activator				
				/**Gonna print a week report for testing*/
				ConsequenceListener.getInstance().printDayReport();
				return new ServiceResponse(CallStatus.succeeded);
			} else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SHOW_WEEK)) {
				System.out.println("PRINTING WEEK!!");
				// Assuming it's created in the Activator				
				/**Gonna print a week report for testing*/
				ConsequenceListener.getInstance().printWeekReport();
				return new ServiceResponse(CallStatus.succeeded);
			}else if (operation
					.startsWith(ProvidedLTBAService.SERVICE_SHOW_MONTH)) {
				System.out.println("PRINTING MONTH!!");
				// Assuming it's created in the Activator				
				/**Gonna print a week report for testing*/
				ConsequenceListener.getInstance().printMonthReport();
				return new ServiceResponse(CallStatus.succeeded);
			}
		}
		return new ServiceResponse(CallStatus.serviceSpecificFailure);

	}

}
