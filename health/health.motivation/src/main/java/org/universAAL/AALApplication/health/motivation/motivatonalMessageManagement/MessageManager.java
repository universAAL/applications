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
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivationalMessages.MotivationalMessageContent;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.Caregiver;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.Question;
import org.universaal.ontology.owl.Questionnaire;

import com.csvreader.CsvReader;

public class MessageManager {

	public File enMessagesDB;
	public File spMessagesDB; 
	
	public static final Locale SPANISH = new Locale ("es", "ES");
	private final int EN = 1;
	private final int ES = 0;
	
	public String disease;
	public String treatment_type;
	public String mot_status;
	public String message_type;
	public String motivational_message_content;
	
	public static SendMotivationMessageIface  mmSender;
		
	public static final String prefixForDisease = "http://health.ontology.universaal.org/Disease#";
	public static final String prefixForTreatment = "http://health.ontology.universaal.org/HealthOntology#";
	
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
			
			if (reader.readHeaders()) {
				String[] headers = reader.getHeaders(); //eliminamos los headers del almacenamiento
			}
			
			while(reader.readRecord()){
				String[] columns = reader.getValues(); // nos devuelve el valor de las columnas, de cada fila
				
				disease = columns[0];
				treatment_type = columns[1];
				mot_status = columns[2];
				message_type = columns[3];
				motivational_message_content = columns[4];
				
				if (!map.containsKey(disease,treatment_type, mot_status,
						message_type)) {
						// if the combination of keys has not been registered yet
						System.out.println("Estoy guardando el registro en el mapa");
						ArrayList<String> mMessagesAssociated = new ArrayList<String>(); //cada array guarda todos los mensajes que se encuentran para una misma combinación de keys.
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
			}
			// At this point, the map structure is build
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * This method returns the content of a motivational message (plain text
	 * or questionnaire) from the file
	 * in which it is stored, by checking the keys given.
	 * @param keys
	 * @return motivational message content (MotivationalPlainMessage or MotivationalQuestionnaire).
	 */

	public static Object getMotivationalMessageContent(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType) {
		
		String diseaseName = disease.replaceFirst(prefixForDisease, "");
		String tType = treatmentType.replaceFirst(prefixForTreatment, "");
		String mStatus = motStatus.getLocalName();
		String mType = messageType.getLocalName();
		
		ArrayList<String> mMessageResults = (ArrayList<String>) map.get(
				diseaseName, tType, mStatus, mType);

		if (mMessageResults.size() > 1) { // there are several messages for the
			// same combination of keys
			Random rndm = new Random();
			int number = rndm.nextInt(mMessageResults.size()); // we get one of
			// those messages randomly
			try{
				
				Class <?> cName = Class.forName(mMessageResults.get(number));
				Object content =( (MotivationalMessageContent) (cName.newInstance()) ).getMessageContent();
				return content;
			}catch (Exception e){
				System.out.println(mMessageResults.get(number));
				return null;
				}
			
		} else if(mMessageResults.size()==1){
			try{
				
				Class <?> cName = Class.forName(mMessageResults.get(0));
				Object content =((MotivationalMessageContent)(cName.newInstance())).getMessageContent();
				System.out.println("Tipo de new instance: " + content.getClass());
				return content;
			}catch (Exception e){
				System.out.println(e);
				return null;
			}
		}else{
			return null;
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

	public static String decodeMessageContent(Object motMessageRawContent) {//no debería ser tipo String?

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
	 * The following method sends a message to the user. The message is sent by
	 * the platform or the caregiver
	 * @param Motivational Message to be sent
	 */
	
	public static MotivationalMessage getMessageToSendToUser(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType) {

		Object unprocessedContent = getMotivationalMessageContent(disease, treatmentType, motStatus, messageType);
		
		String processedMessage =""; 
		String unprocessedQuestion;
		Questionnaire processedQuestionnaire;
		
		MotivationalMessage processedMM;
		
		if(unprocessedContent instanceof String){
			processedMessage = decodeMessageContent(unprocessedContent);
		}
		else if(unprocessedContent instanceof Questionnaire){
			//unprocessedQuestionnaire = ((Questionnaire)(unprocessedContent)).questionnaireToString();
			
			//en el cuestionario sólo tendremos posibles variables en los qwording de las preguntas
			//así que los decodificamos y los volvemos a guardar
			Question[] questions = ((Questionnaire)(unprocessedContent)).getQuestions();
			for (int i = 0; i < questions.length; i++) {
				unprocessedQuestion = questions[i].getQuestionWording();
				processedMessage = decodeMessageContent(unprocessedQuestion);
				questions[i].setQuestionWording(processedMessage);
			}
			((Questionnaire)(unprocessedContent)).setQuestions(questions);
			processedQuestionnaire = ((Questionnaire)(unprocessedContent));
		}
		else{
			//lanzar excepción
		}
		
		
		//mandamos el mensaje motivacional completo al módulo de test o real (en éste se manda al usuario como caja negra).
		/*if(destinationUser instanceof AssistedPerson){
			processedMM = new MotivationalMessage(disease, treatmentType,motStatus, messageType, processedMessage);
			MessageServiceTools.sendMessage(processedMM);	
		}
		else if (destinationUser instanceof Caregiver){
			processedMM = new MotivationalMessage(disease, treatmentType,motStatus, messageType, processedMessage);
			//enviarlo a la interfaz real
		}*/
		return processedMM = new MotivationalMessage(disease, treatmentType,motStatus, messageType, processedMessage);
	}
	
	public static void sendMessageToAssistedPerson(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType, AssistedPerson ap){
		MotivationalMessage mm = getMessageToSendToUser(disease, treatmentType, motStatus, messageType);
		//falta escoger el método sendToAssistedPerson adecuado según la interfaz (real o imaginaria) en la que estemos.
	}
	
	public static void sendMessageToAssistedPerson(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType, Caregiver caregiver){
		MotivationalMessage mm = getMessageToSendToUser(disease, treatmentType, motStatus, messageType);
	}

	
	
	
}
