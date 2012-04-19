package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.MultiKeyMap;
import org.universAAL.AALApplication.health.motivation.motivationalmessages.MotivationalMessagesContent;
import org.universaal.ontology.owl.MotivationalMessage;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MessageManager {

	public File enMessagesDB;
	public File spMessagesDB; 
	
	public final Locale SPANISH = new Locale ("es", "ES");
	private final int EN = 1;
	private final int ES = 0;
	
	public String treatment_type;
	public String message_context;
	public String depth;
	public String message_type;
	public String motivational_status;
	public String motivationalMessageContent;

	static MultiKeyMap map = new MultiKeyMap(); // the map structure

	/**
	 * Class constructor. In this method, we will open and read the motivational
	 * messages stored in an specific data base, that will be chosen depending on the language.
	 * @param language
	 */

	public MessageManager(Locale language) {

		try {

			if (language.equals(Locale.ENGLISH)) {
				MotivationalMessagesDatabase.getDBRute(EN);
//				setEngMotMessageDB();
//				FileReader reader = new FileReader(enMessagesDB);
//				buildMapStructure(reader); //método que me devuelva esto mientras tanto
				//EN ESTE PUNTO HABRÍA QUE IR AL PROPERTIES A CONSULTAR LA DIRECCIÓN
				// DE LA BASE DE DATOS Y ELEGIR LA SUBCARPETA DEL INGLÉS
			}

			else if (language.equals (SPANISH)) {
				MotivationalMessagesDatabase.getDBRute(ES);
//				setSpMotMessageDB();
//				FileReader reader = new FileReader(spMessagesDB);
//				buildMapStructure(reader);
				//EN ESTE PUNTO HABRÍA QUE IR AL PROPERTIES A CONSULTAR LA DIRECCIÓN
				// DE LA BASE DE DATOS Y ELEGIR LA SUBCARPETA DEL ESPAÑOL PARA CREAR EL FILEREADER
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This method builds the map structure from the motivational message
	 * storage (csv file).
	 * @param reader
	 */

	public void buildMapStructure(FileReader reader) {

		try {

			BufferedReader buffer = new BufferedReader(reader);
			String linea;

			String cols[];

			while ((linea = buffer.readLine()) != null) {

				cols = linea.split(";"); // the data stored in a line is
				// splitted up into 6 columns
				treatment_type = cols[0];
				motivational_status = cols[1];
				message_context = cols[2];
				depth = cols[3];
				message_type = cols[4];
				motivationalMessageContent = cols[5];

				if (!map.containsKey(treatment_type, motivational_status,
						message_context, depth, message_type)) {
					// if the combination of keys has not been registered yet
					ArrayList<String> mMessagesAssociated = new ArrayList<String>(); // a
					mMessagesAssociated.add(motivationalMessageContent);
					map.put(treatment_type, motivational_status,
							message_context, depth, message_type,
							mMessagesAssociated);
				} else { // if the combination of keys already exists
					ArrayList<String> mMessagesAssociated = (ArrayList<String>) (map
							.get(treatment_type, motivational_status,
									message_context, depth, message_type));
					mMessagesAssociated.add(motivationalMessageContent);
					map.put(treatment_type, motivational_status,
							message_context, depth, message_type,
							mMessagesAssociated);
				}

			} // At this point, the map structure is build

			reader.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * The following method finds all the message contents related to a set of keys. 
	 * @param motivational message
	 * @return messages's content
	 */
	
	public static ArrayList<String> getMessageResults(MotivationalMessage mm) {
		String typeOfTreatment = mm.getAssociatedTreatment().getClass()
				.getName();
		String motStatus = mm.getMotivationalStatus().toString();
		String mContext = (mm.getContext()).toString();
		String depth = ((Integer) mm.getDepth()).toString();
		String typeOfMessage = mm.getTypeOfMessage();

		ArrayList<String> mresults = (ArrayList<String>) map.get(
				typeOfTreatment, motStatus, mContext, depth, typeOfMessage);
		return mresults;
	}

	/**
	 * This method returns the content of a motivational message from the file
	 * in which it is stored, by checking the keys given
	 * @param keys
	 * @return motivational message content.
	 */

	public Object getMotivationalMessageContent(MotivationalMessage mm) {

		ArrayList<String> mMessageResults = getMessageResults(mm);

		if (mMessageResults.size() > 1) { // there are several messages for the
			// same combination of keys
			Random rndm = new Random();
			int number = rndm.nextInt(mMessageResults.size()); // we get one of
			// those
			// messages
			// randomly
			try{
			Class <?> cName = Class.forName(mMessageResults.get(number));
			Object content =((MotivationalMessagesContent)(cName.newInstance())).getContent();
			return content;
			}catch (Exception e){return null;}
			
		} else{
//			
			try{
			Class <?> cName = Class.forName(mMessageResults.get(0));
			Object content =((MotivationalMessagesContent)(cName.newInstance())).getContent();
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

	public String[] getVariables(String motivationalMessageContent) {

		StringTokenizer message = new StringTokenizer(
				motivationalMessageContent); // the message is divided into
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

	public String readMessage(MotivationalMessage mm) {

		String messageToBeRead = getMotivationalMessageContent(mm);

		if (messageToBeRead.contains("$")) { // there are variables to be
			// replaced

			String[] variables = getVariables(messageToBeRead);
			String[] values = MessageVariables.replaceVariables(variables);

			for (int i = 0; i < variables.length; i++) {
				messageToBeRead = messageToBeRead.replaceAll("\\$"
						+ variables[i], values[i]);

			}
			return messageToBeRead;
		} else
			return messageToBeRead;
	}

	/**
	 * This method deletes a motivational message.
	 * @param motivational message
	 */

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
	}

	/**
	 * The following method stores a motivational message in the specified csv
	 * file
	 * @param MotivationalMessage (the one that will be stored)
	 * @param the rute that contains the file in which the message will be
	 * stored.
	 */

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

	/**
	 * The following method sends a message to the user. The message is sent by
	 * the platform or the caregiver
	 * @param Motivational Message to be sent
	 */
	
	public static void sendMessageToUser(MotivationalMessage mm) {
		// To do: utilizar el método/servicio que proporcione la plataforma
		// para enviar al buzón de entrada del destinatario.
		// Prestar atención a lo que devuelva (mensaje enviado, mensaje leído,
		// etc).
	}

	/**
	 * The following method sends a message to the caregiver. The message is
	 * sent by the platform or the assisted person
	 * @param Motivational Message to be sent
	 */
	
	public static void sendMessageToCaregiver(MotivationalMessage mm) {
	//TO DO
	}
	
	/**
	 * The following method initializates the rute in which is the
	 * csv file that contains the english motivational messages.
	 */

	public void setEngMotMessageDB() {
		enMessagesDB = new File("G:\\motivationalMessages.csv");
	}

	/**
	 * The following method initializates the rute in which is the
	 * csv file that contains the spanish motivational messages.
	 */

	public void setSpMotMessageDB() {
		spMessagesDB = new File("");// especificar la ruta del fichero con los mensajes
		// en español
	}

	/**
	 * The following method generates the line of the csv file associated
	 * to a motivational message 
	 * @param motivational message
	 * @return the string containning the line associated to the 
	 * motivational message in the csv file.
	 */
	
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

	/**
	 * The following method shows the message content.
	 * @param motivational message
	 */
	
	public static void showMotivationalMessage(MotivationalMessage mm){
		//TO DO: en la integración con la plataforma, dar el mm al servicio que corresponda
		//para que lo muestre.
		
		System.out.println(mm.getContent());
	}
}
