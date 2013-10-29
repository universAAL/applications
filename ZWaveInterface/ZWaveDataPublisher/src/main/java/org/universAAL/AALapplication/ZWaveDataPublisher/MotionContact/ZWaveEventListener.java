/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnolog�as para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALapplication.ZWaveDataPublisher.MotionContact;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ZWaveEventListener {

	private MotionContactSensorPublisher mspublisher = null;
	
	
	public ZWaveEventListener(MotionContactSensorPublisher p) {
		mspublisher = p;
	}
	
	public void init(){
		String clientSentence;
	    String capitalizedSentence;
	    System.out.print("TCP SERVER");
	   
	   try{
	    	ServerSocket welcomeSocket = new ServerSocket(53007);
	        while(true)
	        {
	            Socket connectionSocket = welcomeSocket.accept();
	            BufferedReader inFromClient =
	               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	            clientSentence = inFromClient.readLine();
	            System.out.println("Received: " + clientSentence);
	            capitalizedSentence = clientSentence.toUpperCase() + '\n';
	            outToClient.writeBytes(capitalizedSentence);
	            mspublisher.publishMotionDetection(clientSentence);
	         }
	    }
	    catch(Exception e){
	         System.err.print(e.getMessage());
	    }
	}
	
}
