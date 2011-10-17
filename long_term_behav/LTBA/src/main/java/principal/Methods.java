package principal;

import java.util.Hashtable;

import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;




public class Methods extends Service{
	
	
	public static final String LTBA_SERVER_NAMESPACE = "http://ontology.igd.fhg.de/LTBA.owl#";
	
	public static final String MY_URI = LTBA_SERVER_NAMESPACE + "LongTermBehavioralAnalyser";
	
	static final String SERVICE_ADD_RULE = LTBA_SERVER_NAMESPACE + "addRule";
	static final String SERVICE_REMOVE_RULE = LTBA_SERVER_NAMESPACE + "removeRule";
	static final String SERVICE_ADD_FACT = LTBA_SERVER_NAMESPACE + "addFact";
	static final String SERVICE_CHANGE_FACT = LTBA_SERVER_NAMESPACE + "changeFact";
	static final String SERVICE_REMOVE_FACT = LTBA_SERVER_NAMESPACE + "RemoveFact";

	
	
	
	static {
		
		// we need to register all classes in the ontology for the serialization of the object
				register(Methods.class);
		
	
		//Add restrictions
		
				
				
				// Create the service-object for add a rule
				Methods addRule = new Methods(SERVICE_ADD_RULE);
				
				
				// We need an input parameter identical with the previous one
			//	addRule.addFilteringInput();
			
		//Property path
		
		//Set cardinality
		
//		Add output
//				  

		
	}
	
	private Methods(String uri) {
		super(uri);
	}

	@Override
	protected Hashtable getClassLevelRestrictions() {
		// TODO Auto-generated method stub
		return null;
	}
}
