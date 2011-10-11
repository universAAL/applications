/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)
	
	Copyright 2008-2010 TSB-Tecnologías para la Salud y el Bienestar,
	http://www.tsbtecnologias.es
	TSB Soluciones Tecnológicas para la Salud y el Bienestar S.A
	
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
	
 *  Clase encargada de enviar un SMS desde el PC
 *  
 *  @author Alvaro Fides
 *  
 *  
 * */

// Paquete que contendra la clase
package org.universAAL.AALapplication.personal_safety.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class holds properties of account and server to send SMS, and lets
 * others send SMSs. WARNING: The properties identified in this class will
 * depend on the specific server used to send SMSs, which might change from
 * country to country.
 * 
 * @author alfiva
 * 
 */
public class messageSender {
    private final static Logger log = LoggerFactory
	    .getLogger(messageSender.class);

    // Atributos
    /*
     * These properties were specific to a Danish SMS provider used in PERSONA
     * pilots. Other provider servers will be used in uAAL and will depend on
     * the country. Be sure to adapt this code to each situation, as each
     * provider will require different parameters to encode the URL.
     */
    String Username = System.getProperty(
	    "org.universAAL.AALapplication.personal_safety.sms.user",
	    "Account User");
    String Password = System.getProperty(
	    "org.universAAL.AALapplication.personal_safety.sms.pass",
	    "Account Password");
    String Account = System.getProperty(
	    "org.universAAL.AALapplication.personal_safety.sms.account",
	    "Account ID").replace(" ", "+");
    // You might probably want to keep this property, no matter which SMS
    // provider you use. However you can change the default value. In any case
    // it should always be set in the VM arguments with the right server.
    String Server = System.getProperty(
	    "org.universAAL.AALapplication.personal_safety.sms.server",
	    "https://findyourownprovider.com/sendsms.php");
    // Do not change this property, this doesn´t depend on SMS provider.
    boolean doSend = Boolean.parseBoolean(System.getProperty(
	    "org.universAAL.AALapplication.personal_safety.sms.send", "false"));

    // Metodos
    public boolean sendMessage(String numero, String texto) {
	try {
	    // Construct data
	    /*
	     * This is where the URL for the request is built, so this must also
	     * be changed depending on the SMS provider server. Remember to make
	     * it match with the properties above, and the HTTP API of the SMS
	     * provider.
	     */
	    String data = URLEncoder.encode("username", "UTF-8") + "="
		    + URLEncoder.encode(Username, "UTF-8");
	    data += "&" + URLEncoder.encode("password", "UTF-8") + "="
		    + URLEncoder.encode(Password, "UTF-8");
	    data += "&" + URLEncoder.encode("account", "UTF-8") + "="
		    + URLEncoder.encode(Account, "UTF-8");
	    data += "&" + URLEncoder.encode("recipient", "UTF-8") + "="
		    + URLEncoder.encode(numero, "UTF-8");
	    data += "&" + URLEncoder.encode("body", "UTF-8") + "="
		    + URLEncoder.encode(texto, "UTF-8");

	    log.info("Message built, Trying to send the message " + texto
		    + " to the recipient " + numero);
	    if (doSend) {
		// Send data
		URL url = new URL(Server);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn
			.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(
			conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
		    System.out.println(line);
		}
		wr.close();
		rd.close();
		return true;
	    } else {
		log
			.info("Message was not really sent because SMSSender is in debug mode. "
				+ "Set argument org.universAAL.AALapplication.personal_safety.sms.send to 'true' in order to allow sending");
	    }
	} catch (Exception e) {
	    log.error("Exception sending the message oreceiveng the result");
	    e.printStackTrace();
	}

	return false;
    }
}
