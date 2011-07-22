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

 * ServiceProvided del servicio EnvioSMSService (ontologia)
 * 
 * @author mllorente
 * @author Angel Martinez-Cavero
 * 
 *
 */

// Paquete que contendra la clase
package org.universAAL.AALapplication.personal_safety.sms;

// Importamos las clases necesarias
import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;

// Clase principal (hereda de la ontologia definida para el servicio)
public class ServicioSMS extends EnvioSMSService {
    // Atributos
    // URIs

    /** Ruta del servidor de ontologias (de momento no hace nada) */
    public final static String SERVER_NAMESPACE = "http://ontology.ciami.es/ServicioSMS.owl#";

    /** Nombre de la ontologia definida para este servicio */
    public final static String MY_URI = SERVER_NAMESPACE + "ServicioSMS";

    /** Nombre del servicio prestado en este caso */
    public final static String SERVICE_MANDA_SMS = SERVER_NAMESPACE
	    + "mandaSMS";

    /** Entradas esperadas para poder prestar el servicio */
    public static final String INPUT_TEXTO_MENSAJE = SERVER_NAMESPACE
	    + "TextoSMS";
    public static final String INPUT_TEXTO_TELEFONO = SERVER_NAMESPACE
	    + "numeroTelefono";

    /** Donde se almacenan los perfiles */
    public static final ServiceProfile[] profiles = new ServiceProfile[1];

    /** Tabla donde se almacenan las restricciones */
    private static Hashtable serverRestrictions = new Hashtable();

    // Bloque de ejecucion: static

    // Restricciones (son las mismas que las de la ontologia de la que hereda)
    static {
	// Registro del servicio
	register(ServicioSMS.class);
	// Propiedad MANDA_TEXTO
	addRestriction(Restriction.getAllValuesRestriction(
		EnvioSMSService.PROP_MANDA_TEXTO, TypeMapper
			.getDatatypeURI(String.class)), // Vamos a añadir una
							// restricción sobre la
							// propiedad de
							// reproducir el fichero
							// de Audio, que es la
							// que vamos a utilizar.
		new String[] { PROP_MANDA_TEXTO }, serverRestrictions);
	// Propiedad MANDA_TIENE_NUMERO
	addRestriction(Restriction.getAllValuesRestriction(
		EnvioSMSService.PROP_TIENE_NUMERO, TypeMapper
			.getDatatypeURI(String.class)), // Vamos a añadir una
							// restricción sobre la
							// propiedad de
							// reproducir el fichero
							// de Audio, que es la
							// que vamos a utilizar.
		new String[] { PROP_TIENE_NUMERO }, serverRestrictions);

	// Entradas necesarias para poder prestar el servicio (texto)
	ProcessInput EntradaTextoMensaje = new ProcessInput(INPUT_TEXTO_MENSAJE);
	EntradaTextoMensaje.setParameterType(TypeMapper
		.getDatatypeURI(String.class));
	EntradaTextoMensaje.setCardinality(1, 1);

	// Restriccion para la entrada
	Restriction textomensajeRestr = Restriction.getFixedValueRestriction(
		ServicioSMS.PROP_MANDA_TEXTO, EntradaTextoMensaje
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
	Restriction textonumeroRestr = Restriction.getFixedValueRestriction(
		ServicioSMS.PROP_TIENE_NUMERO, EntradaTextoTelefono
			.asVariableReference());
	PropertyPath StringNumeroPath = new PropertyPath(null, true,
		new String[] { EnvioSMSService.PROP_TIENE_NUMERO });
	// Instanciación del servicio para crear el perfil
	ServicioSMS mandaSMS = new ServicioSMS(SERVICE_MANDA_SMS);
	// Obtengo el perfil de mi servicio
	profiles[0] = mandaSMS.getProfile();
	// Añado la primera entrada con su restriccion y su path
	profiles[0].addInput(EntradaTextoMensaje);
	mandaSMS.addInstanceLevelRestriction(textomensajeRestr, StringTextoPath
		.getThePath());
	// Añado la segunda entrada con su restriccion y su path
	profiles[0].addInput(EntradaTextoTelefono);
	mandaSMS.addInstanceLevelRestriction(textonumeroRestr, StringNumeroPath
		.getThePath());
    }

    // Constructor
    private ServicioSMS(String uri) {
	super(uri);
    }

    static public ServiceProfile [] getProfiles(){
    	return profiles;
    }
}
