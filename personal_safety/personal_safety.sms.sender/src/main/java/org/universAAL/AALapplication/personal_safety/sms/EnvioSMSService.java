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
	
 * Ontologia definida para el servicio SMS (manda texto, tiene un numero (destino)
 * 
 * @author mllorente
 * @author Angel Martinez-Cavero
 *
 */

// Paquete que contendra la clase
package org.universAAL.AALapplication.personal_safety.sms;

// Importamos las clases necesarias
import java.util.Hashtable;

import org.universAAL.middleware.owl.Restriction;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;

// Clase principal
public class EnvioSMSService extends Service {

    // Atributos
    public static final String SMS_NAMESPACE;
    public static final String MY_URI;

    /** Propiedades */
    public static final String PROP_MANDA_TEXTO;
    public static final String PROP_TIENE_NUMERO;

    /** Tabla de restricciones */
    private static Hashtable envioSMSServiceRestrictions = new Hashtable(1);

    /** Bloque static (ejecucion al principio) */
    static {
	// URIs de las propiedades
	SMS_NAMESPACE = "http://ontology.ciami.es/EnvioSMSService.owl#";
	;
	MY_URI = EnvioSMSService.SMS_NAMESPACE + "EnvioSMSService";
	PROP_MANDA_TEXTO = EnvioSMSService.SMS_NAMESPACE + "manda";
	PROP_TIENE_NUMERO = EnvioSMSService.SMS_NAMESPACE + "numero";
	// Registro de servicio
	register(EnvioSMSService.class);
	// Restricciones para cada una de las propiedades
	// Propiedad MANDA_TEXTO
	addRestriction(Restriction.getAllValuesRestriction(PROP_MANDA_TEXTO,
		TypeMapper.getDatatypeURI(String.class)),
		new String[] { PROP_MANDA_TEXTO }, envioSMSServiceRestrictions);
	// Propiedad TIENE_NUMERO
	addRestriction(Restriction.getAllValuesRestriction(PROP_TIENE_NUMERO,
		TypeMapper.getDatatypeURI(String.class)),
		new String[] { PROP_TIENE_NUMERO }, envioSMSServiceRestrictions);

    }

    // Constructor
    /** Por defecto */
    public EnvioSMSService() {
	super();
    }

    /** Pasando la URI como parametro de entrada */
    public EnvioSMSService(String uri) {
	super(uri);
    }

    // Metodos
    /** */
    protected Hashtable getClassLevelRestrictions() {
	return envioSMSServiceRestrictions;
    }

    /** */
    public int getPropSerializationType(String propURI) {
	return 0;
    }

    /** */
    public static Restriction getClassRestrictionsOnProperty(String propURI) {
	if (propURI == null)
	    return null;
	Object r = envioSMSServiceRestrictions.get(propURI);
	if (r instanceof Restriction)
	    return (Restriction) r;
	return Service.getClassRestrictionsOnProperty(propURI);
    }

    /** */
    public static String[] getStandardPropertyURIs() {
	String[] inherited = Service.getStandardPropertyURIs();
	// OJO -> length + numero de propiedades que añadimos (en este caso 2)
	String[] toReturn = new String[inherited.length + 2];
	int i = 0;
	while (i < inherited.length) {
	    toReturn[i] = inherited[i];
	    i++;
	}
	// Devuelve el valor de i (corresponde con la primera propiedad) y suma
	// 1 (para apuntar a la siguiente
	// propiedad)
	toReturn[i++] = PROP_MANDA_TEXTO;
	// No suma nada porque esta propiedad es la ultima
	toReturn[i] = PROP_TIENE_NUMERO;
	// Retorno
	return toReturn;
    }

    /** */
    public static String getRDFSComment() {
	return "A simple stub for a service for sending SMS";
    }

    /** */
    public static String getRDFSLabel() {
	return "EnvioSMSService";
    }

}
