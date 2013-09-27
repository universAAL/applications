/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
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
 *****************************************************************************************/

package org.universAAL.ontology.Safety;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * @author dimokas
 * 
 */
public class Notification extends ManagedIndividual {

    public static final String MY_URI;
    public static final String PROP_MESSAGE;
    public static final String PROP_DATE;
    public static final String PROP_TIME;

    static {
	MY_URI = SafetyOntology.NAMESPACE + "Notification";
	PROP_MESSAGE = SafetyOntology.NAMESPACE + "NotificationMessage";
	PROP_DATE = SafetyOntology.NAMESPACE + "NotificationDate";
	PROP_TIME = SafetyOntology.NAMESPACE + "NotificationTime";
    }

    public static String[] getStandardPropertyURIs() {
    	return new String[] { PROP_MESSAGE, PROP_DATE, PROP_TIME };
    }

    /*
     * public static String getRDFSComment() { return
     * "The class of all food items"; }
     * 
     * public static String getRDFSLabel() { return "FoodItem"; }
     */
    public String getClassURI() {
	return MY_URI;
    }

    public Notification() {
	super();
    }

    public Notification(String uri) {
	super(uri);
    }

    public Notification(String uri, String message, String date) {
	super(uri);
	if (message == null)
	    throw new IllegalArgumentException();

		props.put(PROP_MESSAGE, new String(message));
		props.put(PROP_DATE, new String(date));
    }

    public Notification(String uri, String message, String date, String time) {
		super(uri);
		if (message == null)
		    throw new IllegalArgumentException();

		props.put(PROP_MESSAGE, new String(message));
		props.put(PROP_DATE, new String(date));
		props.put(PROP_TIME, new String(time));
    }

    public String getMessage() {
	return (String) props.get(PROP_MESSAGE);
    }

    public void setMessage(String message) {
	if (message != null)
	    props.put(PROP_MESSAGE, message);
    }

    public String getDate() {
	return (String) props.get(PROP_DATE);
    }

    public void setDate(String date) {
	if (date != null)
	    props.put(PROP_DATE, date);
    }

    public String getTime() {
	return (String) props.get(PROP_TIME);
    }

    public void setTime(String time) {
	if (time != null)
	    props.put(PROP_TIME, time);
    }
    
    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_FULL;
	// return (PROP_NAME.equals(propURI)) ? PROP_SERIALIZATION_REDUCED
	// : PROP_SERIALIZATION_FULL;
    }

    public boolean isWellFormed() {
	return true;
	// return props.containsKey(PROP_NAME)
	// && props.containsKey(PROP_QUANTITY);
    }

}
