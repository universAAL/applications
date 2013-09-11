/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es - Universidad Politécnica de Madrid
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

import org.universaal.ontology.health.owl.MotivationalStatusType;

public class MotivationalMessage extends Message {

    // NAMESPACE & PROPERTIES

    public static final String MY_URI = MessageOntology.NAMESPACE
	    + "MotivationalMessage";

    public static final String PROP_DISEASE = MessageOntology.NAMESPACE
	    + "disease";
    public static final String PROP_TREATMENT_TYPE = MessageOntology.NAMESPACE
	    + "treatment_type";
    public static final String PROP_MOTIVATIONAL_STATUS = MessageOntology.NAMESPACE
	    + "motivationalStatus";
    public static final String PROP_MESSAGE_TYPE = MessageOntology.NAMESPACE
	    + "messageType";
    public static final String PROP_MESSAGE_SUBTYPE = MessageOntology.NAMESPACE
	    + "messageSubtype";

    public static final String PROP_NAME = MessageOntology.NAMESPACE + "name"; // tengo

    // que
    // quitar
    // esta
    // propiedad?

    // CONSTRUCTORS

    public MotivationalMessage() {
	super();
    }

    public MotivationalMessage(String uri) {
	super(uri);
    }

    public MotivationalMessage(String diseaseURI, String treatmentTypeURI,
	    MotivationalStatusType motStatus,
	    MotivationalMessageClassification mtype,
	    MotivationalMessageSubclassification msubtype, Object content) {
	this.setDiseaseURI(diseaseURI);
	this.setTreatmentTypeURI(treatmentTypeURI);
	this.setMotivationalStatus(motStatus);
	this.setMMessageType(mtype);
	this.setMMessageSubtype(msubtype);
	this.setContent(content);
    }

    public MotivationalMessage(String name, String diseaseURI,
	    String treatmentTypeURI, MotivationalStatusType motStatus,
	    MotivationalMessageClassification mtype,
	    MotivationalMessageSubclassification msubtype, Object content) {
	this.setMotivationalPlainMessageName(name);
	this.setDiseaseURI(diseaseURI);
	this.setTreatmentTypeURI(treatmentTypeURI);
	this.setMotivationalStatus(motStatus);
	this.setMMessageType(mtype);
	this.setMMessageSubtype(msubtype);
	this.setContent(content);
    }

    public String getClassURI() {
	return MY_URI;
    }

    public int getPropSerializationType(String arg0) {
	return PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true && props.containsKey(PROP_DISEASE)
		&& props.containsKey(PROP_MESSAGE_TYPE)
		&& props.containsKey(PROP_MESSAGE_SUBTYPE)
		&& props.containsKey(PROP_MOTIVATIONAL_STATUS)
		&& props.containsKey(PROP_TREATMENT_TYPE)

	;
    }

    // GETTERS & SETTERS
    public String getMotivationalStatus() {
	return (String) props.get(PROP_MOTIVATIONAL_STATUS);
    }

    public void setMotivationalStatus(MotivationalStatusType mst) {
	if (mst != null)
	    props.put(PROP_MOTIVATIONAL_STATUS, mst);
    }

    public MotivationalMessageClassification getMMessageType() {
	return (MotivationalMessageClassification) props.get(PROP_MESSAGE_TYPE);
    }

    public void setMMessageType(MotivationalMessageClassification mmc) {
	if (mmc != null)
	    props.put(PROP_MESSAGE_TYPE, mmc);
    }

    public MotivationalMessageSubclassification getMMessageSubtype() {
	return (MotivationalMessageSubclassification) props
		.get(PROP_MESSAGE_SUBTYPE);
    }

    public void setMMessageSubtype(MotivationalMessageSubclassification mmsc) {
	if (mmsc != null)
	    props.put(PROP_MESSAGE_SUBTYPE, mmsc);
    }

    public String getTreatmentTypeURI() {
	return (String) props.get(PROP_TREATMENT_TYPE);
    }

    public void setTreatmentTypeURI(String ttype) {
	if (ttype != null)
	    props.put(PROP_TREATMENT_TYPE, ttype);
    }

    public String getDiseaseURI() {
	return (String) props.get(PROP_DISEASE);
    }

    public void setDiseaseURI(String illness) {
	if (illness != null)
	    props.put(PROP_DISEASE, illness);
    }

    public String getMotivationalPlainMessageName() {
	return (String) props.get(PROP_NAME);
    }

    public void setMotivationalPlainMessageName(String name) {
	if (name != null)
	    props.put(PROP_NAME, name);
    }
    // OTHER METHODS

}
