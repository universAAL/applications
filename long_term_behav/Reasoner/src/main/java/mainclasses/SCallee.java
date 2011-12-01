package mainclasses;

import org.osgi.framework.BundleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.drools.Rule;


public class SCallee extends ServiceCallee{

	private static final Object value = null;



	protected SCallee(BundleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		// TODO Auto-generated constructor stub
	}
	
	protected SCallee(BundleContext context) {
		super(context, SCalleeProvidedService.profiles);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public ServiceResponse handleCall(ServiceCall call) {
		ServiceResponse response;
		if (call == null) {
			response = new ServiceResponse(CallStatus.serviceSpecificFailure);
			response.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Null Call!"));
			return response;
		}

		String operation = call.getProcessURI();
		if (operation == null) {
			response = new ServiceResponse(CallStatus.serviceSpecificFailure);
			response.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
					"Null Operation!"));
			return response;
		}

		// if the operation match with the service return a value.
		if (operation.startsWith(SCalleeProvidedService.SERVICE_GET_ADD_RULE_VALUE)) {
			
			System.out.println("CALL RECEIVED....");
			
			return callAddRule();
		} else {
			response = new ServiceResponse(CallStatus.serviceSpecificFailure);
			response.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
					"Invalid Operation!"));
			return response;
		}
	}
	

	
	private ServiceResponse callAddRule() {
		ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
		
		System.out.println("CALL SUCCEED---NOW IM ANSWERING");
		Rule rule_example = new Rule();
		rule_example.setBODY("A+B");
		rule_example.setID("Rule1");
		rule_example.setProperty("http://ontology.universAAL.org/Rulel", value);
		
		
		
		
		mainclasses.Methods.addRule(rule_example);
		
		
	//	sr.addOutput(new ProcessOutput(SCalleeProvidedService.OUTPUT_ADD_RULE_VALUE));
		return sr;
	}

}
