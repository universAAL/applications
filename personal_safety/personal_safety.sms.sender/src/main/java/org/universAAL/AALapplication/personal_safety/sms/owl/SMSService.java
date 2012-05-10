/*
	Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es
	Instituto Tecnologico de Aplicaciones de Comunicacion 
	Avanzadas - Grupo Tecnologias para la Salud y el 
	Bienestar (TSB)

	Copyright 2008-2010 TSB-Tecnolog�as para la Salud y el Bienestar,
	http://www.tsbtecnologias.es
	TSB Soluciones Tecnol�gicas para la Salud y el Bienestar S.A

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

// Paquete que contendra la clase
package org.universAAL.AALapplication.personal_safety.sms.owl;

// Importamos las clases necesarias
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

/**
 * ServiceProvided del servicio EnvioSMSService (ontologia)
 * 
 * @author mllorente
 * @author Angel Martinez-Cavero
 * @author amedrano
 *
 */
public class SMSService extends EnvioSMSService {

	/** Nombre de la ontologia definida para este servicio */
	public final static String MY_URI = SMSOntology.NAMESPACE + "ServicioSMS";

	/** Nombre del servicio prestado en este caso */
	public final static String SERVICE_MANDA_SMS = SMSOntology.NAMESPACE
			+ "mandaSMS";

	/** Entradas esperadas para poder prestar el servicio */
	public static final String INPUT_TEXTO_MENSAJE = SMSOntology.NAMESPACE
			+ "TextoSMS";
	public static final String INPUT_TEXTO_TELEFONO = SMSOntology.NAMESPACE
			+ "numeroTelefono";

	/** Donde se almacenan los perfiles */
	public static final ServiceProfile[] profiles = new ServiceProfile[1];

	// Bloque de ejecucion: static

	// Restricciones (son las mismas que las de la ontologia de la que hereda)
	static {
		// Entradas necesarias para poder prestar el servicio (texto)
		ProcessInput EntradaTextoMensaje = new ProcessInput(INPUT_TEXTO_MENSAJE);
		EntradaTextoMensaje.setParameterType(TypeMapper
				.getDatatypeURI(String.class));
		EntradaTextoMensaje.setCardinality(1, 1);

		// Restriccion para la entrada
		MergedRestriction textomensajeRestr = MergedRestriction.getFixedValueRestriction(
				SMSService.PROP_MANDA_TEXTO, EntradaTextoMensaje
				.asVariableReference());
		PropertyPath StringTextoPath = new PropertyPath(null, true,
				new String[] { EnvioSMSService.PROP_MANDA_TEXTO });

		// Entradas necesarias para poder prestar el servicio (numero de
		// telefono)
		ProcessInput EntradaTextoTelefono = new ProcessInput(
				INPUT_TEXTO_TELEFONO);
		EntradaTextoTelefono.setParameterType(TypeMapper
				.getDatatypeURI(String.class));
		// Envio a multiples detinatarios (max = 0)
		EntradaTextoTelefono.setCardinality(0, 1);
		// Restriccion de la entrada
		MergedRestriction textonumeroRestr = MergedRestriction.getFixedValueRestriction(
				SMSService.PROP_TIENE_NUMERO, EntradaTextoTelefono
				.asVariableReference());
		PropertyPath StringNumeroPath = new PropertyPath(null, true,
				new String[] { EnvioSMSService.PROP_TIENE_NUMERO });
		// Instanciaci�n del servicio para crear el perfil
		SMSService mandaSMS = new SMSService(SERVICE_MANDA_SMS);
		// Obtengo el perfil de mi servicio
		profiles[0] = mandaSMS.getProfile();
		// A�ado la primera entrada con su restriccion y su path
		profiles[0].addInput(EntradaTextoMensaje);
		mandaSMS.addInstanceLevelRestriction(textomensajeRestr, StringTextoPath
				.getThePath());
		// A�ado la segunda entrada con su restriccion y su path
		profiles[0].addInput(EntradaTextoTelefono);
		mandaSMS.addInstanceLevelRestriction(textonumeroRestr, StringNumeroPath
				.getThePath());
	}

	// Constructor
	public SMSService(String uri) {
		super(uri);
	}

	static public ServiceProfile [] getProfiles(){
		return profiles;
	}
}
