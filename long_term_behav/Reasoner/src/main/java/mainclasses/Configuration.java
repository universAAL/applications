package mainclasses;

public class Configuration {

	static boolean state = true;
	static int visits =0;
	static boolean visits_state = true;
	
	
	
	public static boolean getState(){
		
		
		return state;
		
	}
	
	public static void setState(boolean value){
		
		state = value;		
		
	}
	
	public static int getVisits(){
		
		
		return visits;
		
	}
	public static int setVisits(int number_of_visits){
		
		visits = number_of_visits;
		
		return visits;
		
	}
	
	public static boolean getVisitsState(){
		
		
		return visits_state;
		
	}
	public static boolean setVisitsState(boolean visits_state_value){
		
		//Enables or disables visits_state.
		
		visits_state = visits_state_value;
		
		return visits_state;
		
	}
	

	
	public Configuration() {
		super();
	}
}
