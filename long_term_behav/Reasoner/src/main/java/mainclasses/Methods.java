package mainclasses;

import java.util.Hashtable;

import org.universAAL.ontology.drools.Fact;
import org.universAAL.ontology.drools.Rule;
import org.universAAL.middleware.service.owl.Service;





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
	  

		
	}
	
	static boolean  addRule(Rule rule){
		
		String contains= rule.getBODY();
		System.out.println("The following rule will be added: "+contains);
		
		try{
			if(rule.getBODY() != null){
				System.out.println("Rule added");
				
			}
			
			return true;
			}
        catch(Exception e){
		
				System.out.println("Error adding rule");
				return false;
		}
		
	}
	
	static boolean  removeRule(Rule rule){
		
		
		rule.setBODY(null);
        rule.setID(null);
        
        try{
   		
		if(rule.getBODY() == null){
			System.out.println("Rule removed");
		}
			return true;
			
        }
			 catch(Exception e){
					
					System.out.println("Error removing rule");
					return false;
			}
        
	}
		
		static boolean  addFact(Fact fact){
			
		String contains= fact.getID();
			
			System.out.println("The following fact will be added: "+contains);
			
			if(fact.getID() == null){
				System.out.println("Fact added");
				return true;
			}else{
				System.out.println("Error removing tool");
				return false;
			}
	
			
		}	
		
		static void  onOff_ltba(Boolean state){
							
			mainclasses.Configuration.setState(state);
				
				
		}
		
		
		static boolean  getltbaState(){
			
			return mainclasses.Configuration.getState();
		
				
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
