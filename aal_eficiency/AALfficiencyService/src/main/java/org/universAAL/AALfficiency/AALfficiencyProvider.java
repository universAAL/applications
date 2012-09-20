package org.universAAL.AALfficiency;


/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_6#Providing_services_on_the_bus */
import org.universAAL.AALfficiency.model.AALfficiencyEngine;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

public class AALfficiencyProvider extends ServiceCallee {
	

	private AALfficiencyEngine engine = new AALfficiencyEngine();
	
	// this is just to prepare a standard error message for later use
    private static final ServiceResponse invalidInput = new ServiceResponse(
	    CallStatus.serviceSpecificFailure);
    static {
	invalidInput.addOutput(new ProcessOutput(
		ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
    }

    protected AALfficiencyProvider(ModuleContext context, ServiceProfile[] realizedServices) {
	super(context, realizedServices);
	// TODO Auto-generated constructor stub
	
    }

    protected AALfficiencyProvider(ModuleContext context) {
	super(context, ProvidedAALfficiencyService.profiles);
	// TODO Auto-generated constructor stub

    }

    public void communicationChannelBroken() {
	// TODO Auto-generated method stub

    }

    public ServiceResponse handleCall(ServiceCall call) {
    	if (call == null) {
			return null;
		} else {
			String operation = call.getProcessURI();
			
			if (operation == null) {
				return null;
			} else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_ADVICES)) {
				return engine.getAdvices();
			}
			else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_ACTIVITY_DATA)) {
				return engine.getActivityData();
			}
			else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_ELECTRICITY_DATA)) {
				return engine.getElectricityData();
			}
			else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_ADVICE_INFO)) {
				Object input = call
						.getInputValue(ProvidedAALfficiencyService.INPUT_ADVICE_URI);
				return engine.getAdviceInfo(input.toString());
			}
			
			else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_ELECTRICITY_CHALLENGE)) {
				return engine.getChallengeInfo("Electricity");
			}
			else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_ACTIVITY_CHALLENGE)) {
				return engine.getChallengeInfo("Activity");
			}
			else if (operation
					.startsWith(ProvidedAALfficiencyService.SERVICE_GET_CHALLENGE)) {
				Object input = call
						.getInputValue(ProvidedAALfficiencyService.INPUT_CHALLENGE);
				if (input.toString().contains("Activity"))
					return engine.getChallengeInfo("Activity");
				else
					return engine.getChallengeInfo("Electricity");
			}
			}
	return null;
    }

}
