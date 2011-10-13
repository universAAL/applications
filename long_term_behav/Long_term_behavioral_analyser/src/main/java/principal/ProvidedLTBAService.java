package principal;

import java.util.Hashtable;

import org.universAAL.middleware.service.owl.Service;


public class ProvidedLTBAService extends Service{
	
	
	public static final String SERVER_NAMESPACE = "http://ontology.igd.fhg.de/LTBA.owl#";
	
	public static final String MY_URI = SERVER_NAMESPACE + "LongTermBehavioralAnalyser";
	
	public static final String SERVICE_GET_VALUE = SERVER_NAMESPACE + "getValue";
	
	public static final String OUTPUT_VALUE = SERVER_NAMESPACE + "value";
	
//	public static final ServiceProfile[] profiles = new ServiceProfile[1];
	
//	private static Hashtable serverRestrictions = new Hashtable();
	
	static {
		
		
		//I make an instance of the service
		//that is, I extended the service
		
		//I'm going to create a serviceprovided that will give me the home temperature
		
		//I register it.
		
		register(ProvidedLTBAService.class);
	
		//Add restrictions
		
		//Property path
		
		//Set cardinality
		
//		Add output
//				  

		
	}
	
	private ProvidedLTBAService(String uri) {
		super(uri);
	}

	@Override
	protected Hashtable getClassLevelRestrictions() {
		// TODO Auto-generated method stub
		return null;
	}
}
