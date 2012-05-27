/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 Universidad Politecnica de Madrid
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.MultiKeyMap;
import org.universAAL.AALApplication.health.motivation.motivationalMessages.MotivationalMessageContent;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.owl.MotivationalMessageClassification;

import com.csvreader.CsvReader;

public class MessageManager {

	public File enMessagesDB;
	public File spMessagesDB; 
	
	private static boolean testInterface = true;
	
	public final Locale SPANISH = new Locale ("es", "ES");
	private final int EN = 1;
	private final int ES = 0;
	
	public String disease;
	public String treatment_type;
	public String mot_status;
	public String message_type;
	public String motivational_message_content;
	
	static MultiKeyMap map = new MultiKeyMap(); // the map structure

	/**
	 * Class constructor. In this method, we will open and read the motivational
	 * messages stored in an specific data base, that will be chosen depending on the language.
	 * The method that builds the map structure will be invoked.
	 * @param language
	 */

	public MessageManager(Locale language) {

		FileReader reader = null;

		try {
			
			if (language.equals(Locale.ENGLISH)) {
				reader = new FileReader(MotivationalMessagesDatabase.getDBRute(EN));
			}
			else if (language.equals(SPANISH)) {
				reader = new FileReader(MotivationalMessagesDatabase.getDBRute(ES));
			}
			
			buildMapStructure(reader);
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This method builds the map structure from the motivational message
	 * storage (csv file).
	 * @param reader
	 */

	public void buildMapStructure(FileReader freader) {

		try {
			
			CsvReader reader = new CsvReader(freader, ';');
			String line = reader.getRawRecord();
			String[] columns = reader.getValues();
			
			disease = columns[0];
			treatment_type = columns[1];
			mot_status = columns[2];
			message_type = columns[3];
			motivational_message_content = columns[4];

			while (line != null) {

				if (!map.containsKey(disease,treatment_type, mot_status,
					message_type)) {
					// if the combination of keys has not been registered yet
					ArrayList<String> mMessagesAssociated = new ArrayList<String>(); // a
					mMessagesAssociated.add(motivational_message_content);
					map.put(disease,treatment_type, mot_status,
							message_type,mMessagesAssociated);
				} else { // if the combination of keys already exists
					ArrayList<String> mMessagesAssociated = (ArrayList<String>) (map
							.get(disease,treatment_type, mot_status,message_type));
					mMessagesAssociated.add(motivational_message_content);
					map.put(disease,treatment_type, mot_status,
							message_type,mMessagesAssociated);
				}

			} // At this point, the map structure is build

			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

	/**
	 * The following method finds all the motivational messages
	 * related to a set of keys. That's the lines in the data base
	 * which keys are equals. The same combination of keys can 
	 * lead to different motivational messages. 
	 * @param motivational message
	 * @return messages's content
	 */
	/*
	public static ArrayList<String> getMotMessageResults( ) {

		ArrayList<String> mresults = (ArrayList<String>) map.get(
				illness, treatmentType, motStatus, messageType);
		return mresults;
	}
*/
	/**
	 * This method returns the content of a motivational message (plain text
	 * or questionnaire) from the file
	 * in which it is stored, by checking the keys given.
	 * @param keys
	 * @return motivational message content (MotivationalPlainMessage or MotivationalQuestionnaire).
	 */

	public static Object getMotivationalMessageContent(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType) {
		
		String tType = (String) treatmentType.toString();
		String mStatus = (String) motStatus.toString();
		String mType = (String) messageType.toString();
		ArrayList<String> mMessageResults = (ArrayList<String>) map.get(
				disease, tType, mStatus, mType);

		if (mMessageResults.size() > 1) { // there are several messages for the
			// same combination of keys
			Random rndm = new Random();
			int number = rndm.nextInt(mMessageResults.size()); // we get one of
			// those messages randomly
			try{
				Class <?> cName = Class.forName(mMessageResults.get(number));
				Object content =( (MotivationalMessageContent) (cName.newInstance()) ).getContent();
				return content;
			}catch (Exception e){return null;}
			
		} else{
//			
			try{
				Class <?> cName = Class.forName(mMessageResults.get(0));
				Object content =((MotivationalMessageContent)(cName.newInstance())).getContent();
				return content;
			}catch (Exception e){
				return null;
			}
			
		}
	}

	/**
	 * This method identifies and replaces gets the variables' real value
	 * @param motivational message content
	 * @return the value of the variables found
	 */

	public static String[] getVariables(String motivationalMessageRawContent) {

		StringTokenizer message = new StringTokenizer(
				motivationalMessageRawContent); // the message is divided into
		// words

		String[] variablesAux = new String[message.countTokens()];
		int i = 0;

		while (message.hasMoreTokens()) {

			String word = message.nextToken();

			if (word.startsWith("$")) {

				if ((word.endsWith("!") || word.endsWith("?")
						|| word.endsWith(".") || word.endsWith(","))) 
					// check whether the word contains the symbols:
					// '!' , '?' , '.' , ',' 
					//(cambiar en base al idioma)
					word = word.substring(1, (word.length() - 1));
				else
					word = word.substring(1);

				variablesAux[i] = word;
				i++;
			}
		}

		String[] variables = new String[i];
		for (int j = 0; variablesAux[j] != null; j++) {
			variables[j] = variablesAux[j];
		}
		return variables;
	}

	/**
	 * This method returns the final message sent to the assisted
	 * person/caregiver. This means, that if
	 * exists variables to be replaced, they are reestablished.
	 * @param motivational message
	 * @return the message content with the variables replaced
	 */

	public static String decodeMessageContent(Object motMessageRawContent) {

		String rawContent = motMessageRawContent.toString();
		//String messageToBeRead = (String) getMotivationalMessageContent().toString();
		String processedContent;
		
		if (rawContent.contains("$")) { // there are variables to be
			// replaced

			String[] variables = getVariables(rawContent);
			String[] values = MessageVariables.replaceVariables(variables);

			for (int i = 0; i < variables.length; i++) {
				rawContent = rawContent.replaceAll("\\$"
						+ variables[i], values[i]);
			}
		} 
		processedContent = rawContent;
		return processedContent;
	}

	/**
	 * This method deletes a motivational message.
	 * @param motivational message
	 */
/*
	public static void deleteMotivationalMessage(MotivationalMessage mm) {

		ArrayList<String> mMessagesResults = getMessageResults(mm);

		if (mMessagesResults.size() > 1) { // there are several messages for the
			// same combination of keys

			File originalFile = new File(
					"D://Trabajo/Servicio health/Motivational_messages/motivationalMessages.csv");
			File tempFile = new File(
					"D://Trabajo/Servicio health/Motivational_messages/motivationalMessagesTemp.csv");
			try {
				FileReader freader = new FileReader(originalFile);
				FileWriter fwriter = new FileWriter(tempFile, true);

				CsvReader reader = new CsvReader(freader, ';');
				CsvWriter writer = new CsvWriter(fwriter, ';');

				String lineToRemove = getLine(mm);
				String currentLine;

				while ((currentLine = reader.getRawRecord()) != null) {
					// trim newline when comparing with lineToRemove
					String trimmedLine = currentLine.trim();
					if (trimmedLine.equals(lineToRemove))
						continue;
					writer.write(currentLine);
				}

				tempFile.renameTo(originalFile);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else{
			// there is only one message with those keys
			String typeOfTreatment = mm.getAssociatedTreatment().getClass().getName();
			String motStatus = mm.getMotivationalStatus().toString();
			String mContext = (mm.getContext()).toString();
			String depth = ((Integer) mm.getDepth()).toString();
			String typeOfMessage = mm.getTypeOfMessage();

			map.remove(typeOfTreatment, motStatus, mContext,depth, typeOfMessage);
		}
	}*/

	/**
	 * The following method stores a motivational message in the specified csv
	 * file
	 * @param MotivationalMessage (the one that will be stored)
	 * @param the rute that contains the file in which the message will be
	 * stored.
	 */
/*
	public static void storeMotivationalMessage(MotivationalMessage mm,
			String file_rute) {
		try {

			File mmessageDB = new File(file_rute);
			FileWriter fwriter = new FileWriter(mmessageDB, true);

			CsvWriter writer = new CsvWriter(fwriter, ';');

			writer.write(mm.getAssociatedTreatment().getClass().getName());
			writer.write(mm.getMotivationalStatus().toString());
			writer.write((mm.getContext()).toString());
			writer.write(((Integer) mm.getDepth()).toString());
			writer.write(mm.getTypeOfMessage());
			writer.write(mm.getContent().toString());
			writer.endRecord();
			writer.close();

		} catch (Exception e) {
		}
	}
*/
	/**
	 * The following method sends a message to the user. The message is sent by
	 * the platform or the caregiver
	 * @param Motivational Message to be sent
	 */
	
	public static void sendMessageToUser(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType) {
		// To do: utilizar el m�todo/servicio que proporcione la plataforma
		// para enviar al buz�n de entrada del destinatario.
		// Prestar atenci�n a lo que devuelva (mensaje enviado, mensaje le�do,
		// etc).
		
		Object unprocessedContent = getMotivationalMessageContent(disease, treatmentType, motStatus, messageType);
		String processedMessage = decodeMessageContent(unprocessedContent);
		if(testInterface){
			MessageServiceTools.sendMessage(processedMessage);	
		}
		
		
		
	}
	
	private void changeToPlatformInterface(){
		testInterface=false;
	}
	
	private void changeToTestInterface(){
		testInterface=true;
	}

	/**
	 * The following method sends a message to the caregiver. The message is
	 * sent by the platform or the assisted person
	 * @param Motivational Message to be sent
	 */
	
	public static void sendMessageToCaregiver(String illness, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType) {
	//TO DO
	}
	
	/**
	 * The following method initializates the rute in which is the
	 * csv file that contains the english motivational messages.
	 */
/*
	public void setEngMotMessageDB() {
		enMessagesDB = new File("G:\\motivationalMessages.csv");
	}
*/
	/**
	 * The following method initializates the rute in which is the
	 * csv file that contains the spanish motivational messages.
	 */
/*
	public void setSpMotMessageDB() {
		spMessagesDB = new File("");// especificar la ruta del fichero con los mensajes
		// en espanol
	}
*/
	/**
	 * The following method generates the line of the csv file associated
	 * to a motivational message 
	 * @param motivational message
	 * @return the string containning the line associated to the 
	 * motivational message in the csv file.
	 */
	/*
	public static String getLine(MotivationalMessage mm) {
		String typeOfTreatment = mm.getAssociatedTreatment().getClass()
				.getName();
		String motStatus = mm.getMotivationalStatus().toString();
		String mContext = (mm.getContext()).toString();
		String depth = ((Integer) mm.getDepth()).toString();
		String typeOfMessage = mm.getTypeOfMessage();
		Object messageContent = mm.getContent();

		String line = typeOfTreatment + ";" + motStatus + ";" + mContext + ";"
				+ depth + ";" + typeOfMessage + ";" + messageContent;
		return line;
	}
*/
	/**
	 * The following method shows the message content.
	 * @param motivational message
	 */
	/*
	
	public static void showMotivationalMessage(MotivationalMessage mm){
		//TO DO: en la integracion con la plataforma, dar el mm al servicio que corresponda
		//para que lo muestre.
		
		System.out.println(mm.getContent());
	}*/
}
