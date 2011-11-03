package mainclasses;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;




import java.util.Hashtable;

public class SCalleeProvidedService extends ServiceReasoner {

	public SCalleeProvidedService(String uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public static ServiceProfile[] profiles;
	
	private static Hashtable serverRestrictions = new Hashtable();

	public static final String SERVER_NAMESPACE = "http://ontology.tsbtecnologias.es/Reasoner.owl#";

	public static final String MY_URI = SERVER_NAMESPACE + "ReasonerService";

	public static final String SERVICE_GET_ADD_RULE_VALUE = SERVER_NAMESPACE
			+ "getAddRuleValue";

	public static final String OUTPUT_ADD_RULE_VALUE = SERVER_NAMESPACE + "addRuleValue";
	
	

	static {

		// I make an instance of the service
		// that is, I extended the service

		// I'm going to create a serviceprovided that will give me the reasoner

		// I register it.

		register(SCalleeProvidedService.class);

		// difference with service ontology: now we can't just put device but a
		// concrete one

		// that is TempSensor.MY_URI instead of Device.MY_URI

		// serverrestriction: this is just a hashtable it doesn't matter the
		// name.
		// it doesn’t matter the name as long as it’s a hashtable.

		addRestriction(Restriction.getAllValuesRestriction(PROPERTY_CONTROLS,
				ServiceReasoner.MY_URI), new String[] { PROPERTY_CONTROLS },
				serverRestrictions);

		// PropertyPath(String uri, boolean isXMLLiteral, String[] thePath)

		// SERVICE_GET_VALUE=SERVER_NAMESPACE + "getValue"
		SCalleeProvidedService getAddRuleValue = new SCalleeProvidedService(
				SERVICE_GET_ADD_RULE_VALUE);
		// We initialize the profile.
		profiles[0] = getAddRuleValue.getProfile();
		
		

		ProcessOutput output = new ProcessOutput(OUTPUT_ADD_RULE_VALUE);

		// How it works output.setCardinality(,) ?
		//				  
		// We are defining the output data.
		// setCardinality(max,min)
		//				  
		// Min: minimum number of numbers
		// Max: maximum number of numbers.
		//				  
		// Example:
		// (1,0) means that it's optional.
		// (100,1) at least one value to one hundred

		output.setCardinality(1, 1); // output config only 1 value.sometimes
										// we'll put 1-100;
		
		

		profiles[0].addOutput(output);
		// I put the output and the path to the endpoint
		// the path to get the output
		profiles[0].addSimpleOutputBinding(output,
				new String[] { ServiceReasoner.PROPERTY_CONTROLS });

		// Why do we use the addOutputbinding?
		// We are relating the output with the path. So we are saying that you
		// can find the output value in this path.
	
	
	}
	
	
}
