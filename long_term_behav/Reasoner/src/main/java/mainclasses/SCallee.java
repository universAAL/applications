package mainclasses;

import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.io.owl.PrivacyLevel;
import org.universAAL.middleware.io.rdf.Form;
import org.universAAL.middleware.output.OutputEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.drools.Rule;
import org.universAAL.ontology.profile.ElderlyUser;
import org.universAAL.ontology.profile.User;



public class SCallee extends ServiceCallee{

	private static final Object value = null;
	private static final ServiceResponse failure = new ServiceResponse(
			CallStatus.serviceSpecificFailure);
	private final static Logger log = LoggerFactory.getLogger(SCallee.class);



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
		
			if (operation.startsWith(SCalleeProvidedService.SERVICE_START_UI)) {
			
			System.out.println("startin ui....");
			
			Object inputUser = call.getProperty(ServiceRequest.PROP_uAAL_INVOLVED_HUMAN_USER);
			
			
				if ((inputUser == null) || !(inputUser instanceof User)) {
					failure.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
					"Invalid User Input!"));
					log.debug("No user");
					return failure;
				} else {
					inputUser = (User) inputUser;
				}
			
				log.debug("Show dialog from call");
			 
			
				Activator.oprovider.startMainDialog();
			
				ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
				return sr;
			
			}	else {
				response = new ServiceResponse(CallStatus.serviceSpecificFailure);
				response.addOutput(new ProcessOutput(
					ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
					"Invalid Operation!"));
				return response;
			}
		}
		
			
	}
	
	public void showTestDialog(User user) {
		log.debug("Show dialog from ltba");
		Form f = Form.newDialog("LTBA", new Resource());
		
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
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
