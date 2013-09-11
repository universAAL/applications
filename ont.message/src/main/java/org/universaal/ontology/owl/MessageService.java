/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es 
 Universidad Politécnica de Madrid
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

package org.universaal.ontology.owl;

import org.universAAL.middleware.service.owl.Service;

/**
 * This class describes the service given by the message ontology. Given a
 * sender, a receiver and a content, we generate a message.
 * 
 * @author mdelafuente
 * 
 */
public class MessageService extends Service {

    // NAMESPACE & PROPERTIES
    public static final String MY_URI = MessageOntology.NAMESPACE
	    + "MessageService";

    public static final String PROP_SENDER = MessageOntology.NAMESPACE
	    + "sender";
    public static final String PROP_RECEIVER = MessageOntology.NAMESPACE
	    + "receiver";
    public static final String PROP_CONTENT = MessageOntology.NAMESPACE
	    + "messageContent";

    public static final String PROP_GENERATES_MESSAGE = MessageOntology.NAMESPACE
	    + "generatesMessage";

    // CONSTRUCTORS
    public MessageService() {
	super();
    }

    public MessageService(String uri) {
	super(uri);
    }

    public String getClassURI() {
	return MY_URI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.universAAL.middleware.owl.ManagedIndividual#getPropSerializationType
     * (java.lang.String)
     */
    public int getPropSerializationType(String propURI) {
	return PROP_SENDER.equals(propURI) || PROP_RECEIVER.equals(propURI)
		|| PROP_CONTENT.equals(propURI)
		|| PROP_GENERATES_MESSAGE.equals(propURI) ? PROP_SERIALIZATION_FULL
		: super.getPropSerializationType(propURI);
    }

    public boolean isWellFormed() {
	return true;
    }
}