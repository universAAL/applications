package mainclasses;

public class Configuration {

	static boolean state = true; 
	
	
	public static boolean getState(){
		
		
		return state;
		
	}
	
	public static void setState(boolean value){
		
		state = value;		
		
	}
	
	

	
	public Configuration() {
		super();
	}
}
