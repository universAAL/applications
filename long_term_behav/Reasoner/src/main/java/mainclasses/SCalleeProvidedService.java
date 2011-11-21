package mainclasses;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.ontology.drools.Rule;
import org.universAAL.ontology.drools.service.DroolsService;
import org.universAAL.samples.service.utils.SimpleAdd;
import org.universAAL.samples.service.utils.SimpleProfile;
import java.util.Hashtable;

public class SCalleeProvidedService extends DroolsService {

	public SCalleeProvidedService(String uri) {
		super(uri);
	}

	public static ServiceProfile[] profiles=new ServiceProfile[1];
	
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

	

		// Before adding our own restrictions, we first "inherit" the restrictions defined by the superclass
		addRestriction((Restriction)
				DroolsService.getClassRestrictionsOnProperty(DroolsService.RULE).copy(),
				new String[]{DroolsService.RULE},
				serverRestrictions);
		
		//copia las restriciones del servicio a la lista serverrestrictions

//		SimpleRequest set = new SimpleRequest(new DroolsService());
//		
//		
//		
//		
//		set.putArgument(new String[] { DroolsService.RULE }, new SimpleAdd(my_rule));
//		
//		
//		SCalleeProvidedService getAddRuleValue = new SCalleeProvidedService(
//				SERVICE_GET_ADD_RULE_VALUE);
//		
//		// We initialize the profile.
//		profiles[0] = getAddRuleValue.getProfile();
//		
//          
//		ProcessOutput output = new ProcessOutput(OUTPUT_ADD_RULE_VALUE);
//		
//		output.setCardinality(1, 1); // output config only 1 value.sometimes
//										// we'll put 1-100;
//		
//		
//
//		profiles[0].addOutput(output);
//		// I put the output and the path to the endpoint
//		// the path to get the output
//		profiles[0].addSimpleOutputBinding(output,
//				new String[] { DroolsService.RULE});
//
//		// Why do we use the addOutputbinding?
//		// We are relating the output with the path. So we are saying that you
//		// can find the output value in this path.
		
		SimpleProfile prof=new SimpleProfile(new SCalleeProvidedService(SERVICE_GET_ADD_RULE_VALUE));
		prof.putArgument(new String[] { DroolsService.RULE }, new SimpleAdd(new Rule()), OUTPUT_ADD_RULE_VALUE);//Aqui lo que importa de new Rule() es el tipo, da igual la URI
		profiles[0]=prof.getTheProfile();
		
		System.out.println("Profile created");
		
	
	}



	private static void addInputWithAddEffect(String myUri, String myUri2,
			int i, int j, String rule) {
		// TODO Auto-generated method stub
		
	}
	
	
}
