package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.MultiKeyMap;
import org.universAAL.AALApplication.health.motivation.motivationalMessages.MotivationalMessageContent;
import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.middleware.ui.owl.UIBusOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.ontology.space.SpaceOntology;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.disease.owl.DiseaseOntology;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.MeasuredPhysicalActivity;
import org.universaal.ontology.health.owl.MotivationalStatusType;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.owl.MessageOntology;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.Questionnaire;
import org.universaal.ontology.owl.QuestionnaireOntology;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;


public class PruebaCSV {
	
	public static String disease;
	public static String treatment_type;
	public static String mot_status;
	public static String message_type;
	public static String motivational_message_content;	
	
	public static final String prefixForDisease = "http://health.ontology.universaal.org/Disease#";
	public static final String prefixForTreatment = "http://health.ontology.universaal.org/HealthOntology#";
	public static final String prefixForMotStatus = "";
	public static final String prefixForMessageType = "";
	
	static MultiKeyMap map = new MultiKeyMap(); // the map structure
	
	public static void leeFichero(){
		try{
			FileReader freader = new FileReader("C://universAAL/motivationalMessages/PruebaCSV.csv");
			CsvReader reader = new CsvReader("C://universAAL/motivationalMessages/PruebaCSV.csv", ';');
			//String line = reader.getRawRecord();
			if (reader.readHeaders()) {
				String[] headers = reader.getHeaders(); 
				System.out.println("Header: " + headers[0]);
				System.out.println("Header: " + headers[1]);
				System.out.println("Tamaño de Headers: " + headers.length);
			}
			while(reader.readRecord()){
				//String valCol1 = reader.getRawRecord();
				String valCol2 = reader.get(1);
				//String valCol3 = reader.get(2);
				//System.out.println(valCol1 );
				String[] cols = reader.getValues(); 
				
				
				System.out.println("Valor: " + cols[0]);
				System.out.println("Valor: " + cols[1]);
				System.out.println("Valor: " + cols[2]);
				System.out.println("Número de cols: " + cols.length);
			
				System.out.println("Otra vuelta");
			}
			reader.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	
	public static void escribeFichero(){
		
		try{
			CsvWriter writer = new CsvWriter("C://universAAL/motivationalMessages/PruebaCSV.csv");
			writer.write("marina");
			writer.write("de la");
			writer.write("fuente");
			writer.endRecord();
			writer.write("marina");
			writer.write("de la");
			writer.write("fuente");
			writer.endRecord();
			writer.close();
		}catch (Exception e){
			 System.out.println(e);
		}
		
	}
	
	public static void pruebaMManager(FileReader freader){

		try {
			CsvReader reader = new CsvReader(freader, ';');
			
			if (reader.readHeaders()) {
				String[] headers = reader.getHeaders(); //eliminamos los headers
			}
			
			while(reader.readRecord()){
				String[] columns = reader.getValues(); 
				
				disease = columns[0];
				treatment_type = columns[1];
				mot_status = columns[2];
				message_type = columns[3];
				motivational_message_content = columns[4];
				
				System.out.println(disease + " " +treatment_type + " " + mot_status + " " + message_type  + " " + motivational_message_content);
				
				if (!map.containsKey(disease,treatment_type, mot_status,
						message_type)) {
						// if the combination of keys has not been registered yet
						System.out.println("Estoy guardando el registro en el mapa");
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
			}
			
			// At this point, the map structure is build
			
			/*System.out.println("tamaño del mapa" + map.size());
			ArrayList<String> resultados = (ArrayList<String>) map.get("HeartFailure", "Treatment", "precontemplation", "inquiry");
			System.out.println("Contenido: " + resultados.get(0));
			*/
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
			
	}
	
public static Object getMotivationalMessageContent(String disease, String treatmentType, MotivationalStatusType motStatus, MotivationalMessageClassification messageType) {
		
		System.out.println("He entrado en el método que lee el contenido");
		System.out.println("tamaño del mapa" + map.size());
	
		String diseaseName = disease.replaceFirst(prefixForDisease, "");
		String tType = treatmentType.replaceFirst(prefixForTreatment, "");
		String mStatus = motStatus.getLocalName();
		String mType = messageType.getLocalName();
		
		//System.out.println(treatmentType);
		//System.out.println(diseaseName + " " + tType + " " + mStatus +  " " + mType);
		ArrayList<String> mMessageResults = (ArrayList<String>) map.get(
				diseaseName, tType, mStatus, mType);
		//ArrayList<String> mMessageResults = (ArrayList<String>) map.get("HeartFailure", "Treatment", "precontemplation", "inquiry");
		System.out.println("Tamaño de los resultados: " + mMessageResults.size());
		if (mMessageResults.size() > 1) { // there are several messages for the
			// same combination of keys
			System.out.println("Hay más de un resultado");
			Random rndm = new Random();
			int number = rndm.nextInt(mMessageResults.size()); // we get one of
			// those messages randomly
		try{
			
				Class <?> cName = Class.forName(mMessageResults.get(number));
				//Object content =( (MotivationalMessageContent) (cName.newInstance()) ).getMessageContent();
				Object content = cName.newInstance();
				
				return content;
			}catch (Exception e){
				System.out.println(mMessageResults.get(number));
				return null;
				}
			
		} else if(mMessageResults.size()==1){
//			
			System.out.println("Sólo hay un resultado");
			System.out.println(mMessageResults.get(0));
			
			try{
				
				Class <?> cName = Class.forName(mMessageResults.get(0));
				Object content =((MotivationalMessageContent)(cName.newInstance())).getMessageContent();
				//Object content = cName.newInstance();
				System.out.println("Tipo de new instance: " + content.getClass());
				//System.out.println("Modifiers: " + cName.getModifiers() + "son estos.");
				return content;
			}catch (Exception e){
				System.out.println(e);
				return null;
			}
			
		}else{
			return null;
		}
	}

	
	public static void main(String[]args){
		
		OntologyManagement.getInstance().register(new DataRepOntology());
		OntologyManagement.getInstance().register(new ServiceBusOntology());
		OntologyManagement.getInstance().register(new UIBusOntology());
		OntologyManagement.getInstance().register(new LocationOntology());
		OntologyManagement.getInstance().register(new ShapeOntology());
		OntologyManagement.getInstance().register(new PhThingOntology());
		OntologyManagement.getInstance().register(new SpaceOntology());
		OntologyManagement.getInstance().register(new ProfileOntology());//hay otra
		OntologyManagement.getInstance().register(new QuestionnaireOntology());//hay otra
		OntologyManagement.getInstance().register(new DiseaseOntology());
		OntologyManagement.getInstance().register(new HealthMeasurementOntology());
		OntologyManagement.getInstance().register(new HealthOntology());
		OntologyManagement.getInstance().register(new MessageOntology());

		
		System.out.println("Estoy ejecutando el main");
		
		//PruebaCSV.leeFichero();
		//PruebaCSV.escribeFichero();
		
		
		try{
			FileReader freader = new FileReader("C://universAAL/motivationalMessages/en_motivationalMessagesDB.csv");
			PruebaCSV.pruebaMManager(freader);
			Object content = PruebaCSV.getMotivationalMessageContent(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry);
			 
			if(content instanceof Questionnaire){
				System.out.println("Soy de tipo cuestionario");
				Questionnaire questionnaire = (Questionnaire)content;
				System.out.println(questionnaire.questionnaireToString());	
			}
			
			else
			System.out.println("NO soy de tipo cuestionario, soy de tipo: " );
			//System.out.println(content.getClass());
		}catch(Exception e){
			
		}
		
	}
	
}
