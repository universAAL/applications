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
