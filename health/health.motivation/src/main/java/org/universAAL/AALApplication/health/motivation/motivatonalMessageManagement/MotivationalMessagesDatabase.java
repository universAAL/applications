package org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement;

import java.io.File;

public class MotivationalMessagesDatabase {

	//Aquí hay que pedirle al sistema, a partir de las properties
	//que están en configuración, que me de la ruta de la carpeta de los idiomas


	public static final int ES = 0;
	public static final int EN = 1;


	public static File getDBRute(int language){
		File file;
		switch(language){
		case ES:
			return file = new File("G:\\motivationalMessages.csv");
		case EN:
			return file = new File("G:\\englishDBRute.csv");
		default:
			return null;

		}
	}
}
