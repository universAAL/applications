/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 Universidad PolitÃ©cnica de Madrid
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

public class MotivationalMessagesDatabase {

	//Aqu� hay que pedirle al sistema, a partir de las properties
	//que est�n en configuraci�n, que me de la ruta de la carpeta de los idiomas


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
