package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

import java.util.ArrayList;

import org.universAAL.ontology.profile.User;
import org.universaal.ontology.owl.MotivationalMessage;

public class MessageServiceTools {
	
 private User sender = getSender();
 private User receiver = getReceiver(); 
 
 public static ArrayList <String> sentMessages = new ArrayList <String>();
	
	public static void sendMessage(String content){
		//con los servicios de la plataforma, enviar mensaje al destinatario
		//con el contenido especificado.
		
		//para probar que funciona el envío de mensajes (eliminar de la funcionalidad final)
		
		insertSentMessage(content);
		
	}
	
	public static void insertSentMessage(String motivationalMessage){
		sentMessages.add(motivationalMessage);
	}
	
	public static ArrayList <String> getSentMessages(){
		return sentMessages;
	}
	
	public User getSender(){
		return null; //inicializar con las herramientas de la plataforma
	}
	
	public User getReceiver(){
		return null; //inicializar con las herramientas de la plataforma
	}
}
