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
	
 * Service Callee del ServicioSMS. Clase que contiene el método llamado cuando se invoca un servicio desde el bus 
 * de servicio
 * 
 * @author mllorente
 * @author Angel Martinez-Cavero
 * 
 */

// Paquete que contendra la clase
package org.universAAL.AALapplication.personal_safety.sms;

// Importamos los paquetes necesarios
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

// Clase principal
public class SMSCallee extends ServiceCallee {
    private final static Logger log = LoggerFactory.getLogger(SMSCallee.class);

    // Atributos
    private messageSender send = null;

    // Constructor
    protected SMSCallee(BundleContext context, ServiceProfile[] realizedServices) {
	super(context, realizedServices);
    }

    // Metodos
    /** Obligatorio de implementar. Sin interés (de momento) */
    public void communicationChannelBroken() {
	// TODO Auto-generated method stub
    }

    /** Este metodo se encarga de manejar la respuesta */
    public ServiceResponse handleCall(ServiceCall call) {
	ServiceResponse response;
	// Si la llamada es null
	if (call == null) {
	    log.error("Null call!");
	    response = new ServiceResponse(CallStatus.serviceSpecificFailure);
	    response.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Null Call!"));
	    return response;
	}
	// Si la llamada apunta a una URI nula
	String operation = call.getProcessURI();
	if (operation == null) {
	    log.error("Null operation!");
	    response = new ServiceResponse(CallStatus.serviceSpecificFailure);
	    response.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
		    "Null Operation!"));
	    return response;
	}
	// Si es la URI correspondiente a mi servicio
	if (operation.startsWith(ServicioSMS.SERVICE_MANDA_SMS)) {
	    log.info("Service call received for sending a SMS");
	    try {
		// Llamo a un metodo externo que se encargara de realizar el
		// envio SMS
		return mandaSMS(
			(String) call
				.getInputValue(ServicioSMS.INPUT_TEXTO_MENSAJE),
			(String) call
				.getInputValue(ServicioSMS.INPUT_TEXTO_TELEFONO));
	    } catch (Exception e) {
		log.error("Exception trying to send message: {}", e.toString());
		e.printStackTrace();
	    }
	    // La URI es buena pero no coincide
	} else {
	    log.info("Unrecognized service call received");
	    response = new ServiceResponse(CallStatus.serviceSpecificFailure);
	    response.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
		    "Invlaid Operation!"));
	    return response;
	}
	return null;
    }

    /**
     * Este método manda un SMS al número especificado (necesita recibir el
     * texto a enviar y el numero destino)
     */
    private ServiceResponse mandaSMS(String inputTextoMensaje,
	    String inputTextoTelefono) {
	log.info("Trying to send the message");
	send = new messageSender();
	if (send.sendMessage(inputTextoTelefono, inputTextoMensaje)) {
	    log.info("Message sent successfully");
	} else {
	    log.info("Message could not be sent");
	    ServiceResponse sr = new ServiceResponse(
		    CallStatus.serviceSpecificFailure);
	    return sr;
	}
	ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	return sr;
    }

}
