package org.universAAL.AALApplication.health.motivation.Management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.MultiKeyMap;
//import org.universAAL.AALapplication.health.ont.messages.MotivationalMessage;

import com.csvreader.CsvWriter;

public class MessageManager {
	
	public final File enMessagesDB = new File("G:\\motivationalMessages.csv");
	public final File spMessagesDB = new File("");//especificar la ruta del fichero con los mensajes en espa�ol
	
	public String treatment_type;
	public String message_context;
	public String depth;
	public String message_type;
	public String motivational_status;
	public String motivationalMessageContent;
	
	MultiKeyMap map = new MultiKeyMap(); // the map structure

	
	/**
	 * Class constructor.
	 * In this method, we will open and read the motivational messages stored on the 
	 * data base, depending on the language. 
	 * @param language
	 */
	
	public MessageManager(String language){
	
		try{
			
			if (language == "english"){
				FileReader reader = new FileReader (enMessagesDB);
				buildMapStructure(reader);
			}
			
			else if(language == "spanish"){
			
				FileReader reader = new FileReader (spMessagesDB);
				buildMapStructure(reader);
			}
		
		}catch(Exception e){
			System.out.println(e);
		}
	
	}

	/**
	 * This method builds the map structure
	 * @param reader
	 */

	public void buildMapStructure(FileReader reader){
		
		try{
			
			BufferedReader buffer = new BufferedReader (reader);
			String linea;
			
			String cols[];
			
			while((linea=buffer.readLine())!= null){
				
				cols = linea.split(";"); // the data stored in a line is splited up into 6 columns 
				
				treatment_type = cols[0];
				message_context = cols[2];
				motivational_status = cols[1];
				depth = cols[3];
				message_type = cols [4];
				motivationalMessageContent = cols [5];

				if (!map.containsKey(treatment_type, motivational_status, message_context, depth, message_type )){ 
					// if the combination of keys has not been registered yet
					ArrayList <String> mMessagesAssociated = new ArrayList <String>(); // a new ArrayList for containing the messages is created
					mMessagesAssociated.add(motivationalMessageContent);
					map.put(treatment_type, motivational_status, message_context, depth, message_type, mMessagesAssociated);
				}
				else{ //if the combination of keys already exists
					ArrayList <String> mMessagesAssociated = (ArrayList <String>)(map.get(treatment_type, motivational_status, message_context, depth, message_type)); 
					mMessagesAssociated.add(motivationalMessageContent);
					map.put(treatment_type, motivational_status, message_context, depth, message_type, mMessagesAssociated);
				}
				
			} // At this point, the map structure is build 
			
			reader.close(); 
			
			}catch(Exception e){
				System.out.println(e);
			}
	}
	
	/**
	 * This method returns a motivational message by checking the keys given
	 * @param keys
	 * @return motivational message.
	 */
	
	public String getMotivationalMessage(String treatment_type, String motivational_status, String message_context, String depth, String message_type){
		
		ArrayList <String> mMessageResults = (ArrayList <String>) map.get(treatment_type, motivational_status, message_context, depth, message_type);
		
		if (mMessageResults.size()>1){ //there are several messages for the same combination of keys
			Random rndm = new Random(); 
			int number = rndm.nextInt(mMessageResults.size()); // we get one of those messages randomly
			return mMessageResults.get(number);
		}
		else 
			return mMessageResults.get(0);
	}
	
	/**
	 * This method gets a message, based on the combination of keys. 
	 * Then, the message obtained is analyzed. If there are variables to be replace, they are replaced 
	 * @param treatment_type
	 * @param motivational_status
	 * @param message_context
	 * @param depth
	 * @param message_type
	 * @return
	 */
	
	//public String readMessage(String treatment_type, String motivational_status, String message_context, String depth, String message_type){
		public String readMessage(String message){
		
		//String messageToBeRead = getMotivationalMessage(treatment_type, motivational_status, message_context, depth, message_type);
		
		String messageToBeRead = message;
		
		if(message.contains("$")){ //there are variables to be replaced
			
			
			String[] variables = getVariables(message);
			String[] values = MessageVariables.replaceVariables(variables);
			
			
			for (int i=0;i<variables.length;i++){
				message = message.replaceAll("\\$" + variables[i], values[i]);
				
			}
			return message;
		}
		else 
		return message;
	}
	
	/**
	 * This method replaces the variables contained in the message for its value
	 * @param
	 * @return
	 */

		
public String[] getVariables (String motivationalMessage){
		
		StringTokenizer message = new StringTokenizer(motivationalMessage); // the message is divided into words
		
		String[] variablesAux = new String[message.countTokens()];
		
		int i=0;
		
		while(message.hasMoreTokens()){

			String word = message.nextToken();
			
			if(word.startsWith("$")){
				
				if( (word.endsWith("!") || word.endsWith("?") || word.endsWith(".")|| word.endsWith(",")) ) // check whether the word contains symbols: '!', '?', '.', ',' (cambiar en base al idioma)
					word = word.substring(1,(word.length()-1));	
				else
					word= word.substring(1);
				
				variablesAux[i] = word;
				i++;
			}
		}
		
		String[] variables = new String[i];
		for(int j=0;variablesAux[j]!=null;j++){
			variables[j] = variablesAux[j];
		}
		return variables;
	}
	

	/**
	 * This method deletes a motivational message, depending on the keys given.
	 * @param treatment_type
	 * @param motivational_status
	 * @param message_context
	 * @param depth
	 * @param message_type
	 */

	public void deleteMotivationalMessage(String treatment_type, String motivational_status, String message_context, String depth, String message_type){
		
		ArrayList <String> mMessagesResults = (ArrayList <String>) map.get(treatment_type, motivational_status, message_context, depth, message_type);
		int answer = 0; //= m�todo que devuelve la respuesta.
		
		if (mMessagesResults.size()>1){ //there are several messages for the same combination of keys
			//For the future: send a message to the user asking which message he/she wants to delete
			
			//hacer un cuestionario con los contenidos del mensaje como posibles respuestas
			//si la respuesta es uno especifico, seleccionar el String y compararlo con el arraylist
			//borramos el elemento que coincida del arrayList y volvemos a insertarlo en el map
			
			//si la respuesta es "all"
			if(answer==0){ //cambiar por la condici�n: la respuesta es all (int=0 reperesenta answer = all)
				map.remove(treatment_type, motivational_status, message_context, depth, message_type);
			}
			else{ //se quiere quitar uno en concreto
				
				mMessagesResults.remove(answer);
				map.put(treatment_type, motivational_status, message_context, depth, message_type, mMessagesResults);
				
			}	
		}
		else  //there is only one message with those keys
			map.remove(treatment_type, motivational_status, message_context, depth, message_type);
	}
	/*
	public void editMotivationalMessage(MotivationalMessage mmessage){
		
	}
	*/
	public void deleteSpecificMotivationalMessage(){
		
	}
	
	
	public void storeMotivationalMessage(String treatment_type, String motivational_status, String message_context, String depth, String message_type, String message_content){
		
		try{
		
			File fichero = new File("D://Trabajo/Servicio health/Motivational messages/motivationalMessages3.csv");   
			FileWriter fwriter = new FileWriter(fichero, true);
			
			CsvWriter writer = new CsvWriter(fwriter, ';');
			
			writer.write(treatment_type);
			writer.write(motivational_status);
			writer.write(message_context);
			writer.write(depth);
			writer.write(message_type);
			writer.write(message_content);
			writer.endRecord();
			writer.close();
			
		}catch(Exception e){
		}
	}



}
