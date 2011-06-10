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
	
 * Activator correspondiente al servicio de envio de SMS. Se instancia el callee y se le pasa el perfil
 * necesario
 * 
 * @author mllorente
 * @author Angel Martinez-Cavero
 *
 */

// Paquete que contendra la clase
package org.universAAL.AALapplication.personal_safety.sms;

// Importamos los paquetes necesarios
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

// Clase principal
public class Activator implements BundleActivator {

    // Atributos
    /** Control del contexto */
    private BundleContext contexto = null;

    /** Instancia del callee */
    private SMSCallee smsCall = null;

    // Constructor

    // Metodos
    /** Arranca el bundle */
    public void start(BundleContext arg0) throws Exception {
	contexto = arg0;
	smsCall = new SMSCallee(contexto, ServicioSMS.profiles);
    }

    /** Para el bundle */
    public void stop(BundleContext arg0) throws Exception {
	contexto = null;
	smsCall.close();
	smsCall = null;
    }

}
