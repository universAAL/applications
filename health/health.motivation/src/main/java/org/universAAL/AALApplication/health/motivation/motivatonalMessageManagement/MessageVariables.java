package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

//llamar a un método que obtenga del sistema estas variables
public class MessageVariables {
	
public static String[] replaceVariables(String[] variable){
		
	String[] values = new String[variable.length];
	
	for (int i=0;i<variable.length;i++){
			
		if (variable[i].equals("caregiverRol"))
			values[i] = "Doctor";
			
		else if(variable[i].equals("treatmentName"))
			values[i] = "Nombredeltratamiento";
			
		else if(variable[i].equals("username"))
			values[i] = "Pepe";
			
		else if(variable[i].equals("treatmentDescription"))
			values[i] = "Definición del tratamiento";
			
		else if(variable[i].equals("treatmentPuropose"))
			values[i] = "Propósito del tratamiento";
		else if(variable[i].equals("prueba"))
			values[i] = "Marina para saber si esto funciona";	
		else if(variable[i].equals("partOfDay"))
			values[i] = "Morning";	
		else{
			System.out.println("Parámetro no definido");
			values[i] = null;
		}
	}
		return values;
}

}
