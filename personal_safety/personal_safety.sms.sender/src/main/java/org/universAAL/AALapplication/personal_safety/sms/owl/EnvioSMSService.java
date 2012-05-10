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


import org.universAAL.middleware.service.owl.Service;

/**
 * Ontologia definida para el servicio SMS (manda texto, tiene un numero (destino)
 * 
 * @author mllorente
 * @author Angel Martinez-Cavero
 * @author amedrano
 *
 */
public class EnvioSMSService extends Service {


	public static final String MY_URI =  SMSOntology.NAMESPACE + "EnvioSMSService";

    /** Propiedades */
    public static final String PROP_MANDA_TEXTO =  SMSOntology.NAMESPACE + "manda";
    public static final String PROP_TIENE_NUMERO =  SMSOntology.NAMESPACE + "numero";



    // Constructor
    public EnvioSMSService() {
    	super();
    }

    /** Pasando la URI como parametro de entrada */
    public EnvioSMSService(String uri) {
    	super(uri);
    }

    /* (non-Javadoc)
	 * @see org.universAAL.middleware.service.owl.Service#getPropSerializationType(java.lang.String)
	 */
	public int getPropSerializationType(String propURI) {
		if (propURI.equals(PROP_MANDA_TEXTO) 
				|| propURI.equals(PROP_TIENE_NUMERO)) {
			return PROP_SERIALIZATION_FULL;
		}
		return super.getPropSerializationType(propURI);
	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
	 */
	public String getClassURI() {
		return MY_URI;
	}

	/* (non-Javadoc)
	 * @see org.universAAL.middleware.owl.ManagedIndividual#isWellFormed()
	 */
	public boolean isWellFormed() {
		return true;
	}
}
